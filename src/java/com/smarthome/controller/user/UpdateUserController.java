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
    private static final String SUCCESS = "MainController?action=ViewUser";
    private static final String ERROR = "user_edit.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            boolean status = "1".equals(request.getParameter("status")); // Hoặc từ checkbox

            UserDTO user = new UserDTO(userId, "", "", fullName, email, status, "");
            UserDAO dao = new UserDAO();
            boolean check = dao.updateUser(user, roleId);
            
            if (check) {
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR_MSG", "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            log("Error at UpdateUserController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}