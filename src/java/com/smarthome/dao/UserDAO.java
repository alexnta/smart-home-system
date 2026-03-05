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
}
