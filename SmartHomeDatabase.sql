-- 1. Tạo Database (Nếu chưa có) 05/02
CREATE DATABASE SmartHomeDB;
GO
USE SmartHomeDB;
GO

-- =============================================
-- PHẦN 1: QUẢN LÝ NGƯỜI DÙNG VÀ PHÂN QUYỀN
-- =============================================

-- 2. Bảng Roles (Vai trò)
-- Mục đích: Định nghĩa các quyền hạn trong hệ thống (Admin, Owner,...)
CREATE TABLE Roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY, -- Tự động tăng
    role_name NVARCHAR(50) NOT NULL UNIQUE, -- Tên vai trò (Admin, Viewer...)
    description NVARCHAR(200) -- Mô tả thêm
);
GO

-- 3. Bảng Users (Người dùng)
-- Mục đích: Lưu thông tin đăng nhập và cá nhân
CREATE TABLE Users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE, -- Tên đăng nhập (không dấu)
    password VARCHAR(255) NOT NULL, -- Mật khẩu (Nên lưu Hash, demo có thể lưu plain text tạm)
    full_name NVARCHAR(100), -- Tên hiển thị (có dấu tiếng Việt)
    email VARCHAR(100),
    status BIT DEFAULT 1, -- 1: Active (Hoạt động), 0: Inactive (Bị khóa)
    created_at DATETIME DEFAULT GETDATE() -- Thời điểm tạo tài khoản
);
GO

-- 4. Bảng UserRole (Bảng trung gian)
-- Mục đích: Gán người dùng vào vai trò (Theo thiết kế mục 9.1 [73])
CREATE TABLE UserRole (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    assigned_at DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (user_id, role_id), -- Khóa chính kép
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);
GO

-- =============================================
-- PHẦN 2: QUẢN LÝ NHÀ (HOME)
-- =============================================

-- 5. Bảng Home (Ngôi nhà)
-- Mục đích: Quản lý thông tin căn nhà cần giám sát [75]
CREATE TABLE Home (
    home_id INT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE, -- Mã nhà (Ví dụ: H01, H02) để dễ tìm kiếm
    name NVARCHAR(100) NOT NULL, -- Tên gợi nhớ (Nhà Cầu Giấy, Nhà nghỉ dưỡng...)
    address_text NVARCHAR(255), -- Địa chỉ cụ thể
    status NVARCHAR(20) DEFAULT 'Active', -- Trạng thái: Active, Maintenance, Inactive
    owner_user_id INT, -- Ai là chủ nhà này? (FK liên kết sang Users)
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (owner_user_id) REFERENCES Users(user_id)
);
GO

-- =============================================
-- SEED DATA (DỮ LIỆU MẪU BAN ĐẦU)
-- =============================================

-- Thêm các vai trò chuẩn theo thiết kế [26, 66-70]
INSERT INTO Roles (role_name, description) VALUES 
(N'Admin', N'Quản trị viên hệ thống'),
(N'Home Owner', N'Chủ nhà - Quản lý thiết bị và luật'),
(N'Technician', N'Kỹ thuật viên - Bảo trì thiết bị'),
(N'Viewer', N'Người xem - Chỉ xem dashboard');

-- Thêm 1 User mẫu (Mật khẩu: 123)
INSERT INTO Users (username, password, full_name, email) VALUES 
('admin', '123', N'Nguyễn Văn Admin', 'admin@smarthome.com');

-- Gán quyền Admin (role_id = 1) cho user vừa tạo (user_id = 1)
INSERT INTO UserRole (user_id, role_id) VALUES (1, 1);

-- Thêm 1 Nhà mẫu
INSERT INTO Home (code, name, address_text, owner_user_id) VALUES 
('HOME-01', N'Nhà Riêng FPT', N'Khu Công nghệ cao Hòa Lạc', 1);

INSERT INTO Users (username, password, full_name, email, status) VALUES 
('owner_minh', '123', N'Lê Quang Minh', 'minh.owner@fpt.edu.vn', 1),   -- Sẽ là Home Owner
('tech_hung',  '123', N'Phạm Văn Hùng', 'hung.tech@fpt.edu.vn', 1),    -- Sẽ là Technician
('view_lan',   '123', N'Trần Thị Lan',  'lan.viewer@fpt.edu.vn', 1),   -- Sẽ là Viewer
('admin_phu',  '123', N'Hoàng Admin Phú', 'phu.admin@fpt.edu.vn', 1);  -- Thêm 1 Admin dự phòng

