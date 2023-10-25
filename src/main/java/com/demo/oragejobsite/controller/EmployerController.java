package com.demo.oragejobsite.controller;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.demo.oragejobsite.dao.EmployerDao;
import com.demo.oragejobsite.dao.RefreshTokenRepository;
import com.demo.oragejobsite.entity.Employer;
import com.demo.oragejobsite.entity.RefreshToken;
import com.demo.oragejobsite.entity.User;
import com.demo.oragejobsite.util.JwtTokenUtil;
import com.demo.oragejobsite.util.TokenProvider;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class EmployerController {
	@Autowired
	private EmployerDao ed;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

	
	// Generate a secure key for HS256 algorithm
	private final byte[] refreshTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
	 private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	 private final TokenProvider tokenProvider; // Inject your TokenProvider here
	    private final RefreshTokenRepository refreshTokenRepository;
	    
	    @Autowired
	    public EmployerController(TokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
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
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/insertEmployer")
	public ResponseEntity<Object> insertEmployer(@RequestBody Employer emp) {
	    try {
	        // Generate a random alphanumeric 10-character employer ID
	        // Generate a random UUID as a string
	        String randomString = UUID.randomUUID().toString();

	        // Remove hyphens and special symbols
	        randomString = randomString.replaceAll("-", "");
	        // Set the generated ID as the employer ID
	        emp.setEmpid(randomString);
	        String pass=emp.getEmppass();
            pass=hashPassword(pass);
            
            emp.setEmppass(pass);

	        // Check if the employer name already exists in the database
	        Employer existingEmployer = ed.findByEmpmailid(emp.getEmpmailid());

	        if (existingEmployer != null) {
	            // Employer with the same name already exists, return a conflict response
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employer with this name already exists");
	        } else {
	            // Employer with the name doesn't exist, save the data
	            ed.save(emp);
	            System.out.println("Employer Created Successfully");
	            
	            // Return the created employer object in the response body
	            return ResponseEntity.status(HttpStatus.CREATED).body(emp);
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

	
	
	//Fetch Employer Details
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/fetchemployer")
	public ResponseEntity<List<Employer>> fetchemployer() {
	    try {
	        List<Employer> users = ed.findAll();
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
	
	
	
	//update employer
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/updateEmployee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employer updatedEmployer) {
	    try {
	    	 String empid = updatedEmployer.getEmpid();
             // Log the received UID for debugging
             System.out.println("Received UID: " + empid);
	        // Check if an employer with the provided empid exists
	        Optional<Employer> existingEmployerOptional = ed.findById(updatedEmployer.getEmpid());

	        if (existingEmployerOptional.isPresent()) {
	            // If it exists, update the existing record
	            Employer existingEmployer = existingEmployerOptional.get();

	            // Update the fields you want to change, but only if they are not null in the request
	            if (updatedEmployer.getEmpfname() != null) {
	                existingEmployer.setEmpfname(updatedEmployer.getEmpfname());
	            }
	            if (updatedEmployer.getEmplname() != null) {
	                existingEmployer.setEmplname(updatedEmployer.getEmplname());
	            }
	            if (updatedEmployer.getEmpcompany() != null) {
	                existingEmployer.setEmpcompany(updatedEmployer.getEmpcompany());
	            }
	            if (updatedEmployer.getEmpphone() != null) {
	                existingEmployer.setEmpphone(updatedEmployer.getEmpphone());
	            }
	            if (updatedEmployer.getEmpcountry() != null) {
	                existingEmployer.setEmpcountry(updatedEmployer.getEmpcountry());
	            }
	            if (updatedEmployer.getEmpstate() != null) {
	                existingEmployer.setEmpstate(updatedEmployer.getEmpstate());
	            }
	            if (updatedEmployer.getEmpcity() != null) {
	                existingEmployer.setEmpcity(updatedEmployer.getEmpcity());
	            }
	            if (updatedEmployer.getDescriptionemp() != null) {
	                existingEmployer.setDescriptionemp(updatedEmployer.getDescriptionemp());
	            }
	            
	            // Update the 'verified' field if it's not null in the request
                if (updatedEmployer.isVerifiedemp() != false) {
                	existingEmployer.setVerifiedemp(updatedEmployer.isVerifiedemp());
                }

	            // Save the updated record
	            Employer updatedRecord = ed.save(existingEmployer);

	            // Log the updated data for verification
	            System.out.println("Updated Record: " + updatedRecord.toString());

	            return ResponseEntity.ok(updatedRecord);
	        } else {
	            // If it doesn't exist, create a new record
	            Employer newEmployer = ed.save(updatedEmployer);
	            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployer);
	        }
	    } catch (Exception e) {
	        // Handle any exceptions that may occur, and return an INTERNAL SERVER ERROR response
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
	    }
	}

	
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/employerLoginCheck")
	public ResponseEntity<?> employerLoginCheck(@RequestBody Employer employer, HttpServletResponse response) {
	    try {
	        String checkEmail = employer.getEmpmailid(); // Assuming the employer's email field is "empmailid."

	        // Check if the email exists in your employer database
	        boolean emailExists = checkIfEmailExists(checkEmail);

	        if (emailExists) {
	            // Fetch the employer's data by checking the email
	            Optional<Employer> employerOptional = Optional.ofNullable(ed.findByEmpmailid(checkEmail));
	            if (employerOptional.isPresent()) {
	                Employer foundEmployer = employerOptional.get();

	                // Create and set cookies here
	                Cookie employerCookie = new Cookie("emp", checkEmail);
	                employerCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	                employerCookie.setPath("/"); // Set the path to match your frontend
	                response.addCookie(employerCookie);

	                // Generate an access token for the employer
	                String accessToken = jwtTokenUtil.generateToken(checkEmail);
	                // Generate and set a refresh token
	                String refreshToken = tokenProvider.generateRefreshToken(checkEmail);
	                // Save the refresh token in the database
	                RefreshToken refreshTokenEntity = new RefreshToken();
	                refreshTokenEntity.setTokenId(refreshToken);
	                refreshTokenEntity.setUsername(foundEmployer.getEmpid()); // Assuming you have an ID field in your Employer entity
	                // Set the expiry date using TokenProvider
	                refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
	                refreshTokenRepository.save(refreshTokenEntity);

	                // Create a response object that includes the access token, refresh token, and employer data
	                Map<String, Object> responseBody = new HashMap<>();
	                responseBody.put("accessToken", accessToken);
	                responseBody.put("refreshToken", refreshToken);
	                responseBody.put("empid", foundEmployer.getEmpid());

	                return ResponseEntity.ok(responseBody);
	            } else {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch employer data");
	            }
	        } else {
	            // Email doesn't exist, return an unauthorized response
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}


	public boolean checkIfEmailExists(String email) {
	    // Use the EmployerDao (or your equivalent) to check if the email exists
	    Employer existingEmployer = ed.findByEmpmailid(email);
	    return existingEmployer != null; // If the email exists, this will be true
	}

	
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/logincheckemp")
	public ResponseEntity<?> logincheckemp(@RequestBody Employer e12, HttpServletResponse response) {
	    try {
	        String checkemail = e12.getEmpmailid();
	        String checkpass = e12.getEmppass();
	        checkpass = hashPassword(checkpass);
	        System.out.println(checkemail + " " + checkpass);

	        Employer checkmail = checkMailUser(checkemail, checkpass);
	        if (checkmail != null) {
	            // Create and set cookies here
	            Cookie employerCookie = new Cookie("emp", checkmail.toString());
	            // Set the domain to match your frontend (e.g., localhost)
//	          
	            employerCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	            employerCookie.setPath("/");
	            response.addCookie(employerCookie);
	         
	            
	            // Generate an access token for the employer
                String accessToken = jwtTokenUtil.generateToken(checkemail);
                // Generate and set a refresh token
                String refreshToken = tokenProvider.generateRefreshToken(checkemail);
                // Save the refresh token in the database
                RefreshToken refreshTokenEntity = new RefreshToken();
                refreshTokenEntity.setTokenId(refreshToken);
                refreshTokenEntity.setUsername(checkmail.getEmpid()); // Assuming you have an ID field in your Employer entity
                // Set the expiry date using TokenProvider
                refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
                refreshTokenRepository.save(refreshTokenEntity);

                // Create a response object that includes the access token, refresh token, and employer data
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("accessToken", accessToken);
                responseBody.put("refreshToken", refreshToken);
                responseBody.put("empid", checkmail.getEmpid());


                return ResponseEntity.ok(responseBody);
	        }

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	    } catch (Exception e) {
	        // Handle any exceptions that may occur
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // You can customize the error response as needed
	    }
	}


	private Employer checkMailUser(String checkemail, String checkpass) {
	    System.out.println("hello");
	    List<Employer> allMails = ed.findAll();
	    for (Employer u1 : allMails) {
	        System.out.println(checkemail);
	        System.out.println("Checking the password"+checkpass);
	        if (u1.getEmpmailid() != null && u1.getEmpmailid().equals(checkemail) && u1.getEmppass() != null && u1.getEmppass().equals(checkpass) && u1.isVerifiedemp()) {
	            System.out.println("inside");
	            System.out.println("Checking the password"+u1.getEmppass());
	            return u1; // Email and password match
	        }
	    }
	    return null; // Email and password do not match
	}


	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/verifyEmployer")
	public ResponseEntity<Object> verifyEmployer(@RequestBody Map<String, String> request) {
	    try {
	        String empmailid = request.get("empmailid");

	        // Find the employer by empmailid
	        Employer employer = ed.findByEmpmailid(empmailid);

	        if (employer != null) {
	            // Set the 'verifiedemp' field to true
	            employer.setVerifiedemp(true);

	            // Save the updated employer record
	            ed.save(employer);

	            // Create a response JSON object
	            Map<String, Object> response = new HashMap<>();
	            response.put("status", "Employer verified successfully");
	            response.put("employer", employer);

	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employer with empmailid " + empmailid + " not found.");
	        }
	    } catch (Exception e) {
	        // Handle any exceptions that may occur
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
	    }
	}

	@CrossOrigin(origins="http://localhost:4200")
	@DeleteMapping("/deleteEmployer/{empid}")
	public ResponseEntity<?> deleteEmployer(@PathVariable String empid) {
	    try {
	        // Check if an employer with the provided empid exists
	        Optional<Employer> existingEmployerOptional = ed.findById(empid);

	        if (existingEmployerOptional.isPresent()) {
	            // If it exists, delete the employer
	            ed.delete(existingEmployerOptional.get());

	            return ResponseEntity.status(HttpStatus.OK).body(true);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employer with empid " + empid + " not found.");
	        }
	    } catch (Exception e) {
	        // Handle any exceptions that may occur
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
	    }
	}

	

	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/resetPasswordEmp")
	public ResponseEntity<Boolean> resetPasswordEmp(@RequestBody Map<String, String> request) {

	    try {

	        String empmailid = request.get("empmailid");

	        String oldPassword = request.get("oldPassword");

	        String newPassword = request.get("newPassword");



	        // Find the employer by empmailid

	        Employer employer = ed.findByEmpmailid(empmailid);



	        if (employer != null) {

	            // Check if the provided old password matches the current password

	            if (employer.getEmppass().equals(hashPassword(oldPassword))) {

	                // Hash the new password

	                String hashedPassword = hashPassword(newPassword);

	                employer.setEmppass(hashedPassword);



	                // Save the updated employer record with the new password

	                ed.save(employer);



	                return ResponseEntity.status(HttpStatus.OK).body(true);

	            } else {

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
	@PostMapping("/resetPasswordEmpverify")
	public ResponseEntity<Boolean> resetPasswordEmpverify(@RequestBody Map<String, String> request) {
	    try {
	        String empmailid = request.get("empmailid");
	        String newPassword = request.get("newPassword");

	        // Find the employer by empmailid
	        Employer employer = ed.findByEmpmailid(empmailid);

	        if (employer != null && employer.isVerifiedemp()) {
	            // Hash the new password
	            String hashedPassword = hashPassword(newPassword);
	            employer.setEmppass(hashedPassword);

	            // Save the updated employer record with the new password
	            ed.save(employer);

	            return ResponseEntity.status(HttpStatus.OK).body(true);
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
    @GetMapping("/checkEmployer")
    public ResponseEntity<Object> checkEmployer(@RequestParam String empmailid) {
        try {
            Employer employer = ed.findByEmpmailid(empmailid);
            if (employer != null) {
                // Return user details as JSON
                return ResponseEntity.ok(employer);
            } else {
                // User does not exist
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"User with userName " + empmailid + " does not exist.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"message\": \"An error occurred while processing your request.\"}");
        }
    }

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/logoutEmployer")
	public ResponseEntity<String> logoutEmployer(@RequestParam String refreshToken) {
		
		  RefreshToken token = refreshTokenRepository.findByTokenId(refreshToken);
	        if (token != null) {
	            refreshTokenRepository.delete(token);
	            return ResponseEntity.ok("Logout successful");
	        }
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Refresh token not found");
	}


	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/createOrGetEmployer")
	public ResponseEntity<Map<String, Object>> createOrGetEmployer(@RequestBody String empmailid, HttpServletResponse response) {
	    try {
	        // Check if the employer with the provided email (empmailid) exists
	        Employer existingEmployer = ed.findByEmpmailid(empmailid);

	        if (existingEmployer != null) {
	            // Employer exists, return employer data and access token
	            String accessToken = jwtTokenUtil.generateToken(empmailid);

	            // Generate and set a refresh token
	            String refreshToken = tokenProvider.generateRefreshToken(empmailid);

	            // Save the refresh token in the database
	            RefreshToken refreshTokenEntity = new RefreshToken();
	            refreshTokenEntity.setTokenId(refreshToken);
	            refreshTokenEntity.setUsername(existingEmployer.getEmpid());
	            // Set the expiry date using TokenProvider
	            refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
	            refreshTokenRepository.save(refreshTokenEntity);

	            Map<String, Object> responseBody = new HashMap<>();
	            responseBody.put("empmailid", empmailid);
	            responseBody.put("accessToken", accessToken);
	            responseBody.put("refreshToken", refreshToken);
	            responseBody.put("empid", existingEmployer.getEmpid());

	            // Set a user cookie (if needed)
	            Cookie userCookie = new Cookie("emp", empmailid);
	            userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	            userCookie.setPath("/"); // Set the path to match your frontend
	            response.addCookie(userCookie);

	            return ResponseEntity.ok(responseBody);
	        } else {
	            // Employer doesn't exist, create a new employer
	            Employer newEmployer = createEmployer(empmailid, true);

	            // Set an employer cookie
	            Cookie employerCookie = new Cookie("emp", empmailid);
	            employerCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	            employerCookie.setPath("/"); // Set the path to match your frontend
	            response.addCookie(employerCookie);

	            // Generate an access token for the employer
	            String accessToken = jwtTokenUtil.generateToken(empmailid);

	            // Generate and set a refresh token
	            String refreshToken = tokenProvider.generateRefreshToken(empmailid);

	            // Save the refresh token in the database
	            RefreshToken refreshTokenEntity = new RefreshToken();
	            refreshTokenEntity.setTokenId(refreshToken);
	            refreshTokenEntity.setUsername(newEmployer.getEmpid());
	            // Set the expiry date using TokenProvider
	            refreshTokenEntity.setExpiryDate(tokenProvider.getExpirationDateFromRefreshToken(refreshToken));
	            refreshTokenRepository.save(refreshTokenEntity);

	            // Create a response object that includes the access token and refresh token
	            Map<String, Object> responseBody = new HashMap<>();
	            responseBody.put("empmailid", empmailid);
	            responseBody.put("accessToken", accessToken);
	            responseBody.put("refreshToken", refreshToken);
	            responseBody.put("empid", newEmployer.getEmpid());

	            // Set a user cookie (if needed)
	            Cookie userCookie = new Cookie("emp", empmailid);
	            userCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
	            userCookie.setPath("/"); // Set the path to match your frontend
	            response.addCookie(userCookie);

	            return ResponseEntity.ok(responseBody);
	        }
	    } catch (Exception e) {
	        // Handle any errors and return an appropriate error response
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Employer creation and login failed");
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}


	    // Create a new Employer (similar to createUser)
	    public Employer createEmployer(String empmailid, boolean verified) {
	        Employer newEmployer = new Employer();
	        newEmployer.setEmpmailid(empmailid);
	        newEmployer.setVerifiedemp(verified);

	        // Generate an employer ID, similar to your User ID generation
	        // Generate a UUID for the new user
	        String uuid = UUID.randomUUID().toString();
	        // Remove hyphens and special symbols
	        uuid = uuid.replaceAll("-", "");
	        newEmployer.setEmpid(uuid);
	        // Perform the necessary operations to save the employer to your database.
	        // You might need to use JPA, Hibernate, or your database's API here.

	        // After saving the employer, you should return the saved employer entity.
	        return ed.save(newEmployer);
	    }
}
