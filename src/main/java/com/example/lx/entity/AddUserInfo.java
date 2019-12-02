package com.example.lx.entity;

import java.util.List;

public class AddUserInfo
{
    private  String needroom;
    private List<UserInfo> user;
    private String homeName;
    private String price1;

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getNeedroom() {
        return needroom;
    }

    public void setNeedroom(String needroom) {
        this.needroom = needroom;
    }

    public List<UserInfo> getUser() {
        return user;
    }

    public void setUser(List<UserInfo> user) {
        this.user = user;
    }
}

