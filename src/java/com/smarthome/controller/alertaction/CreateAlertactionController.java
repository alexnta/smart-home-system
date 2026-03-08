package com.smarthome.controller.alertaction;

import com.smarthome.dao.AlertActionDAO;
import com.smarthome.dto.AlertActionDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class CreateAlertactionController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int alertId = Integer.parseInt(request.getParameter("alertId"));
            String actionType = request.getParameter("actionType");
            String note = request.getParameter("note");

            // Lấy user đang login
            HttpSession session = request.getSession();
            int actorUserId = (int) session.getAttribute("USER_ID");

            AlertActionDTO dto = new AlertActionDTO();

            dto.setAlertId(alertId);
            dto.setActorUserId(actorUserId);
            dto.setActionType(actionType);
            dto.setNote(note);

            AlertActionDAO dao = new AlertActionDAO();
            dao.insert(dto);

            response.sendRedirect("MainController?action=viewAlertAction&alertId=" + alertId);

        } catch (Exception e) {
            e.printStackTrace();
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