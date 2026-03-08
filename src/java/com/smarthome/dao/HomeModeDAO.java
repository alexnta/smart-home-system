package com.smarthome.dao;

import com.smarthome.dto.HomeModeDTO;
import com.smarthome.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeModeDAO {

    // VIEW
    public List<HomeModeDTO> getByHomeId(int homeId) throws Exception {

        List<HomeModeDTO> list = new ArrayList<>();

        String sql = "SELECT * FROM HomeMode WHERE home_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                HomeModeDTO mode = new HomeModeDTO();

                mode.setModeId(rs.getInt("mode_id"));
                mode.setHomeId(rs.getInt("home_id"));
                mode.setModeName(rs.getString("mode_name"));
                mode.setDescription(rs.getString("description"));
                mode.setIsActive(rs.getBoolean("is_active"));

                list.add(mode);
            }
        }

        return list;
    }

    // CREATE
    public boolean insert(HomeModeDTO mode) throws Exception {

        String sql = "INSERT INTO HomeMode(home_id, mode_name, description, is_active) VALUES (?, ?, ?, 0)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mode.getHomeId());
            ps.setString(2, mode.getModeName());
            ps.setString(3, mode.getDescription());

            return ps.executeUpdate() > 0;
        }
    }

    // UPDATE
    public boolean update(HomeModeDTO mode) throws Exception {

        String sql = "UPDATE HomeMode SET mode_name=?, description=? WHERE mode_id=?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mode.getModeName());
            ps.setString(2, mode.getDescription());
            ps.setInt(3, mode.getModeId());

            return ps.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean delete(int modeId) throws Exception {

        String sql = "DELETE FROM HomeMode WHERE mode_id=?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, modeId);

            return ps.executeUpdate() > 0;
        }
    }

    // SET ACTIVE MODE (QUAN TRỌNG)
    public boolean setActiveMode(int homeId, int modeId) throws Exception {

        Connection conn = null;

        try {

            conn = DBUtils.getConnection();
            conn.setAutoCommit(false);

            // reset mode
            String resetSql = "UPDATE HomeMode SET is_active = 0 WHERE home_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(resetSql);
            ps1.setInt(1, homeId);
            ps1.executeUpdate();

            // set active
            String activeSql = "UPDATE HomeMode SET is_active = 1 WHERE mode_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(activeSql);
            ps2.setInt(1, modeId);
            ps2.executeUpdate();

            conn.commit();

            return true;

        } catch (Exception e) {

            if (conn != null) conn.rollback();
            throw e;

        } finally {

            if (conn != null) conn.close();
        }
    }
}