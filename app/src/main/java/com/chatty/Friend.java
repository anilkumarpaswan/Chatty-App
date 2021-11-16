package com.chatty;

/**
 * Created by dell on 4/30/2018.
 */

public class Friend {
    private String MemberId;
    private String FriendName;
    private String FriendId;


    public Friend(String memberId, String friendId, String friendName) {
        MemberId = memberId;
        FriendId = friendId;
        FriendName = friendName;
    }

    public String getMemberId() {
        return MemberId;
    }

    public String getFriendId() {
        return FriendId;
    }

    public String getFriendName() {
        return FriendName;
    }
}
