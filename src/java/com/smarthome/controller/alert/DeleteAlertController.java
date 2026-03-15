package com.smarthome.controller.alert;

import com.smarthome.dao.AlertDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DeleteAlertController", urlPatterns = {"/DeleteAlertController"})
public class DeleteAlertController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // 1. Bảo mật: Kiểm tra đăng nhập
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // 2. Lấy đúng tên tham số "alertId" từ Form
            String alertIdStr = request.getParameter("alertId");
            
            if (alertIdStr != null && !alertIdStr.isEmpty()) {
                int id = Integer.parseInt(alertIdStr);
                
                // 3. Gọi DAO xóa dữ liệu
                AlertDAO dao = new AlertDAO();
                boolean success = dao.delete(id);
                
                if (success) {
                    System.out.println("Deleted Alert ID: " + id);
                }
            }

            // 4. Redirect về ViewAlert (Chú ý chữ V viết hoa)
            response.sendRedirect("MainController?action=ViewAlert");

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi (như lỗi DB), vẫn redirect về trang danh sách để không bị kẹt trang trắng
            response.sendRedirect("MainController?action=ViewAlert");
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