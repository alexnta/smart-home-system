/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dto;

import java.sql.Timestamp;

/**
 *
 * @author Alex
 */
public class HomeDTO {
    private int homeId;
    private String code;
    private String name;
    private String addressText;
    private String status;
    private int ownerUserId;
    private Timestamp createdAt;
    
    // Owner info (join từ bảng Users)
    private String ownerName; // Tên chủ nhà

    public HomeDTO() {
    }

    public HomeDTO(int homeId, String code, String name, String addressText, 
                   String status, int ownerUserId, Timestamp createdAt, String ownerName) {
        this.homeId = homeId;
        this.code = code;
        this.name = name;
        this.addressText = addressText;
        this.status = status;
        this.ownerUserId = ownerUserId;
        this.createdAt = createdAt;
        this.ownerName = ownerName;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
