package sample;

import java.util.Date;

public class Member {
    private Integer refID;
    private String phoneNumber;
    private String name;
    private String aadhaarNumber;
    private Date DOB;
    private Integer age;
    private String dose1Status;
    private String dose2Status;
    private int dose1CentreID;
    private int dose2CentreID;
    private String dose1date;
    private String dose2date;
    private int dose1Slot;
    private int dose2Slot;
    private String dose1Name;
    private String dose2Name;
    private int gender;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDose1Status() {
        return dose1Status;
    }

    public String getDose2Status() {
        return dose2Status;
    }

    public String getDose1date() {
        return dose1date;
    }

    public void setDose1date(String dose1date) {
        this.dose1date = dose1date;
    }

    public String getDose2date() {
        return dose2date;
    }

    public void setDose2date(String dose2date) {
        this.dose2date = dose2date;
    }

    public int getDose1Slot() {
        return dose1Slot;
    }

    public void setDose1Slot(int dose1Slot) {
        this.dose1Slot = dose1Slot;
    }

    public int getDose2Slot() {
        return dose2Slot;
    }

    public void setDose2Slot(int dose2Slot) {
        this.dose2Slot = dose2Slot;
    }

    public String getDose1Name() {
        return dose1Name;
    }

    public void setDose1Name(String dose1Name) {
        this.dose1Name = dose1Name;
    }

    public String getDose2Name() {
        return dose2Name;
    }

    public void setDose2Name(String dose2Name) {
        this.dose2Name = dose2Name;
    }



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

    public String isDose1Status() {
        return dose1Status;
    }

    public void setDose1Status(String dose1Status) {
        this.dose1Status = dose1Status;
    }

    public String isDose2Status() { return dose2Status; }

    public void setDose2Status(String dose2Status) {
        this.dose2Status = dose2Status;
    }

    public int getDose1CentreID() {
        return dose1CentreID;
    }

    public void setDose1CentreID(int dose1CentreID) {
        this.dose1CentreID = dose1CentreID;
    }

    public int getDose2CentreID() {
        return dose2CentreID;
    }

    public void setDose2CentreID(int dose2CentreID) {
        this.dose2CentreID = dose2CentreID;
    }
}