-- User 2 (Minh) -> Role 2 (Home Owner)
INSERT INTO UserRole (user_id, role_id) VALUES (2, 2);

-- User 3 (Hùng) -> Role 3 (Technician)
INSERT INTO UserRole (user_id, role_id) VALUES (3, 3);

-- User 4 (Lan) -> Role 4 (Viewer)
INSERT INTO UserRole (user_id, role_id) VALUES (4, 4);

-- User 5 (Phú) -> Role 1 (Admin)
INSERT INTO UserRole (user_id, role_id) VALUES (5, 1);



-- =============================================
-- PHẦN 3: QUẢN LÝ KHÔNG GIAN (ROOM)
-- =============================================

CREATE TABLE Room (
    room_id INT IDENTITY(1,1) PRIMARY KEY,
    home_id INT NOT NULL,
    name NVARCHAR(100) NOT NULL, -- Tên phòng (Phòng ngủ Master, Bếp...)
    floor INT DEFAULT 1, -- Số tầng (1, 2, 3...)
    room_type NVARCHAR(50), -- Loại phòng: LivingRoom, BedRoom, Kitchen, BathRoom
    status NVARCHAR(20) DEFAULT 'Active',
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (home_id) REFERENCES Home(home_id) ON DELETE CASCADE
);
GO

-- =============================================
-- PHẦN 4: QUẢN LÝ THIẾT BỊ (DEVICE)
-- =============================================

CREATE TABLE Device (
    device_id INT IDENTITY(1,1) PRIMARY KEY,
    room_id INT, -- Thiết bị có thể chưa gán vào phòng nào (NULL)
    name NVARCHAR(100) NOT NULL, -- Tên gợi nhớ (Đèn trần, Cảm biến cửa chính)
    
    -- Quan trọng: Loại thiết bị để phân chia logic xử lý [78]
    -- Giá trị dự kiến: 'DoorSensor', 'SmartLock', 'LightSwitch', 'SmartLight'
    device_type VARCHAR(50) NOT NULL, 
    
    serial_no VARCHAR(50) UNIQUE, -- Mã seri phần cứng (giả lập)
    vendor VARCHAR(50), -- Hãng sản xuất (Xiaomi, Tuya, Samsung...)
    
    -- Trạng thái: 'On', 'Off', 'Locked', 'Unlocked', 'Open', 'Closed'
    -- Hoặc trạng thái kết nối: 'Active', 'Offline', 'Error'
    -- Ở đây ta dùng status để lưu trạng thái hoạt động chính
    status NVARCHAR(50) DEFAULT 'Off', 
    
    -- Thời điểm cuối cùng thiết bị gửi tín hiệu. 
    -- Dùng để phát hiện thiết bị Offline (Device Health) [89]
    last_seen_ts DATETIME DEFAULT GETDATE(),
    
    is_active BIT DEFAULT 1, -- 1: Đang sử dụng, 0: Đã gỡ bỏ
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (room_id) REFERENCES Room(room_id) ON DELETE SET NULL
);
GO

-- =============================================
-- PHẦN 5: NGỮ CẢNH NHÀ (HOME MODE)
-- =============================================

CREATE TABLE HomeMode (
    mode_id INT IDENTITY(1,1) PRIMARY KEY,
    home_id INT NOT NULL,
    mode_name NVARCHAR(50) NOT NULL, -- 'Home', 'Away', 'Night', 'Vacation'
    description NVARCHAR(200),
    is_active BIT DEFAULT 0, -- Chỉ có 1 dòng là 1 tại một thời điểm
    
    FOREIGN KEY (home_id) REFERENCES Home(home_id) ON DELETE CASCADE
);
GO

-- =============================================
-- SEED DATA: DỮ LIỆU MẪU ĐỂ TEST
-- =============================================

