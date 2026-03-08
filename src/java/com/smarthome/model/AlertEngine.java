package com.smarthome.model;

import com.smarthome.dao.AlertDAO;
import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.AlertDTO;
import com.smarthome.dto.RuleDTO;


import java.sql.Timestamp;
import java.util.List;

public class AlertEngine {

    private RuleDAO ruleDAO = new RuleDAO();
    private AlertDAO alertDAO = new AlertDAO();

    public void processDeviceData(int homeId, int deviceId, double deviceValue) throws Exception {

        List<RuleDTO> rules = ruleDAO.getByHomeId(homeId);

        for (RuleDTO rule : rules) {

            if (!rule.isIsActive()) continue;

            if (isViolated(rule, deviceValue)) {

                if (alertDAO.existsOpenAlert(deviceId, rule.getRuleId())) {
                    continue;
                }

                AlertDTO alert = new AlertDTO();

                alert.setHomeId(homeId);
                alert.setDeviceId(deviceId);
                alert.setRuleId(rule.getRuleId());
                alert.setAlertType(rule.getTriggerType());
                alert.setSeverity(rule.getSeverity());
                alert.setStatus("Open");
                alert.setMessage("Rule violated: " + rule.getRuleName());
                alert.setStartTs(new Timestamp(System.currentTimeMillis()));

                alertDAO.insertAlert(alert);
            }
        }
    }

    private boolean isViolated(RuleDTO rule, double value) {

     

        String operator = rule.getOperator();
        double threshold = rule.getThrehold();

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