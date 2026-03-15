package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "CreateRuleController", urlPatterns = {"/CreateRuleController"})
public class CreateRuleController extends HttpServlet {

    private static final String ERROR_PAGE = "admin/admin.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(">>> CreateRuleController called");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");

        try {
            // 1. Check Session (Bảo mật)
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // 2. Lấy dữ liệu và xử lý HomeID an toàn
            String homeIdStr = request.getParameter("homeId");
            int homeId = 0; // Mặc định là 0 (Rule Template chung)
            if (homeIdStr != null && !homeIdStr.trim().isEmpty()) {
                homeId = Integer.parseInt(homeIdStr.trim());
            }

            RuleDTO rule = new RuleDTO();
            rule.setHomeId(homeId);
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setTriggerType(request.getParameter("triggerType"));
            rule.setConditionJson(request.getParameter("conditionjson"));
            rule.setSeverity(request.getParameter("severity"));
            rule.setIsActive(true); // Luật mới mặc định là Active

            // 3. Insert vào Database
            RuleDAO dao = new RuleDAO();
            boolean success = dao.insert(rule);

            // 4. Điều hướng an toàn (KHÔNG DÙNG FINALLY)
            if (success) {
                // Thành công: Redirect đi và thoát ngay!
                response.sendRedirect("MainController?action=ViewRule");
                return; 
            } else {
                // Thất bại do DB: Forward kèm báo lỗi
                request.setAttribute("ERROR_MSG", "Failed to create rule!");
                request.setAttribute("CURRENT_SECTION", "create_rule_section");
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Invalid number format for Home ID!");
            request.setAttribute("CURRENT_SECTION", "create_rule_section");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "create_rule_section");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
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