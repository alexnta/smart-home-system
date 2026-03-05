package com.smarthome.dao;

import com.smarthome.dto.Alert;
import com.smarthome.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // Lấy tất cả alert
    public List<Alert> getAll() throws Exception {
        List<Alert> list = new ArrayList<>();
        String sql = "SELECT * FROM Alert";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alert alert = mapRow(rs);
                list.add(alert);
            }
        }
        return list;
    }

    // Lấy alert theo id
    public Alert getById(int alertId) throws Exception {
        String sql = "SELECT * FROM Alert WHERE alert_id = ?";
        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alertId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }
        }
        return null;
    }

   
    public boolean insert(Alert alert) throws Exception {
        String sql = "INSERT INTO Alert(home_id, device_id, rule_id, alert_type, severity, status, message, start_ts) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alert.getHomeId());
            ps.setInt(2, alert.getDeviceId());
            ps.setInt(3, alert.getRuleId());
            ps.setString(4, alert.getAlertType());
            ps.setString(5, alert.getSeverity());
            ps.setString(6, alert.getStatus());
            ps.setString(7, alert.getMessage());
            ps.setTimestamp(8, alert.getStartTs());

            return ps.executeUpdate() > 0;
        }
    }

    // Cập nhật status
    public boolean updateStatus(int alertId, String status) throws Exception {
        String sql = "UPDATE Alert SET status = ? WHERE alert_id = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, alertId);

            return ps.executeUpdate() > 0;
        }
    }

    // Đóng alert (Resolved + end_ts)
    public boolean closeAlert(int alertId) throws Exception {
        String sql = "UPDATE Alert SET status = 'Resolved', end_ts = GETDATE() WHERE alert_id = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, alertId);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy alert theo home
    public List<Alert> getByHomeId(int homeId) throws Exception {
        List<Alert> list = new ArrayList<>();
        String sql = "SELECT * FROM Alert WHERE home_id = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, homeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    // Map dữ liệu từ DB sang Alert object
    private Alert mapRow(ResultSet rs) throws Exception {
        Alert alert = new Alert();
        alert.setAlertId(rs.getInt("alert_id"));
        alert.setHomeId(rs.getInt("home_id"));
        alert.setDeviceId(rs.getInt("device_id"));
        alert.setRuleId(rs.getInt("rule_id"));
        alert.setAlertType(rs.getString("alert_type"));
        alert.setSeverity(rs.getString("severity"));
        alert.setStatus(rs.getString("status"));
        alert.setMessage(rs.getString("message"));
        alert.setStartTs(rs.getTimestamp("start_ts"));
        alert.setEndTs(rs.getTimestamp("end_ts"));
        alert.setCreatedAt(rs.getTimestamp("created_at"));

        return alert;
    }
}