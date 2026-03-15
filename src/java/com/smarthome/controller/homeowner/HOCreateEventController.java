package com.smarthome.controller.homeowner;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOCreateEventController", urlPatterns = {"/HOCreateEventController"})
public class HOCreateEventController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int deviceId = Integer.parseInt(request.getParameter("deviceId"));
            String eventType = request.getParameter("eventType");
            String eventValue = request.getParameter("eventValue");
            Timestamp ts = new Timestamp(System.currentTimeMillis());

            EventLogDTO event = new EventLogDTO(0, deviceId, eventType, eventValue, ts, null, null, null);
            EventLogDAO dao = new EventLogDAO();
            
            // Hàm này bạn đã có sẵn trong EventLogDAO, nó tự động ghi log VÀ cập nhật trạng thái Device
            boolean check = dao.insertEvent(event);

            if (check) {
                request.setAttribute("SUCCESS_MSG", "Event simulated successfully! Device status updated.");
            } else {
                request.setAttribute("ERROR_MSG", "Failed to simulate event.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Simulator Error: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "simulator_section");
            request.getRequestDispatcher("MainController?action=HODashboard").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}