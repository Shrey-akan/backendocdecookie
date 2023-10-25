package com.demo.oragejobsite.entity;


public class UserAnswer {
    private Long questionId;
    private String selectedAnswer;
    private String userResponse;
    public UserAnswer() {
        super();
    }

   

    public UserAnswer(Long questionId, String selectedAnswer, String userResponse) {
		super();
		this.questionId = questionId;
		this.selectedAnswer = selectedAnswer;
		this.userResponse = userResponse;
	}



	public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }



	public String getUserResponse() {
		return userResponse;
	}



	public void setUserResponse(String userResponse) {
		this.userResponse = userResponse;
	}


}

