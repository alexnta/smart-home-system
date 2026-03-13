package com.smarthome.dao;

import com.smarthome.dto.StatisticDTO;
import com.smarthome.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAO {

    // 1. Event Distribution
    public List<StatisticDTO> getEventDistribution() {
        List<StatisticDTO> list = new ArrayList<>();

        String sql = "SELECT event_type, COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS percentage "
                + "FROM EventLog "
                + "GROUP BY event_type";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("event_type");
                double percentage = rs.getDouble("percentage");

                list.add(new StatisticDTO(name, percentage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Light Status Percentage
    public List<StatisticDTO> getLightStatusPercentage() {
        List<StatisticDTO> list = new ArrayList<>();

        String sql
                = "SELECT event_type, COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS percentage "
                + "FROM EventLog "
                + "WHERE event_type IN ('LightOn','LightOff') "
                + "GROUP BY event_type";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String name = rs.getString("event_type");
                double percentage = rs.getDouble("percentage");

                list.add(new StatisticDTO(name, percentage));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 3. Alert Severity Percentage
    public List<StatisticDTO> getAlertSeverityPercentage() {
        List<StatisticDTO> list = new ArrayList<>();

        String sql = "SELECT severity, COUNT(*) * 100.0 / SUM(COUNT(*)) OVER() AS percentage "
                + "FROM Alert "
                + "GROUP BY severity";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("severity");
                double percentage = rs.getDouble("percentage");

                list.add(new StatisticDTO(name, percentage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
