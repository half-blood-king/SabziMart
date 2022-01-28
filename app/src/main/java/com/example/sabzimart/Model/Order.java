package com.example.sabzimart.Model;

public class Order {
    String Address,Date,House,Name,Product_NAMES,Product_Quantity,State,Time,Phone,Price;
    public Order(){}

    public Order(String address, String date, String house, String name, String product_NAMES, String product_Quantity, String state, String time, String phone, String price) {
        Address = address;
        Date = date;
        House = house;
        Name = name;
        Product_NAMES = product_NAMES;
        Product_Quantity = product_Quantity;
        State = state;
        Time = time;
        Phone = phone;
        Price = price;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProduct_NAMES() {
        return Product_NAMES;
    }

    public void setProduct_NAMES(String product_NAMES) {
        Product_NAMES = product_NAMES;
    }

    public String getProduct_Quantity() {
        return Product_Quantity;
    }

    public void setProduct_Quantity(String product_Quantity) {
        Product_Quantity = product_Quantity;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
