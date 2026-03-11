package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteRuleController", urlPatterns = {"/DeleteRuleController"})
public class DeleteRuleController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            int ruleId = Integer.parseInt(request.getParameter("ruleId"));
            String homeId = request.getParameter("homeId");

            RuleDAO dao = new RuleDAO();
            boolean success = dao.delete(ruleId);

            if (success) {
                response.sendRedirect("MainController?action=ViewRule&homeId=" + homeId);
            } else {
                request.setAttribute("ERROR", "Failed to delete rule!");
                request.setAttribute("CURRENT_SECTION", "edit_rule_section");
                request.getRequestDispatcher("admin/admin.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            request.getRequestDispatcher("admin/admin.jsp").forward(request, response);
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