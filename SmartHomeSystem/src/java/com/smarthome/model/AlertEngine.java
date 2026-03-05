package com.smarthome.model;

import com.smarthome.dao.AlertDAO;
import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.Alert;
import com.smarthome.dto.Rule;

import java.sql.Timestamp;
import java.util.List;

public class AlertEngine {

    private RuleDAO ruleDAO = new RuleDAO();
    private AlertDAO alertDAO = new AlertDAO();

    public void processDeviceData(int homeId, int deviceId, double deviceValue) throws Exception {

        List<Rule> rules = ruleDAO.getByHome(homeId);

        for (Rule rule : rules) {

            if (!rule.isActive()) continue;

            if (isViolated(rule.getConditionJson(), deviceValue)) {

            
                Alert alert = new Alert();
                alert.setHomeId(homeId);
                alert.setDeviceId(deviceId);
                alert.setRuleId(rule.getRuleId());
                alert.setAlertType(rule.getTriggerType());
                alert.setSeverity(rule.getSeverity());
                alert.setStatus("Open");
                alert.setMessage("Rule violated: " + rule.getRuleName());
                alert.setStartTs(new Timestamp(System.currentTimeMillis()));

                alertDAO.insert(alert);
            }
        }
    }

    private boolean isViolated(String condition, double value) {

        condition = condition.trim();

        String operator;
        double threshold;

        // xử lý toán tử 2 ký tự trước
        if (condition.startsWith(">=") || condition.startsWith("<=")) {
            operator = condition.substring(0, 2);
            threshold = Double.parseDouble(condition.substring(2));
        } else {
            operator = condition.substring(0, 1);
            threshold = Double.parseDouble(condition.substring(1));
        }

        switch (operator) {
            case ">":
                return value > threshold;
            case "<":
                return value < threshold;
            case "=":
                return value == threshold;
            case ">=":
                return value >= threshold;
            case "<=":
                return value <= threshold;
            default:
                return false;
        }
    }
}