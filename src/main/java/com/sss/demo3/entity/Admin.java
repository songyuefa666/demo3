package com.sss.demo3.entity;

import java.util.Date;

public class Admin {
    private Long adminId;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private Date registerDate;
    private String role; // SYS_ADMIN, OPERATOR

    public Admin() {}

    public Admin(Long adminId, String username, String password, String realName, String phone, Date registerDate, String role) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.phone = phone;
        this.registerDate = registerDate;
        this.role = role;
    }

    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Date getRegisterDate() { return registerDate; }
    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}