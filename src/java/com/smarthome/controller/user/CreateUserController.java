package com.smarthome.controller.user;

import com.smarthome.dao.UserDAO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CreateUserController", urlPatterns = {"/CreateUserController"})
public class CreateUserController extends HttpServlet {
    private static final String SUCCESS = "MainController?action=ViewUser";
    private static final String ERROR = "user_create.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Xử lý tiếng Việt
        String url = ERROR;
        try {
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            int roleId = Integer.parseInt(request.getParameter("roleId")); // Lấy RoleID từ thẻ <select>
            boolean status = request.getParameter("status") != null; // Checkbox

            UserDTO user = new UserDTO(0, username, password, fullName, email, status, "");
            UserDAO dao = new UserDAO();
            boolean check = dao.createUser(user, roleId);
            
            if (check) {
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR_MSG", "Tạo người dùng thất bại!");
            }
        } catch (Exception e) {
            log("Error at CreateUserController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}