package com.smarthome.controller.home;

import com.smarthome.dao.HomeDAO;
import com.smarthome.dto.HomeDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateHomeController", urlPatterns = {"/UpdateHomeController"})
public class UpdateHomeController extends HttpServlet {

    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "admin/admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            String homeIdStr = request.getParameter("txtHomeId");
            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtName");
            String address = request.getParameter("txtAddress");
            String status = request.getParameter("txtStatus");
            String ownerIdStr = request.getParameter("txtOwnerId");

            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home ID is required!");
                request.setAttribute("CURRENT_SECTION", "house_management_section");
            } else if (code == null || code.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home code is required!");
                request.setAttribute("CURRENT_SECTION", "house_management_section");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home name is required!");
                request.setAttribute("CURRENT_SECTION", "house_management_section");
            } else {
                int homeId = Integer.parseInt(homeIdStr);
                int ownerId = 0;
                if (ownerIdStr != null && !ownerIdStr.trim().isEmpty()) {
                    ownerId = Integer.parseInt(ownerIdStr);
                }

                HomeDTO home = new HomeDTO();
                home.setHomeId(homeId);
                home.setCode(code.trim());
                home.setName(name.trim());
                home.setAddressText(address != null ? address.trim() : "");
                home.setStatus(status != null ? status : "Active");
                home.setOwnerUserId(ownerId);

                HomeDAO dao = new HomeDAO();
                boolean success = dao.updateHome(home);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to update home!");
                    request.setAttribute("CURRENT_SECTION", "house_management_section");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
            request.setAttribute("CURRENT_SECTION", "house_management_section");
        } catch (Exception e) {
            log("Error at UpdateHomeController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "house_management_section");
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