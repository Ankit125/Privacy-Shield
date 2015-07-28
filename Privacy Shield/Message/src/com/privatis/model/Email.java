package com.privatis.model;

public class Email {

    private String email;
    private String name;
    private String notes;
    private String time;
    private String flag;

    private String CreatedDate;
    private String EmailBody;
    private String EmailFrom;
    private String EmailSubject;
    private String EmailTo;
    private String IsRead;
    private String MemberId;
    private String MessageEmailId;
    private String RecordCount;

//    public String getNotes() {
//        return notes;
//    }
//
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
//
//    public String getFlag() {
//        return flag;
//    }
//
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }


    public String MessageId;
    public String Sender_Email_Id;
    public String Subject;
    public String Body;
    public String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getSender_Email_Id() {
        return Sender_Email_Id;
    }

    public void setSender_Email_Id(String sender_Email_Id) {
        Sender_Email_Id = sender_Email_Id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }
}
