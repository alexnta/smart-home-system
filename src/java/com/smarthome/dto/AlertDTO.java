package com.smarthome.dto;

import java.sql.Timestamp;

public class AlertDTO {
    private int alertId;
    private int homeId;
    private int deviceId;
    private int ruleId;
    private String alertType;
    private String severity;
    private String status; // Open, Assigned, Resolved, Closed
    private String message;
    private Timestamp startTs;
    private Timestamp endTs;
    private Timestamp createdAt;

    // Dữ liệu nối (JOIN) để hiển thị lên bảng cho đẹp
    private String homeName;
    private String deviceName;
    private String ruleName;

    public AlertDTO() {
    }

    // Getters and Setters
    public int getAlertId() { return alertId; }
    public void setAlertId(int alertId) { this.alertId = alertId; }

    public int getHomeId() { return homeId; }
    public void setHomeId(int homeId) { this.homeId = homeId; }

    public int getDeviceId() { return deviceId; }
    public void setDeviceId(int deviceId) { this.deviceId = deviceId; }

    public int getRuleId() { return ruleId; }
    public void setRuleId(int ruleId) { this.ruleId = ruleId; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Timestamp getStartTs() { return startTs; }
    public void setStartTs(Timestamp startTs) { this.startTs = startTs; }

    public Timestamp getEndTs() { return endTs; }
    public void setEndTs(Timestamp endTs) { this.endTs = endTs; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getHomeName() { return homeName; }
    public void setHomeName(String homeName) { this.homeName = homeName; }

    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
}