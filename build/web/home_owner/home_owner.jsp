<%@page import="com.smarthome.dto.RuleDTO"%>
<%@page import="com.smarthome.dto.RuleDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.smarthome.dto.AlertDTO"%>
<%@page import="com.smarthome.dto.EventLogDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.smarthome.dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.smarthome.dto.DeviceDTO"%>

<%
    // Security check + Anti-cache
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Smart Door - Home Owner</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
            body { background-color: #f8f9fa; }
            .item_list { display: none; }
            .nav-link.active { background-color: #0c63e4; color: white !important; border-radius: 5px;}
            .nav-link { border-radius: 5px; cursor: pointer; }
            .card_container { transition: transform 0.2s; }
            .card_container:hover { transform: translateY(-5px); }
        </style>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg bg-white shadow-sm border-bottom">
            <div class="container-fluid">
                <a class="navbar-brand fw-bold text-success" href="#">
                    <i class="bi bi-house-heart-fill"></i> My Smart Home
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                    <span class="navbar-toggler-icon"></span>
                </button>
                
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link tab_display active" data-target="dashboard_section" href="MainController?action=HODashboard">
                                <i class="bi bi-speedometer2"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display" data-target="mode_section" href="MainController?action=HODashboard&targetTab=mode_section">
                                <i class="bi bi-moon-stars"></i> Home Mode
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display" data-target="rules_section" href="MainController?action=HOViewRule">
                                <i class="bi bi-shield-lock"></i> My Rules
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display" data-target="simulator_section">
                                <i class="bi bi-controller"></i> Simulator
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display" data-target="device_section" href="MainController?action=HOViewDevice">
                                <i class="bi bi-cpu"></i> Devices
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display" data-target="log_section">
                                <i class="bi bi-clock-history"></i> Event Logs
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display text-danger fw-bold" data-target="alert_section" href="MainController?action=HOViewAlert">
                                <i class="bi bi-bell-fill"></i> Alerts
                            </a>
                        </li>
                    </ul>
                    
                    <div class="d-flex align-items-center">
                        <span class="me-3 text-muted fw-bold">Welcome, <%= user.getFullName() %></span>
                        <form action="../MainController" method="post" class="m-0">
                            <button class="btn btn-outline-danger btn-sm" type="submit" name="action" value="Logout">Logout</button>
                        </form>
                    </div>
                </div>
            </div>
        </nav>

        <div class="container mt-3">
            <%
                String successMsg = (String) request.getAttribute("SUCCESS_MSG");
                String errorMsg = (String) request.getAttribute("ERROR_MSG");
                if (successMsg != null && !successMsg.isEmpty()) {
            %>
            <div class="alert alert-success alert-dismissible fade show shadow-sm text-center fw-bold" role="alert">
                <i class="bi bi-check-circle-fill"></i> <%= successMsg %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%  } else if (errorMsg != null && !errorMsg.isEmpty()) { %>
            <div class="alert alert-danger alert-dismissible fade show shadow-sm text-center fw-bold" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> <%= errorMsg %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%  } %>
        </div>

        <div id="dashboard_section" class="item_list container mt-4" style="display: block;">
            <h4 class="text-success border-bottom pb-2 mb-4"><i class="bi bi-speedometer2"></i> My Home Dashboard</h4>
            <div class="row">
                <div class="col-md-4 mb-3">
                    <div class="card shadow-sm border-info text-center h-100">
                        <div class="card-body">
                            <h5 class="card-title text-info"><i class="bi bi-broadcast"></i> Recent Events</h5>
                            <h2 class="display-4 fw-bold"><%= request.getAttribute("STAT_EVENTS") != null ? request.getAttribute("STAT_EVENTS") : 0 %></h2>
                            <p class="text-muted mb-0">In the last 24 hours</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="card shadow-sm border-danger text-center h-100">
                        <div class="card-body">
                            <h5 class="card-title text-danger"><i class="bi bi-bell"></i> Active Alerts</h5>
                            <h2 class="display-4 fw-bold text-danger"><%= request.getAttribute("STAT_ALERTS") != null ? request.getAttribute("STAT_ALERTS") : 0 %></h2>
                            <p class="text-muted mb-0">Needs attention</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4 mb-3">
                    <div class="card shadow-sm border-success text-center h-100">
                        <div class="card-body">
                            <h5 class="card-title text-success"><i class="bi bi-cpu"></i> Online Devices</h5>
                            <h2 class="display-4 fw-bold"><%= request.getAttribute("STAT_ONLINE_DEV") != null ? request.getAttribute("STAT_ONLINE_DEV") : 0 %> / <%= request.getAttribute("STAT_TOTAL_DEV") != null ? request.getAttribute("STAT_TOTAL_DEV") : 0 %></h2>
                            <p class="text-muted mb-0">All systems operational</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="mode_section" class="item_list container mt-4">
            <h4 class="text-success border-bottom pb-2 mb-4"><i class="bi bi-moon-stars"></i> Manage Home Mode</h4>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-body text-center p-5">
                            <h5 class="mb-4">Current Mode: <span class="badge bg-primary fs-5"><%= request.getAttribute("STAT_MODE") != null ? request.getAttribute("STAT_MODE") : "Unknown" %></span></h5>

                            <form action="MainController" method="POST" class="d-flex justify-content-center gap-3">
                                <input type="hidden" name="action" value="HOUpdateMode">

                                <button type="submit" name="modeName" value="Home" class="btn btn-outline-primary btn-lg"><i class="bi bi-house"></i> Home</button>
                                <button type="submit" name="modeName" value="Away" class="btn btn-outline-secondary btn-lg"><i class="bi bi-car-front"></i> Away</button>
                                <button type="submit" name="modeName" value="Night" class="btn btn-outline-dark btn-lg"><i class="bi bi-moon"></i> Night</button>
                            </form>
                            <p class="text-muted mt-3 small">Changing the mode will affect how alert rules are evaluated.</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="rules_section" class="item_list container mt-4">
            <div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-4">
                <h4 class="text-success mb-0"><i class="bi bi-shield-lock"></i> My Security Rules</h4>
                <button class="btn btn-success btn-sm"><i class="bi bi-plus-lg"></i> Add Rule from Template</button>
            </div>
            
            <div class="row g-3">
                <%
                    List<RuleDTO> rules = (List<RuleDTO>) request.getAttribute("RULE_LIST");
                    if (rules != null && !rules.isEmpty()) {
                        for (RuleDTO r : rules) {
                %>
                <div class="col-md-6">
                    <div class="card shadow-sm h-100 border-start border-4 border-success">
                        <div class="card-body">
                            <h5 class="card-title text-success fw-bold"><%= r.getRuleName() %></h5>
                            <div class="mb-2">
                                <span class="badge bg-dark"><%= r.getTriggerType() %></span>
                                <span class="badge <%= "Critical".equals(r.getSeverity()) ? "bg-danger" : "bg-warning text-dark" %>"><%= r.getSeverity() %></span>
                                <span class="badge <%= r.isIsActive() ? "bg-success" : "bg-secondary" %>"><%= r.isIsActive() ? "Active" : "Disabled" %></span>
                            </div>
                            <p class="card-text text-muted small font-monospace bg-light p-2 rounded border">
                                Condition: <%= r.getConditionJson() %>
                            </p>
                            <div class="d-flex justify-content-end gap-2 mt-3">
                                <button class="btn btn-sm btn-outline-primary"><i class="bi bi-pencil"></i> Edit</button>
                                <button class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i> Delete</button>
                            </div>
                        </div>
                    </div>
                </div>
                <%      }
                    } else { %>
                <div class="col-12">
                    <div class="alert alert-secondary text-center">
                        <i class="bi bi-info-circle"></i> You haven't configured any custom rules yet.
                    </div>
                </div>
                <%  } %>
            </div>
        </div>

        <div id="simulator_section" class="item_list container mt-4">
            <h4 class="text-success border-bottom pb-2 mb-4"><i class="bi bi-controller"></i> Device Simulator</h4>
            <div class="row g-4 justify-content-center">
                <div class="col-md-4">
                    <div class="card shadow-sm text-center h-100 border-primary">
                        <div class="card-header bg-primary text-white fw-bold"><i class="bi bi-door-open"></i> Front Door</div>
                        <div class="card-body d-flex flex-column justify-content-center">
                            <form action="MainController" method="post" class="mb-3">
                                <input type="hidden" name="action" value="HOCreateEvent">
                                <input type="hidden" name="deviceId" value="1">
                                <input type="hidden" name="eventType" value="DoorOpen">
                                <input type="hidden" name="eventValue" value="Open">
                                <button class="btn btn-outline-warning w-100 fw-bold">Simulate "Open"</button>
                            </form>
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="HOCreateEvent">
                                <input type="hidden" name="deviceId" value="1">
                                <input type="hidden" name="eventType" value="DoorClose">
                                <input type="hidden" name="eventValue" value="Closed">
                                <button class="btn btn-outline-success w-100 fw-bold">Simulate "Close"</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm text-center h-100 border-warning">
                        <div class="card-header bg-warning text-dark fw-bold"><i class="bi bi-lightbulb"></i> Living Room Light</div>
                        <div class="card-body d-flex flex-column justify-content-center">
                            <form action="MainController" method="post" class="mb-3">
                                <input type="hidden" name="action" value="HOCreateEvent">
                                <input type="hidden" name="deviceId" value="3"> 
                                <input type="hidden" name="eventType" value="LightOn">
                                <input type="hidden" name="eventValue" value="On">
                                <button class="btn btn-warning w-100 fw-bold">Turn ON</button>
                            </form>
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="HOCreateEvent">
                                <input type="hidden" name="deviceId" value="3">
                                <input type="hidden" name="eventType" value="LightOff">
                                <input type="hidden" name="eventValue" value="Off">
                                <button class="btn btn-dark w-100 fw-bold">Turn OFF</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="device_section" class="item_list container mt-4">
            <h4 class="text-success border-bottom pb-2 mb-4"><i class="bi bi-cpu"></i> My Devices Overview</h4>
            <div class="row g-3">
                <%
                    List<DeviceDTO> devices = (List<DeviceDTO>) request.getAttribute("DEVICE_LIST");
                    if (devices != null && !devices.isEmpty()) {
                        for (DeviceDTO d : devices) {
                %>
                <div class="col-md-4 col-lg-3">
                    <div class="card shadow-sm h-100 card_container border-secondary">
                        <div class="card-body">
                            <h5 class="card-title text-secondary fw-bold"><%= d.getName() %></h5>
                            <hr class="mt-1 mb-2">
                            <p class="mb-1 small"><strong>Type:</strong> <%= d.getDeviceType() %></p>
                            <p class="mb-1 small">
                                <strong>Status:</strong> 
                                <span class="badge <%= "Active".equals(d.getStatus()) ? "bg-success" : "bg-danger" %>">
                                    <%= d.getStatus() %>
                                </span>
                            </p>
                            <p class="mb-0 small text-muted"><i class="bi bi-upc-scan"></i> Serial: <%= d.getSerialNo() %></p>
                        </div>
                    </div>
                </div>
                <%      }
                    } else { %>
                <div class="col-12">
                    <div class="alert alert-light text-center">No devices found.</div>
                </div>
                <%  } %>
            </div>
        </div>

        <div id="log_section" class="item_list container mt-4">
            <h4 class="text-success border-bottom pb-2 mb-4"><i class="bi bi-clock-history"></i> Search My Logs</h4>
            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <form action="MainController" method="get" class="row g-3 align-items-end">
                        <input type="hidden" name="action" value="HOSearchEvent">

                        <div class="col-md-2">
                            <label class="form-label fw-bold small">Device ID</label>
                            <input type="text" name="txtDeviceId" class="form-control" placeholder="e.g. 1">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Event Type</label>
                            <input type="text" name="txtEventType" class="form-control" list="eventTypes" placeholder="e.g. DoorOpen">
                            <datalist id="eventTypes">
                                <option value="DoorOpen">
                                <option value="DoorClose">
                                <option value="LightOn">
                                <option value="LightOff">
                            </datalist>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">Start Date</label>
                            <input type="date" name="txtStartTime" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small">End Date</label>
                            <input type="date" name="txtEndTime" class="form-control">
                        </div>
                        <div class="col-md-1 d-flex gap-2">
                            <button type="submit" class="btn btn-primary w-100"><i class="bi bi-search"></i></button>
                        </div>
                    </form>
                </div>
            </div>

            <% 
                List<EventLogDTO> eventList = (List<EventLogDTO>) request.getAttribute("EVENT_LIST");
                String result = (String) request.getAttribute("SEARCH_RESULT");
                if (result != null) { 
            %>
                <div class="alert alert-info py-2"><%= result %></div>
            <%  } %>
            
            <div class="table-responsive shadow-sm bg-white rounded">
                <table class="table table-hover table-bordered align-middle mb-0">
                    <thead class="table-dark text-center">
                        <tr>
                            <th style="width: 10%;">ID</th>
                            <th style="width: 20%;">Device ID</th>
                            <th style="width: 25%;">Event Type</th>
                            <th style="width: 20%;">Value</th>
                            <th style="width: 25%;">Timestamp</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (eventList != null && !eventList.isEmpty()) {
                            for (EventLogDTO e : eventList) { %>
                        <tr>
                            <td class="text-center fw-bold"><%= e.getEventId() %></td>
                            <td class="text-center"><span class="badge bg-secondary"><%= e.getDeviceId() %></span></td>
                            <td class="text-center"><span class="text-primary fw-bold"><%= e.getEventType() %></span></td>
                            <td class="text-center"><%= e.getEventValue() %></td>
                            <td class="text-center text-muted"><%= e.getTs() %></td>
                        </tr>
                        <%  } } else { %>
                        <tr><td colspan="5" class="text-center py-3 text-muted">No logs to display.</td></tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>

        <div id="alert_section" class="item_list container mt-4">
            <div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-4">
                <h4 class="text-danger mb-0"><i class="bi bi-bell-fill"></i> My Alerts</h4>
            </div>

            <div class="row g-3">
                <%
                    List<AlertDTO> alerts = (List<AlertDTO>) request.getAttribute("ALERT_LIST");
                    if (alerts != null && !alerts.isEmpty()) {
                        for (AlertDTO a : alerts) {
                %>
                <div class="col-md-6 col-lg-4">
                    <div class="card shadow-sm h-100 border-danger card_container">
                        <div class="card-body">
                            <h5 class="card-title text-danger fw-bold"><i class="bi bi-exclamation-triangle"></i> <%= a.getAlertType() %></h5>
                            <p class="mb-1 small"><strong>Message:</strong> <%= a.getMessage() %></p>
                            <p class="mb-1 small">
                                <strong>Severity:</strong> <span class="badge bg-warning text-dark"><%= a.getSeverity() %></span>
                            </p>
                            <p class="mb-2 small">
                                <strong>Status:</strong> <span class="badge bg-danger"><%= a.getStatus() %></span>
                            </p>
                            <p class="text-muted small mb-3"><i class="bi bi-clock"></i> <%= a.getCreatedAt() %></p>
                            
                            <form action="MainController" method="post" class="m-0 text-center">
                                <input type="hidden" name="action" value="HOAckAlert">
                                <input type="hidden" name="alertId" value="<%= a.getAlertId()%>">
                                <button class="btn btn-outline-danger btn-sm w-100 fw-bold">Acknowledge</button>
                            </form>
                        </div>
                    </div>
                </div>
                <%      }
                    } else { %>
                <div class="col-12">
                    <div class="alert alert-success text-center py-4">
                        <i class="bi bi-shield-check fs-3 d-block mb-2"></i>
                        <strong>All Clear!</strong> No active alerts for your home.
                    </div>
                </div>
                <%  } %>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                // Kiểm tra biến CURRENT_SECTION từ backend đẩy sang, nếu không có mặc định là dashboard
                const currentSection = '<%= request.getAttribute("CURRENT_SECTION") != null ? request.getAttribute("CURRENT_SECTION") : "dashboard_section" %>';
                
                // Hàm chuyển tab
                function showTab(targetId) {
                    document.querySelectorAll(".item_list").forEach(sec => sec.style.display = "none");
                    document.querySelectorAll(".tab_display").forEach(link => link.classList.remove("active"));
                    
                    const targetEl = document.getElementById(targetId);
                    if (targetEl) targetEl.style.display = "block";
                    
                    const targetLink = document.querySelector(`.tab_display[data-target='${targetId}']`);
                    if (targetLink) targetLink.classList.add("active");
                }

                // Chạy tab mặc định lúc load trang
                showTab(currentSection);

                // Gắn sự kiện click cho các thẻ nav-link
                document.querySelectorAll(".tab_display").forEach(btn => {
                    btn.addEventListener("click", function(e) {
                        // Nếu thẻ a có href trỏ về Controller (như tab Devices), không chặn reload
                        if (this.getAttribute("href") && this.getAttribute("href") !== "#") {
                            return; 
                        }
                        e.preventDefault(); // Ngăn chặn nhảy trang nếu chỉ là tab JS thuần
                        showTab(this.getAttribute("data-target"));
                    });
                });
            });
        </script>
    </body>
</html>