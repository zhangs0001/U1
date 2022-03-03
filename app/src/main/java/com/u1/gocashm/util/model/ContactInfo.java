package com.u1.gocashm.util.model;

import java.io.Serializable;

public class ContactInfo implements Serializable {

    //    @SerializedName("id")
    private String contactFullname;
    private String contactTelephone;
    private String contactRelation;
    private String contactRelationCode;
    private String lastTimeContacted;
    private String timesContacted;
    private String contactLastUpdatedTimestamp;
    private String contactCreatedAt;

    public String getContactFullname() {
        return contactFullname;
    }

    public void setContactFullname(String contactFullname) {
        this.contactFullname = contactFullname;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public String getLastTimeContacted() {
        return lastTimeContacted;
    }

    public void setLastTimeContacted(String lastTimeContacted) {
        this.lastTimeContacted = lastTimeContacted;
    }

    public String getTimesContacted() {
        return timesContacted;
    }

    public void setTimesContacted(String timesContacted) {
        this.timesContacted = timesContacted;
    }

    public String getContactRelationCode() {
        return contactRelationCode;
    }

    public void setContactRelationCode(String contactRelationCode) {
        this.contactRelationCode = contactRelationCode;
    }

    public String getContactLastUpdatedTimestamp() {
        return contactLastUpdatedTimestamp;
    }

    public void setContactLastUpdatedTimestamp(String contactLastUpdatedTimestamp) {
        this.contactLastUpdatedTimestamp = contactLastUpdatedTimestamp;
    }

    public String getContactCreatedAt() {
        return contactCreatedAt;
    }

    public void setContactCreatedAt(String contactCreatedAt) {
        this.contactCreatedAt = contactCreatedAt;
    }
}
