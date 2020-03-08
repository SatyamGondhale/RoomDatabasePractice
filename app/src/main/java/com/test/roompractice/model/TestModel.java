package com.test.roompractice.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "testdata")
public class TestModel {
    @PrimaryKey(autoGenerate = true)
    int id;

    public TestModel(String name, String phone, String city, String company) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "phone")
    String phone;

    @ColumnInfo(name = "city")
    String city;

    @ColumnInfo(name = "company")
    String company;
}
