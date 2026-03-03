/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dao;

import com.smarthome.dto.EventLogDTO;
import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class EventLogDAO {
    public boolean insertEvent(EventLogDTO event) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;
        
        String sql = "INSERT INTO EventLog (device_id, event_type, event_value, ts) "
                   + "VALUES (?, ?, ?, ?)";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, event.getDeviceId());
                ptm.setString(2, event.getEventType());
                ptm.setString(3, event.getEventValue());
                ptm.setTimestamp(4, event.getTs());
                
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
    
    // Tìm kiếm event logs theo nhiều điều kiện
    public List<EventLogDTO> searchEvents(Integer deviceId, String eventType, 
                                           Timestamp startTime, Timestamp endTime) 
            throws SQLException, ClassNotFoundException {
        List<EventLogDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.event_id, e.device_id, e.event_type, e.event_value, e.ts, ");
        sql.append("d.name AS deviceName, r.name AS roomName, h.name AS homeName ");
        sql.append("FROM EventLog e ");
        sql.append("LEFT JOIN Device d ON e.device_id = d.device_id ");
        sql.append("LEFT JOIN Room r ON d.room_id = r.room_id ");
        sql.append("LEFT JOIN Home h ON r.home_id = h.home_id ");
        sql.append("WHERE 1=1 "); // Trick để dễ thêm điều kiện

        // Thêm điều kiện động
        if (deviceId != null && deviceId > 0) {
            sql.append("AND e.device_id = ? ");
        }
        if (eventType != null && !eventType.trim().isEmpty()) {
            sql.append("AND e.event_type = ? ");
        }
        if (startTime != null) {
            sql.append("AND e.ts >= ? ");
        }
        if (endTime != null) {
            sql.append("AND e.ts <= ? ");
        }

        sql.append("ORDER BY e.ts DESC");

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql.toString());

                // Set parameters động
                int paramIndex = 1;
                if (deviceId != null && deviceId > 0) {
                    ptm.setInt(paramIndex++, deviceId);
                }
                if (eventType != null && !eventType.trim().isEmpty()) {
                    ptm.setString(paramIndex++, eventType);
                }
                if (startTime != null) {
                    ptm.setTimestamp(paramIndex++, startTime);
                }
                if (endTime != null) {
                    ptm.setTimestamp(paramIndex++, endTime);
                }

                rs = ptm.executeQuery();

                while (rs.next()) {
                    EventLogDTO event = new EventLogDTO(
                        rs.getLong("event_id"),
                        rs.getInt("device_id"),
                        rs.getString("event_type"),
                        rs.getString("event_value"),
                        rs.getTimestamp("ts"),
                        rs.getString("deviceName"),
                        rs.getString("roomName"),
                        rs.getString("homeName")
                    );
                    list.add(event);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return list;
    }

    public List<EventLogDTO> getAllEvents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
