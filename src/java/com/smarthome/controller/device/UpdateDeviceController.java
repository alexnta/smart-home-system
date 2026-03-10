package com.smarthome.controller.device;

import com.smarthome.dao.DeviceDAO;
import com.smarthome.dto.DeviceDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateDeviceController", urlPatterns = {"/UpdateDeviceController"})
public class UpdateDeviceController extends HttpServlet {

    private static final String ERROR_PAGE = "dashboard/dashboard.jsp";
    private static final String SUCCESS_PAGE = "ViewDeviceController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            String deviceIdStr = request.getParameter("txtDeviceId");
            String roomIdStr = request.getParameter("txtRoomId");
            String name = request.getParameter("txtName");
            String deviceType = request.getParameter("txtDeviceType");
            String serialNo = request.getParameter("txtSerialNo");
            String vendor = request.getParameter("txtVendor");
            String status = request.getParameter("txtStatus");
            String isActiveStr = request.getParameter("txtIsActive");

            if (deviceIdStr == null || deviceIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device ID is required!");
                request.setAttribute("CURRENT_SECTION", "device_management_section");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device name is required!");
                request.setAttribute("CURRENT_SECTION", "device_management_section");
            } else if (deviceType == null || deviceType.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device type is required!");
                request.setAttribute("CURRENT_SECTION", "device_management_section");
            } else {
                int deviceId = Integer.parseInt(deviceIdStr);
                int roomId = 0;
                if (roomIdStr != null && !roomIdStr.trim().isEmpty()) {
                    roomId = Integer.parseInt(roomIdStr);
                }

                boolean isActive = true;
                if (isActiveStr != null && !isActiveStr.trim().isEmpty()) {
                    isActive = Boolean.parseBoolean(isActiveStr);
                }

                DeviceDTO device = new DeviceDTO();
                device.setDeviceId(deviceId);
                device.setRoomId(roomId);
                device.setName(name.trim());
                device.setDeviceType(deviceType.trim());
                device.setSerialNo(serialNo != null ? serialNo.trim() : "");
                device.setVendor(vendor != null ? vendor.trim() : "");
                device.setStatus(status != null ? status : "Off");
                device.setIsActive(isActive);

                DeviceDAO dao = new DeviceDAO();
                boolean success = dao.updateDevice(device);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to update device!");
                    request.setAttribute("CURRENT_SECTION", "device_management_section");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
            request.setAttribute("CURRENT_SECTION", "device_management_section");
        } catch (Exception e) {
            log("Error at UpdateDeviceController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "device_management_section");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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