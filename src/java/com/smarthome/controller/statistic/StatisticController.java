package com.smarthome.controller.statistic;

import com.smarthome.dao.StatisticDAO;
import com.smarthome.dto.StatisticDTO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "StatisticController", urlPatterns = {"/StatisticController"})
public class StatisticController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        StatisticDAO dao = new StatisticDAO();

        // Lấy dữ liệu dạng List
        List<StatisticDTO> eventStats = dao.getEventDistribution();
        List<StatisticDTO> lightStats = dao.getLightStatusPercentage();
        List<StatisticDTO> alertStats = dao.getAlertSeverityPercentage();

        // Ép List thành chuỗi JSON thủ công (để không cần tải thư viện Gson)
        request.setAttribute("JSON_EVENT_STATS", convertListToJson(eventStats));
        request.setAttribute("JSON_LIGHT_STATS", convertListToJson(lightStats));
        request.setAttribute("JSON_ALERT_STATS", convertListToJson(alertStats));

        // Mở đúng section statistics trong admin.jsp
        request.setAttribute("CURRENT_SECTION", "statistic_section");

        // ĐÃ SỬA: Phải trả về admin.jsp để giữ nguyên thanh Menu và bố cục
        request.getRequestDispatcher("admin/admin.jsp").forward(request, response);
    }

    // Hàm phụ trợ biến List thành chuỗi JSON: [{"label":"A","value":50}, ...]
    private String convertListToJson(List<StatisticDTO> list) {
        if (list == null || list.isEmpty()) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            StatisticDTO dto = list.get(i);
            sb.append("{")
              .append("\"label\": \"").append(dto.getName()).append("\", ")
              .append("\"value\": ").append(dto.getPercentage())
              .append("}");
            if (i < list.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
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