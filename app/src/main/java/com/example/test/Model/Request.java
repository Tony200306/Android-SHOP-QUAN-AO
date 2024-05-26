package com.example.test.Model;



import java.io.Serializable;
import java.util.ArrayList;

public class Request implements Serializable {
    private String id;
    private String phone;
    private String name;

    private String address;
    private String total;
    private ArrayList<Clothes>  clothesOder ;
    private String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = (status);
    }

    public Request() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request(String id, String phone, String name, String address, String total, ArrayList<Clothes> clothesOder ) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.clothesOder = clothesOder;
        this.Status = "0";
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Clothes> getclothesOder() {
        return clothesOder ;
    }

    public void setclothesOder(ArrayList<Clothes> clothesOder) {
        this.clothesOder = clothesOder;
    }
}

