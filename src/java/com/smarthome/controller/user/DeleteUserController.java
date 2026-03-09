package com.smarthome.controller;

import com.smarthome.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "DeleteUserController", urlPatterns = {"/DeleteUserController"})
public class DeleteUserController extends HttpServlet {

    private static final String ERROR_PAGE = "ViewUserController";
    private static final String SUCCESS_PAGE = "ViewUserController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        
        try {
            // Lấy userId từ URL (ví dụ: MainController?action=DeleteUser&userId=2)
            String userIdStr = request.getParameter("userId");
            
            if (userIdStr != null && !userIdStr.isEmpty()) {
                int userId = Integer.parseInt(userIdStr);
                UserDAO dao = new UserDAO();
                
                boolean check = dao.deleteUser(userId);
                if (check) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Không tìm thấy người dùng hoặc xóa thất bại!");
                }
            }
        } catch (Exception e) {
            log("Error at DeleteUserController: " + e.toString());
            request.setAttribute("ERROR_MSG", "Lỗi hệ thống khi xóa người dùng: Cấp quyền hoặc ràng buộc dữ liệu.");
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