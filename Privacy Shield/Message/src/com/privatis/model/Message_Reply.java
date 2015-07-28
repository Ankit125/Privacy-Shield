package com.privatis.model;

public class Message_Reply {


    public String SmsId;
    public String CreatedDate;
    public String MemberId;
    public String PhoneSmsFrom;
    public String SmsMessage;

    public String PhoneSmsTo;

    public String getSmsMessage() {
        return SmsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        SmsMessage = smsMessage;
    }

    public String getPhoneSmsTo() {
        return PhoneSmsTo;
    }

    public void setPhoneSmsTo(String phoneSmsTo) {
        PhoneSmsTo = phoneSmsTo;
    }

    public String getPhoneSmsFrom() {
        return PhoneSmsFrom;
    }

    public void setPhoneSmsFrom(String phoneSmsFrom) {
        PhoneSmsFrom = phoneSmsFrom;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getSmsId() {
        return SmsId;
    }

    public void setSmsId(String smsId) {
        SmsId = smsId;
    }


}
