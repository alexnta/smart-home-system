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
    private static final String SUCCESS_PAGE = "MainController?action=ViewHome";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean isSuccess = false;

        try {
            String homeIdStr = request.getParameter("txtHomeId");
            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtFacilityName"); // <-- ĐÃ SỬA THÀNH txtFacilityName
            String address = request.getParameter("txtAddress");
            String status = request.getParameter("txtStatus");
            String ownerIdStr = request.getParameter("txtOwnerId");

            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home ID is required!");
            } else if (code == null || code.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home code is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home name is required!");
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
                isSuccess = dao.updateHome(home);

                if (isSuccess) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Failed to update home in database!");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR_MSG", "Invalid number format for Home ID or Owner ID!");
        } catch (Exception e) {
            log("Error at UpdateHomeController: " + e.toString());
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "house_management_section");
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