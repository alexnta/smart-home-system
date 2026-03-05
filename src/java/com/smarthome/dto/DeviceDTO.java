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
public class DeviceDTO {

    private int deviceId;
    private int roomId;
    private String name;
    private String deviceType; // DoorSensor, SmartLock, LightSwitch, SmartLight
    private String serialNo;
    private String vendor;
    private String status; // On, Off, Locked, Unlocked, Open, Closed, Active, Offline, Error
    private Timestamp lastSeenTs;
    private boolean isActive; // 1: Đang sử dụng, 0: Đã gỡ bỏ
    private Timestamp createdAt;

    // Thông tin bổ sung từ Room và Home (JOIN)
    private String roomName;
    private String homeName;

    public DeviceDTO() {
    }

    public DeviceDTO(int deviceId, int roomId, String name, String deviceType, String serialNo, String vendor, String status, Timestamp lastSeenTs, boolean isActive, Timestamp createdAt, String roomName, String homeName) {
        this.deviceId = deviceId;
        this.roomId = roomId;
        this.name = name;
        this.deviceType = deviceType;
        this.serialNo = serialNo;
        this.vendor = vendor;
        this.status = status;
        this.lastSeenTs = lastSeenTs;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.roomName = roomName;
        this.homeName = homeName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastSeenTs() {
        return lastSeenTs;
    }

    public void setLastSeenTs(Timestamp lastSeenTs) {
        this.lastSeenTs = lastSeenTs;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }
}
