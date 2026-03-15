package com.smarthome.dao;

import com.smarthome.dto.RuleDTO;
import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RuleDAO {

    // VIEW TEMPLATES (DÀNH CHO ADMIN) -> Lấy các luật có home_id IS NULL
    public List<RuleDTO> getAllRuleTemplates() throws Exception {
        List<RuleDTO> list = new ArrayList<>();
        // Giả sử các mẫu luật chung được lưu với home_id = NULL
        String sql = "SELECT * FROM [Rule] WHERE home_id IS NULL ORDER BY created_at DESC";

        try (Connection conn = DBUtils.getConnection();  
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractRuleDTO(rs));
            }
        }
        return list;
    }

    // Lấy danh sách Rule theo Home ID
    public List<RuleDTO> getRulesByHomeId(int homeId) throws Exception {
        List<RuleDTO> list = new ArrayList<>();
        // Lấy cả luật riêng của nhà (home_id = ?) và luật chung của hệ thống (home_id IS NULL)
        String sql = "SELECT * FROM [Rule] WHERE home_id = ? OR home_id IS NULL ORDER BY created_at DESC";
        
        try (java.sql.Connection conn = com.smarthome.utils.DBUtils.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, homeId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RuleDTO rule = new RuleDTO();
                    rule.setRuleId(rs.getInt("rule_id"));
                    
                    // Xử lý home_id có thể NULL
                    if (rs.getObject("home_id") != null) {
                        rule.setHomeId(rs.getInt("home_id"));
                    } else {
                        rule.setHomeId(0);
                    }
                    
                    rule.setRuleName(rs.getString("rule_name"));
                    rule.setTriggerType(rs.getString("trigger_type"));
                    rule.setConditionJson(rs.getString("condition_json"));
                    rule.setSeverity(rs.getString("severity"));
                    rule.setIsActive(rs.getBoolean("is_active"));
                    
                    list.add(rule);
                }
            }
        }
        return list;
    }

    // CREATE (Tạo Mẫu luật mới hoặc Luật riêng cho nhà)
    public boolean insert(RuleDTO rule) throws Exception {
        String sql = "INSERT INTO [Rule](home_id, rule_name, trigger_type, condition_json, severity, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();  
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Xử lý home_id: Nếu là 0 thì lưu dạng NULL (Template)
            if (rule.getHomeId() > 0) {
                ps.setInt(1, rule.getHomeId());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            
            ps.setString(2, rule.getRuleName());
            ps.setString(3, rule.getTriggerType());
            ps.setString(4, rule.getConditionJson());
            ps.setString(5, rule.getSeverity());
            ps.setBoolean(6, rule.isIsActive());

            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE
    public boolean update(RuleDTO rule) throws Exception {
        String sql = "UPDATE [Rule] SET rule_name=?, trigger_type=?, condition_json=?, severity=?, is_active=? WHERE rule_id=?";
        try (Connection conn = DBUtils.getConnection();  
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rule.getRuleName());
            ps.setString(2, rule.getTriggerType());
            ps.setString(3, rule.getConditionJson());
            ps.setString(4, rule.getSeverity());
            ps.setBoolean(5, rule.isIsActive());
            ps.setInt(6, rule.getRuleId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE (Hard Delete)
    public boolean delete(int ruleId) throws Exception {
        String sql = "DELETE FROM [Rule] WHERE rule_id = ?";
        try (Connection conn = DBUtils.getConnection();  
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ruleId);
            return ps.executeUpdate() > 0;
        }
    }

    // Hàm phụ trợ để tránh lặp code (Helper method)
    private RuleDTO extractRuleDTO(ResultSet rs) throws Exception {
        RuleDTO rule = new RuleDTO();
        rule.setRuleId(rs.getInt("rule_id"));
        
        // Dùng rs.getObject để lấy null an toàn thay vì getInt (getInt trả về 0 nếu null)
        Object homeIdObj = rs.getObject("home_id");
        rule.setHomeId(homeIdObj != null ? (Integer) homeIdObj : 0); 
        
        rule.setRuleName(rs.getString("rule_name"));
        rule.setTriggerType(rs.getString("trigger_type"));
        rule.setConditionJson(rs.getString("condition_json"));
        rule.setSeverity(rs.getString("severity"));
        rule.setIsActive(rs.getBoolean("is_active"));
        rule.setCreatedAt(rs.getTimestamp("created_at"));
        return rule;
    }
    
    // Xóa Rule (Chỉ được xóa luật của chính nhà mình)
    public boolean deleteRule(int ruleId, int homeId) throws Exception {
        boolean check = false;
        String sql = "DELETE FROM [Rule] WHERE rule_id = ? AND home_id = ?";
        
        try (java.sql.Connection conn = com.smarthome.utils.DBUtils.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, ruleId);
            ps.setInt(2, homeId);
            
            if (ps.executeUpdate() > 0) {
                check = true;
            }
        }
        return check;
    }
}