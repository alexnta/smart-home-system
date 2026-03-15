package com.smarthome.controller.room;

import com.smarthome.dao.RoomDAO;
import com.smarthome.dto.RoomDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateRoomController", urlPatterns = {"/UpdateRoomController"})
public class UpdateRoomController extends HttpServlet {

    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "MainController?action=ViewRoom";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean isSuccess = false;

        try {
            String roomIdStr = request.getParameter("txtRoomId");
            String homeIdStr = request.getParameter("txtHomeId");
            String name = request.getParameter("txtFacilityName"); // <-- ĐÃ SỬA
            String floorStr = request.getParameter("txtFloor");
            String roomType = request.getParameter("txtRoomType");
            String status = request.getParameter("txtStatus");

            if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Room ID is required!");
            } else if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home ID is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Room name is required!");
            } else {
                int roomId = Integer.parseInt(roomIdStr);
                int homeId = Integer.parseInt(homeIdStr);
                int floor = 1;
                if (floorStr != null && !floorStr.trim().isEmpty()) {
                    floor = Integer.parseInt(floorStr);
                }

                RoomDTO room = new RoomDTO();
                room.setRoomId(roomId);
                room.setHomeId(homeId);
                room.setName(name.trim());
                room.setFloor(floor);
                room.setRoomType(roomType != null ? roomType.trim() : "Other");
                room.setStatus(status != null ? status : "Active");

                RoomDAO dao = new RoomDAO();
                isSuccess = dao.updateRoom(room);

                if (isSuccess) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Failed to update room!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR_MSG", "Invalid number format!");
        } catch (Exception e) {
            log("Error at UpdateRoomController: " + e.toString());
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "room_management_section");
            if (isSuccess) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}