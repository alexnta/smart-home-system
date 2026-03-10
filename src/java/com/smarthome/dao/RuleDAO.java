package com.smarthome.dao;

import com.smarthome.dto.RuleDTO;
import com.smarthome.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RuleDAO {

    // VIEW
    public List<RuleDTO> getByHomeId(int homeId) throws Exception {

        List<RuleDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM [Rule] WHERE home_id = ?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                RuleDTO rule = new RuleDTO();

                rule.setRuleId(rs.getInt("rule_id"));
                rule.setHomeId(rs.getInt("home_id"));
                rule.setRuleName(rs.getString("rule_name"));
                rule.setTriggerType(rs.getString("trigger_type"));
                rule.setOperator(rs.getString("operator"));
                rule.setThrehold(rs.getDouble("threhold"));
                rule.setSeverity(rs.getString("severity"));
                rule.setIsActive(rs.getBoolean("is_active"));
                rule.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(rule);
            }
        }

        return list;
    }

    // CREATE
    public boolean insert(RuleDTO rule) throws Exception {

        String sql = "INSERT INTO [Rule](home_id, rule_name, trigger_type, operator, threhold, severity) VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rule.getHomeId());
            ps.setString(2, rule.getRuleName());
            ps.setString(3, rule.getTriggerType());
            ps.setString(4, rule.getOperator());
            ps.setDouble(5, rule.getThrehold());
            ps.setString(6, rule.getSeverity());

            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE
    public boolean update(RuleDTO rule) throws Exception {

        String sql = "UPDATE [Rule] SET rule_name=?, trigger_type=?, operator=?, threhold=?, severity=? WHERE rule_id=?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rule.getRuleName());
            ps.setString(2, rule.getTriggerType());
            ps.setString(3, rule.getOperator());
            ps.setDouble(4, rule.getThrehold());
            ps.setString(5, rule.getSeverity());
            ps.setInt(6, rule.getRuleId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE (Hard Delete)
    public boolean delete(int ruleId) throws Exception {

        String sql = "DELETE FROM [Rule] WHERE rule_id = ?";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ruleId);

            return ps.executeUpdate() > 0;
        }
    }
}
