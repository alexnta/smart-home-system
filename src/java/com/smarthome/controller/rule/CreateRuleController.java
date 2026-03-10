/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */

@WebServlet(name = "CreateRuleController", urlPatterns = {"/CreateRuleController"})
public class CreateRuleController extends HttpServlet {

    private static final String ERROR_PAGE = "/dashboard/dashboard.jsp";
    private static final String SUCCESS_PAGE = "/dashboard/dashboard.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">>> CreateRuleController called");
        String url = ERROR_PAGE;
        response.setContentType("text/html;charset=UTF-8");

        try {
            System.out.println("homeId = " + request.getParameter("homeId"));
            System.out.println("ruleName = " + request.getParameter("ruleName"));
            System.out.println("triggerType = " + request.getParameter("triggerType"));
            System.out.println("conditionjson = " + request.getParameter("conditionjson"));
            System.out.println("severity = " + request.getParameter("severity"));

            RuleDTO rule = new RuleDTO();

            rule.setHomeId(Integer.parseInt(request.getParameter("homeId")));
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setTriggerType(request.getParameter("triggerType"));
            rule.setConditionJson(request.getParameter("conditionjson"));
            rule.setSeverity(request.getParameter("severity"));

            RuleDAO dao = new RuleDAO();
            System.out.println("Before insert");
            boolean success = dao.insert(rule);
            System.out.println("After insert");
            System.out.println("success = " + success);

            if (success) {
                url = SUCCESS_PAGE;
                request.setAttribute("SUCCESS", "Create rule successfully!");
                request.setAttribute("CURRENT_SECTION", "create_rule_section");
            } else {
                url = ERROR_PAGE;
                request.setAttribute("ERROR", "Failed to create rule!");
                request.setAttribute("CURRENT_SECTION", "create_rule_section");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Invalid number format!");
            request.setAttribute("CURRENT_SECTION", "create_rule_section");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "create_rule_section");
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