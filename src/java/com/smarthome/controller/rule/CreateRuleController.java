/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class CreateRuleController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        private static final String ERROR_PAGE = "dashboard/dashboard.jsp";
    private static final String SUCCESS_PAGE = "dashboard/dashboard.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                String url = ERROR_PAGE;
        response.setContentType("text/html;charset=UTF-8");
        try {

            RuleDTO rule = new RuleDTO();

            rule.setHomeId(Integer.parseInt(request.getParameter("homeId")));
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setTriggerType(request.getParameter("triggerType"));
            rule.setOperator(request.getParameter("operator"));
            rule.setThrehold(Double.parseDouble(request.getParameter("threshold")));
            rule.setSeverity(request.getParameter("severity"));

            RuleDAO dao = new RuleDAO();
           boolean success =  dao.insert(rule); 
                           if (success) {
                    url = SUCCESS_PAGE;
                } else {
                            request.setAttribute("ERROR", "Failed to create home!");
        request.setAttribute("CURRENT_SECTION", "create_rule_section");
                }
        } catch (NumberFormatException e) {
                request.setAttribute("ERROR", "Invalid number format!");
    request.setAttribute("CURRENT_SECTION", "create_rule_section");
        } catch (Exception e) {
    request.setAttribute("ERROR", "System error: " + e.getMessage());
    request.setAttribute("CURRENT_SECTION", "create_rule_section");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
