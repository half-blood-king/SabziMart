package com.example.sabzimart.Model;

public class Cart {
    private String P_name,P_price,P_quantity;
    Cart(){}

    public Cart(String p_name, String p_price, String p_quantity) {
        P_name = p_name;
        P_price = p_price;
        P_quantity = p_quantity;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getP_price() {
        return P_price;
    }

    public void setP_price(String p_price) {
        P_price = p_price;
    }

    public String getP_quantity() {
        return P_quantity;
    }

    public void setP_quantity(String p_quantity) {
        P_quantity = p_quantity;
    }
}
