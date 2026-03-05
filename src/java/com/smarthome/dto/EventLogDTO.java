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
public class EventLogDTO {

    private long eventId;
    private int deviceId;
    private String eventType;  // DoorOpen, DoorClose, LightOn, LightOff, Lock, Unlock, Heartbeat
    private String eventValue; // Open, Closed, On, Off, Locked, Unlocked
    private Timestamp ts;

    //additional info from Device, Room, Home (JOIN)
    private String deviceName;
    private String roomName;
    private String homeName;

    public EventLogDTO() {
    }

    public EventLogDTO(long eventId, int deviceId, String eventType, String eventValue, Timestamp ts, String deviceName, String roomName, String homeName) {
        this.eventId = eventId;
        this.deviceId = deviceId;
        this.eventType = eventType;
        this.eventValue = eventValue;
        this.ts = ts;
        this.deviceName = deviceName;
        this.roomName = roomName;
        this.homeName = homeName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventValue() {
        return eventValue;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
