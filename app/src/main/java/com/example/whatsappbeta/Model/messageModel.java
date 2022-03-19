package com.example.whatsappbeta.Model;

public class messageModel {
    String message,uid;
    Long timeStamp;

    public messageModel(){

    }
    public messageModel(String message, String uid, Long timeStamp) {
        this.message = message;
        this.uid = uid;
        this.timeStamp = timeStamp;
    }

    public messageModel(String message, String uid) {
        this.message = message;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
