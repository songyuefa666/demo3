package com.sss.demo3.entity;

import java.util.Date;

public class Book {
    private Integer bookId;
    private String barcode;
    private String name;
    private String isbn;
    private Integer bookTypeId;
    private String bookTypeName; // Display
    private String author;
    private String publisher;
    private String format; // 开本
    private String binding; // 装订
    private String edition; // 版次
    private Double price;
    private Integer stock;
    private Integer bookshelfId;
    private String bookshelfName; // Display
    private String bookshelfArea; // Display
    private Date createTime;

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getBookTypeId() { return bookTypeId; }
    public void setBookTypeId(Integer bookTypeId) { this.bookTypeId = bookTypeId; }
    public String getBookTypeName() { return bookTypeName; }
    public void setBookTypeName(String bookTypeName) { this.bookTypeName = bookTypeName; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    public String getBinding() { return binding; }
    public void setBinding(String binding) { this.binding = binding; }
    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Integer getBookshelfId() { return bookshelfId; }
    public void setBookshelfId(Integer bookshelfId) { this.bookshelfId = bookshelfId; }
    public String getBookshelfName() { return bookshelfName; }
    public void setBookshelfName(String bookshelfName) { this.bookshelfName = bookshelfName; }
    public String getBookshelfArea() { return bookshelfArea; }
    public void setBookshelfArea(String bookshelfArea) { this.bookshelfArea = bookshelfArea; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
