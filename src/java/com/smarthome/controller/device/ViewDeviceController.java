/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller.device;

import com.smarthome.dao.DeviceDAO;
import com.smarthome.dto.DeviceDTO;
import com.smarthome.dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
@WebServlet(name = "ViewDeviceController", urlPatterns = {"/ViewDeviceController"})
public class ViewDeviceController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ADMIN_PAGE = "admin/admin.jsp";
       private static final String TECHNICIAN_PAGE = "technician/technician.jsp";
       private static final String OWNER_PAGE = "home_owner/home_owner.jsp";
    private static final String ERROR_PAGE = "../error.jsp";

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    String url = ERROR_PAGE;

    try {
        HttpSession session = request.getSession(false);

        System.out.println("=== ViewDeviceController called ===");

        if (session == null) {
            System.out.println("Session is null");
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("USER attr = " + session.getAttribute("USER"));
        System.out.println("LOGIN_USER attr = " + session.getAttribute("LOGIN_USER"));

        if (session.getAttribute("LOGIN_USER") == null) {
            System.out.println("LOGIN_USER is null");
            response.sendRedirect("login.jsp");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
        System.out.println("Role = " + user.getRoleName());

        DeviceDAO dao = new DeviceDAO();
        List<DeviceDTO> deviceList = dao.getAllDevices();

        System.out.println("deviceList = " + deviceList);
        System.out.println("deviceList size = " + (deviceList == null ? "null" : deviceList.size()));

        request.setAttribute("DEVICE_LIST", deviceList);
        System.out.println(deviceList);
        request.setAttribute("CURRENT_SECTION", "device_management_section");

        String role = user.getRoleName();
        if ("Admin".equals(role)) {
            url = ADMIN_PAGE;
        } else if ("Technician".equals(role)) {
            url = TECHNICIAN_PAGE;
        } else if("Home Owner".equals(role)){
            url = OWNER_PAGE;
        }

        System.out.println("Forward to = " + url);

    } catch (Exception e) {
        e.printStackTrace();
        log("Error at ViewDeviceController: " + e.toString());
        request.setAttribute("ERROR", "Cannot load device list!");
        request.setAttribute("CURRENT_SECTION", "device_management_section");
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
