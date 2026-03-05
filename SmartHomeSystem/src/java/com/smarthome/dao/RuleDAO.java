package com.smarthome.dao;

import com.smarthome.dto.Rule;
import com.smarthome.utils.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RuleDAO {

    // ===============================
    // INSERT
    // ===============================
    public boolean insert(Rule rule)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Rule(home_id, rule_name, trigger_type, "
                   + "condition_json, severity, is_active, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rule.getHomeId());
            ps.setString(2, rule.getRuleName());
            ps.setString(3, rule.getTriggerType());
            ps.setString(4, rule.getConditionJson());
            ps.setString(5, rule.getSeverity());
            ps.setBoolean(6, rule.isActive());
            ps.setTimestamp(7, rule.getCreatedAt());

            return ps.executeUpdate() > 0;
        }
    }

    // ===============================
    // GET ALL
    // ===============================
    public List<Rule> getAll()
            throws SQLException, ClassNotFoundException {

        List<Rule> list = new ArrayList<>();
        String sql = "SELECT * FROM Rule ORDER BY created_at DESC";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRule(rs));
            }
        }

        return list;
    }

    // ===============================
    // GET BY HOME
    // ===============================
    public List<Rule> getByHome(int homeId)
            throws SQLException, ClassNotFoundException {

        List<Rule> list = new ArrayList<>();
        String sql = "SELECT * FROM Rule WHERE home_id = ? AND is_active = 1";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRule(rs));
            }
        }

        return list;
    }

    // ===============================
    // UPDATE
    // ===============================
    public boolean update(Rule rule)
            throws SQLException, ClassNotFoundException {

        String sql = "UPDATE Rule SET rule_name = ?, trigger_type = ?, "
                   + "condition_json = ?, severity = ?, is_active = ? "
                   + "WHERE rule_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rule.getRuleName());
            ps.setString(2, rule.getTriggerType());
            ps.setString(3, rule.getConditionJson());
            ps.setString(4, rule.getSeverity());
            ps.setBoolean(5, rule.isActive());
            ps.setInt(6, rule.getRuleId());

            return ps.executeUpdate() > 0;
        }
    }

    // ===============================
    // DELETE
    // ===============================
    public boolean delete(int ruleId)
            throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM Rule WHERE rule_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ruleId);
            return ps.executeUpdate() > 0;
        }
    }

    // ===============================
    // MAP FUNCTION
    // ===============================
    private Rule mapRule(ResultSet rs) throws SQLException {
        return new Rule(
                rs.getInt("rule_id"),
                rs.getInt("home_id"),
                rs.getString("rule_name"),
                rs.getString("trigger_type"),
                rs.getString("condition_json"),
                rs.getString("severity"),
                rs.getBoolean("is_active"),
                rs.getTimestamp("created_at")
        );
    }
}