package com.smarthome.controller.log;

import com.smarthome.dao.EventLogDAO;
import com.smarthome.dto.EventLogDTO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ImportCSVController", urlPatterns = {"/ImportCSVController"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB: Kích thước đưa vào bộ nhớ tạm
    maxFileSize = 1024 * 1024 * 10,       // 10MB: Kích thước tối đa 1 file
    maxRequestSize = 1024 * 1024 * 50     // 50MB: Kích thước tối đa cả request
)
public class ImportCSVController extends HttpServlet {

    private static final String ADMIN_PAGE = "admin/admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // Cấu hình để form luôn hiển thị lại tab Import
        request.setAttribute("CURRENT_SECTION", "import_csv_section");

        try {
            // 1. Lấy file từ Request
            Part filePart = request.getPart("csvFile");
            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("ERROR_MSG", "No file uploaded!");
                request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
                return;
            }

            // 2. Chuẩn bị đọc file
            InputStream fileContent = filePart.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, StandardCharsets.UTF_8));
            
            EventLogDAO dao = new EventLogDAO();
            String line;
            boolean isFirstLine = true;
            int successCount = 0;
            int failCount = 0;

            // 3. Đọc từng dòng của CSV
            while ((line = reader.readLine()) != null) {
                // Bỏ qua dòng tiêu đề (Header)
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Cấu trúc dự kiến: device_id, event_type, event_value, timestamp
                String[] data = line.split(",");
                if (data.length >= 4) {
                    try {
                        EventLogDTO event = new EventLogDTO();
                        event.setDeviceId(Integer.parseInt(data[0].trim()));
                        event.setEventType(data[1].trim());
                        event.setEventValue(data[2].trim());
                        
                        // Parse chuỗi ngày tháng sang Timestamp (Format chuẩn SQL: yyyy-[m]m-[d]d hh:mm:ss)
                        event.setTs(Timestamp.valueOf(data[3].trim()));

                        // Insert vào Database
                        if (dao.insertEvent(event)) {
                            successCount++;
                        } else {
                            failCount++;
                        }
                    } catch (Exception e) {
                        failCount++;
                        System.out.println("Lỗi dòng CSV: " + line + " - Reason: " + e.getMessage());
                    }
                }
            }

            // 4. Trả về kết quả
            if (successCount > 0 && failCount == 0) {
                // Đổi thành SUCCESS_MSG
                request.setAttribute("SUCCESS_MSG", "Import Complete! Successfully added " + successCount + " events.");
            } else if (successCount > 0 && failCount > 0) {
                // Dùng SUCCESS_MSG nhưng cảnh báo thêm có dòng lỗi
                request.setAttribute("SUCCESS_MSG", "Import Partial! Added " + successCount + " events. Skipped " + failCount + " invalid rows.");
            } else {
                request.setAttribute("ERROR_MSG", "Import Failed! No valid events found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR_MSG", "System error during import: " + e.getMessage());
        }

        request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}