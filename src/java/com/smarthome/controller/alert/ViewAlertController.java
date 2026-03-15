package com.smarthome.controller.alert;

import com.smarthome.dao.AlertDAO;
import com.smarthome.dto.AlertDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ViewAlertController", urlPatterns = {"/ViewAlertController"})
public class ViewAlertController extends HttpServlet {

    private static final String ADMIN_PAGE = "admin/admin.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 1. Kiểm tra đăng nhập
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // 2. Lấy tham số tìm kiếm từ Form
            String status = request.getParameter("filterStatus");
            String severity = request.getParameter("filterSeverity");
            String keyword = request.getParameter("filterKeyword");

            // 3. Gọi DAO
            AlertDAO dao = new AlertDAO();
            List<AlertDTO> alertList = dao.searchAlerts(status, severity, keyword);

            // 4. Gửi dữ liệu về JSP
            request.setAttribute("ALERT_LIST", alertList);
            
            // Đảm bảo Form lưu lại giá trị cũ sau khi Search
            request.setAttribute("PARAM_STATUS", status);
            request.setAttribute("PARAM_SEVERITY", severity);
            request.setAttribute("PARAM_KEYWORD", keyword);
            
            request.setAttribute("CURRENT_SECTION", "alert_management_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Error loading alerts: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "alert_management_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
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