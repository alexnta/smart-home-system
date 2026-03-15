/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.room;

import com.smarthome.dao.RoomDAO;
import com.smarthome.dto.RoomDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "CreateRoomController", urlPatterns = {"/CreateRoomController"})
public class CreateRoomController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
       private static final String ERROR_PAGE ="admin/admin.jsp";
    private static final String SUCCESS_PAGE ="MainController?action=ViewRoom";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean success = false;

        try {
            String homeIdStr = request.getParameter("txtHomeId");
            String name = request.getParameter("txtFacilityName");
            String floorStr = request.getParameter("txtFloor");
            String roomType = request.getParameter("txtRoomType");
            String status = request.getParameter("txtStatus");
            
            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home ID is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Room name is required!");
            } else {
                int homeId = Integer.parseInt(homeIdStr);
                int floor = 1;
                if (floorStr != null && !floorStr.trim().isEmpty()) {
                    floor = Integer.parseInt(floorStr);
                }

                RoomDTO room = new RoomDTO();
                room.setHomeId(homeId);
                room.setName(name.trim());
                room.setFloor(floor);
                room.setRoomType(roomType != null ? roomType.trim() : "");
                room.setStatus(status != null ? status : "Active");

                RoomDAO dao = new RoomDAO();
                success = dao.insertRoom(room);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Failed to create room!");
                    request.setAttribute("CURRENT_SECTION", "add_facilities_section");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR_MSG", "Invalid number format for Home ID or Floor!");
            request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } catch (Exception e) {
            log("Error at CreateRoomController: " + e.toString());
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } finally {
            // ÁP DỤNG PRG Ở ĐÂY
            if (success) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
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
