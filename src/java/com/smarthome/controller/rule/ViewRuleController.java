package com.smarthome.controller.rule;

import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.RuleDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ViewRuleController", urlPatterns = {"/ViewRuleController"})
public class ViewRuleController extends HttpServlet {

    private static final String ADMIN_PAGE = "admin/admin.jsp";
    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // 1. Kiểm tra an toàn bảo mật (Bắt buộc)
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.sendRedirect(LOGIN_PAGE);
                return;
            }

            // 2. Lấy homeId một cách AN TOÀN
            String homeIdStr = request.getParameter("homeId");
            int homeId = 0; // 0 Mặc định là tìm các Template dùng chung

            if (homeIdStr != null && !homeIdStr.trim().isEmpty()) {
                homeId = Integer.parseInt(homeIdStr.trim());
            }

            // 3. Gọi DAO để lấy danh sách
            RuleDAO dao = new RuleDAO();
            List<RuleDTO> list;

            if (homeId > 0) {
                // Đang tìm kiếm luật của 1 nhà cụ thể
                list = dao.getRulesByHomeId(homeId); 
                request.setAttribute("SEARCH_MODE", "HOME");
                request.setAttribute("SEARCH_HOME_ID", homeId);
            } else {
                // Đang xem các Mẫu luật chung (Template)
                list = dao.getAllRuleTemplates(); 
                request.setAttribute("SEARCH_MODE", "TEMPLATE");
            }

            // 4. Gắn dữ liệu và ném về giao diện
            request.setAttribute("RULE_LIST", list);
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);

        } catch (NumberFormatException e) {
            // Xử lý khi Admin nhập chữ cái vào ô Home ID
            request.setAttribute("ERROR_MSG", "Home ID must be a number!");
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
            
        } catch (Exception e) {
            // Bắt lỗi Database và trả về giao diện hộp thoại đỏ
            e.printStackTrace();
            log("Error at ViewRuleController: " + e.toString());
            request.setAttribute("ERROR_MSG", "Cannot load rules: " + e.getMessage());
            request.setAttribute("CURRENT_SECTION", "edit_rule_section");
            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}