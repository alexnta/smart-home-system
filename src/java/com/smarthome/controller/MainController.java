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
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
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

    private static final String VIEW_DEVICE = "ViewDevice";
    private static final String VIEW_DEVICE_CONTROLLER = "ViewDeviceController";
    private static final String CREATE_DEVICE = "CreateDevice";
    private static final String CREATE_DEVICE_CONTROLLER = "CreateDeviceController";
    private static final String UPDATE_DEVICE = "UpdateDevice";
    private static final String UPDATE_DEVICE_CONTROLLER = "UpdateDeviceController";
    private static final String DELETE_DEVICE = "DeleteDevice";
    private static final String DELETE_DEVICE_CONTROLLER = "DeleteDeviceController";

    private static final String VIEW_EVENT = "ViewEvent";
    private static final String VIEW_EVENT_CONTROLLER = "ViewEventController";
    private static final String CREATE_EVENT = "CreateEvent";
    private static final String CREATE_EVENT_CONTROLLER = "CreateEventController";
    private static final String SEARCH_EVENT = "SearchEvent";
    private static final String SEARCH_EVENT_CONTROLLER = "SearchEventController";

    // RULE
    private static final String VIEW_RULE = "ViewRule";
    private static final String VIEW_RULE_CONTROLLER = "ViewRuleController";
    private static final String CREATE_RULE = "CreateRule";
    private static final String CREATE_RULE_CONTROLLER = "CreateRuleController";
    private static final String UPDATE_RULE = "UpdateRule";
    private static final String UPDATE_RULE_CONTROLLER = "UpdateRuleController";
    private static final String DELETE_RULE = "DeleteRule";
    private static final String DELETE_RULE_CONTROLLER = "DeleteRuleController";

// HOME MODE
    private static final String VIEW_HOME_MODE = "ViewHomeMode";
    private static final String VIEW_HOME_MODE_CONTROLLER = "ViewHomeModeController";
    private static final String CREATE_HOME_MODE = "CreateHomeMode";
    private static final String CREATE_HOME_MODE_CONTROLLER = "CreateHomeModeController";
    private static final String UPDATE_HOME_MODE = "UpdateHomeMode";
    private static final String UPDATE_HOME_MODE_CONTROLLER = "UpdateHomeModeController";
    private static final String DELETE_HOME_MODE = "DeleteHomeMode";
    private static final String DELETE_HOME_MODE_CONTROLLER = "DeleteHomeModeController";

// ALERT
    private static final String VIEW_ALERT = "ViewAlert";
    private static final String VIEW_ALERT_CONTROLLER = "ViewAlertController";
    private static final String ACK_ALERT = "AckAlert";
    private static final String ACK_ALERT_CONTROLLER = "AckAlertController";
    private static final String RESOLVE_ALERT = "ResolveAlert";
    private static final String RESOLVE_ALERT_CONTROLLER = "ResolveAlertController";
    private static final String DELETE_ALERT = "DeleteAlert";
    private static final String DELETE_ALERT_CONTROLLER = "DeleteAlertController";

// ALERT ACTION
    private static final String VIEW_ALERT_ACTION = "ViewAlertAction";
    private static final String VIEW_ALERT_ACTION_CONTROLLER = "ViewAlertActionController";
    private static final String CREATE_ALERT_ACTION = "CreateAlertAction";
    private static final String CREATE_ALERT_ACTION_CONTROLLER = "CreateAlertActionController";
    private static final String DELETE_ALERT_ACTION = "DeleteAlertAction";
    private static final String DELETE_ALERT_ACTION_CONTROLLER = "DeleteAlertActionController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        try {
            String action = request.getParameter("action");

            // home
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
                // room
            } else if (VIEW_ROOM.equals(action)) {
                url = VIEW_ROOM_CONTROLLER;
            } else if (CREATE_ROOM.equals(action)) {
                url = CREATE_ROOM_CONTROLLER;
            } else if (UPDATE_ROOM.equals(action)) {
                url = UPDATE_ROOM_CONTROLLER;
            } else if (DELETE_ROOM.equals(action)) {
                url = DELETE_ROOM_CONTROLLER;
                // device
            } else if (VIEW_DEVICE.equals(action)) {
                url = VIEW_DEVICE_CONTROLLER;
            } else if (CREATE_DEVICE.equals(action)) {
                url = CREATE_DEVICE_CONTROLLER;
            } else if (UPDATE_DEVICE.equals(action)) {
                url = UPDATE_DEVICE_CONTROLLER;
            } else if (DELETE_DEVICE.equals(action)) {
                url = DELETE_DEVICE_CONTROLLER;
                // event log
            } else if (VIEW_EVENT.equals(action)) {
                url = VIEW_EVENT_CONTROLLER;
            } else if (CREATE_EVENT.equals(action)) {
                url = CREATE_EVENT_CONTROLLER;
            } else if (SEARCH_EVENT.equals(action)) {
                url = SEARCH_EVENT_CONTROLLER;

                // rule
            } else if (VIEW_RULE.equals(action)) {
                url = VIEW_RULE_CONTROLLER;
            } else if (CREATE_RULE.equals(action)) {
                url = CREATE_RULE_CONTROLLER;
            } else if (UPDATE_RULE.equals(action)) {
                url = UPDATE_RULE_CONTROLLER;
            } else if (DELETE_RULE.equals(action)) {
                url = DELETE_RULE_CONTROLLER;

// home mode
            } else if (VIEW_HOME_MODE.equals(action)) {
                url = VIEW_HOME_MODE_CONTROLLER;
            } else if (CREATE_HOME_MODE.equals(action)) {
                url = CREATE_HOME_MODE_CONTROLLER;
            } else if (UPDATE_HOME_MODE.equals(action)) {
                url = UPDATE_HOME_MODE_CONTROLLER;
            } else if (DELETE_HOME_MODE.equals(action)) {
                url = DELETE_HOME_MODE_CONTROLLER;

// alert
            } else if (VIEW_ALERT.equals(action)) {
                url = VIEW_ALERT_CONTROLLER;
            } else if (ACK_ALERT.equals(action)) {
                url = ACK_ALERT_CONTROLLER;
            } else if (RESOLVE_ALERT.equals(action)) {
                url = RESOLVE_ALERT_CONTROLLER;
            } else if (DELETE_ALERT.equals(action)) {
                url = DELETE_ALERT_CONTROLLER;

// alert action
            } else if (VIEW_ALERT_ACTION.equals(action)) {
                url = VIEW_ALERT_ACTION_CONTROLLER;
            } else if (CREATE_ALERT_ACTION.equals(action)) {
                url = CREATE_ALERT_ACTION_CONTROLLER;
            } else if (DELETE_ALERT_ACTION.equals(action)) {
                url = DELETE_ALERT_ACTION_CONTROLLER;
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
