package com.chatty;



public class Chat {
    private int image;
    private String friendName,message,friendId;

    public Chat(int image, String friendName, String message, String friendId) {
        this.image = image;
        this.friendName = friendName;
        this.message = message;
        this.friendId = friendId;
    }

    /* public Chat(String friendName, String friendId) {
            this.friendName = friendName;
            this.friendId = friendId;
        }
    *//*
    public Chat(int image, String friendName, String message) {
        this.image = image;
        this.friendName = friendName;
        this.message = message;
    }
*/
    public int getImage() {
        return image;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getMessage() {
        return message;
    }

    public String getFriendId() {
        return friendId;
    }
}
