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

@WebServlet(name = "HOUpdateModeController", urlPatterns = {"/HOUpdateModeController"})
public class HOUpdateModeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            // Lấy tên chế độ mà người dùng vừa bấm (Home, Away, hoặc Night)
            String modeName = request.getParameter("modeName");
            
            HODashboardDAO hoDao = new HODashboardDAO();
            int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

            if (homeId != -1 && modeName != null && !modeName.isEmpty()) {
                boolean check = hoDao.updateHomeMode(homeId, modeName);
                if (check) {
                    request.setAttribute("SUCCESS_MSG", "Home mode updated to " + modeName + " successfully!");
                } else {
                    request.setAttribute("ERROR_MSG", "Failed to update home mode.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Mode Update Error: " + e.getMessage());
        } finally {
            // Xong xuôi thì lùi về HODashboard để nó load lại dữ liệu hiển thị (kẹp theo lời nhắn mở tab mode)
            request.getRequestDispatcher("MainController?action=HODashboard&targetTab=mode_section").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}