-- 1. Tạo các phòng cho ngôi nhà 'HOME-01' (home_id = 1)
INSERT INTO Room (home_id, name, floor, room_type) VALUES 
(1, N'Phòng Khách', 1, 'LivingRoom'),
(1, N'Phòng Bếp', 1, 'Kitchen'),
(1, N'Phòng Ngủ Master', 2, 'BedRoom'),
(1, N'Phòng Làm Việc', 2, 'WorkRoom');

-- 2. Tạo thiết bị (Giả lập hệ thống có Cửa và Đèn)
-- Lưu ý: room_id 1=Khách, 2=Bếp, 3=Ngủ, 4=Làm việc (dựa trên lệnh insert trên)

-- Nhóm Cửa (An ninh)
INSERT INTO Device (room_id, name, device_type, status, serial_no) VALUES 
(1, N'Cảm biến Cửa Chính', 'DoorSensor', 'Closed', 'DS-001'),
(3, N'Khóa Cửa Phòng Ngủ', 'SmartLock', 'Locked', 'SL-002'),
(2, N'Cảm biến Cửa Sổ Bếp', 'DoorSensor', 'Closed', 'DS-003');

-- Nhóm Đèn (Năng lượng)
INSERT INTO Device (room_id, name, device_type, status, serial_no) VALUES 
(1, N'Đèn Chùm Phòng Khách', 'LightSwitch', 'Off', 'LS-101'),
(1, N'Đèn Downlight Hành Lang', 'SmartLight', 'Off', 'SL-102'),
(2, N'Đèn Bếp', 'LightSwitch', 'Off', 'LS-103'),
(4, N'Đèn Bàn Làm Việc', 'SmartLight', 'Off', 'SL-104');

-- 3. Tạo các chế độ cho nhà (Mặc định đang ở chế độ 'Home')
INSERT INTO HomeMode (home_id, mode_name, description, is_active) VALUES 
(1, 'Home', N'Đang có người ở nhà - An ninh mức thấp', 1),
(1, 'Away', N'Vắng nhà - Kích hoạt mọi báo động cửa', 0),
(1, 'Night', N'Ban đêm - Cảnh báo chuyển động và đèn', 0);
GO




-- =============================================
-- PHẦN 6. BẢNG RULE (LUẬT CẢNH BÁO)
-- =============================================
CREATE TABLE [Rule] (
    rule_id INT IDENTITY(1,1) PRIMARY KEY,
    home_id INT,
    rule_name NVARCHAR(100), -- VD: Cảnh báo mở cửa quá lâu
    
    -- Loại trigger: 'Schedule' (Theo giờ), 'Event' (Theo sự kiện), 'Threshold' (Ngưỡng)
    trigger_type VARCHAR(50), 
    
    -- Điều kiện (Lưu JSON để linh hoạt hoặc text đơn giản)
    -- VD: {"duration": 10, "unit": "minute"}
    condition_json NVARCHAR(MAX), 
    
    severity NVARCHAR(20) DEFAULT 'Warning', -- Info, Warning, Critical
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (home_id) REFERENCES Home(home_id)
);
GO

-- =============================================
-- PHẦN 7. BẢNG EVENT LOG (NHẬT KÝ SỰ KIỆN)
-- =============================================
CREATE TABLE EventLog (
    event_id BIGINT IDENTITY(1,1) PRIMARY KEY, -- Dùng BIGINT vì bảng này sẽ rất lớn
    device_id INT NOT NULL,
    
    -- Loại sự kiện: DoorOpen, DoorClose, LightOn, LightOff, Heartbeat...
    event_type VARCHAR(50), 
    
    event_value NVARCHAR(50), -- Giá trị (nếu có)
    ts DATETIME DEFAULT GETDATE(), -- Thời điểm xảy ra (Timestamp)
    
    FOREIGN KEY (device_id) REFERENCES Device(device_id)
);

-- Tạo Index để tìm kiếm nhanh theo thời gian và thiết bị (Yêu cầu bài toán [87])
CREATE INDEX IDX_EventLog_Device_Time ON EventLog(device_id, ts);
GO

