package com.sss.demo3.entity;

import java.util.Date;

public class BorrowRecord {
    private Long borrowId;
    private Integer readerId;
    private String readerBarcode; // Display
    private String readerName; // Display
    private Integer bookId;
    private String bookBarcode; // Display
    private String bookName; // Display
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private Integer renewTimes;
    private Integer status; // 1: borrowing, 2: returned, 3: overdue
    private Double fine;
    private Long operatorId;

    public Long getBorrowId() { return borrowId; }
    public void setBorrowId(Long borrowId) { this.borrowId = borrowId; }
    public Integer getReaderId() { return readerId; }
    public void setReaderId(Integer readerId) { this.readerId = readerId; }
    public String getReaderBarcode() { return readerBarcode; }
    public void setReaderBarcode(String readerBarcode) { this.readerBarcode = readerBarcode; }
    public String getReaderName() { return readerName; }
    public void setReaderName(String readerName) { this.readerName = readerName; }
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public String getBookBarcode() { return bookBarcode; }
    public void setBookBarcode(String bookBarcode) { this.bookBarcode = bookBarcode; }
    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }
    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public Integer getRenewTimes() { return renewTimes; }
    public void setRenewTimes(Integer renewTimes) { this.renewTimes = renewTimes; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Double getFine() { return fine; }
    public void setFine(Double fine) { this.fine = fine; }
    public Long getOperatorId() { return operatorId; }
    public void setOperatorId(Long operatorId) { this.operatorId = operatorId; }
}