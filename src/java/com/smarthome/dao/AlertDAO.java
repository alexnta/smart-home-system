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
    public boolean insertAlert(AlertDTO alert) throws Exception {

        String sql = "INSERT INTO Alert(home_id, device_id, rule_id, alert_type, severity, status, message, start_ts) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alert.getHomeId());
            ps.setInt(2, alert.getDeviceId());

            if (alert.getRuleId() != null) {
                ps.setInt(3, alert.getRuleId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.setString(4, alert.getAlertType());
            ps.setString(5, alert.getSeverity());
            ps.setString(6, alert.getStatus());
            ps.setString(7, alert.getMessage());
            ps.setTimestamp(8, alert.getStartTs());

            return ps.executeUpdate() > 0;
        }
    }

    // Lấy tất cả alert đang mở
    public List<AlertDTO> getOpenAlerts(int homeId) throws Exception {

        List<AlertDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM Alert WHERE home_id=? AND status='Open' ORDER BY start_ts DESC";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AlertDTO a = new AlertDTO();

                a.setAlertId(rs.getInt("alert_id"));
                a.setHomeId(rs.getInt("home_id"));
                a.setDeviceId(rs.getInt("device_id"));
                a.setRuleId((Integer) rs.getObject("rule_id"));
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
}
