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
    // get home list
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

    // get home info by id
    public HomeDTO getHomeById(int homeId) throws SQLException, ClassNotFoundException {
        HomeDTO home = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        String sql = "SELECT h.home_id, h.code, h.name, h.address_text, h.status, "
                   + "h.owner_user_id, h.created_at, u.full_name AS ownerName "
                   + "FROM Home h "
                   + "LEFT JOIN Users u ON h.owner_user_id = u.user_id "
                   + "WHERE h.home_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, homeId);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    home = new HomeDTO(
                        rs.getInt("home_id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("address_text"),
                        rs.getString("status"),
                        rs.getInt("owner_user_id"),
                        rs.getTimestamp("created_at"),
                        rs.getString("ownerName")
                    );
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (ptm != null) ptm.close();
            if (conn != null) conn.close();
        }

        return home;
    }    
    
    // add a new home
        public boolean insertHome(HomeDTO home) throws SQLException, ClassNotFoundException {
            Connection conn = null;
            PreparedStatement ptm = null;
            boolean result = false;

            String sql = "INSERT INTO Home (code, name, address_text, status, owner_user_id) "
                       + "VALUES (?, ?, ?, ?, ?)";

            try {
                conn = DBUtils.getConnection();
                if (conn != null) {
                    ptm = conn.prepareStatement(sql);
                    ptm.setString(1, home.getCode());
                    ptm.setString(2, home.getName());
                    ptm.setString(3, home.getAddressText());
                    ptm.setString(4, home.getStatus());
                    if (home.getOwnerUserId() > 0) {
                        ptm.setInt(5, home.getOwnerUserId());
                    } else {
                        ptm.setNull(5, java.sql.Types.INTEGER);
                    }

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
    
    // home update
    public boolean updateHome(HomeDTO home) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "UPDATE Home SET code = ?, name = ?, address_text = ?, "
                   + "status = ?, owner_user_id = ? "
                   + "WHERE home_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, home.getCode());
                ptm.setString(2, home.getName());
                ptm.setString(3, home.getAddressText());
                ptm.setString(4, home.getStatus());
                if (home.getOwnerUserId() > 0) {
                    ptm.setInt(5, home.getOwnerUserId());
                } else {
                    ptm.setNull(5, java.sql.Types.INTEGER);
                }
                ptm.setInt(6, home.getHomeId());

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
    
    // delete home by id
    public boolean deleteHome(int homeId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "DELETE FROM Home WHERE home_id = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, homeId);

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
    
    public boolean assignOwnerToHome(String houseCode, int ownerUserId) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean result = false;

        String sql = "UPDATE Home SET owner_user_id = ? WHERE code = ?";

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, ownerUserId);
                ptm.setString(2, houseCode);

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
