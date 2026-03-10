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
public class RuleDTO {
    
    private int ruleId;
    private int homeId;
    private String ruleName;
    private String triggerType;
    private String conditionJson;
    private String severity;
    private boolean isActive;
    private Timestamp createdAt;

    public RuleDTO() {
    }

    
    
    public RuleDTO(int ruleId, int homeId, String ruleName, String triggerType, String conditionJson, String severity, boolean isActive, Timestamp createdAt) {
        this.ruleId = ruleId;
        this.homeId = homeId;
        this.ruleName = ruleName;
        this.triggerType = triggerType;
        this.conditionJson = conditionJson;
        this.severity = severity;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    
    
    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getHomeId() {
        return homeId;
    }

    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
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

    public String getConditionJson() {
        return conditionJson;
    }

    public void setConditionJson(String conditionJson) {
        this.conditionJson = conditionJson;
    }

    public void setOperator(String parameter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    

}