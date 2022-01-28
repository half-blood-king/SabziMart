package com.example.sabzimart.Model;

public class Products {
    String product_name,product_price,image,product_quantity;
    Products(){}


    public Products(String product_name, String product_price, String image, String product_quantity) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.image = image;
        this.product_quantity = product_quantity;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
