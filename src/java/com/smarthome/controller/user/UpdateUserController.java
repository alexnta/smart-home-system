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
    private static final String SUCCESS_PAGE = "MainController?action=ViewUser";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean isSuccess = false;

        try {
            String userIdStr = request.getParameter("txtUserId");
            String fullName = request.getParameter("txtFullName");
            String email = request.getParameter("txtEmail");
            String roleName = request.getParameter("txtRoleName");
            String statusStr = request.getParameter("txtStatus");
            String houseCode = request.getParameter("txtHouseId");
            
            System.out.println("txtUserId = " + userIdStr);
            System.out.println("txtFullName = " + fullName);
            System.out.println("txtEmail = " + email);
            System.out.println("txtRoleName = " + roleName);
            System.out.println("txtStatus = " + statusStr);

            if (userIdStr == null || userIdStr.trim().isEmpty()
                    || fullName == null || fullName.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || roleName == null || roleName.trim().isEmpty()
                    || statusStr == null || statusStr.trim().isEmpty()) {

                request.setAttribute("ERROR_MSG", "Thiếu dữ liệu update!");
                request.setAttribute("CURRENT_SECTION", "user_management_section");
            } else {
                int userId = Integer.parseInt(userIdStr);
                boolean status = "1".equals(statusStr);

                int roleId = 0;
                switch (roleName.trim().toLowerCase()) {
                    case "admin":
                        roleId = 1;
                        break;
                    case "home owner":
                    case "house owner":
                        roleId = 2;
                        break;
                    case "technician":
                        roleId = 3;
                        break;
                    case "viewer":
                        roleId = 4;
                        break;
                }

                UserDTO user = new UserDTO();
                user.setUserId(userId);
                user.setFullName(fullName);
                user.setEmail(email);
                user.setStatus(status);

                UserDAO dao = new UserDAO();
                boolean check = dao.updateUser(user, roleId);

                if (check) {
                    isSuccess = true;
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Cập nhật thất bại!");
                    request.setAttribute("CURRENT_SECTION", "user_management_section");
                }
            }
        } catch (Exception e) {
            log("Error at UpdateUserController: " + e.toString());
            request.setAttribute("ERROR_MSG", "Lỗi: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "user_management_section");
        } finally {
            if (isSuccess) {
                // Tránh lỗi F5 resubmit form
                response.sendRedirect(url); 
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }
}