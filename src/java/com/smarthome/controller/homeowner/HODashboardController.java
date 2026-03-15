package com.smarthome.controller.homeowner;

import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HODashboardController", urlPatterns = {"/HODashboardController"})
public class HODashboardController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra session và Role (Chỉ HomeOwner mới được vào)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        if (!user.getRoleName().equals("Home Owner")) { // Giả sử 2 là HomeOwner
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            HODashboardDAO dao = new HODashboardDAO();

            // 2. Tìm Home ID của người này
            int homeId = dao.getHomeIdByOwnerId(user.getUserId());

            if (homeId == -1) {
                // Người này chưa được Admin cấp phát nhà
                request.setAttribute("ERROR_MSG", "You don't have any assigned home yet. Please contact Admin.");
            } else {
                // 3. Nếu có nhà, lấy số liệu thống kê
                String currentMode = dao.getHomeMode(homeId);
                int recentEvents = dao.countRecentEvents(homeId);
                int activeAlerts = dao.countActiveAlerts(homeId);
                int onlineDevices = dao.countOnlineDevices(homeId);
                int totalDevices = dao.countTotalDevices(homeId);

                // 4. Đẩy số liệu sang JSP
                request.setAttribute("HOME_ID", homeId);
                request.setAttribute("HOME_MODE", currentMode);
                request.setAttribute("STAT_EVENTS", recentEvents);
                request.setAttribute("STAT_ALERTS", activeAlerts);
                request.setAttribute("STAT_ONLINE_DEV", onlineDevices);
                request.setAttribute("STAT_TOTAL_DEV", totalDevices);
                
                request.setAttribute("STAT_MODE", currentMode);
            }

            // Luôn mở Tab Dashboard đầu tiên
            request.setAttribute("CURRENT_SECTION", "dashboard_section");
            request.getRequestDispatcher("/home_owner/home_owner.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Dashboard Error: " + e.getMessage());
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