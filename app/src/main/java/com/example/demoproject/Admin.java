package com.example.demoproject;

public class Admin {
    String isResultPublish,email;
    Admin()
    {

    }

    public Admin(String isResultPublish, String email) {
        this.isResultPublish = isResultPublish;
        this.email = email;
    }

    public String getIsResultPublish() {
        return isResultPublish;
    }

    public void setIsResultPublish(String isResultPublish) {
        this.isResultPublish = isResultPublish;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
