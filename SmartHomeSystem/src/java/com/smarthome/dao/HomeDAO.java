/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smarthome.dao;

import com.smarthome.dto.HomeDTO;
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
public class HomeDAO {
    // Lấy danh sách tất cả các nhà (có thông tin chủ nhà)
    public List<HomeDTO> getAllHomes() throws SQLException, ClassNotFoundException {
        List<HomeDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        
        String sql = "SELECT h.home_id, h.code, h.name, h.address_text, h.status, "
                   + "h.owner_user_id, h.created_at, u.full_name AS ownerName "
                   + "FROM Home h "
                   + "LEFT JOIN Users u ON h.owner_user_id = u.user_id "
                   + "ORDER BY h.created_at DESC";
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                
                while (rs.next()) {
                    HomeDTO home = new HomeDTO(
                        rs.getInt("home_id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address_text"),
                        rs.getString("status"),
                        rs.getInt("owner_user_id"),
                        rs.getTimestamp("created_at"),
                        rs.getString("ownerName")
                    );
                    list.add(home);
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
