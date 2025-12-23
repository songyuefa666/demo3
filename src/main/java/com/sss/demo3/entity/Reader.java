package com.sss.demo3.entity;

import java.util.Date;

public class Reader {
    private Integer readerId;
    private String readerBarcode;
    private String name;
    private String gender;
    private String unit;
    private String phone;
    private Integer typeId;
    private String typeName; // For display
    private Date registerDate;
    private Integer status; // 0: invalid, 1: valid

    // Transient fields for display logic (populated from ReaderType)
    private Integer maxBorrowNum;
    private Integer maxBorrowDays;
    private Double finePerDay;

    public Integer getReaderId() { return readerId; }
    // ... setters/getters
    public void setReaderId(Integer readerId) { this.readerId = readerId; }
    public String getReaderBarcode() { return readerBarcode; }
    public void setReaderBarcode(String readerBarcode) { this.readerBarcode = readerBarcode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public Date getRegisterDate() { return registerDate; }
    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Integer getMaxBorrowNum() { return maxBorrowNum; }
    public void setMaxBorrowNum(Integer maxBorrowNum) { this.maxBorrowNum = maxBorrowNum; }
    public Integer getMaxBorrowDays() { return maxBorrowDays; }
    public void setMaxBorrowDays(Integer maxBorrowDays) { this.maxBorrowDays = maxBorrowDays; }
    public Double getFinePerDay() { return finePerDay; }
    public void setFinePerDay(Double finePerDay) { this.finePerDay = finePerDay; }
}