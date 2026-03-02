/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.smarthome.controller;

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
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String ERROR = "error.jsp";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String VIEW_HOME = "ViewHome";
    private static final String VIEW_HOME_CONTROLLER = "ViewHomeController";
    private static final String CREATE_HOME = "CreateHome";
    private static final String CREATE_HOME_CONTROLLER = "CreateHomeController";
    private static final String UPDATE_HOME = "UpdateHome";
    private static final String UPDATE_HOME_CONTROLLER = "UpdateHomeController";
    private static final String DELETE_HOME = "DeleteHome";
    private static final String DELETE_HOME_CONTROLLER = "DeleteHomeController";
    private static final String VIEW_ROOM = "ViewRoom";
    private static final String VIEW_ROOM_CONTROLLER = "ViewRoomController";
    private static final String CREATE_ROOM = "CreateRoom";
    private static final String CREATE_ROOM_CONTROLLER = "CreateRoomController";
    private static final String UPDATE_ROOM = "UpdateRoom";
    private static final String UPDATE_ROOM_CONTROLLER = "UpdateRoomController";
    private static final String DELETE_ROOM = "DeleteRoom";
    private static final String DELETE_ROOM_CONTROLLER = "DeleteRoomController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        try {
            String action = request.getParameter("action");

            if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (VIEW_HOME.equals(action)) {
                url = VIEW_HOME_CONTROLLER;
            } else if (CREATE_HOME.equals(action)) {
                url = CREATE_HOME_CONTROLLER;
            } else if (UPDATE_HOME.equals(action)) {
                url = UPDATE_HOME_CONTROLLER;
            } else if (DELETE_HOME.equals(action)) {
                url = DELETE_HOME_CONTROLLER;
            } else if (VIEW_ROOM.equals(action)) {
                url = VIEW_ROOM_CONTROLLER;
            } else if (CREATE_ROOM.equals(action)) {
                url = CREATE_ROOM_CONTROLLER;
            } else if (UPDATE_ROOM.equals(action)) {
                url = UPDATE_ROOM_CONTROLLER;
            } else if (DELETE_ROOM.equals(action)) {
                url = DELETE_ROOM_CONTROLLER;
            } else {
                request.setAttribute("ERROR", "Your action is not supported.");
            }
        } catch (Exception e) {
            log("Error at Main Controller: " + e.toString());
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
