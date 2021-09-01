package com.example.trivia.model;

public class Question {

    String ques;
    Boolean correctAns;


    public Question()
    {}

    public Question(String ques, Boolean correctAns) {
        this.ques=ques;
        this.correctAns=correctAns;
    }

    public String getques() {
        return ques;
    }

    public void setques(String ques) {
        this.ques = ques;
    }

    public Boolean getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(Boolean correctAns) {
        this.correctAns = correctAns;
    }
}
