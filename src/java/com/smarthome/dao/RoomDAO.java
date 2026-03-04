/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dao;

import com.smarthome.dto.RoomDTO;
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
public class RoomDAO {
    // get room list
    public List<RoomDTO> getAllRooms() throws SQLException, ClassNotFoundException {
        List<RoomDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        String sql = "SELECT r.room_id, r.home_id, r.name, r.floor, r.room_type, "
                   + "r.status, r.created_at, h.name AS homeName "
                   + "FROM Room r "
                   + "LEFT JOIN Home h ON r.home_id = h.home_id "
                   + "ORDER BY r.created_at DESC";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    RoomDTO room = new RoomDTO(
                        rs.getInt("room_id"),
                        rs.getInt("home_id"),
                        rs.getString("name"),
                        rs.getInt("floor"),
                        rs.getString("room_type"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getString("homeName")
                    );
                    list.add(room);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        
        return list;
    }
    
    public RoomDTO getRoomById(int roomId) throws SQLException, ClassNotFoundException {
        RoomDTO room = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT r.room_id, r.home_id, r.name, r.floor, r.room_type, "
                   + "r.status, r.created_at, h.name AS homeName "
                   + "FROM Room r "
                   + "LEFT JOIN Home h ON r.home_id = h.home_id "
                   + "WHERE r.room_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, roomId);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    room = new RoomDTO(
                        rs.getInt("room_id"),
                        rs.getInt("home_id"),
                        rs.getString("name"),
                        rs.getInt("floor"),
                        rs.getString("room_type"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getString("homeName")
                    );
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return room;
    }

    
    public boolean insertRoom(RoomDTO room) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "INSERT INTO Room (home_id, name, floor, room_type, status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, room.getHomeId());
                ptm.setString(2, room.getName());
                ptm.setInt(3, room.getFloor());
                ptm.setString(4, room.getRoomType());
                ptm.setString(5, room.getStatus());

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
    
    public boolean updateRoom(RoomDTO room) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "UPDATE Room SET home_id = ?, name = ?, floor = ?, "
                   + "room_type = ?, status = ? "
                   + "WHERE room_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, room.getHomeId());
                ptm.setString(2, room.getName());
                ptm.setInt(3, room.getFloor());
                ptm.setString(4, room.getRoomType());
                ptm.setString(5, room.getStatus());
                ptm.setInt(6, room.getRoomId());

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
    
    public boolean deleteRoom(int roomId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "DELETE FROM Room WHERE room_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, roomId);

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
