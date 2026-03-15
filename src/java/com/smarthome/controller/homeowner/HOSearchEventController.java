package com.smarthome.controller.homeowner;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dto.EventLogDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOSearchEventController", urlPatterns = {"/HOSearchEventController"})
public class HOSearchEventController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            // Lấy tham số search
            String txtDeviceId = request.getParameter("txtDeviceId");
            String eventType = request.getParameter("txtEventType");
            String txtStartTime = request.getParameter("txtStartTime");
            String txtEndTime = request.getParameter("txtEndTime");

            Integer deviceId = null;
            if (txtDeviceId != null && !txtDeviceId.trim().isEmpty()) {
                deviceId = Integer.parseInt(txtDeviceId);
            }
            Timestamp startTime = (txtStartTime != null && !txtStartTime.isEmpty()) ? Timestamp.valueOf(txtStartTime + " 00:00:00") : null;
            Timestamp endTime = (txtEndTime != null && !txtEndTime.isEmpty()) ? Timestamp.valueOf(txtEndTime + " 23:59:59") : null;

            // Tìm Home ID
            HODashboardDAO hoDao = new HODashboardDAO();
            int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

            if (homeId != -1) {
                EventLogDAO dao = new EventLogDAO();
                List<EventLogDTO> list = dao.searchHomeEvents(homeId, deviceId, eventType, startTime, endTime);
                request.setAttribute("EVENT_LIST", list);
                if (list.isEmpty()) {
                    request.setAttribute("SEARCH_RESULT", "No logs found matching your criteria.");
                }
            } else {
                request.setAttribute("ERROR_MSG", "Home not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Search Error: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "log_section");
            request.getRequestDispatcher("home_owner/home_owner.jsp").forward(request, response);
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