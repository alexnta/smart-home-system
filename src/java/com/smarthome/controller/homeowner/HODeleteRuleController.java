package com.smarthome.controller.homeowner;

import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HODeleteRuleController", urlPatterns = {"/HODeleteRuleController"})
public class HODeleteRuleController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            String ruleIdStr = request.getParameter("ruleId");
            if (ruleIdStr != null && !ruleIdStr.isEmpty()) {
                int ruleId = Integer.parseInt(ruleIdStr);
                
                // Tìm homeId của chủ nhà để kiểm tra bảo mật
                HODashboardDAO hoDao = new HODashboardDAO();
                int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

                if (homeId != -1) {
                    RuleDAO dao = new RuleDAO();
                    boolean check = dao.deleteRule(ruleId, homeId);
                    
                    if (check) {
                        request.setAttribute("SUCCESS_MSG", "Rule deleted successfully!");
                    } else {
                        request.setAttribute("ERROR_MSG", "Failed to delete rule. It might belong to another home or system template.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Delete Error: " + e.getMessage());
        } finally {
            // Xóa xong thì load lại danh sách Rule mới nhất
            request.getRequestDispatcher("MainController?action=HOViewRule").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}