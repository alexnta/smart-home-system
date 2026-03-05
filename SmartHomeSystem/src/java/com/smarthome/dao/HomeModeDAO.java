package com.smarthome.dao;

import com.smarthome.dto.HomeMode;
import com.smarthome.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomeModeDAO {

    // INSERT
    public boolean insert(HomeMode mode)
            throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO HomeMode(home_id, mode_name, description, is_active) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mode.getHomeId());
            ps.setString(2, mode.getModeName());
            ps.setString(3, mode.getDescription());
            ps.setBoolean(4, mode.isIsActive());

            return ps.executeUpdate() > 0;
        }
    }

    // GET BY HOME
    public List<HomeMode> getByHome(int homeId)
            throws SQLException, ClassNotFoundException {

        List<HomeMode> list = new ArrayList<>();
        String sql = "SELECT * FROM HomeMode WHERE home_id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, homeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapMode(rs));
            }
        }

        return list;
    }

    // SET ACTIVE MODE
    public boolean setActiveMode(int modeId, int homeId)
            throws SQLException, ClassNotFoundException {

        String deactivateSql = "UPDATE HomeMode SET is_active = 0 WHERE home_id = ?";
        String activateSql = "UPDATE HomeMode SET is_active = 1 WHERE mode_id = ?";

        try (Connection conn = DBUtils.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(deactivateSql);
                 PreparedStatement ps2 = conn.prepareStatement(activateSql)) {

                ps1.setInt(1, homeId);
                ps1.executeUpdate();

                ps2.setInt(1, modeId);
                ps2.executeUpdate();

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private HomeMode mapMode(ResultSet rs) throws SQLException {
        HomeMode mode = new HomeMode();
        mode.setModeId(rs.getInt("mode_id"));
        mode.setHomeId(rs.getInt("home_id"));
        mode.setModeName(rs.getString("mode_name"));
        mode.setDescription(rs.getString("description"));
        mode.setIsActive(rs.getBoolean("is_active"));
        return mode;
    }
}