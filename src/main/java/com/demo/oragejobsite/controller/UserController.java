package com.demo.oragejobsite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.security.Keys;
import com.demo.oragejobsite.dao.RefreshTokenRepository;
import com.demo.oragejobsite.dao.UserDao;
import com.demo.oragejobsite.entity.Employer;
import com.demo.oragejobsite.entity.RefreshToken;
import com.demo.oragejobsite.entity.User;
import com.demo.oragejobsite.util.JwtTokenUtil;
import com.demo.oragejobsite.util.TokenProvider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {

	@Autowired
	private UserDao ud;
	
	

	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	

	
	// Generate a secure key for HS256 algorithm
	private final byte[] refreshTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 private final TokenProvider tokenProvider; // Inject your TokenProvider here
	    private final RefreshTokenRepository refreshTokenRepository;
	    @Autowired
	    public UserController(TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
	        this.tokenProvider = tokenProvider;
	        this.refreshTokenRepository = refreshTokenRepository;
	    }
	private static  String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add the password bytes to the digest
            md.update(password.getBytes());

            // Get the hashed password bytes
            byte[] hashedPasswordBytes = md.digest();

            // Convert the bytes to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
	
	
	//Insert User And Also Check if the user already exist in the database 
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/insertusermail")
	public ResponseEntity<Object> insertusermail(@RequestBody User c1) {
	    try {
	    	// Generate a random UUID as a string
	        String randomString = UUID.randomUUID().toString();

	        // Remove hyphens and special symbols
	        randomString = randomString.replaceAll("-", "");

	        // Set the randomString as the Juid for the ApplyJob
	        c1.setUid(randomString);
            String pass=c1.getUserPassword();
            pass=hashPassword(pass);
            
            c1.setUserPassword(pass);
	   
	        // Set the initial value of 'verify' to false
	        c1.setVerified(false);

	        // Check if the userName already exists in the database
	        User existingUser = ud.findByUserName(c1.getUserName());
	        if (existingUser != null) {
	            // User with the same userName already exists, return a conflict response
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this username already exists");
	        } else {
	            // User with the userName doesn't exist, save the data
	            ud.save(c1);
	            System.out.println("User Created Successfully");
	            return ResponseEntity.status(HttpStatus.CREATED).body(c1);
	        }
	    } catch (DataAccessException e) {
	        // Handle database-related exceptions (e.g., unique constraint violation)
	        // Log the exception message for debugging
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred");
	    } catch (Exception e) {
	        // Handle any other exceptions that may occur
	        // Log the exception message for debugging
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
	    }
	}





	//fetch user data
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/fetchuser")
	public ResponseEntity<List<User>> fetchuser() {
	    try {
	        List<User> users = ud.findAll();
	        if (users.isEmpty()) {
	            // Return a NOT FOUND response if no users are found
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        } else {
	            // Return a OK response with the list of users
	            return ResponseEntity.ok(users);
	        }
	    } catch (Exception e) {
	        // Handle any exceptions that may occur, and return a INTERNAL SERVER ERROR response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	//Update User data
	@CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        try {
        	 String uid = updatedUser.getUid();
             // Log the received UID for debugging
             System.out.println("Received UID: " + uid);

             // Check if a user with the provided UID exists
             Optional<User> existingUserOptional = ud.findById(uid);
             System.out.println("Existing User Optional: " + existingUserOptional);
            if (existingUserOptional.isPresent()) {
                // If it exists, update the existing record
                User existingUser = existingUserOptional.get();
                // Update the 'profile' field if it's not null in the request
                if (updatedUser.getProfile() != null) {
                    existingUser.setProfile(updatedUser.getProfile());
                }
                // Update the fields you want to change, but only if they are not null in the request
                if (updatedUser.getUserName() != null) {
                    existingUser.setUserName(updatedUser.getUserName());
                }
                if (updatedUser.getUserFirstName() != null) {
                    existingUser.setUserFirstName(updatedUser.getUserFirstName());
                }
                if (updatedUser.getUserLastName() != null) {
                    existingUser.setUserLastName(updatedUser.getUserLastName());
                }
                if (updatedUser.getCompanyuser() != null) {
                    existingUser.setCompanyuser(updatedUser.getCompanyuser());
                }
                if (updatedUser.getUserphone() != null) {
                    existingUser.setUserphone(updatedUser.getUserphone());
                }
                if (updatedUser.getUsercountry() != null) {
                    existingUser.setUsercountry(updatedUser.getUsercountry());
                }
                if (updatedUser.getUserstate() != null) {
                    existingUser.setUserstate(updatedUser.getUserstate());
                }
                if (updatedUser.getUsercity() != null) {
                    existingUser.setUsercity(updatedUser.getUsercity());
                }
                if (updatedUser.getWebsiteuser() != null) {
                    existingUser.setWebsiteuser(updatedUser.getWebsiteuser());
                }
                
                // Update the 'verified' field if it's not null in the request
                if (updatedUser.isVerified() != false) {
                    existingUser.setVerified(updatedUser.isVerified());
                }

                // Save the updated record
                User updatedRecord = ud.save(existingUser);

                // Log the updated data for verification
                System.out.println("Updated Record: " + updatedRecord.toString());

                return ResponseEntity.ok(updatedRecord);
            } else {
                // If the user with the provided UID doesn't exist, return a NOT FOUND response
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with UID " + updatedUser.getUid() + " not found.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur, and return an INTERNAL SERVER ERROR response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }
	
	
	
	
	

@CrossOrigin(origins = "http://localhost:4200")
@PostMapping("/logincheck")
public ResponseEntity<?> logincheck(@RequestBody User c12, HttpServletResponse response) {
    try {
        String checkemail = c12.getUserName();
        String checkpass = c12.getUserPassword();
        checkpass = hashPassword(checkpass); // Hash the password (implement the hashPassword method)

        User checkmail = checkMailUser(checkemail, checkpass); // Check user credentials

        if (checkmail != null) {
            // Create and set cookies here
            Cookie userCookie = new Cookie("user", checkemail);
            userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
            userCookie.setPath("/"); // Set the path to match your frontend
            response.addCookie(userCookie);

            // Generate and set a refresh token
            // Generate and set a refresh token
            String refreshToken = tokenProvider.generateRefreshToken(checkemail);
            // Save the refresh token in the database
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setTokenId(refreshToken);
            refreshTokenEntity.setUsername(checkmail.getUid());
            // Set the expiry date using TokenProvider
            refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
            refreshTokenRepository.save(refreshTokenEntity);

            // Generate and set an access token using TokenProvider
            String accessToken = tokenProvider.generateAccessToken(checkmail.getUid());

            // Create a response object that includes the access token and refresh token
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", accessToken);
            responseBody.put("refreshToken", refreshToken);
            responseBody.put("uid", checkmail.getUid());

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    } catch (Exception e) {
        // Handle any exceptions that may occur
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
    }
}


@CrossOrigin(origins = "http://localhost:4200")
@PostMapping("/refreshToken")
public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
    try {
        // Validate the provided refresh token and extract the username (e.g., "uid")
        String username = tokenProvider.validateAndExtractUsernameFromRefreshToken(refreshToken);

        if (username != null) {
            // Generate a new access token
            String newAccessToken = tokenProvider.generateAccessToken(username);

            // Return the new access token
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("accessToken", newAccessToken);

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/logincheckgmail")
	public ResponseEntity<?> logincheckgmail(@RequestBody User c12, HttpServletResponse response) {
	    try {
	        String checkemail = c12.getUserName();

	        // Check if the email exists in your database
	        boolean emailExists = checkIfEmailExists(checkemail);

	        if (emailExists) {
	            // Fetch the user's UID by checking the email
	            Optional<User> userOptional = Optional.ofNullable(ud.findByUserName(checkemail));
	            if (userOptional.isPresent()) {
	                User user = userOptional.get();

	                // Create and set cookies here
	                Cookie userCookie = new Cookie("user", checkemail);
	                userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	                userCookie.setPath("/"); // Set the path to match your frontend
	                response.addCookie(userCookie);

	                // Generate and set a refresh token
	                String refreshToken = tokenProvider.generateRefreshToken(checkemail);
	                // Save the refresh token in the database
	                RefreshToken refreshTokenEntity = new RefreshToken();
	                refreshTokenEntity.setTokenId(refreshToken);
	                refreshTokenEntity.setUsername(user.getUid());
	                // Set the expiry date using TokenProvider
	                refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
	                refreshTokenRepository.save(refreshTokenEntity);

	                // Generate and set an access token using TokenProvider
	                String accessToken = tokenProvider.generateAccessToken(user.getUid());

	                // Create a response object that includes the access token and refresh token
	                Map<String, Object> responseBody = new HashMap<>();
	                responseBody.put("accessToken", accessToken);
	                responseBody.put("refreshToken", refreshToken);
	                responseBody.put("uid", user.getUid());

	                return ResponseEntity.ok(responseBody);
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch UID");
	            }
	        } else {
	            // Email doesn't exist, return an unauthorized response
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	public boolean checkIfEmailExists(String email) {
	    // Use the UserRepository to check if the email exists
	    Optional<User> userOptional = Optional.ofNullable(ud.findByUserName(email));
	    return userOptional.isPresent(); // If the email exists, this will be true
	}


    // ... Other methods ...

    private User checkMailUser(String checkemail, String checkpass) {
        // TODO Auto-generated method stub
        List<User> allMails = ud.findAll();
        for (User u1 : allMails) {
        	 System.out.println("Checking the password"+checkpass);
        	if (u1.getUserName().equals(checkemail) && u1.getUserPassword().equals(checkpass) && u1.isVerified()) {
        		  System.out.println("Checking the password"+u1.getUserPassword());
                return u1; // User found, return user details
            }
        }
        return null; // User not found
    }
	
    
    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/verifyUser")
    public ResponseEntity<?> verifyUser(@RequestBody Map<String, String> request) {
        try {
            String userName = request.get("userName");

            // Find the user by userName
            User user = ud.findByUserName(userName);

            if (user != null) {
                // Set the 'verified' field to true
                user.setVerified(true);

                // Save the updated user record
                ud.save(user);
                Map<String, Object> response = new HashMap<>();
	            response.put("status", "User verified successfully");
	            response.put("employer", user);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with userName " + userName + " not found.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }



	
    @CrossOrigin(origins="http://localhost:4200")
    @DeleteMapping("/deleteUser/{uid}")
    public ResponseEntity<Object> deleteUserByUid(@PathVariable String uid) {
        try {
            // Check if a user with the provided UID exists
            Optional<User> existingUserOptional = ud.findById(uid);

            if (existingUserOptional.isPresent()) {
                // If it exists, delete the user
                ud.delete(existingUserOptional.get());

                return ResponseEntity.status(HttpStatus.OK).body(true);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with UID " + uid + " not found.");
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }
    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/resetPassword")
    public ResponseEntity<Boolean> resetPassword(@RequestBody Map<String, String> request) {

        try {

            String userName = request.get("userName");

            String oldPassword = request.get("oldPassword");

            String newPassword = request.get("newPassword");



            // Find the user by userName

            User user = ud.findByUserName(userName);



            if (user != null) {

                // Verify the old password

                String hashedOldPassword = hashPassword(oldPassword);

                if (hashedOldPassword.equals(user.getUserPassword())) {

                    // Hash the new password

                    String hashedNewPassword = hashPassword(newPassword);

                    user.setUserPassword(hashedNewPassword);



                    // Save the updated user record with the new password

                    ud.save(user);



                    return ResponseEntity.ok(true);

                } else {

                    // Old password does not match

                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);

                }

            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);

            }

        } catch (Exception e) {

            // Handle any exceptions that may occur

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);

        }

    }
    
    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/resetPasswordUser")
    public ResponseEntity<Boolean> resetPasswordUser(@RequestBody Map<String, String> request) {
        try {
            String userName = request.get("userName");
            String newPassword = request.get("newPassword");

            // Find the user by userName
            User user = ud.findByUserName(userName);

            if (user != null && user.isVerified()) {
                // Hash the new password
                String hashedNewPassword = hashPassword(newPassword);
                user.setUserPassword(hashedNewPassword);

                // Save the updated user record with the new password
                ud.save(user);

                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
        } catch (Exception e) {
            // Handle any exceptions that may occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    
    @CrossOrigin(origins="http://localhost:4200")
    @GetMapping("/checkuser")
    public ResponseEntity<Object> checkUser(@RequestParam String userName) {
        try {
            User user = ud.findByUserName(userName);
            if (user != null) {
                // Return user details as JSON
                return ResponseEntity.ok(user);
            } else {
                // User does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"User with userName " + userName + " does not exist.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"An error occurred while processing your request.\"}");
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByTokenId(refreshToken);
        if (token != null) {
            refreshTokenRepository.delete(token);
            return ResponseEntity.ok("Logout successful");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh token not found");
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/createOrGetUser")
    public ResponseEntity<Map<String, Object>> createOrGetUser(@RequestBody String userName, HttpServletResponse response) {
        try {
            // Remove any invalid characters (e.g., CR) from the userName
            userName = userName.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

            // Check if the user with the provided email (userName) exists
            User existingUser = ud.findByUserName(userName);

            if (existingUser != null) {
                // User exists, return user data and access token
                String accessToken = jwtTokenUtil.generateToken(existingUser.getUserName());

                // Generate and set a refresh token
                String refreshToken = tokenProvider.generateRefreshToken(userName);

                // Save the refresh token in the database
                RefreshToken refreshTokenEntity = new RefreshToken();
                refreshTokenEntity.setTokenId(refreshToken);
                refreshTokenEntity.setUsername(existingUser.getUid());
                // Set the expiry date using TokenProvider
                refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
                refreshTokenRepository.save(refreshTokenEntity);

                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("userName", userName);
                responseBody.put("accessToken", accessToken);
                responseBody.put("refreshToken", refreshToken);
                responseBody.put("uid", existingUser.getUid());

                // Set a user cookie (if needed)
                Cookie userCookie = new Cookie("user", userName);
                userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
                userCookie.setPath("/"); // Set the path to match your frontend
                response.addCookie(userCookie);

                return ResponseEntity.ok(responseBody);
            } else {
                // User doesn't exist, create a new user
                User newUser = createUser(userName, true);

             // Generate and set a refresh token
                String refreshToken = tokenProvider.generateRefreshToken(userName);


                // Save the refresh token in the database
                RefreshToken refreshTokenEntity = new RefreshToken();
                refreshTokenEntity.setTokenId(refreshToken);
                refreshTokenEntity.setUsername(newUser.getUid());
                // Set the expiry date using TokenProvider
                refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
                refreshTokenRepository.save(refreshTokenEntity);

                // Generate an access token
                String accessToken = jwtTokenUtil.generateToken(userName);

                // Create a response object that includes the access token and refresh token
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("userName", userName);
                responseBody.put("accessToken", accessToken);
                responseBody.put("refreshToken", refreshToken);
                responseBody.put("uid", newUser.getUid());

                // Set a user cookie (if needed)
                Cookie userCookie = new Cookie("user", userName);
                userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
                userCookie.setPath("/"); // Set the path to match your frontend
                response.addCookie(userCookie);

                return ResponseEntity.ok(responseBody);
            }
        } catch (Exception e) {
            // Handle any errors and return an appropriate error response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "User creation and login failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    public User createUser(String userName, boolean verified) {
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setVerified(verified);

        // Generate a UUID for the new user
        String uuid = UUID.randomUUID().toString();
        // Remove hyphens and special symbols
        uuid = uuid.replaceAll("-", "");
        newUser.setUid(uuid);
        
        // Perform the necessary operations to save the user to your database.
        // You might need to use JPA, Hibernate, or your database's API here.

        // After saving the user, you should return the saved user entity.
        return ud.save(newUser);
    }


}
