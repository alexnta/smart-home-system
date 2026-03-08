/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.device;

import com.smarthome.dao.DeviceDAO;
import com.smarthome.dto.DeviceDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "CreateDeviceController", urlPatterns = {"/CreateDeviceController"})
public class CreateDeviceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "admin/admin.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            // Lấy thông tin từ form
            String roomIdStr = request.getParameter("txtRoomId");
            String name = request.getParameter("txtFacilityName");
            String deviceType = request.getParameter("txtDeviceType");
            String serialNo = request.getParameter("txtCode");
            String vendor = request.getParameter("txtVendor");
            String status = request.getParameter("txtStatus");
            String isActiveStr = request.getParameter("txtIsActive");
            
            

            // Validate
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device name is required!");
            } else if (deviceType == null || deviceType.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device type is required!");
            } else {
                int roomId = 0;
                if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                    roomId = Integer.parseInt(roomIdStr);
                }
                
                boolean isActive = true; // Mặc định là active
                if (isActiveStr != null && !isActiveStr.trim().isEmpty()) {
                    isActive = Boolean.parseBoolean(isActiveStr);
                }

                // Tạo DTO
                DeviceDTO device = new DeviceDTO();
                device.setRoomId(roomId);
                device.setName(name.trim());
                device.setDeviceType(deviceType.trim());
                device.setSerialNo(serialNo != null ? serialNo.trim() : "");
                device.setVendor(vendor != null ? vendor.trim() : "");
                device.setStatus(status != null ? status : "Off");
                device.setIsActive(isActive);

                // Insert vào DB
                DeviceDAO dao = new DeviceDAO();
                boolean success = dao.insertDevice(device);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                            request.setAttribute("ERROR", "Failed to create device!");
        request.setAttribute("CURRENT_SECTION", "add_facilities_section");
                }
            }

        } catch (NumberFormatException e) {
                request.setAttribute("ERROR", "Invalid number format!");
    request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } catch (Exception e) {
    log("Error at CreateDeviceController: " + e.toString());
    request.setAttribute("ERROR", "System error: " + e.getMessage());
    request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
