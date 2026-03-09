/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dao;

import com.smarthome.dto.UserDTO;
import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class UserDAO {
    public UserDTO checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT u.user_id, u.username, u.password, u.full_name, u.email, u.status, r.role_name "
                   + "FROM Users u "
                   + "JOIN UserRole ur ON u.user_id = ur.user_id "
                   + "JOIN Roles r ON ur.role_id = r.role_id "
                   + "WHERE u.username = ? AND u.password = ? AND u.status = 1";

        try {
            conn = DBUtils.getConnection(); 
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, username);
                ptm.setString(2, password);

                rs = ptm.executeQuery();

                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
                    boolean status = rs.getBoolean("status");
                    String roleName = rs.getString("role_name");
                    
                    user = new UserDTO(userId, username, password, fullName, email, status, roleName);
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return user; 
    }
    
    // 1. Lấy danh sách tất cả người dùng
    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        String sql = "SELECT u.user_id, u.username, u.password, u.full_name, u.email, u.status, r.role_name "
                   + "FROM Users u "
                   + "LEFT JOIN UserRole ur ON u.user_id = ur.user_id "
                   + "LEFT JOIN Roles r ON ur.role_id = r.role_id "
                   + "ORDER BY u.created_at DESC";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password"); // Thực tế nên che pass
                    String fullName = rs.getString("full_name");
                    String email = rs.getString("email");
                    boolean status = rs.getBoolean("status");
                    String roleName = rs.getString("role_name");
                    
                    list.add(new UserDTO(userId, username, password, fullName, email, status, roleName));
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return list;
    }

    // 2. Thêm mới User (Cần Insert vào Users và UserRole)
    public boolean createUser(UserDTO user, int roleId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;
        
        String sqlUser = "INSERT INTO Users(username, password, full_name, email, status) VALUES(?, ?, ?, ?, ?)";
        String sqlRole = "INSERT INTO UserRole(user_id, role_id) VALUES(?, ?)";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); // Bắt đầu Transaction
                
                // Insert User và lấy user_id vừa tạo
                ptm = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, user.getUsername());
                ptm.setString(2, user.getPassword());
                ptm.setNString(3, user.getFullName());
                ptm.setString(4, user.getEmail());
                ptm.setBoolean(5, user.isStatus());
                
                int rowEffected = ptm.executeUpdate();
                if (rowEffected > 0) {
                    rs = ptm.getGeneratedKeys();
                    if (rs.next()) {
                        int newUserId = rs.getInt(1);
                        
                        // Insert Role
                        PreparedStatement ptmRole = conn.prepareStatement(sqlRole);
                        ptmRole.setInt(1, newUserId);
                        ptmRole.setInt(2, roleId);
                        ptmRole.executeUpdate();
                        ptmRole.close();
                        
                        check = true;
                    }
                }
                conn.commit(); // Hoàn tất Transaction
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback(); // Rollback nếu có lỗi
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }

    // 3. Cập nhật User
    public boolean updateUser(UserDTO user, int roleId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        
        String sqlUser = "UPDATE Users SET full_name = ?, email = ?, status = ? WHERE user_id = ?";
        String sqlRole = "UPDATE UserRole SET role_id = ? WHERE user_id = ?";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                
                // Cập nhật Users
                ptm = conn.prepareStatement(sqlUser);
                ptm.setNString(1, user.getFullName());
                ptm.setString(2, user.getEmail());
                ptm.setBoolean(3, user.isStatus());
                ptm.setInt(4, user.getUserId());
                ptm.executeUpdate();
                ptm.close();
                
                // Cập nhật Role
                ptm = conn.prepareStatement(sqlRole);
                ptm.setInt(1, roleId);
                ptm.setInt(2, user.getUserId());
                ptm.executeUpdate();
                
                check = true;
                conn.commit();
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }

    // 4. Xóa User (Hard Delete - Sẽ cascade tự xóa UserRole theo script DB của bạn)
    public boolean deleteUser(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        String sql = "DELETE FROM Users WHERE user_id = ?";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, userId);
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }
        return check;
    }
}
