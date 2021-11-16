package com.chatty;

/**
 * Created by Shreya on 23-Apr-18.
 */

public class SingleRow {
    private int image;
    private String username;
    private String email;

    public SingleRow(int image, String username, String email) {
        this.image = image;
        this.username = username;
        this.email = email;
    }

    public int getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;

    }
}

