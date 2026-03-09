package com.smarthome.controller.user;

import com.smarthome.dao.UserDAO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateUserController", urlPatterns = {"/UpdateUserController"})
public class UpdateUserController extends HttpServlet {

    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "ViewUserController";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String url = ERROR_PAGE;

        try {
            String userIdStr = request.getParameter("txtUserId");
            String fullName = request.getParameter("txtFullName");
            String email = request.getParameter("txtEmail");
            String roleName = request.getParameter("txtRoleName");
            String statusStr = request.getParameter("txtStatus");

            System.out.println("txtUserId = " + userIdStr);
            System.out.println("txtFullName = " + fullName);
            System.out.println("txtEmail = " + email);
            System.out.println("txtRoleName = " + roleName);
            System.out.println("txtStatus = " + statusStr);

            int userId = Integer.parseInt(userIdStr);

            boolean status = "true".equalsIgnoreCase(statusStr) || "1".equals(statusStr);

            int roleId = 0;
            if ("Admin".equalsIgnoreCase(roleName)) {
                roleId = 1;
            } else if ("Home Owner".equalsIgnoreCase(roleName) || "House owner".equalsIgnoreCase(roleName)) {
                roleId = 2;
            } else if ("Technician".equalsIgnoreCase(roleName)) {
                roleId = 3;
            } else if ("Viewer".equalsIgnoreCase(roleName)) {
                roleId = 4;
            } else {
                request.setAttribute("ERROR_MSG", "Role name is invalid!");
                request.setAttribute("CURRENT_SECTION", "user_management_section");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setStatus(status);

            UserDAO dao = new UserDAO();
            boolean check = dao.updateUser(user, roleId);

            if (check) {
                url = SUCCESS_PAGE;
            } else {
                request.setAttribute("ERROR_MSG", "Cập nhật thất bại!");
                request.setAttribute("CURRENT_SECTION", "user_management_section");
            }

        } catch (Exception e) {
            log("Error at UpdateUserController: " + e.toString());
            request.setAttribute("ERROR_MSG", "Lỗi: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "user_management_section");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}