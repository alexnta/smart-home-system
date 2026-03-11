package com.smarthome.engine;

import com.smarthome.dao.AlertDAO;
import com.smarthome.dao.EventLogDAO;
import com.smarthome.dao.HomeModeDAO;
import com.smarthome.dao.RuleDAO;
import com.smarthome.dto.AlertDTO;
import com.smarthome.dto.EventLogDTO;
import com.smarthome.dto.HomeModeDTO;
import com.smarthome.dto.RuleDTO;

import java.sql.Timestamp;
import java.util.List;

public class AlertEngine {

    private RuleDAO ruleDAO = new RuleDAO();
    private AlertDAO alertDAO = new AlertDAO();
    private HomeModeDAO homeModeDAO = new HomeModeDAO();
    private EventLogDAO eventLogDAO = new EventLogDAO();

   /* public void processDeviceData(int homeId, int deviceId, double deviceValue) throws Exception {

        List<RuleDTO> rules = ruleDAO.getByHomeId(homeId);

        for (RuleDTO rule : rules) {

            if (!rule.isIsActive()) continue;

            if (isViolated(rule, deviceValue)) {

                if (alertDAO.existsOpenAlert(deviceId, rule.getRuleId())) {
                    continue;
                }

                AlertDTO alert = new AlertDTO();

                alert.setHomeId(homeId);
                alert.setDeviceId(deviceId);
                alert.setRuleId(rule.getRuleId());
                alert.setAlertType(rule.getTriggerType());
                alert.setSeverity(rule.getSeverity());
                alert.setStatus("Open");
                alert.setMessage("Rule violated: " + rule.getRuleName());
                alert.setStartTs(new Timestamp(System.currentTimeMillis()));

                alertDAO.insertAlert(alert);
            }
        }
    }*/

    /*private boolean isViolated(RuleDTO rule, double value) {
    }*/
    
    // Thay đổi tham số truyền vào để phù hợp với EventLog
    public void processDeviceData(int homeId, int deviceId, String eventType, String eventValue) throws Exception {

        List<RuleDTO> rules = ruleDAO.getByHomeId(homeId);

        for (RuleDTO rule : rules) {

            // Bỏ qua các luật đang bị tắt
            if (!rule.isIsActive()) continue;

            // Kiểm tra xem luật có bị vi phạm không
            if (isViolated(rule, homeId, deviceId, eventType, eventValue)) {

                // Nếu cảnh báo cho luật này trên thiết bị này đang Open rồi thì không tạo thêm nữa
                if (alertDAO.existsOpenAlert(deviceId, rule.getRuleId())) {
                    continue;
                }

                // Nếu vi phạm, tạo Alert mới
                AlertDTO alert = new AlertDTO();
                alert.setHomeId(homeId);
                alert.setDeviceId(deviceId);
                alert.setRuleId(rule.getRuleId());
                
                // Phân loại nhóm cảnh báo
                if ("Threshold".equalsIgnoreCase(rule.getTriggerType())) {
                    alert.setAlertType("Safety"); // Ví dụ: quên đóng cửa là Safety
                } else {
                    alert.setAlertType("Security"); 
                }
                        
                alert.setSeverity(rule.getSeverity());
                alert.setStatus("Open");
                alert.setMessage("Phát hiện vi phạm: " + rule.getRuleName());
                alert.setStartTs(new Timestamp(System.currentTimeMillis()));

                alertDAO.insertAlert(alert);
            }
        }
    }

    private boolean isViolated(RuleDTO rule, int homeId, int deviceId, String eventType, String eventValue) throws Exception {
        
        // 1. Loại EVENT (Sự kiện tức thời - dùng data realtime)
        if ("Event".equalsIgnoreCase(rule.getTriggerType())) {
            return evaluateEventRule(rule, homeId, eventType, eventValue);
        }
        
        // 2. Loại THRESHOLD (Ngưỡng thời gian - BẮT BUỘC DÙNG EVENT LOG)
        if ("Threshold".equalsIgnoreCase(rule.getTriggerType())) {
            return evaluateThresholdRule(rule, deviceId);
        }
        
        // 3. Loại SCHEDULE (Lịch trình / Khung giờ)
        if ("Schedule".equalsIgnoreCase(rule.getTriggerType())) {
            return evaluateScheduleRule(rule, eventType, eventValue);
        }
        
        return false;
    }

