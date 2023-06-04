package com.example.expenda;

public class ModelCategory {
    String id,category;
    long timestamp;
    String Uid;

    public ModelCategory() {

    }

    public String getUid() {
        return Uid;
    }

    public ModelCategory(String id, String category, long timestamp) {
        this.id = id;
        this.category = category;
        this.timestamp = timestamp;
        this.Uid = Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl(){return Uid;}

    public void setUrl(String Url){this.Uid=Uid;}
}
