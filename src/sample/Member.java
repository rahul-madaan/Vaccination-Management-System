package sample;

import java.util.Date;

public class Member {
    private Integer refID;
    private String phoneNumber;
    private String name;
    private String aadhaarNumber;
    private Date DOB;
    private Integer age;
    private boolean dose1Status;
    private boolean dose2Status;
    private String dose1CentreID;
    private String dose2CentreID;

    public Integer getRefID() {
        return refID;
    }

    public void setRefID(Integer refID) {
        this.refID = refID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isDose1Status() {
        return dose1Status;
    }

    public void setDose1Status(boolean dose1Status) {
        this.dose1Status = dose1Status;
    }

    public boolean isDose2Status() {
        return dose2Status;
    }

    public void setDose2Status(boolean dose2Status) {
        this.dose2Status = dose2Status;
    }

    public String getDose1CentreID() {
        return dose1CentreID;
    }

    public void setDose1CentreID(String dose1CentreID) {
        this.dose1CentreID = dose1CentreID;
    }

    public String getDose2CentreID() {
        return dose2CentreID;
    }

    public void setDose2CentreID(String dose2CentreID) {
        this.dose2CentreID = dose2CentreID;
    }
}
