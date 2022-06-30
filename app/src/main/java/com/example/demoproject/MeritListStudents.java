package com.example.demoproject;

public class MeritListStudents {
    String id,merit;
    MeritListStudents()
    {

    }
    public MeritListStudents(String id, String merit) {
        this.id = id;
        this.merit = merit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerit() {
        return merit;
    }

    public void setMerit(String merit) {
        this.merit = merit;
    }
}