-- =============================================
-- PHẦN 8. BẢNG ALERT (CẢNH BÁO) & ALERT ACTION
-- =============================================
CREATE TABLE Alert (
    alert_id INT IDENTITY(1,1) PRIMARY KEY,
    home_id INT,
    device_id INT,
    rule_id INT, -- Vi phạm luật nào?
    
    alert_type NVARCHAR(50), -- Security, Safety, Energy, DeviceHealth
    severity NVARCHAR(20), -- Critical, Warning
    status NVARCHAR(20) DEFAULT 'Open', -- Open, Assigned, Resolved, Closed
    message NVARCHAR(255),
    
    start_ts DATETIME, -- Thời điểm bắt đầu sự cố
    end_ts DATETIME,   -- Thời điểm kết thúc (nếu đã xong)
    
    created_at DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (home_id) REFERENCES Home(home_id),
    FOREIGN KEY (device_id) REFERENCES Device(device_id),
    FOREIGN KEY (rule_id) REFERENCES [Rule](rule_id)
);

CREATE TABLE AlertAction (
    action_id INT IDENTITY(1,1) PRIMARY KEY,
    alert_id INT,
    actor_user_id INT, -- Ai là người thực hiện?
    action_type NVARCHAR(50), -- Acknowledge, Fix, Comment, Close
    note NVARCHAR(255),
    action_ts DATETIME DEFAULT GETDATE(),
    
    FOREIGN KEY (alert_id) REFERENCES Alert(alert_id) ON DELETE CASCADE,
    FOREIGN KEY (actor_user_id) REFERENCES Users(user_id)
);
GO

-- =============================================
--   INSERT DỮ LIỆU MẪU (DATA SEEDING)
-- =============================================

-- A. Tạo 2 luật mẫu cho nhà HOME-01
INSERT INTO [Rule] (home_id, rule_name, trigger_type, severity, condition_json) VALUES
(1, N'Cửa mở quá 15 phút', 'Threshold', 'Warning', '{"duration_minutes": 15}'),
(1, N'Đột nhập khi vắng nhà', 'Event', 'Critical', '{"home_mode": "Away"}');

-- B. Giả lập lịch sử sự kiện (EventLog) trong ngày hôm qua
-- Kịch bản: Người dùng về nhà, mở cửa, bật đèn, sau đó quên đóng cửa sổ.

-- 18:00: Về nhà, mở cửa chính (device_id=1 là Cửa Chính - xem ở bước trước)
INSERT INTO EventLog (device_id, event_type, event_value, ts) VALUES 
(1, 'DoorOpen', 'Open', DATEADD(HOUR, -24, GETDATE())); -- Mở lúc hôm qua

-- 18:01: Đóng cửa chính
INSERT INTO EventLog (device_id, event_type, event_value, ts) VALUES 
(1, 'DoorClose', 'Closed', DATEADD(MINUTE, 1, DATEADD(HOUR, -24, GETDATE())));

-- 18:05: Bật đèn phòng khách (device_id=4 là Đèn chùm)
INSERT INTO EventLog (device_id, event_type, event_value, ts) VALUES 
(4, 'LightOn', 'On', DATEADD(MINUTE, 5, DATEADD(HOUR, -24, GETDATE())));

-- 18:10: Mở cửa sổ bếp (device_id=3) để thoáng gió... QUÊN ĐÓNG!
INSERT INTO EventLog (device_id, event_type, event_value, ts) VALUES 
(3, 'DoorOpen', 'Open', DATEADD(MINUTE, 10, DATEADD(HOUR, -24, GETDATE())));

-- C. Giả lập Cảnh báo (Alert) sinh ra do cái cửa sổ bếp bị quên đóng
INSERT INTO Alert (home_id, device_id, rule_id, alert_type, severity, message, start_ts) VALUES 
(1, 3, 1, 'Security', 'Warning', N'Cửa sổ bếp mở quá 15 phút!', DATEADD(MINUTE, 25, DATEADD(HOUR, -24, GETDATE())));

-- D. Giả lập hành động xử lý: Chủ nhà (User 2) đã xem và tắt cảnh báo
INSERT INTO AlertAction (alert_id, actor_user_id, action_type, note, action_ts) VALUES 
(1, 2, 'Acknowledge', N'Đã nhờ con trai lên đóng cửa', GETDATE());

-- Update trạng thái Alert thành 'Resolved'
UPDATE Alert SET status = 'Resolved', end_ts = GETDATE() WHERE alert_id = 1;
GO