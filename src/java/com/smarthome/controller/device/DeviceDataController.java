/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.device;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import com.smarthome.engine.AlertEngine;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class DeviceDataController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
     
//        try {
//
//            int homeId = Integer.parseInt(request.getParameter("homeId"));
//            int deviceId = Integer.parseInt(request.getParameter("deviceId"));
//            double value = Double.parseDouble(request.getParameter("value"));
//
//            AlertEngine engine = new AlertEngine();
//            /*engine.processDeviceData(homeId, deviceId, value);*/
//
//          
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try (PrintWriter out = response.getWriter()) {
            // 1. Lấy thông số gửi lên từ thiết bị (hoặc giả lập từ Postman/Browser)
            String homeIdStr = request.getParameter("homeId");
            String deviceIdStr = request.getParameter("deviceId");
            String eventType = request.getParameter("eventType");   // VD: DoorOpen, LightOn, Heartbeat...
            String eventValue = request.getParameter("eventValue"); // VD: Open, Closed, On, Off...

            // Validate dữ liệu cơ bản
            if (homeIdStr == null || deviceIdStr == null || eventType == null) {
                out.print("{\"status\":\"error\", \"message\":\"Missing parameters!\"}");
                return;
            }

            int homeId = Integer.parseInt(homeIdStr);
            int deviceId = Integer.parseInt(deviceIdStr);

            // 2. TẠO VÀ LƯU EVENT LOG VÀO DATABASE (QUAN TRỌNG)
            // Phải lưu trước để các luật loại 'Threshold' có dữ liệu lịch sử để tính toán
            EventLogDTO eventLog = new EventLogDTO();
            eventLog.setDeviceId(deviceId);
            eventLog.setEventType(eventType.trim());
            eventLog.setEventValue(eventValue != null ? eventValue.trim() : "");
            eventLog.setTs(new Timestamp(System.currentTimeMillis()));

            EventLogDAO eventLogDAO = new EventLogDAO();
            boolean isEventSaved = eventLogDAO.insertEvent(eventLog);

            if (!isEventSaved) {
                out.print("{\"status\":\"error\", \"message\":\"Failed to save Event Log.\"}");
                return;
            }

            // 3. KÍCH HOẠT ALERT ENGINE
            // Ngay sau khi có sự kiện mới, động cơ kiểm tra luật sẽ chạy ngay lập tức
            AlertEngine engine = new AlertEngine();
            engine.processDeviceData(homeId, deviceId, eventType, eventValue);

            // 4. Phản hồi về cho thiết bị (hoặc client gọi API)
            out.print("{\"status\":\"success\", \"message\":\"Device data processed and rules evaluated.\"}");

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, in ra log hệ thống
            log("Error in DeviceDataController: " + e.getMessage());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
