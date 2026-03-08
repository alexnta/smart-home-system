package com.smarthome.dao;

import com.smarthome.dto.AlertActionDTO;
import com.smarthome.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlertActionDAO {

    // CREATE
    public boolean insert(AlertActionDTO action) throws Exception {

        String sql = "INSERT INTO AlertAction(alert_id, actor_user_id, action_type, note) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, action.getAlertId());
            ps.setInt(2, action.getActorUserId());
            ps.setString(3, action.getActionType());
            ps.setString(4, action.getNote());

            return ps.executeUpdate() > 0;
        }
    }

    // VIEW (lấy action theo alert)
    public List<AlertActionDTO> getByAlertId(int alertId) throws Exception {

        List<AlertActionDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM AlertAction WHERE alert_id=? ORDER BY action_ts DESC";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alertId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                AlertActionDTO a = new AlertActionDTO();

                a.setActionId(rs.getInt("action_id"));
                a.setAlertId(rs.getInt("alert_id"));
                a.setActorUserId(rs.getInt("actor_user_id"));
                a.setActionType(rs.getString("action_type"));
                a.setNote(rs.getString("note"));
                a.setActionTs(rs.getTimestamp("action_ts"));

                list.add(a);
            }
        }

        return list;
    }

    // DELETE
    public boolean delete(int actionId) throws Exception {

        String sql = "DELETE FROM AlertAction WHERE action_id=?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, actionId);

            return ps.executeUpdate() > 0;
        }
    }
}