package com.smarthome.controller.homeowner;

import com.smarthome.dao.AlertDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOAckAlertController", urlPatterns = {"/HOAckAlertController"})
public class HOAckAlertController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Lấy ID của Alert cần xác nhận
            String alertIdStr = request.getParameter("alertId");
            if (alertIdStr != null && !alertIdStr.isEmpty()) {
                int alertId = Integer.parseInt(alertIdStr);
                
                // Gọi hàm ackAlert đã có sẵn trong AlertDAO
                AlertDAO dao = new AlertDAO();
                boolean check = dao.ackAlert(alertId);

                if (check) {
                    request.setAttribute("SUCCESS_MSG", "Đã xác nhận cảnh báo thành công!");
                } else {
                    request.setAttribute("ERROR_MSG", "Không thể xác nhận cảnh báo này.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Lỗi: " + e.getMessage());
        } finally {
            // QUAN TRỌNG: Điều hướng ngược lại HOViewAlert để nó load lại danh sách (Lúc này Alert đã mất đi vì không còn là Open nữa)
            request.getRequestDispatcher("MainController?action=HOViewAlert").forward(request, response);
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