    private boolean evaluateEventRule(RuleDTO rule, int homeId, String eventType, String eventValue) throws Exception {
        String conditionJson = rule.getConditionJson();
        if (conditionJson == null || conditionJson.isEmpty()) return false;

        // Trích xuất giá trị home_mode từ chuỗi JSON: {"home_mode": "Away"}
        // (Tôi dùng hàm tự viết parse JSON đơn giản để bạn không cần phải add thêm thư viện Gson/Jackson)
        String targetMode = extractJsonValue(conditionJson, "home_mode");

        if (targetMode != null) {
            
            // XÁC ĐỊNH SỰ KIỆN GÂY HẠI:
            // Chỉ bắt vi phạm nếu sự kiện là mở cửa (DoorOpen) hoặc phát hiện chuyển động.
            // Nếu chỉ là 'Heartbeat' (thiết bị gửi tín hiệu sống) thì không báo động.
            if ("DoorOpen".equalsIgnoreCase(eventType) && "Open".equalsIgnoreCase(eventValue)) {
                
                // Lấy tất cả các mode của nhà hiện tại
                List<HomeModeDTO> modes = homeModeDAO.getByHomeId(homeId);
                
                // Tìm mode đang Active, nếu trùng với targetMode (ví dụ: Away) -> Vi phạm!
                for (HomeModeDTO mode : modes) {
                    if (mode.isIsActive() && targetMode.equalsIgnoreCase(mode.getModeName())) {
                        return true; // Phát hiện đột nhập khi vắng nhà!
                    }
                }
            }
        }
        return false;
    }
    
    // =========================================================================
    // HÀM ĐÁNH GIÁ LUẬT THRESHOLD (SỬ DỤNG LỊCH SỬ EVENT LOG)
    // =========================================================================
    private boolean evaluateThresholdRule(RuleDTO rule, int deviceId) throws Exception {
        String conditionJson = rule.getConditionJson();
        if (conditionJson == null || conditionJson.isEmpty()) return false;

        String durationStr = extractJsonValue(conditionJson, "duration_minutes");
        if (durationStr != null) {
            int maxMinutes = Integer.parseInt(durationStr);

            // GỌI EVENT LOG: Lấy toàn bộ lịch sử sự kiện của thiết bị này
            // Truyền null cho các tham số khác để DAO tự động sắp xếp theo thời gian mới nhất (DESC)
            List<EventLogDTO> logs = eventLogDAO.searchEvents(deviceId, null, null, null);

            if (logs != null && !logs.isEmpty()) {
                // Lấy sự kiện mới nhất (vì DAO đã ORDER BY ts DESC)
                EventLogDTO latestEvent = logs.get(0);

                // Nếu trạng thái cuối cùng của thiết bị này đang là Mở (Open)
                if ("DoorOpen".equalsIgnoreCase(latestEvent.getEventType()) && "Open".equalsIgnoreCase(latestEvent.getEventValue())) {
                    
                    // Lấy thời điểm mở cửa từ EventLog
                    long openTimeMillis = latestEvent.getTs().getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    
                    // Tính số phút đã trôi qua
                    long diffMinutes = (currentTimeMillis - openTimeMillis) / (60 * 1000);

                    // Nếu thời gian mở lớn hơn hoặc bằng thời gian trong Rule (ví dụ >= 15 phút) -> Vi phạm!
                    if (diffMinutes >= maxMinutes) {
                        return true; 
                    }
                }
                // Chú ý: Nếu latestEvent là 'DoorClose' thì có nghĩa là cửa đã được đóng an toàn, không vi phạm.
            }
        }
        return false;
    }

