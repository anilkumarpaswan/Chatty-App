package com.chatty;

public class AddFriend {
    //private String memberName;
    private String FriendName;
    private String EmailID;
    private String memberId;
    private String ApplicationId;


    public AddFriend(String friendName, String emailId,String memberId,String applicationId) {
        FriendName = friendName;
        EmailID = emailId;
        this.memberId=memberId;
       this.ApplicationId=applicationId;
    }

    public String getFriendName() {
        return FriendName;
    }

    public String getEmailID() {
        return EmailID;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getApplicationId()    {  return ApplicationId;  }

}
