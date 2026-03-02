package com.smarthome.controller;

import com.smarthome.dao.RoomDAO;
import com.smarthome.dto.RoomDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateRoomServlet", urlPatterns = {"/UpdateRoomController"})
public class UpdateRoomController extends HttpServlet {

    private static final String ERROR_PAGE = "updateRoom.jsp";
    private static final String SUCCESS_PAGE = "ViewRoomController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            // Lấy thông tin từ form
            String roomIdStr = request.getParameter("txtRoomId");
            String homeIdStr = request.getParameter("txtHomeId");
            String name = request.getParameter("txtName");
            String floorStr = request.getParameter("txtFloor");
            String roomType = request.getParameter("txtRoomType");
            String status = request.getParameter("txtStatus");

            // Validate
            if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Room ID is required!");
            } else if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home ID is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Room name is required!");
            } else {
                int roomId = Integer.parseInt(roomIdStr);
                int homeId = Integer.parseInt(homeIdStr);
                int floor = 1;
                if (floorStr != null && !floorStr.trim().isEmpty()) {
                    floor = Integer.parseInt(floorStr);
                }

                // Tạo DTO
                RoomDTO room = new RoomDTO();
                room.setRoomId(roomId);
                room.setHomeId(homeId);
                room.setName(name.trim());
                room.setFloor(floor);
                room.setRoomType(roomType != null ? roomType.trim() : "");
                room.setStatus(status != null ? status : "Active");

                // Update vào DB
                RoomDAO dao = new RoomDAO();
                boolean success = dao.updateRoom(room);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to update room!");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
        } catch (Exception e) {
            log("Error at UpdateRoomController: " + e.toString());
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