package com.smarthome.controller.eventlog;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateEventServlet", urlPatterns = {"/CreateEventController"})
public class CreateEventController extends HttpServlet {

    private static final String ERROR_PAGE = "createEvent.jsp";
    private static final String SUCCESS_PAGE = "ViewEventController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            // Lấy thông tin từ form
            String deviceIdStr = request.getParameter("txtDeviceId");
            String eventType = request.getParameter("txtEventType");
            String eventValue = request.getParameter("txtEventValue");
            String timestampStr = request.getParameter("txtTimestamp");

            // Validate
            if (deviceIdStr == null || deviceIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Device ID is required!");
            } else if (eventType == null || eventType.trim().isEmpty()) {
                request.setAttribute("ERROR", "Event type is required!");
            } else {
                int deviceId = Integer.parseInt(deviceIdStr);
                
                // Nếu không nhập timestamp thì lấy thời gian hiện tại
                Timestamp ts = (timestampStr != null && !timestampStr.trim().isEmpty()) 
                    ? Timestamp.valueOf(timestampStr) 
                    : new Timestamp(System.currentTimeMillis());

                // Tạo DTO
                EventLogDTO event = new EventLogDTO();
                event.setDeviceId(deviceId);
                event.setEventType(eventType.trim());
                event.setEventValue(eventValue != null ? eventValue.trim() : "");
                event.setTs(ts);

                // Insert vào DB
                EventLogDAO dao = new EventLogDAO();
                boolean success = dao.insertEvent(event);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to create event log!");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
        } catch (Exception e) {
            log("Error at CreateEventController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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