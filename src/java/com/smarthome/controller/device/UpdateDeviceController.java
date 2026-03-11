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
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String SUCCESS_CONTROLLER = "ViewDeviceController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String url = ADMIN_PAGE;

        try {
            HttpSession session = request.getSession(false);

            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            String role = loginUser.getRoleName();

            url = "Admin".equals(role) ? ADMIN_PAGE : TECHNICIAN_PAGE;

            String deviceIdStr = request.getParameter("txtDeviceId");
            String roomIdStr = request.getParameter("txtRoomId");
            String status = request.getParameter("txtStatus");

            if (deviceIdStr == null || deviceIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device ID is required!");
                request.setAttribute("CURRENT_SECTION", "device_management_section");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            int deviceId = Integer.parseInt(deviceIdStr);

            DeviceDAO dao = new DeviceDAO();

            if ("Admin".equals(role)) {
                DeviceDTO device = dao.getDeviceById(deviceId);

                if (device == null) {
                    request.setAttribute("ERROR", "Device not found!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                String name = request.getParameter("txtName");
                String deviceType = request.getParameter("txtDeviceType");
                String serialNo = request.getParameter("txtSerialNo");
                String vendor = request.getParameter("txtVendor");
                String isActiveStr = request.getParameter("txtIsActive");

                if (name == null || name.trim().isEmpty()) {
                    request.setAttribute("ERROR", "Device name is required!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                if (deviceType == null || deviceType.trim().isEmpty()) {
                    request.setAttribute("ERROR", "Device type is required!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                int roomId = (roomIdStr != null && !roomIdStr.trim().isEmpty())
                        ? Integer.parseInt(roomIdStr) : device.getRoomId();

                boolean isActive = (isActiveStr != null && !isActiveStr.trim().isEmpty())
                        ? Boolean.parseBoolean(isActiveStr) : device.isIsActive();

                device.setRoomId(roomId);
                device.setStatus(status != null && !status.trim().isEmpty() ? status.trim() : device.getStatus());
                device.setName(name.trim());
                device.setDeviceType(deviceType.trim());
                device.setSerialNo(serialNo != null ? serialNo.trim() : "");
                device.setVendor(vendor != null ? vendor.trim() : "");
                device.setIsActive(isActive);

                boolean success = dao.updateDevice(device);

                if (success) {
                    request.getRequestDispatcher(SUCCESS_CONTROLLER).forward(request, response);
                    return;
                } else {
                    request.setAttribute("ERROR", "Failed to update device!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

            } else if ("Technician".equals(role)) {
                if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                    request.setAttribute("ERROR", "Room ID is required!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

                int roomId = Integer.parseInt(roomIdStr);
                String newStatus = (status != null && !status.trim().isEmpty()) ? status.trim() : "Off";

                boolean success = dao.updateDeviceRoomAndStatus(deviceId, roomId, newStatus);

                if (success) {
                    request.getRequestDispatcher(SUCCESS_CONTROLLER).forward(request, response);
                    return;
                } else {
                    request.setAttribute("ERROR", "Failed to update device!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                    request.getRequestDispatcher(url).forward(request, response);
                    return;
                }

            } else {
                request.setAttribute("ERROR", "You do not have permission to update this device!");
                request.setAttribute("CURRENT_SECTION", "device_management_section");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
            request.setAttribute("CURRENT_SECTION", "device_management_section");
        } catch (Exception e) {
            log("Error at UpdateDeviceController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "device_management_section");
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}