/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dao;

import com.smarthome.dto.DeviceDTO;
import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class DeviceDAO {
    public List<DeviceDTO> getAllDevices() throws SQLException, ClassNotFoundException {
        List<DeviceDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        String sql = "SELECT d.device_id, d.room_id, d.name, d.device_type, d.serial_no, "
                   + "d.vendor, d.status, d.last_seen_ts, d.is_active, d.created_at, "
                   + "r.name AS roomName, h.name AS homeName "
                   + "FROM Device d "
                   + "LEFT JOIN Room r ON d.room_id = r.room_id "
                   + "LEFT JOIN Home h ON r.home_id = h.home_id "
                   + "ORDER BY d.created_at DESC";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    DeviceDTO device = new DeviceDTO(
                        rs.getInt("device_id"),
                        rs.getInt("room_id"),
                        rs.getString("name"),
                        rs.getString("device_type"),
                        rs.getString("serial_no"),
                        rs.getString("vendor"),
                        rs.getString("status"),
                        rs.getTimestamp("last_seen_ts"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at"),
                        rs.getString("roomName"),
                        rs.getString("homeName")
                    );
                    list.add(device);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return list;
    }
    
    // Lấy thông tin 1 thiết bị theo ID
    public DeviceDTO getDeviceById(int deviceId) throws SQLException, ClassNotFoundException {
        DeviceDTO device = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT d.device_id, d.room_id, d.name, d.device_type, d.serial_no, "
                   + "d.vendor, d.status, d.last_seen_ts, d.is_active, d.created_at, "
                   + "r.name AS roomName, h.name AS homeName "
                   + "FROM Device d "
                   + "LEFT JOIN Room r ON d.room_id = r.room_id "
                   + "LEFT JOIN Home h ON r.home_id = h.home_id "
                   + "WHERE d.device_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, deviceId);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    device = new DeviceDTO(
                        rs.getInt("device_id"),
                        rs.getInt("room_id"),
                        rs.getString("name"),
                        rs.getString("device_type"),
                        rs.getString("serial_no"),
                        rs.getString("vendor"),
                        rs.getString("status"),
                        rs.getTimestamp("last_seen_ts"),
                        rs.getBoolean("is_active"),
                        rs.getTimestamp("created_at"),
                        rs.getString("roomName"),
                        rs.getString("homeName")
                    );
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return device;
    }
        
    // Thêm mới một thiết bị
    public boolean insertDevice(DeviceDTO device) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "INSERT INTO Device (room_id, name, device_type, serial_no, vendor, status, is_active) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, device.getRoomId());
                ptm.setString(2, device.getName());
                ptm.setString(3, device.getDeviceType());
                ptm.setString(4, device.getSerialNo());
                ptm.setString(5, device.getVendor());
                ptm.setString(6, device.getStatus());
                ptm.setBoolean(7, device.isIsActive());

                int rowsAffected = ptm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return result;
    }
    
    // Cập nhật thông tin thiết bị
    public boolean updateDevice(DeviceDTO device) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "UPDATE Device SET room_id = ?, name = ?, device_type = ?, "
                   + "serial_no = ?, vendor = ?, status = ?, is_active = ? "
                   + "WHERE device_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, device.getRoomId());
                ptm.setString(2, device.getName());
                ptm.setString(3, device.getDeviceType());
                ptm.setString(4, device.getSerialNo());
                ptm.setString(5, device.getVendor());
                ptm.setString(6, device.getStatus());
                ptm.setBoolean(7, device.isIsActive());
                ptm.setInt(8, device.getDeviceId());

                int rowsAffected = ptm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return result;
    }
    
    
    // Xóa một thiết bị theo ID
    public boolean deleteDevice(int deviceId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "DELETE FROM Device WHERE device_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, deviceId);

                int rowsAffected = ptm.executeUpdate();
                if (rowsAffected > 0) {
                    result = true;
                }
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return result;
    }
    
    public boolean updateDeviceRoomAndStatus(int deviceId, int roomId, String status) throws SQLException, ClassNotFoundException {
    Connection conn = null;
    PreparedStatement ptm = null;
    boolean result = false;

    String sql = "UPDATE Device SET room_id = ?, status = ? WHERE device_id = ?";

    try {
        conn = DBUtils.getConnection();
        if (conn != null) {
            ptm = conn.prepareStatement(sql);
            ptm.setInt(1, roomId);
            ptm.setString(2, status);
            ptm.setInt(3, deviceId);

            int rowsAffected = ptm.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
        }
    } finally {
        if (ptm != null) ptm.close();
        if (conn != null) conn.close();
    }

    return result;
}
}
