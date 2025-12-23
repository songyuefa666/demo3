package com.sss.demo3.entity;

public class ReaderType {
    private Integer typeId;
    private String typeName;
    private Integer maxBorrowNum;
    private Integer maxBorrowDays;
    private Double finePerDay;

    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public Integer getMaxBorrowNum() { return maxBorrowNum; }
    public void setMaxBorrowNum(Integer maxBorrowNum) { this.maxBorrowNum = maxBorrowNum; }
    public Integer getMaxBorrowDays() { return maxBorrowDays; }
    public void setMaxBorrowDays(Integer maxBorrowDays) { this.maxBorrowDays = maxBorrowDays; }
    public Double getFinePerDay() { return finePerDay; }
    public void setFinePerDay(Double finePerDay) { this.finePerDay = finePerDay; }
}