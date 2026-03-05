package com.smarthome.dao;

import com.smarthome.dto.AlertAction;
import com.smarthome.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertActionDAO {

    // INSERT
    public boolean insert(AlertAction action)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO AlertAction(alert_id, actor_user_id, "
                   + "action_type, note, action_ts) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, action.getAlertId());
            ps.setInt(2, action.getActorUserId());
            ps.setString(3, action.getActionType());
            ps.setString(4, action.getNote());
            ps.setTimestamp(5, action.getActionTs());

            return ps.executeUpdate() > 0;
        }
    }

    // GET BY ALERT
    public List<AlertAction> getByAlert(int alertId)
            throws SQLException, ClassNotFoundException {

        List<AlertAction> list = new ArrayList<>();
        String sql = "SELECT * FROM AlertAction WHERE alert_id = ? ORDER BY action_ts DESC";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapAction(rs));
            }
        }

        return list;
    }

    private AlertAction mapAction(ResultSet rs) throws SQLException {
        AlertAction action = new AlertAction();
        action.setActionId(rs.getInt("action_id"));
        action.setAlertId(rs.getInt("alert_id"));
        action.setActorUserId(rs.getInt("actor_user_id"));
        action.setActionType(rs.getString("action_type"));
        action.setNote(rs.getString("note"));
        action.setActionTs(rs.getTimestamp("action_ts"));
        return action;
    }
}
