package com.smarthome.controller.homeowner;

import com.smarthome.dao.DeviceDAO;
import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dto.DeviceDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOViewDeviceController", urlPatterns = {"/HOViewDeviceController"})
public class HOViewDeviceController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            // 1. Tìm Home ID của Owner
            HODashboardDAO hoDao = new HODashboardDAO();
            int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

            if (homeId != -1) {
                // 2. Lấy danh sách thiết bị của riêng nhà này
                DeviceDAO deviceDao = new DeviceDAO();
                List<DeviceDTO> myDevices = deviceDao.getDevicesByHomeId(homeId);
                
                // 3. Đẩy lên JSP
                request.setAttribute("DEVICE_LIST", myDevices);
            } else {
                request.setAttribute("ERROR_MSG", "You don't have any assigned home.");
            }

            // Luôn mở Tab Devices
            request.setAttribute("CURRENT_SECTION", "device_section");
            request.getRequestDispatcher("/home_owner/home_owner.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Error loading devices: " + e.getMessage());
            request.getRequestDispatcher("/home_owner/home_owner.jsp").forward(request, response);
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