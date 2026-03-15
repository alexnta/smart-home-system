package com.smarthome.controller.eventlog;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SearchEventController", urlPatterns = {"/SearchEventController"})
public class SearchEventController extends HttpServlet {

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

            // 2. Lấy dữ liệu từ Form (Khớp với name trong admin.jsp)
            String keyword = request.getParameter("filterDevice");
            String filterDate = request.getParameter("filterDate");

            // 3. SMART SEARCH LOGIC (Đoán xem người dùng nhập số hay chữ)
            Integer deviceId = null;
            String eventType = null;

            if (keyword != null && !keyword.trim().isEmpty()) {
                keyword = keyword.trim();
                try {
                    deviceId = Integer.parseInt(keyword); // Thử ép kiểu thành số (Device ID)
                } catch (NumberFormatException e) {
                    eventType = keyword; // Nếu lỗi (không phải số) -> Đó là chữ (Event Type)
                }
            }

            // 4. XỬ LÝ NGÀY THÁNG (Lấy từ 00:00:00 đến 23:59:59 của ngày được chọn)
            Timestamp startTime = null;
            Timestamp endTime = null;

            if (filterDate != null && !filterDate.trim().isEmpty()) {
                startTime = Timestamp.valueOf(filterDate + " 00:00:00");
                endTime = Timestamp.valueOf(filterDate + " 23:59:59");
            }

            // 5. Gọi DAO tìm kiếm
            EventLogDAO dao = new EventLogDAO();
            List<EventLogDTO> eventList;
            
            // Nếu không nhập gì, lấy tất cả (Hàm getAllEvents mình đã fix ở DAO trước đó)
            if (deviceId == null && eventType == null && startTime == null) {
                eventList = dao.getAllEvents();
            } else {
                eventList = dao.searchEvents(deviceId, eventType, startTime, endTime);
            }

            // 6. Gửi dữ liệu về JSP
            request.setAttribute("EVENT_LIST", eventList);
            request.setAttribute("CURRENT_SECTION", "log_management_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("ERROR_MSG", "Invalid date format!");
            request.setAttribute("CURRENT_SECTION", "log_management_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "log_management_section");
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