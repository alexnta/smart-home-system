package com.smarthome.controller.user;

import com.smarthome.dao.HomeDAO;
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
    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "MainController?action=ViewUser";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");
    
        String url = ERROR_PAGE;
        boolean isSuccess = false;
        
        try {
            System.out.println(request.getParameter("roleId"));
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            int roleId = Integer.parseInt(request.getParameter("roleId"));
            boolean status = request.getParameter("status") != null;

            // house id for house owner
            String houseCode = request.getParameter("txtHouseId");
            
            UserDTO user = new UserDTO(0, username, password, fullName, email, status, "");
            UserDAO dao = new UserDAO();
            
            int newUserId = dao.createUser(user, roleId); 
            
            if (newUserId > 0) {
                isSuccess = true;
                url = SUCCESS_PAGE;
                
                // Logic Assign Home
                if (roleId == 2 && houseCode != null && !houseCode.trim().isEmpty()) {
                    HomeDAO homeDao = new HomeDAO();
                    homeDao.assignOwnerToHome(houseCode, newUserId); 
                }
            } else {
                request.setAttribute("ERROR_MSG", "Tạo người dùng thất bại!");
                request.setAttribute("CURRENT_SECTION", "create_user_section");
            }
        } catch (Exception e) {
            log("Error at CreateUserController: " + e.toString());
            request.setAttribute("ERROR_MSG", "System Error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "create_user_section");
        } finally {
            if (isSuccess) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }
}