    // =========================================================================
    // HÀM ĐÁNH GIÁ LUẬT SCHEDULE (SO SÁNH KHUNG GIỜ)
    // =========================================================================
    private boolean evaluateScheduleRule(RuleDTO rule, String eventType, String eventValue) {
        String conditionJson = rule.getConditionJson();
        if (conditionJson == null || conditionJson.isEmpty()) return false;

        // Trích xuất các thông số từ JSON
        String startTimeStr = extractJsonValue(conditionJson, "start_time"); // VD: "23:00"
        String endTimeStr = extractJsonValue(conditionJson, "end_time");     // VD: "05:00"
        String targetEvent = extractJsonValue(conditionJson, "target_event"); // VD: "DoorOpen"

        if (startTimeStr != null && endTimeStr != null && targetEvent != null) {
            
            // Chỉ kiểm tra nếu sự kiện hiện tại ĐÚNG LÀ sự kiện bị cấm (vd: DoorOpen)
            // Nếu người dùng đóng cửa (DoorClose) thì bỏ qua không báo động
            if (targetEvent.equalsIgnoreCase(eventType) && "Open".equalsIgnoreCase(eventValue)) {
                try {
                    java.time.LocalTime startTime = java.time.LocalTime.parse(startTimeStr);
                    java.time.LocalTime endTime = java.time.LocalTime.parse(endTimeStr);
                    java.time.LocalTime now = java.time.LocalTime.now(); // Lấy giờ hiện tại của hệ thống

                    // Trường hợp 1: Khung giờ bình thường trong cùng 1 ngày (VD: 08:00 đến 17:00)
                    if (startTime.isBefore(endTime)) {
                        if (now.isAfter(startTime) && now.isBefore(endTime)) {
                            return true; // Vi phạm!
                        }
                    } 
                    // Trường hợp 2: Khung giờ qua đêm chéo sang ngày hôm sau (VD: 23:00 đến 05:00)
                    else {
                        // Chỉ cần lớn hơn giờ bắt đầu (vd: 23:30) HOẶC nhỏ hơn giờ kết thúc (vd: 03:00)
                        if (now.isAfter(startTime) || now.isBefore(endTime)) {
                            return true; // Vi phạm!
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi parse thời gian trong Rule Schedule: " + e.getMessage());
                }
            }
        }
        return false;
    }
    
    /**
     * Hàm tiện ích nhỏ để lấy value từ chuỗi JSON đơn giản mà không cần thư viện bên ngoài
     * Ví dụ truyền vào json={"home_mode": "Away"}, key="home_mode" -> Trả về "Away"
     */
    /**
     * Hàm tiện ích lấy value từ chuỗi JSON đơn giản
     * Hỗ trợ cả giá trị String ("Away") và Number (15)
     */
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex != -1) {
            int colonIndex = json.indexOf(":", keyIndex);
            if (colonIndex != -1) {
                // Cắt lấy phần sau dấu hai chấm
                String remainder = json.substring(colonIndex + 1).trim();
                
                // Trường hợp 1: Giá trị là Chuỗi (bắt đầu bằng ngoặc kép)
                if (remainder.startsWith("\"")) {
                    int endQuote = remainder.indexOf("\"", 1);
                    if (endQuote != -1) {
                        return remainder.substring(1, endQuote); // Trả về text bên trong ngoặc kép
                    }
                } 
                // Trường hợp 2: Giá trị là Số (không có ngoặc kép)
                else {
                    // Tìm vị trí kết thúc của số (dấu phẩy nếu có nhiều tham số, hoặc dấu } nếu là tham số cuối)
                    int commaIndex = remainder.indexOf(",");
                    int braceIndex = remainder.indexOf("}");
                    
                    int endIndex = -1;
                    if (commaIndex != -1 && braceIndex != -1) {
                        endIndex = Math.min(commaIndex, braceIndex);
                    } else if (commaIndex != -1) {
                        endIndex = commaIndex;
                    } else if (braceIndex != -1) {
                        endIndex = braceIndex;
                    }
                    
                    if (endIndex != -1) {
                        return remainder.substring(0, endIndex).trim(); // Trả về con số (dạng chuỗi)
                    }
                }
            }
        }
        return null;
    }
}