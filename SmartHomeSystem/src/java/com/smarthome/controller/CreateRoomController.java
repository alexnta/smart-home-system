/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller;

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
       private static final String ERROR_PAGE = "createRoom.jsp";
    private static final String SUCCESS_PAGE = "ViewRoomController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {

            String homeIdStr = request.getParameter("txtHomeId");
            String name = request.getParameter("txtName");
            String floorStr = request.getParameter("txtFloor");
            String roomType = request.getParameter("txtRoomType");
            String status = request.getParameter("txtStatus");

            // Validate
            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home ID is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Room name is required!");
            } else {
                int homeId = Integer.parseInt(homeIdStr);
                int floor = 1; // default
                if (floorStr != null && !floorStr.trim().isEmpty()) {
                    floor = Integer.parseInt(floorStr);
                }

                // DTO
                RoomDTO room = new RoomDTO();
                room.setHomeId(homeId);
                room.setName(name.trim());
                room.setFloor(floor);
                room.setRoomType(roomType != null ? roomType.trim() : "");
                room.setStatus(status != null ? status : "Active");

                // Insert vào DB
                RoomDAO dao = new RoomDAO();
                boolean success = dao.insertRoom(room);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to create room!");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
        } catch (Exception e) {
            log("Error at CreateRoomController: " + e.toString());
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
