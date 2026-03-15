/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.home;

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
@WebServlet(name = "CreateHomeController", urlPatterns = {"/CreateHomeController"})
public class CreateHomeController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String SUCCESS_PAGE = "MainController?action=ViewHome";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR_PAGE;
        boolean success = false;

        try {

            String code = request.getParameter("txtCode");
            String name = request.getParameter("txtFacilityName");
            String address = request.getParameter("txtAddress");
            String status = request.getParameter("txtStatus");
            String ownerIdStr = request.getParameter("txtOwnerId");

            System.out.println("code = " + code);
            System.out.println("name = " + name);
            System.out.println("address = " + address);
            System.out.println("status = " + status);
            System.out.println("ownerId = " + ownerIdStr);
            // basic validation
            if (code == null || code.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home code is required!");
            } else if (name == null || name.trim().isEmpty()) {
                request.setAttribute("ERROR_MSG", "Home name is required!");
            } else {
                int ownerId = 0;
                if (ownerIdStr != null && !ownerIdStr.trim().isEmpty()) {
                    ownerId = Integer.parseInt(ownerIdStr);
                }

                HomeDTO home = new HomeDTO();
                home.setCode(code.trim());
                home.setName(name.trim());
                home.setAddressText(address != null ? address.trim() : "");
                home.setStatus(status != null ? status : "Active");
                home.setOwnerUserId(ownerId);

                HomeDAO dao = new HomeDAO();
                success = dao.insertHome(home);
                System.out.println(success);
                if (success) {
                    url = SUCCESS_PAGE;
                } else {
                    request.setAttribute("ERROR_MSG", "Failed to create home!");
                    request.setAttribute("CURRENT_SECTION", "add_facilities_section");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR_MSG", "Invalid number format!");
            request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } catch (Exception e) {
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "add_facilities_section");
        } finally {
            if (success) {
                response.sendRedirect(url); // Thành công thì Redirect
            } else {
                request.getRequestDispatcher(url).forward(request, response); // Lỗi thì Forward để hiện ERROR_MSG
            }
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
