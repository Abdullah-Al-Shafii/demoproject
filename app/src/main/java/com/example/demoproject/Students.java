package com.example.demoproject;

public class Students {
    String id,name,hscRegistrationNumber,examCentre,hscGPA;
    double hscTotalMarks;

    Students()
    {

    }

    public Students(String id, String name, String hscRegistrationNumber, String examCentre, String hscGPA, double hscTotalMarks) {
        this.id = id;
        this.name = name;
        this.hscRegistrationNumber = hscRegistrationNumber;
        this.examCentre = examCentre;
        this.hscGPA = hscGPA;
        this.hscTotalMarks = hscTotalMarks;
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

    public String getHscRegistrationNumber() {
        return hscRegistrationNumber;
    }

    public void setHscRegistrationNumber(String hscRegistrationNumber) {
        this.hscRegistrationNumber = hscRegistrationNumber;
    }

    public String getExamCentre() {
        return examCentre;
    }

    public void setExamCentre(String examCentre) {
        this.examCentre = examCentre;
    }

    public String getHscGPA() {
        return hscGPA;
    }

    public void setHscGPA(String hscGPA) {
        this.hscGPA = hscGPA;
    }

    public double getHscTotalMarks() {
        return hscTotalMarks;
    }

    public void setHscTotalMarks(double hscTotalMarks) {
        this.hscTotalMarks = hscTotalMarks;
    }
}
