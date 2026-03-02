package com.smarthome.controller;

import com.smarthome.dao.RoomDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteRoomServlet", urlPatterns = {"/DeleteRoomController"})
public class DeleteRoomController extends HttpServlet {

    private static final String ERROR_PAGE = "error.jsp";
    private static final String SUCCESS_PAGE = "ViewRoomController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            String roomIdStr = request.getParameter("roomId");

            if (roomIdStr == null || roomIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Room ID is required!");
            } else {
                int roomId = Integer.parseInt(roomIdStr);

                RoomDAO dao = new RoomDAO();
                boolean success = dao.deleteRoom(roomId);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to delete room! Room might not exist.");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid Room ID format!");
        } catch (Exception e) {
            log("Error at DeleteRoomController: " + e.toString());
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