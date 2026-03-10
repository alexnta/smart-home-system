package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateRuleController", urlPatterns = {"/UpdateRuleController"})
public class UpdateRuleController extends HttpServlet {

    private static final String ERROR_PAGE = "/dashboard/dashboard.jsp";
    private static final String SUCCESS_PAGE = "/dashboard/dashboard.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
             
            System.out.println("ruleName = " + request.getParameter("ruleName"));
            System.out.println("triggerType = " + request.getParameter("triggerType"));
            System.out.println("conditionjson = " + request.getParameter("conditionjson"));
            System.out.println("severity = " + request.getParameter("severity"));

            RuleDTO rule = new RuleDTO();
rule.setRuleId(Integer.parseInt(request.getParameter("ruleId")));
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setTriggerType(request.getParameter("triggerType"));
            rule.setConditionJson(request.getParameter("conditionjson"));
            rule.setSeverity(request.getParameter("severity"));

            RuleDAO dao = new RuleDAO();
            boolean success = dao.update(rule);

            if (success) {
                url = SUCCESS_PAGE;
                request.setAttribute("SUCCESS", "Update rule successfully!");
                request.setAttribute("CURRENT_SECTION", "edit_rule_section");

                String homeId = request.getParameter("homeId");
                request.setAttribute("HOME_ID", homeId);
            } else {
                request.setAttribute("ERROR", "Failed to update rule!");
                request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
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