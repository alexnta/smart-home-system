package com.smarthome.controller.user;

import com.smarthome.dao.UserDAO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ViewUserController", urlPatterns = {"/ViewUserController"})
public class ViewUserController extends HttpServlet {
private static final String USER_LIST_PAGE = "admin/admin.jsp";
private static final String ERROR_PAGE = "admin/admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR_PAGE;
        try {
            UserDAO dao = new UserDAO();
            List<UserDTO> userList = dao.getAllUsers();
            if (userList != null) {
                request.setAttribute("USER_LIST", userList);
                        request.setAttribute("CURRENT_SECTION", "user_management_section");
                                        url = USER_LIST_PAGE;

            }
        } catch (Exception e) {
            log("Error at ViewUserController: " + e.toString());
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