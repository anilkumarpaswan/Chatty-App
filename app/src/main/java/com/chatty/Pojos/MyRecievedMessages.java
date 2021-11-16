package com.chatty.Pojos;

import java.io.Serializable;

/**
 * Created by Akhil on 18-May-2018.
 */

public class MyRecievedMessages implements Serializable {

        private String Id;
        private String SenderId;
        private String ReceiverId;
        private String Subject;
        private String MessageBody;
        private String ParentMessageId;
        private String CreatedDate;
        private String IsActive;
        private String SenderName;
        private String ReceiverName;

    public String getId() {
        return Id;
    }

    public MyRecievedMessages(String id, String senderId, String receiverId, String subject, String messageBody, String parentMessageId, String createdDate, String isActive, String senderName, String receiverName) {
        Id = id;
        SenderId = senderId;
        ReceiverId = receiverId;
        Subject = subject;
        MessageBody = messageBody;
        ParentMessageId = parentMessageId;
        CreatedDate = createdDate;
        IsActive = isActive;
        SenderName = senderName;
        ReceiverName = receiverName;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getMessageBody() {
        return MessageBody;
    }

    public void setMessageBody(String messageBody) {
        MessageBody = messageBody;
    }

    public String getParentMessageId() {
        return ParentMessageId;
    }

    public void setParentMessageId(String parentMessageId) {
        ParentMessageId = parentMessageId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }
}
