package com.smarthome.controller.log;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ExportCSVController", urlPatterns = {"/ExportCSVController"})
public class ExportCSVController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 2. Lấy lại tham số tìm kiếm từ Form Export
        String keyword = request.getParameter("exportDevice");
        String filterDate = request.getParameter("exportDate");

        // Logic Smart Search (y hệt như SearchEventController)
        Integer deviceId = null;
        String eventType = null;
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            try {
                deviceId = Integer.parseInt(keyword);
            } catch (NumberFormatException e) {
                eventType = keyword;
            }
        }

        Timestamp startTime = null;
        Timestamp endTime = null;
        if (filterDate != null && !filterDate.trim().isEmpty()) {
            startTime = Timestamp.valueOf(filterDate + " 00:00:00");
            endTime = Timestamp.valueOf(filterDate + " 23:59:59");
        }

        try {
            // 3. Truy vấn lấy dữ liệu cần Export
            EventLogDAO dao = new EventLogDAO();
            List<EventLogDTO> list;
            if (deviceId == null && eventType == null && startTime == null) {
                list = dao.getAllEvents();
            } else {
                list = dao.searchEvents(deviceId, eventType, startTime, endTime);
            }

            // 4. Thiết lập Header để trình duyệt hiểu đây là File tải xuống
            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"event_logs_export.csv\"");
            
            // Dùng BOM để Excel không bị lỗi font Tiếng Việt
            PrintWriter out = response.getWriter();
            out.write('\ufeff');

            // 5. In Header của file CSV
            out.println("Log ID,Home Name,Room Name,Device Name,Device ID,Event Type,Event Value,Timestamp");

            // 6. Lặp và in Data
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (list != null && !list.isEmpty()) {
                for (EventLogDTO log : list) {
                    // Xử lý các dấu phẩy trong tên (nếu có) để không làm hỏng cấu trúc CSV
                    String home = log.getHomeName() != null ? "\"" + log.getHomeName() + "\"" : "N/A";
                    String room = log.getRoomName() != null ? "\"" + log.getRoomName() + "\"" : "N/A";
                    String device = log.getDeviceName() != null ? "\"" + log.getDeviceName() + "\"" : "N/A";
                    String ts = log.getTs() != null ? sdf.format(log.getTs()) : "N/A";

                    out.printf("%d,%s,%s,%s,%d,%s,%s,%s\n",
                            log.getEventId(), home, room, device,
                            log.getDeviceId(), log.getEventType(), log.getEventValue(), ts);
                }
            }
            
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi, trả về lại trang log kèm câu chửi
            request.setAttribute("ERROR_MSG", "Export Failed: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "log_management_section");
            request.getRequestDispatcher("admin/admin.jsp").forward(request, response);
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