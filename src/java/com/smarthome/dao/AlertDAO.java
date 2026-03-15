package com.smarthome.dao;

import com.smarthome.dto.AlertDTO;
import com.smarthome.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // Tạo alert
//    public boolean insertAlert(AlertDTO alert) throws Exception {
//
//        String sql = "INSERT INTO Alert(home_id, device_id, rule_id, alert_type, severity, status, message, start_ts) "
//                + "VALUES(?,?,?,?,?,?,?,?)";
//
//        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, alert.getHomeId());
//            ps.setInt(2, alert.getDeviceId());
//
//            if (alert.getRuleId() != null) {
//                ps.setInt(3, alert.getRuleId());
//            } else {
//                ps.setNull(3, java.sql.Types.INTEGER);
//            }
//
//            ps.setString(4, alert.getAlertType());
//            ps.setString(5, alert.getSeverity());
//            ps.setString(6, alert.getStatus());
//            ps.setString(7, alert.getMessage());
//            ps.setTimestamp(8, alert.getStartTs());
//
//            return ps.executeUpdate() > 0;
//        }
//    }

    // Lấy tất cả alert đang cần xử lý (Open hoặc Assigned)
    public List<AlertDTO> getOpenAlerts(int homeId) throws Exception {

        List<AlertDTO> list = new ArrayList<>();

        // ĐÃ SỬA: Lấy cả Open và Assigned để khớp với số lượng trên Dashboard
        String sql = "SELECT * FROM Alert WHERE home_id=? AND status IN ('Open', 'Assigned') ORDER BY start_ts DESC";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AlertDTO a = new AlertDTO();

                a.setAlertId(rs.getInt("alert_id"));
                a.setHomeId(rs.getInt("home_id"));
                a.setDeviceId(rs.getInt("device_id"));
                
                // ĐÃ SỬA: Cách an toàn tuyệt đối để lấy dữ liệu có thể chứa giá trị NULL
                if (rs.getObject("rule_id") != null) {
                    a.setRuleId(rs.getInt("rule_id"));
                } else {
                    a.setRuleId(0); // Trả về 0 (hoặc null tuỳ DTO của bạn) nếu không có rule_id
                }

                a.setAlertType(rs.getString("alert_type"));
                a.setSeverity(rs.getString("severity"));
                a.setStatus(rs.getString("status"));
                a.setMessage(rs.getString("message"));
                a.setStartTs(rs.getTimestamp("start_ts"));
                a.setEndTs(rs.getTimestamp("end_ts"));
                a.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(a);
            }
        }
        return list;
    }
    // Kiểm tra đã có alert đang mở chưa
    public boolean existsOpenAlert(int deviceId, int ruleId) throws Exception {

        String sql = "SELECT alert_id FROM Alert "
                + "WHERE device_id=? AND rule_id=? AND status='Open'";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, deviceId);
            ps.setInt(2, ruleId);

            ResultSet rs = ps.executeQuery();

            return rs.next();
        }
    }

    public boolean delete(int alertId) throws Exception {

        String sql = "DELETE FROM Alert WHERE alert_id=?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean ackAlert(int alertId) throws Exception {

        String sql = "UPDATE Alert SET status='Ack' WHERE alert_id=?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertId);

            return ps.executeUpdate() > 0;
        }
    }

    // Đóng alert
    public boolean resolveAlert(int alertId) throws Exception {

        String sql = "UPDATE Alert SET status='Resolved', end_ts=? WHERE alert_id=?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, alertId);

            return ps.executeUpdate() > 0;
        }
    }
    
    // =========================================================================
    // HÀM DÀNH RIÊNG CHO ADMIN: Tìm kiếm toàn cục với JOIN để lấy Tên
    // =========================================================================
    public List<AlertDTO> searchAlerts(String status, String severity, String keyword) throws Exception {
        List<AlertDTO> list = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT a.*, h.name AS homeName, d.name AS deviceName, r.rule_name AS ruleName " +
            "FROM Alert a " +
            "LEFT JOIN Home h ON a.home_id = h.home_id " +
            "LEFT JOIN Device d ON a.device_id = d.device_id " +
            "LEFT JOIN [Rule] r ON a.rule_id = r.rule_id " +
            "WHERE 1=1 "
        );

        if (status != null && !status.isEmpty()) {
            sql.append("AND a.status = ? ");
        }
        if (severity != null && !severity.isEmpty()) {
            sql.append("AND a.severity = ? ");
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (a.message LIKE ? OR d.name LIKE ? OR r.rule_name LIKE ?) ");
        }

        sql.append("ORDER BY a.start_ts DESC");

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (severity != null && !severity.isEmpty()) {
                ps.setString(paramIndex++, severity);
            }
            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchKeyword = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, searchKeyword);
                ps.setString(paramIndex++, searchKeyword);
                ps.setString(paramIndex++, searchKeyword);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlertDTO alert = new AlertDTO();
                    alert.setAlertId(rs.getInt("alert_id"));
                    alert.setHomeId(rs.getInt("home_id"));
                    alert.setDeviceId(rs.getInt("device_id"));
                    alert.setRuleId((Integer) rs.getObject("rule_id"));
                    alert.setAlertType(rs.getString("alert_type"));
                    alert.setSeverity(rs.getString("severity"));
                    alert.setStatus(rs.getString("status"));
                    alert.setMessage(rs.getString("message"));
                    alert.setStartTs(rs.getTimestamp("start_ts"));
                    alert.setEndTs(rs.getTimestamp("end_ts"));
                    alert.setCreatedAt(rs.getTimestamp("created_at"));
                    
                    alert.setHomeName(rs.getString("homeName"));
                    alert.setDeviceName(rs.getString("deviceName"));
                    alert.setRuleName(rs.getString("ruleName"));
                    
                    list.add(alert);
                }
            }
        }
        return list;
    }
}
