package com.example.demoproject;

public class EligibleListStudents {
    String sl,id,name;
    EligibleListStudents()
    {

    }

    public EligibleListStudents(String sl, String id, String name) {
        this.sl = sl;
        this.id = id;
        this.name = name;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
