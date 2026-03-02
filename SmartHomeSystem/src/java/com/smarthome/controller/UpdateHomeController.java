/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller;

import com.smarthome.dao.HomeDAO;
import com.smarthome.dto.HomeDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alex
 */
@WebServlet(name = "UpdateHomeController", urlPatterns = {"/UpdateHomeController"})
public class UpdateHomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final String ERROR_PAGE = "updateHome.jsp";
    private static final String SUCCESS_PAGE = "ViewHomeController";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;

        try {
            String homeIdStr = request.getParameter("txtHomeId");
            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtName");
            String address = request.getParameter("txtAddress");
            String status = request.getParameter("txtStatus");
            String ownerIdStr = request.getParameter("txtOwnerId");

            // Validate
            if (homeIdStr == null || homeIdStr.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home ID is required!");
            } else if (code == null || code.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home code is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR", "Home name is required!");
            } else {
                int homeId = Integer.parseInt(homeIdStr);
                int ownerId = 0;
                if (ownerIdStr != null && !ownerIdStr.trim().isEmpty()) {
                    ownerId = Integer.parseInt(ownerIdStr);
                }

                HomeDTO home = new HomeDTO();
                home.setHomeId(homeId);
                home.setCode(code.trim());
                home.setName(name.trim());
                home.setAddressText(address != null ? address.trim() : "");
                home.setStatus(status != null ? status : "Active");
                home.setOwnerUserId(ownerId);

                // DB update
                HomeDAO dao = new HomeDAO();
                boolean success = dao.updateHome(home);

                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR", "Failed to update home!");
                }
            }

        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid number format!");
        } catch (Exception e) {
            log("Error at UpdateHomeController: " + e.toString());
            request.setAttribute("ERROR", "System error: " + e.getMessage());
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
