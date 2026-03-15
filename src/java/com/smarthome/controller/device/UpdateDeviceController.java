package com.smarthome.controller.device;

import com.smarthome.dao.DeviceDAO;
import com.smarthome.dto.DeviceDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UpdateDeviceController", urlPatterns = {"/UpdateDeviceController"})
public class UpdateDeviceController extends HttpServlet {

    private static final String ADMIN_PAGE = "admin/admin.jsp";
    private static final String TECHNICIAN_PAGE = "technician/technician.jsp";
    private static final String SUCCESS_PAGE = "MainController?action=ViewDevice";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ADMIN_PAGE;
        boolean isSuccess = false;

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            String role = loginUser.getRoleName();
            url = "Admin".equals(role) ? ADMIN_PAGE : TECHNICIAN_PAGE;

            String deviceIdStr = request.getParameter("txtDeviceId");
            if (deviceIdStr == null || deviceIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Device ID is required!");
            } else {
                int deviceId = Integer.parseInt(deviceIdStr);
                DeviceDAO dao = new DeviceDAO();
                DeviceDTO device = dao.getDeviceById(deviceId);

                if (device == null) {
                    request.setAttribute("ERROR_MSG", "Device not found!");
                } else {
                    // Logic tùy thuộc vào người dùng
                    if ("Admin".equals(role)) {
                        String homeIdStr = request.getParameter("txtHomeId"); // Lấy thêm homeId
                        String roomIdStr = request.getParameter("txtRoomId");
                        String name = request.getParameter("txtFacilityName"); // <-- ĐÃ SỬA
                        String deviceType = request.getParameter("txtDeviceType");
                        String serialNo = request.getParameter("txtCode"); // <-- ĐÃ SỬA
                        String vendor = request.getParameter("txtVendor");
                        String status = request.getParameter("txtStatus");
                        
                        if (name == null || name.trim().isEmpty()) {
                            request.setAttribute("ERROR_MSG", "Device name is required!");
                        } else {
                            int homeId = (homeIdStr != null && !homeIdStr.trim().isEmpty()) ? Integer.parseInt(homeIdStr) : device.getHomeId();
                            int roomId = (roomIdStr != null && !roomIdStr.trim().isEmpty()) ? Integer.parseInt(roomIdStr) : 0;

                            device.setHomeId(homeId);
                            device.setRoomId(roomId);
                            device.setName(name.trim());
                            device.setDeviceType(deviceType != null ? deviceType.trim() : device.getDeviceType());
                            device.setSerialNo(serialNo != null ? serialNo.trim() : device.getSerialNo());
                            device.setVendor(vendor != null ? vendor.trim() : device.getVendor());
                            device.setStatus(status != null ? status.trim() : device.getStatus());
                            
                            isSuccess = dao.updateDevice(device);
                            if (!isSuccess) request.setAttribute("ERROR_MSG", "Failed to update device details!");
                        }
                    } else if ("Technician".equals(role)) {
                        String roomIdStr = request.getParameter("txtRoomId");
                        String status = request.getParameter("txtStatus");
                        
                        int roomId = (roomIdStr != null && !roomIdStr.trim().isEmpty()) ? Integer.parseInt(roomIdStr) : 0;
                        String newStatus = (status != null && !status.trim().isEmpty()) ? status.trim() : "Off";
                        
                        isSuccess = dao.updateDeviceRoomAndStatus(deviceId, roomId, newStatus);
                        if (!isSuccess) request.setAttribute("ERROR_MSG", "Failed to update device status!");
                    } else {
                        request.setAttribute("ERROR_MSG", "Permission Denied: You cannot update this device.");
                    }
                }
            }

            if (isSuccess) {
                url = SUCCESS_PAGE;
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR_MSG", "Invalid ID format!");
        } catch (Exception e) {
            log("Error at UpdateDeviceController: " + e.toString());
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "device_management_section");
            if (isSuccess) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}