/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.eventlog;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "SearchEventController", urlPatterns = {"/SearchEventController"})
public class SearchEventController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String RESULT_PAGE = "eventList.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            // Lấy tham số tìm kiếm
            String deviceIdStr = request.getParameter("txtDeviceId");
            String eventType = request.getParameter("txtEventType");
            String startTimeStr = request.getParameter("txtStartTime");
            String endTimeStr = request.getParameter("txtEndTime");

            // Parse parameters
            Integer deviceId = null;
            if (deviceIdStr != null && !deviceIdStr.trim().isEmpty()) {
                deviceId = Integer.parseInt(deviceIdStr);
            }

            Timestamp startTime = null;
            if (startTimeStr != null && !startTimeStr.trim().isEmpty()) {
                startTime = Timestamp.valueOf(startTimeStr + " 00:00:00");
            }

            Timestamp endTime = null;
            if (endTimeStr != null && !endTimeStr.trim().isEmpty()) {
                endTime = Timestamp.valueOf(endTimeStr + " 23:59:59");
            }

            // Gọi DAO search
            EventLogDAO dao = new EventLogDAO();
            List<EventLogDTO> eventList = dao.searchEvents(deviceId, eventType, startTime, endTime);

            request.setAttribute("EVENT_LIST", eventList);
            request.setAttribute("SEARCH_RESULT", "Found " + eventList.size() + " events");
            url = RESULT_PAGE;

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
        } catch (IllegalArgumentException e) {
            request.setAttribute("ERROR", "Invalid date format! Use: yyyy-MM-dd");
        } catch (Exception e) {
            log("Error at SearchEventController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
