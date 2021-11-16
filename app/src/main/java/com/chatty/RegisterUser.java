package com.chatty;

import java.io.Serializable;

public class RegisterUser implements Serializable{
    private String Name;
    private String EmailID;
    private String MemberId;

    public RegisterUser(String name, String emailID, String MemberId) {
        Name = name;
        EmailID = emailID;
        this.MemberId = MemberId;
    }

    public String getName() {
        return Name;
    }

    public String getEmailID() {
        return EmailID;
    }

    public String getMemberId() {
        return MemberId;
    }
}
