package com.smarthome.controller.homeowner;

import com.smarthome.dao.HODashboardDAO;
import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HOViewRuleController", urlPatterns = {"/HOViewRuleController"})
public class HOViewRuleController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

        try {
            HODashboardDAO hoDao = new HODashboardDAO();
            int homeId = hoDao.getHomeIdByOwnerId(user.getUserId());

            if (homeId != -1) {
                RuleDAO ruleDao = new RuleDAO();
                List<RuleDTO> list = ruleDao.getRulesByHomeId(homeId);
                request.setAttribute("RULE_LIST", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Error loading rules: " + e.getMessage());
        } finally {
            request.setAttribute("CURRENT_SECTION", "rules_section");
            request.getRequestDispatcher("/home_owner/home_owner.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}