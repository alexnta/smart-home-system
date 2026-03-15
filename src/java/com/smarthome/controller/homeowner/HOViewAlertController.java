package com.smarthome.controller.homeowner;

import com.smarthome.dao.AlertDAO;
import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dto.AlertDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOViewAlertController", urlPatterns = {"/HOViewAlertController"})
public class HOViewAlertController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            // 1. Tự động lấy Home ID của chủ nhà
            HODashboardDAO hoDao = new HODashboardDAO();
            int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

            if (homeId != -1) {
                // 2. Lấy danh sách Alert đang Open của đúng nhà này (Hàm này bạn đã có sẵn trong AlertDAO)
                AlertDAO alertDao = new AlertDAO();
                List<AlertDTO> list = alertDao.getOpenAlerts(homeId);
                
                // 3. Đẩy lên giao diện
                request.setAttribute("ALERT_LIST", list);
            } else {
                request.setAttribute("ERROR_MSG", "You don't have any assigned home.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Error loading alerts: " + e.getMessage());
        } finally {
            // Cố định mở tab Alert
            request.setAttribute("CURRENT_SECTION", "alert_section");
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