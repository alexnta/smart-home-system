/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dto;

/**
 *
 * @author Lenovo
 */
public class HomeModeDTO {
    private int modeId;
    private int homeId;
    private String modeName;
    private String description;
    private boolean isActive;

    public HomeModeDTO() {
    }

    public HomeModeDTO(int modeId, int homeId, String modeName, String description, boolean isActive) {
        this.modeId = modeId;
        this.homeId = homeId;
        this.modeName = modeName;
        this.description = description;
        this.isActive = isActive;
    }

    
    
    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    
}
