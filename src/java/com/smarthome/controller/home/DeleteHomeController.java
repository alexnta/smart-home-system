package com.smarthome.controller.home;

import com.smarthome.dao.HomeDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteHomeController", urlPatterns = {"/DeleteHomeController"})
public class DeleteHomeController extends HttpServlet {

    private static final String ERROR_PAGE = "dashboard/dashboard.jsp";
    private static final String SUCCESS_PAGE = "dashboard/dashboard.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            System.out.println("=== vao DeleteHomeController ===");

            String homeIdStr = request.getParameter("homeId");
            System.out.println("homeIdStr = " + homeIdStr);

            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home ID is required!");
            } else {
                int homeId = Integer.parseInt(homeIdStr);

                HomeDAO dao = new HomeDAO();
                boolean success = dao.deleteHome(homeId);
                System.out.println("delete success = " + success);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to delete home! Home might not exist.");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid Home ID format!");
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at DeleteHomeController: " + e.toString());
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