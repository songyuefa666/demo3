package com.sss.demo3.entity;

public class LibraryInfo {
    private Integer id;
    private String libraryName;
    private String address;
    private String phone;
    private String openHours;
    private String introduction;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLibraryName() { return libraryName; }
    public void setLibraryName(String libraryName) { this.libraryName = libraryName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getOpenHours() { return openHours; }
    public void setOpenHours(String openHours) { this.openHours = openHours; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
}