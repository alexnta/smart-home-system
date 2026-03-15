package com.smarthome.dao;

import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HODashboardDAO {

    // 1. ĐÃ SỬA: Bảng Home dùng cột 'status' thay vì 'is_active'
    public int getHomeIdByOwnerId(int ownerUserId) throws Exception {
        int homeId = -1; 
        String sql = "SELECT home_id FROM Home WHERE owner_user_id = ? AND status = 'Active'";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ownerUserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    homeId = rs.getInt("home_id");
                }
            }
        }
        return homeId;
    }

    // 2. ĐÃ SỬA: Lấy chế độ nhà từ bảng HomeMode (chỗ này bảng HomeMode lại dùng is_active = 1)
    public String getHomeMode(int homeId) throws Exception {
        String mode = "Unknown"; 
        String sql = "SELECT mode_name FROM HomeMode WHERE home_id = ? AND is_active = 1";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, homeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getString("mode_name") != null) {
                    mode = rs.getString("mode_name");
                }
            }
        }
        return mode;
    }

    // 3. Đếm sự kiện (Event) trong 24 giờ qua của nhà này
    public int countRecentEvents(int homeId) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM EventLog e " +
                     "JOIN Device d ON e.device_id = d.device_id " +
                     "WHERE d.home_id = ? AND e.ts >= DATEADD(day, -1, GETDATE())";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, homeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        }
        return count;
    }

    // 4. Đếm số Cảnh báo (Alert) đang hoạt động
    public int countActiveAlerts(int homeId) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM Alert WHERE home_id = ? AND status IN ('Open', 'Assigned')";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, homeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        }
        return count;
    }

    // 5. ĐÃ SỬA: Đếm số thiết bị đang Online (Dùng is_active = 1 theo database của bạn)
    public int countOnlineDevices(int homeId) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM Device WHERE home_id = ? AND is_active = 1";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, homeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        }
        return count;
    }

    // 6. Đếm Tổng số thiết bị của nhà này
    public int countTotalDevices(int homeId) throws Exception {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM Device WHERE home_id = ?";
        
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, homeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        }
        return count;
    }
    
    // Thêm hàm này vào HODashboardDAO.java
    public boolean updateHomeMode(int homeId, String modeName) throws Exception {
        java.sql.Connection conn = null;
        java.sql.PreparedStatement psDeactivate = null;
        java.sql.PreparedStatement psActivate = null;
        boolean check = false;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // Bật Transaction
                
                // 1. Tắt tất cả các mode của nhà này (is_active = 0)
                String sql1 = "UPDATE HomeMode SET is_active = 0 WHERE home_id = ?";
                psDeactivate = conn.prepareStatement(sql1);
                psDeactivate.setInt(1, homeId);
                psDeactivate.executeUpdate();
                
                // 2. Bật mode được người dùng chọn lên (is_active = 1)
                String sql2 = "UPDATE HomeMode SET is_active = 1 WHERE home_id = ? AND mode_name = ?";
                psActivate = conn.prepareStatement(sql2);
                psActivate.setInt(1, homeId);
                psActivate.setString(2, modeName);
                int rows = psActivate.executeUpdate();
                
                if (rows > 0) {
                    conn.commit(); // Thành công -> Lưu vào DB
                    check = true;
                } else {
                    conn.rollback();
                }
            }
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (psDeactivate != null) psDeactivate.close();
            if (psActivate != null) psActivate.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return check;
    }
}