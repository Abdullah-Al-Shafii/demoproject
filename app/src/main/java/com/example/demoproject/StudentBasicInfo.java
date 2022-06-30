package com.example.demoproject;

public class StudentBasicInfo {
    String email;
    String hscRollNumber,hscRegistrationNumber,hscBoardName,hscPassingYear;
    String sscRollNumber,sscRegistrationNumber,sscBoardName,sscPassingYear;
    String name,fatherName,motherName,permanentAdress,presentAddress;
    String gender,mobileNumber,quota;
    String dateofBirth;
    String imageUri,signatureUri;
    String guardianName,relationwithgurdian,gurdiancontactnumber;
    String group,id,hscGPA,sscGPA;
    String paymentStatus,paymentNumber,transactionID;
    String examCentre,birthCertificateNumber,bloodGroup;
    String meritPosition;
    String hscphysicsMarks,hscchemistryMarks,hscmathematicsMarks,hscEnglishMarks;
    double hscTotalMarks,negativehscTotalMarks;
    double hscMarksGPA;
    String eligibleStatus;

    StudentBasicInfo()
    {

    }

    public StudentBasicInfo(String email, String hscRollNumber, String hscRegistrationNumber, String hscBoardName, String hscPassingYear, String sscRollNumber, String sscRegistrationNumber, String sscBoardName, String sscPassingYear, String name, String fatherName, String motherName, String permanentAdress, String presentAddress, String gender, String mobileNumber, String quota, String dateofBirth, String imageUri, String signatureUri, String guardianName, String relationwithgurdian, String gurdiancontactnumber, String group, String id, String hscGPA, String sscGPA, String paymentStatus, String paymentNumber, String transactionID, String examCentre, String birthCertificateNumber, String bloodGroup, String meritPosition, String hscphysicsMarks, String hscchemistryMarks, String hscmathematicsMarks, String hscEnglishMarks, double hscTotalMarks, double negativehscTotalMarks, double hscMarksGPA, String eligibleStatus) {
        this.email = email;
        this.hscRollNumber = hscRollNumber;
        this.hscRegistrationNumber = hscRegistrationNumber;
        this.hscBoardName = hscBoardName;
        this.hscPassingYear = hscPassingYear;
        this.sscRollNumber = sscRollNumber;
        this.sscRegistrationNumber = sscRegistrationNumber;
        this.sscBoardName = sscBoardName;
        this.sscPassingYear = sscPassingYear;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.permanentAdress = permanentAdress;
        this.presentAddress = presentAddress;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.quota = quota;
        this.dateofBirth = dateofBirth;
        this.imageUri = imageUri;
        this.signatureUri = signatureUri;
        this.guardianName = guardianName;
        this.relationwithgurdian = relationwithgurdian;
        this.gurdiancontactnumber = gurdiancontactnumber;
        this.group = group;
        this.id = id;
        this.hscGPA = hscGPA;
        this.sscGPA = sscGPA;
        this.paymentStatus = paymentStatus;
        this.paymentNumber = paymentNumber;
        this.transactionID = transactionID;
        this.examCentre = examCentre;
        this.birthCertificateNumber = birthCertificateNumber;
        this.bloodGroup = bloodGroup;
        this.meritPosition = meritPosition;
        this.hscphysicsMarks = hscphysicsMarks;
        this.hscchemistryMarks = hscchemistryMarks;
        this.hscmathematicsMarks = hscmathematicsMarks;
        this.hscEnglishMarks = hscEnglishMarks;
        this.hscTotalMarks = hscTotalMarks;
        this.negativehscTotalMarks = negativehscTotalMarks;
        this.hscMarksGPA = hscMarksGPA;
        this.eligibleStatus = eligibleStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHscRollNumber() {
        return hscRollNumber;
    }

    public void setHscRollNumber(String hscRollNumber) {
        this.hscRollNumber = hscRollNumber;
    }

    public String getHscRegistrationNumber() {
        return hscRegistrationNumber;
    }

    public void setHscRegistrationNumber(String hscRegistrationNumber) {
        this.hscRegistrationNumber = hscRegistrationNumber;
    }

    public String getHscBoardName() {
        return hscBoardName;
    }

    public void setHscBoardName(String hscBoardName) {
        this.hscBoardName = hscBoardName;
    }

    public String getHscPassingYear() {
        return hscPassingYear;
    }

    public void setHscPassingYear(String hscPassingYear) {
        this.hscPassingYear = hscPassingYear;
    }

    public String getSscRollNumber() {
        return sscRollNumber;
    }

    public void setSscRollNumber(String sscRollNumber) {
        this.sscRollNumber = sscRollNumber;
    }

    public String getSscRegistrationNumber() {
        return sscRegistrationNumber;
    }

    public void setSscRegistrationNumber(String sscRegistrationNumber) {
        this.sscRegistrationNumber = sscRegistrationNumber;
    }

    public String getSscBoardName() {
        return sscBoardName;
    }

    public void setSscBoardName(String sscBoardName) {
        this.sscBoardName = sscBoardName;
    }

    public String getSscPassingYear() {
        return sscPassingYear;
    }

    public void setSscPassingYear(String sscPassingYear) {
        this.sscPassingYear = sscPassingYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getPermanentAdress() {
        return permanentAdress;
    }

    public void setPermanentAdress(String permanentAdress) {
        this.permanentAdress = permanentAdress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getSignatureUri() {
        return signatureUri;
    }

    public void setSignatureUri(String signatureUri) {
        this.signatureUri = signatureUri;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getRelationwithgurdian() {
        return relationwithgurdian;
    }

    public void setRelationwithgurdian(String relationwithgurdian) {
        this.relationwithgurdian = relationwithgurdian;
    }

    public String getGurdiancontactnumber() {
        return gurdiancontactnumber;
    }

    public void setGurdiancontactnumber(String gurdiancontactnumber) {
        this.gurdiancontactnumber = gurdiancontactnumber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHscGPA() {
        return hscGPA;
    }

    public void setHscGPA(String hscGPA) {
        this.hscGPA = hscGPA;
    }

    public String getSscGPA() {
        return sscGPA;
    }

    public void setSscGPA(String sscGPA) {
        this.sscGPA = sscGPA;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getExamCentre() {
        return examCentre;
    }

    public void setExamCentre(String examCentre) {
        this.examCentre = examCentre;
    }

    public String getBirthCertificateNumber() {
        return birthCertificateNumber;
    }

    public void setBirthCertificateNumber(String birthCertificateNumber) {
        this.birthCertificateNumber = birthCertificateNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMeritPosition() {
        return meritPosition;
    }

    public void setMeritPosition(String meritPosition) {
        this.meritPosition = meritPosition;
    }

    public String getHscphysicsMarks() {
        return hscphysicsMarks;
    }

    public void setHscphysicsMarks(String hscphysicsMarks) {
        this.hscphysicsMarks = hscphysicsMarks;
    }

    public String getHscchemistryMarks() {
        return hscchemistryMarks;
    }

    public void setHscchemistryMarks(String hscchemistryMarks) {
        this.hscchemistryMarks = hscchemistryMarks;
    }

    public String getHscmathematicsMarks() {
        return hscmathematicsMarks;
    }

    public void setHscmathematicsMarks(String hscmathematicsMarks) {
        this.hscmathematicsMarks = hscmathematicsMarks;
    }

    public String getHscEnglishMarks() {
        return hscEnglishMarks;
    }

    public void setHscEnglishMarks(String hscEnglishMarks) {
        this.hscEnglishMarks = hscEnglishMarks;
    }

    public double getHscTotalMarks() {
        return hscTotalMarks;
    }

    public void setHscTotalMarks(double hscTotalMarks) {
        this.hscTotalMarks = hscTotalMarks;
    }

    public double getNegativehscTotalMarks() {
        return negativehscTotalMarks;
    }

    public void setNegativehscTotalMarks(double negativehscTotalMarks) {
        this.negativehscTotalMarks = negativehscTotalMarks;
    }

    public double getHscMarksGPA() {
        return hscMarksGPA;
    }

    public void setHscMarksGPA(double hscMarksGPA) {
        this.hscMarksGPA = hscMarksGPA;
    }

    public String getEligibleStatus() {
        return eligibleStatus;
    }

    public void setEligibleStatus(String eligibleStatus) {
        this.eligibleStatus = eligibleStatus;
    }
}



