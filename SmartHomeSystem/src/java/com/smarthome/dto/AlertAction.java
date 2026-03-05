/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dto;

import java.sql.Timestamp;

/**
 *
 * @author Lenovo
 */
public class AlertAction {
    private int actionId;
    private int alertId;
    private int actorUserId;
    private String actionType;
    private String note;
    private Timestamp actionTs;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getAlertId() {
        return alertId;
    }

    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }

    public int getActorUserId() {
        return actorUserId;
    }

    public void setActorUserId(int actorUserId) {
        this.actorUserId = actorUserId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getActionTs() {
        return actionTs;
    }

    public void setActionTs(Timestamp actionTs) {
        this.actionTs = actionTs;
    }
    
    
}
