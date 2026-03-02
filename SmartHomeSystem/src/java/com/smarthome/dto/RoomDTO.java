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
public class RoomDTO {

    private int roomId;
    private int homeId;
    private String name;
    private int floor;
    private String roomType;
    private String status;
    private Timestamp createdAt;

    private String homeName;

    public RoomDTO() {
    }

    public RoomDTO(int roomId, int homeId, String name, int floor, String roomType, String status, Timestamp createdAt, String homeName) {
        this.roomId = roomId;
        this.homeId = homeId;
        this.name = name;
        this.floor = floor;
        this.roomType = roomType;
        this.status = status;
        this.createdAt = createdAt;
        this.homeName = homeName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

}
