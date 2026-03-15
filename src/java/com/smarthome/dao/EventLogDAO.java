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

public class EventLogDAO {
    
    // ĐÃ SỬA: Thêm logic cập nhật last_seen_ts cho thiết bị
    public boolean insertEvent(EventLogDTO event) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptmEvent = null;
        PreparedStatement ptmDeviceUpdate = null;
        boolean result = false;
        
        String sqlInsertEvent = "INSERT INTO EventLog (device_id, event_type, event_value, ts) "
                              + "VALUES (?, ?, ?, ?)";
                              
        String sqlUpdateDevice = "UPDATE Device SET last_seen_ts = ?, status = ? WHERE device_id = ?";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                // Bật Transaction để đảm bảo tính toàn vẹn dữ liệu (Cả 2 lệnh cùng thành công hoặc cùng thất bại)
                conn.setAutoCommit(false); 
                
                // 1. Insert Event Log
                ptmEvent = conn.prepareStatement(sqlInsertEvent);
                ptmEvent.setInt(1, event.getDeviceId());
                ptmEvent.setString(2, event.getEventType());
                ptmEvent.setString(3, event.getEventValue());
                ptmEvent.setTimestamp(4, event.getTs());
                
                int rowsInserted = ptmEvent.executeUpdate();
                
                // 2. Update Device last_seen_ts & current status
                if (rowsInserted > 0) {
                    ptmDeviceUpdate = conn.prepareStatement(sqlUpdateDevice);
                    ptmDeviceUpdate.setTimestamp(1, event.getTs());
                    // Cập nhật trạng thái mới nhất cho thiết bị luôn (Ví dụ: Từ Off thành On)
                    ptmDeviceUpdate.setString(2, event.getEventValue() != null ? event.getEventValue() : "Active"); 
                    ptmDeviceUpdate.setInt(3, event.getDeviceId());
                    
                    int rowsUpdated = ptmDeviceUpdate.executeUpdate();
                    if (rowsUpdated > 0) {
                        result = true;
                    }
                }
                
                if (result) {
                    conn.commit(); // Thành công -> Lưu vào DB
                } else {
                    conn.rollback(); // Thất bại -> Quay lui
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Nếu có lỗi SQL thì rollback luôn
            }
            throw e;
        } finally {
            if (ptmEvent != null) ptmEvent.close();
            if (ptmDeviceUpdate != null) ptmDeviceUpdate.close();
            if (conn != null) {
                conn.setAutoCommit(true); // Trả lại cấu hình mặc định cho pool
                conn.close();
            }
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
        sql.append("WHERE 1=1 "); 

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
    
    // Đã thay đổi: Load tất cả event (Có thể giới hạn top 500 để không treo DB)
    public List<EventLogDTO> getAllEvents() throws SQLException, ClassNotFoundException {
        return searchEvents(null, null, null, null); 
    }
    
    
    // Tìm Log theo Home ID (Dành cho Home Owner) - ĐÃ TỐI ƯU
    public List<EventLogDTO> searchHomeEvents(int homeId, Integer deviceId, String eventType, 
                                              Timestamp startTime, Timestamp endTime) 
            throws SQLException, ClassNotFoundException {
        List<EventLogDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.event_id, e.device_id, e.event_type, e.event_value, e.ts, ");
        sql.append("d.name AS deviceName, r.name AS roomName "); // Không cần select tên nhà nữa
        sql.append("FROM EventLog e ");
        sql.append("JOIN Device d ON e.device_id = d.device_id ");
        sql.append("LEFT JOIN Room r ON d.room_id = r.room_id ");
        // Lấy trực tiếp từ bảng Device, bỏ Join Home đi cho nhẹ
        sql.append("WHERE d.home_id = ? "); 

        if (deviceId != null && deviceId > 0) sql.append("AND e.device_id = ? ");
        if (eventType != null && !eventType.trim().isEmpty()) sql.append("AND e.event_type = ? ");
        if (startTime != null) sql.append("AND e.ts >= ? ");
        if (endTime != null) sql.append("AND e.ts <= ? ");
        sql.append("ORDER BY e.ts DESC");

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql.toString());
                int paramIndex = 1;
                
                ptm.setInt(paramIndex++, homeId); // Truyền Home ID
                
                if (deviceId != null && deviceId > 0) ptm.setInt(paramIndex++, deviceId);
                if (eventType != null && !eventType.trim().isEmpty()) ptm.setString(paramIndex++, eventType);
                if (startTime != null) ptm.setTimestamp(paramIndex++, startTime);
                if (endTime != null) ptm.setTimestamp(paramIndex++, endTime);

                rs = ptm.executeQuery();
                while (rs.next()) {
                    // Chỗ homeName truyền thẳng chuỗi "My Home" hoặc rỗng vì Owner chỉ xem nhà mình
                    list.add(new EventLogDTO(
                        rs.getLong("event_id"), rs.getInt("device_id"), rs.getString("event_type"),
                        rs.getString("event_value"), rs.getTimestamp("ts"), rs.getString("deviceName"),
                        rs.getString("roomName"), "My Home" 
                    ));
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }
}