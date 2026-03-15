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

    private static final String ERROR_PAGE = "admin/admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); 
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Lấy dữ liệu từ form
            RuleDTO rule = new RuleDTO();
            rule.setRuleId(Integer.parseInt(request.getParameter("ruleId")));
            rule.setRuleName(request.getParameter("ruleName"));
            rule.setTriggerType(request.getParameter("triggerType"));
            rule.setConditionJson(request.getParameter("conditionjson"));
            rule.setSeverity(request.getParameter("severity"));
            rule.setIsActive(true); // Mặc định kích hoạt

            RuleDAO dao = new RuleDAO();
            boolean success = dao.update(rule);

            if (success) {
                // THÀNH CÔNG: Redirect (chuyển hướng) và KHÔNG chạy tiếp
                String homeId = request.getParameter("homeId");
                
                // Nếu update Luật chung thì không gắn homeId vào URL
                if(homeId != null && !homeId.trim().isEmpty() && !homeId.equals("0")){
                     response.sendRedirect("MainController?action=ViewRule&homeId=" + homeId);
                } else {
                     response.sendRedirect("MainController?action=ViewRule");
                }
                return; // Thoát hàm ngay lập tức

            } else {
                // THẤT BẠI: Thiết lập lỗi và Forward
                request.setAttribute("ERROR_MSG", "Failed to update rule in database!");
                request.setAttribute("CURRENT_SECTION", "edit_rule_section");
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "Invalid Rule ID format!");
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "System error: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
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