<%@page import="com.smarthome.dto.AlertDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.smarthome.dto.EventLogDTO"%>
<%@page import="com.smarthome.dto.RuleDTO"%>
<%@page import="com.smarthome.dto.UserDTO"%>
<%@page import="com.smarthome.dto.HomeDTO"%>
<%@page import="com.smarthome.dto.RoomDTO"%>
<%@page import="com.smarthome.dto.DeviceDTO"%>
<%@page import="java.util.List"%>

<%
    // no cache
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies

    // security check    
    if (session.getAttribute("LOGIN_USER") == null) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <style>
        #main {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            height: 100vh;
        }

        /* Nested Dropdown Styles */
        .dropdown-item-wrapper {
            position: relative;
        }

        .dropdown-item-wrapper:hover .dropdown-menu-nested,
        .dropdown-item-wrapper .dropdown-toggle-custom.show~.dropdown-menu-nested {
            display: block;
        }

        .dropdown-menu-nested {
            display: none;
            position: absolute;
            left: 100%;
            top: 0;
            background-color: #fff;
            border: 1px solid #dee2e6;
            border-radius: 0.25rem;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
            z-index: 1000;
            min-width: 160px;
            padding: 0.5rem 0;
            margin: 0;
            list-style: none;
        }

        .dropdown-menu-nested .dropdown-item {
            display: block;
            width: 100%;
            padding: 0.25rem 1.5rem;
            clear: both;
            font-weight: 400;
            color: #212529;
            text-align: inherit;
            text-decoration: none;
            white-space: nowrap;
            background-color: transparent;
            border: 0;
            cursor: pointer;
        }

        .dropdown-menu-nested .dropdown-item:hover, .dropdown-item:hover {
            color: #0c63e4;
            background-color: #f8f9fa;
        }

        .dropdown-toggle-custom::after {
            display: inline-block;
            margin-left: 0.5rem;
            vertical-align: 0.255em;
            content: "";
            border-top: 0.3em solid;
            border-right: 0.3em solid transparent;
            border-bottom: 0;
            border-left: 0.3em solid transparent;
        }

        /*Overlay*/
        .overlay {
            position: fixed;
            inset: 0;
            background: rgba(0, 0, 0, 0.5);
            z-index: 9998;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /*Create User Card*/
        .section_container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .card_container {
            padding-bottom: 15px;
            background: white;
            width: 500px;
            max-width: 90%;
            padding: 24px;
            border-radius: 10px;
        }

        .card-title {
            text-align: center;
        }

        .submit_form {
            display: flex;
            flex-direction: column;
        }

        .input_data {
            width: 50%;
            min-width: 220px;
        }

        .submit_button {
            transition: all 0.5s;
            transform-origin: center;
            margin-bottom: 16px;
            width: 25%;
        }

        .button_container {
            display: flex;
            justify-content: space-evenly;
        }

        .btn:hover {
            transform: scale(1.05);
        }

        .nav-link.active {
            background-color: #0c63e4;
            color: white !important;
        }

        .nav-link{
            border-radius: 5px;
        }

        h5 {
            margin-top: 10px;
        }

        @media (max-width: 318px) {
            .btn {
                width: 50%;
            }
        }

        .item_list {
            position: relative;
        }
    </style>

    <body>
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand fw-bold text-primary" href="#">Smart Door Admin</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link tab_display active"
                               href="../MainController?action=ViewStatistic"
                               data-target="statistic_section">
                                Statistics
                            </a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">User</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display" data-target="create_user_section"
                                       href="#">Create user</a></li>
                                <li><a class="dropdown-item nav-link tab_display" data-target="user_management_section"
                                       href="../MainController?action=ViewUser">User management</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">Facilities</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display" data-target="add_facilities_section"
                                       href="#">Add facilities</a></li>
                                <li class="dropdown-item-wrapper">
                                    <a class="dropdown-item dropdown-toggle-custom nav-link tab_display" href="#"
                                       role="button">Adjust
                                        facilities</a>
                                    <ul class="dropdown-menu-nested">
                                        <li><a class="dropdown-item nav-link tab_display"
                                               data-target="house_management_section" href="../MainController?action=ViewHome">Houses</a></li>
                                        <li><a class="dropdown-item nav-link tab_display"
                                               data-target="room_management_section" href="../MainController?action=ViewRoom">Rooms</a></li>
                                        <li><a class="dropdown-item nav-link tab_display"
                                               data-target="device_management_section" href="../MainController?action=ViewDevice">Devices</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">Rules</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display" data-target="create_rule_section"
                                       href="#">Create
                                        rule template</a></li>
                                <li><a class="dropdown-item nav-link tab_display" data-target="edit_rule_section"
                                       href="../MainController?action=ViewRule">Edit
                                        rule template</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">Monitoring</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display" data-target="alert_management_section"
                                       href="../MainController?action=ViewAlert">Global Alerts</a></li>
                                <li><a class="dropdown-item nav-link tab_display" data-target="log_management_section"
                                       href="../MainController?action=SearchEvent">Event Logs</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">System</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display" data-target="system_settings_section"
                                       href="#">System Settings</a></li>
                                <li><a class="dropdown-item nav-link tab_display" data-target="import_csv_section"
                                       href="#">Import CSV Data</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="d-flex justify-content-center flex-grow-1">
                        <form class="d-flex" style="max-width: 300px; width: 100%;">
                            <input class="form-control" type="search" placeholder="Search">
                        </form>
                    </div>
                    <form action="../MainController" method="post" class="d-flex" role="search">
                        <button class="btn btn-outline-danger" type="submit" name = "action" value = "Logout">Logout</button>
                    </form>
                </div>
            </div>
        </nav>

        <!--    MESSAGE       -->
        <div class="container mt-3">
            <%
                // 1. X? LÝ THÔNG BÁO THÀNH CÔNG (MÀU XANH)
                String successMsg = (String) request.getAttribute("SUCCESS_MSG");
                String successAttr = (String) request.getAttribute("SUCCESS");

                String finalSuccess = "";
                if (successMsg != null && !successMsg.trim().isEmpty()) {
                    finalSuccess = successMsg;
                } else if (successAttr != null && !successAttr.trim().isEmpty()) {
                    finalSuccess = successAttr;
                }

                if (!finalSuccess.isEmpty()) {
            %>
            <div class="alert alert-success text-center fw-bold shadow-sm alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill fs-5 align-middle me-1"></i> <%= finalSuccess%>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                }

                // 2. X? LÝ THÔNG BÁO L?I (MÀU ??)
                String errorMsg = (String) request.getAttribute("ERROR_MSG");
                Object errorObj = request.getAttribute("ERROR");

                String finalError = "";
                if (errorMsg != null && !errorMsg.trim().isEmpty()) {
                    finalError = errorMsg;
                } else if (errorObj != null) {
                    finalError = errorObj.toString();
                }

                if (!finalError.isEmpty()) {
            %>
            <div class="alert alert-danger text-center fw-bold shadow-sm alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill fs-5 align-middle me-1"></i> <%= finalError%>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <%
                }
            %>
        </div>

        <div id="statistic_section" class="item_list">
            <div class="container mt-4">
                <h3 class="text-center text-primary">System Statistics</h3>
                <div class="row mt-4">
                    <div class="col-md-4">
                        <canvas id="eventChart"></canvas>
                    </div>
                    <div class="col-md-4">
                        <canvas id="lightChart"></canvas>
                    </div>
                    <div class="col-md-4">
                        <canvas id="alertChart"></canvas>
                    </div>
                </div>
            </div>
        </div>

        <div id="create_user_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container">
                    <h5 class="card-title">Create User</h5>

                    <form action="../MainController" method="post" class="d-flex submit_form" id="create_user_form">
                        <input
                            class="form-control mx-auto mt-3 mb-0 input_data input_user_username"
                            type="text"
                            name="username"
                            placeholder="Enter username"
                            required
                            >

                        <input
                            class="form-control mx-auto mt-3 mb-0 input_data input_user_fullname"
                            type="text"
                            name="fullName"
                            placeholder="Enter full name"
                            required
                            >

                        <input
                            class="form-control mx-auto mt-3 mb-0 input_data input_user_email"
                            type="email"
                            name="email"
                            placeholder="Enter email"
                            required
                            >

                        <input
                            class="form-control mx-auto mt-3 mb-0 input_data input_user_password"
                            type="password"
                            name="password"
                            placeholder="Enter password"
                            required
                            >

                        <select
                            class="form-select input_data mx-auto mt-3 mb-0 role_select"
                            id="role_select"
                            name="roleId"
                            required
                            >
                            <option value="" selected disabled>Choose role</option>
                            <option value="2">House owner</option>
                            <option value="3">Technician</option>
                            <option value="4">Viewer</option>
                        </select>

                        <select name="status" class="form-select input_data mx-auto mt-3 mb-0">
                            <option value="1">Active</option>
                            <option value="0">Inactive</option>
                        </select>


                        <div class="button_container" id="create_user_button_container">
                            <button
                                class="btn btn-outline-success mx-1 mt-3 mb-0"
                                id="submit_user_button"
                                type="submit"
                                name="action"
                                value="CreateUser"
                                >
                                Create
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div id="user_management_section" class="row g-3 item_list mt-3" style="display: none;">
                <%
                    List<UserDTO> userList = (List<UserDTO>) request.getAttribute("USER_LIST");
                    if (userList != null && !userList.isEmpty()) {
                        for (UserDTO u : userList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card user_card h-100 shadow-sm border-primary">
                        <div class="card-body">
                            <h5 class="card-title text-primary"><%= u.getFullName()%></h5>
                            <p class="card-text"><strong>Role:</strong> <%= u.getRoleName()%></p>
                            <p class="card-text"><strong>User ID:</strong> <%= u.getUserId()%></p>
                            <p class="card-text"><strong>Username:</strong> <%= u.getUsername()%></p>
                            <p class="card-text"><strong>Email:</strong> <%= u.getEmail()%></p>
                            <p class="card-text"><strong>Status:</strong> 
                                <span class="badge <%= u.isStatus() ? "bg-success" : "bg-danger"%>">
                                    <%= u.isStatus() ? "Active" : "Inactive"%>
                                </span>
                            </p>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtUserId" value="<%= u.getUserId()%>">
                                <input type="hidden" name="txtFullName" value="<%= u.getFullName()%>">
                                <input type="hidden" name="txtEmail" value="<%= u.getEmail()%>">
                                <input type="hidden" name="txtRoleName" value="<%= u.getRoleName()%>">
                                <input type="hidden" name="txtStatus" value="<%= u.isStatus() ? "1" : "0"%>">
                                <button type="submit" name="action" value="UpdateUser" class="btn btn-primary update_user_button mt-2">
                                    Update
                                </button>
                            </form>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="userId" value="<%= u.getUserId()%>">
                                <button type="submit" name="action" value="DeleteUser" class="btn btn-danger delete_user_button mx-2 mt-2">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <div class="alert alert-secondary text-center w-100">No users found. Data list is empty.</div>
                <%
                    }
                %>
            </div>
        </div>

        <div id="add_facilities_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container">
                    <h5 class="card-title">Add facility</h5>
                    <form action="../MainController" method="post" class="d-flex submit_form" role="submit" id="create_facility_form">
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_facility_name" type="text" name="txtFacilityName" placeholder="Enter facility name (Required)" required>

                        <select class="form-select input_data mx-auto mt-3 mb-0 type_select" id="facility_type_select" required>
                            <option value="" selected disabled>Choose facility type</option>
                            <option value="1">House</option>
                            <option value="2">Room</option>
                            <option value="3">Device</option>
                        </select>

                        <div id="additionalInputContainer" class="d-flex justify-content-center flex-column mt-0 mb-0">
                        </div>

                        <div class="button_container" id="create_facility_button_container">
                            <button class="btn btn-outline-success mx-1 mt-3 mb-0" id="submit_facility_button" type="submit" name="action">Create</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div id="house_management_section" class="row g-3 item_list mt-3" style="display: none;">
                <%
                    List<HomeDTO> homeList = (List<HomeDTO>) request.getAttribute("HOME_LIST");
                    if (homeList != null && !homeList.isEmpty()) {
                        for (HomeDTO h : homeList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100 border-secondary">
                        <div class="card-body">
                            <h5 class="card-title text-secondary">House: <%= h.getName()%></h5>
                            <p class="card-text"><strong>Code:</strong> <%= h.getCode()%></p>
                            <p class="card-text"><strong>Status:</strong> <%= h.getStatus()%></p>
                            <p class="card-text"><strong>Owner ID:</strong> <%= h.getOwnerUserId()%></p>
                            <p class="card-text"><strong>Address:</strong> <%= h.getAddressText()%></p>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtHomeId" value="<%= h.getHomeId()%>">
                                <input type="hidden" name="txtCode" value="<%= h.getCode()%>">
                                <input type="hidden" name="txtName" value="<%= h.getName()%>">
                                <input type="hidden" name="txtAddress" value="<%= h.getAddressText()%>">
                                <input type="hidden" name="txtStatus" value="<%= h.getStatus()%>">
                                <input type="hidden" name="txtOwnerId" value="<%= h.getOwnerUserId()%>">
                                <button type="submit" name="action" value="UpdateHome" class="btn btn-primary update_facility_button mt-2">
                                    Update
                                </button>
                            </form>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="homeId" value="<%= h.getHomeId()%>">
                                <button type="submit" name="action" value="DeleteHome" class="btn btn-danger delete_facility_button mx-2 mt-2">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <div class="alert alert-secondary text-center w-100">No houses found. Data list is empty.</div>
                <%
                    }
                %>
            </div>
        </div>

        <div class="container-fluid">
            <div id="room_management_section" class="row g-3 item_list mt-3" style="display: none;">
                <%
                    List<RoomDTO> roomList = (List<RoomDTO>) request.getAttribute("ROOM_LIST");
                    if (roomList != null && !roomList.isEmpty()) {
                        for (RoomDTO r : roomList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100 border-info">
                        <div class="card-body">
                            <h5 class="card-title text-info">Room: <%= r.getName()%></h5>
                            <p class="card-text"><strong>Status:</strong> <%= r.getStatus()%></p>
                            <p class="card-text"><strong>House ID:</strong> <%= r.getHomeId()%></p>
                            <p class="card-text"><strong>Room ID:</strong> <%= r.getRoomId()%></p>
                            <p class="card-text"><strong>Type:</strong> <%= r.getRoomType()%></p>
                            <p class="card-text"><strong>Floor:</strong> <%= r.getFloor()%></p>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtRoomId" value="<%= r.getRoomId()%>">
                                <input type="hidden" name="txtHomeId" value="<%= r.getHomeId()%>">
                                <input type="hidden" name="txtName" value="<%= r.getName()%>">
                                <input type="hidden" name="txtFloor" value="<%= r.getFloor()%>">
                                <input type="hidden" name="txtRoomType" value="<%= r.getRoomType()%>">
                                <input type="hidden" name="txtStatus" value="<%= r.getStatus()%>">
                                <button type="submit" name="action" value="UpdateRoom" class="btn btn-primary update_facility_button mt-2">
                                    Update
                                </button>
                            </form>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="roomId" value="<%= r.getRoomId()%>">
                                <button type="submit" name="action" value="DeleteRoom" class="btn btn-danger delete_facility_button mx-2 mt-2">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <div class="alert alert-info text-center w-100">No rooms found. Data list is empty.</div>
                <%
                    }
                %>
            </div>
        </div>

        <div class="container-fluid">
            <div id="device_management_section" class="row g-3 item_list mt-3" style="display: none;">
                <%
                    List<DeviceDTO> deviceList = (List<DeviceDTO>) request.getAttribute("DEVICE_LIST");
                    if (deviceList != null && !deviceList.isEmpty()) {
                        for (DeviceDTO d : deviceList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100 border-dark">
                        <div class="card-body">
                            <h5 class="card-title text-dark">Device: <%= d.getName()%></h5>
                            <p class="card-text"><strong>Status:</strong> <%= d.getStatus()%></p>
                            <p class="card-text"><strong>Room ID:</strong> <%= d.getRoomId()%></p>
                            <p class="card-text"><strong>Serial No:</strong> <%= d.getSerialNo()%></p>
                            <p class="card-text"><strong>Type:</strong> <%= d.getDeviceType()%></p>
                            <p class="card-text"><strong>Vendor:</strong> <%= d.getVendor()%></p>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtDeviceId" value="<%= d.getDeviceId()%>">
                                <input type="hidden" name="txtHomeId" value="<%= d.getHomeId()%>">
                                <input type="hidden" name="txtRoomId" value="<%= d.getRoomId()%>">
                                <input type="hidden" name="txtName" value="<%= d.getName()%>">
                                <input type="hidden" name="txtDeviceType" value="<%= d.getDeviceType()%>">
                                <input type="hidden" name="txtSerialNo" value="<%= d.getSerialNo()%>">
                                <input type="hidden" name="txtVendor" value="<%= d.getVendor()%>">
                                <input type="hidden" name="txtStatus" value="<%= d.getStatus()%>">
                                <button type="submit" name="action" value="UpdateDevice" class="btn btn-primary update_facility_button mt-2">
                                    Update
                                </button>
                            </form>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="deviceId" value="<%= d.getDeviceId()%>">
                                <button type="submit" name="action" value="DeleteDevice" class="btn btn-danger delete_facility_button mx-2 mt-2">
                                    Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                %>
                <div class="alert alert-dark text-center w-100">No devices found. Data list is empty.</div>
                <%
                    }
                %>
            </div>
        </div>

        <div id="create_rule_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container" style="width: 600px; max-width: 95%;"> <h5 class="card-title text-primary fw-bold">Create Rule Template</h5>
                    <form action="../MainController" method="POST" class="d-flex submit_form" id="create_rule_form">

                        <label class="fw-bold text-muted small mt-3">Rule Name</label>
                        <input class="form-control mx-auto mt-1 mb-0 input_data w-100" type="text" placeholder="e.g. Door open too long" name="ruleName" required>

                        <input type="hidden" name="homeId" value="0">

                        <div class="row mt-2">
                            <div class="col-6">
                                <label class="fw-bold text-muted small">Trigger Type</label>
                                <select class="form-select input_data w-100" name="triggerType" required>
                                    <option value="" selected disabled>Choose trigger</option>
                                    <option value="Event">Event</option>
                                    <option value="Schedule">Schedule</option>
                                    <option value="Threshold">Threshold</option>
                                </select>
                            </div>
                            <div class="col-6">
                                <label class="fw-bold text-muted small">Severity</label>
                                <select class="form-select input_data w-100" name="severity" required>
                                    <option value="" selected disabled>Choose severity</option>
                                    <option value="Info">Info</option>
                                    <option value="Warning">Warning</option>
                                    <option value="Critical">Critical</option>
                                </select>
                            </div>
                        </div>

                        <label class="fw-bold text-muted small mt-3">Condition (JSON Format)</label>
                        <textarea class="form-control mx-auto mt-1 mb-0 input_data w-100 font-monospace" 
                                  rows="4" name="conditionjson" id="json_create_input" 
                                  placeholder='{"duration_minutes": 15}' required></textarea>
                        <small id="json_create_error" class="text-danger fw-bold d-none">Invalid JSON format!</small>

                        <div class="button_container mt-4">
                            <button class="btn btn-primary w-50" id="submit_rule_button" type="submit" name="action" value="CreateRule">Create Template</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div id="edit_rule_section" class="row g-3 item_list mt-3" style="display: none;">

                <div class="col-12 d-flex justify-content-center">
                    <form action="../MainController" method="post" style="display: flex;" class="w-50 gap-2">
                        <input class="form-control input_data w-100" aria-label="Name"
                               type="text" placeholder="Enter home ID to view specific rules (Leave blank for Templates)" name="homeId">
                        <button type="submit" name="action" value="ViewRule" class="btn btn-primary text-nowrap">
                            Search Rules
                        </button>
                    </form>
                </div>

                <%
                    String searchMode = (String) request.getAttribute("SEARCH_MODE");
                    Integer searchHomeId = (Integer) request.getAttribute("SEARCH_HOME_ID");

                    // L?P PHÒNG TH?: N?u m?i vào trang ch?a có d? li?u, m?c ??nh là xem Template
                    if (searchMode == null) {
                        searchMode = "TEMPLATE";
                    }

                    if ("TEMPLATE".equals(searchMode)) {
                %>
                <div class="col-12 mt-4 mb-2">
                    <h4 class="text-primary border-bottom pb-2">
                        <i class="bi bi-journal-text"></i> Global Rule Templates
                    </h4>
                </div>
                <%
                } else if ("HOME".equals(searchMode)) {
                %>
                <div class="col-12 mt-4 mb-2 d-flex justify-content-between align-items-end border-bottom pb-2">
                    <h4 class="text-success mb-0">
                        <i class="bi bi-house-door"></i> Rules for Home ID: <%= searchHomeId%>
                    </h4>
                    <a href="../MainController?action=ViewRule" class="btn btn-sm btn-outline-secondary">
                        View Templates
                    </a>
                </div>
                <%
                    }
                %>

                <%
                    List<RuleDTO> ruleList = (List<RuleDTO>) request.getAttribute("RULE_LIST");
                    if (ruleList != null && !ruleList.isEmpty()) {
                        for (RuleDTO r : ruleList) {
                %>

                <div class="col-6 col-lg-3 card_container mt-3">
                    <div class="card facility_card h-100 border-warning shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title text-warning fw-bold"><%= r.getRuleName()%></h5>
                            <hr>
                            <p class="card-text mb-1"><strong>Rule ID:</strong> <%= r.getRuleId()%></p>
                            <p class="card-text mb-1"><strong>Home ID:</strong> <%= r.getHomeId() == 0 ? "N/A (Template)" : r.getHomeId()%></p>
                            <p class="card-text mb-1"><strong>Trigger type:</strong> <%= r.getTriggerType()%></p>
                            <p class="card-text mb-1">
                                <strong>Severity:</strong> 
                                <span class="badge <%= r.getSeverity().equals("Critical") ? "bg-danger" : (r.getSeverity().equals("Warning") ? "bg-warning text-dark" : "bg-info text-dark")%>">
                                    <%= r.getSeverity()%>
                                </span>
                            </p>
                            <p class="card-text mb-3"><strong>Condition:</strong> <code class="text-secondary"><%= r.getConditionJson()%></code></p>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="ruleName" value="<%= r.getRuleName()%>">
                                <input type="hidden" name="homeId" value="<%= r.getHomeId()%>">
                                <input type="hidden" name="triggerType" value="<%= r.getTriggerType()%>">
                                <input type="hidden" name="conditionjson" value="<%= r.getConditionJson() != null ? r.getConditionJson().replace("\"", "&quot;") : ""%>">
                                <input type="hidden" name="severity" value="<%= r.getSeverity()%>">
                                <input type="hidden" name="ruleId" value="<%= r.getRuleId()%>">

                                <button type="submit" name="action" value="UpdateRule" class="btn btn-outline-primary btn-sm update_rule_button w-100 mb-2">
                                    <i class="bi bi-pencil-square"></i> Edit
                                </button>
                            </form>

                            <form action="../MainController" method="post" style="display:inline;">
                                <input type="hidden" name="ruleId" value="<%= r.getRuleId()%>">
                                <input type="hidden" name="homeId" value="<%= r.getHomeId()%>">
                                <button type="submit" name="action" value="DeleteRule" class="btn btn-outline-danger btn-sm delete_rule_button w-100">
                                    <i class="bi bi-trash"></i> Delete
                                </button>
                            </form>
                        </div>
                    </div>
                </div>

                <%
                    }
                } else {
                    // Thông báo khi không có d? li?u (Linh ho?t theo Mode)
                    if ("TEMPLATE".equals(searchMode)) {
                %>
                <div class="alert alert-info text-center w-100 mt-4 shadow-sm">
                    <i class="bi bi-info-circle-fill"></i> No Rule Templates found. Please create a new template first!
                </div>
                <%      } else {%>
                <div class="alert alert-warning text-center w-100 mt-4 shadow-sm">
                    <i class="bi bi-exclamation-triangle-fill"></i> No rules found for Home ID <strong><%= searchHomeId != null ? searchHomeId : "Unknown"%></strong>. Try a different ID.
                </div>
                <%      }
                    }
                %>
            </div>
        </div>

        <div id="alert_management_section" class="item_list" style="display: none;">
            <div class="container-fluid mt-4">
                <h4 class="text-danger border-bottom pb-2 mb-4">
                    <i class="bi bi-exclamation-triangle-fill"></i> Global Alerts Management
                </h4>

                <div class="card shadow-sm mb-4">
                    <div class="card-body">
                        <form action="../ViewAlertController" method="POST" class="d-flex justify-content-center gap-3">

                            <%
                                String pStatus = (String) request.getAttribute("PARAM_STATUS");
                                String pSeverity = (String) request.getAttribute("PARAM_SEVERITY");
                                String pKeyword = (String) request.getAttribute("PARAM_KEYWORD");
                            %>

                            <select name="filterStatus" class="form-select w-25">
                                <option value="">-- All Statuses --</option>
                                <option value="Open" <%= "Open".equals(pStatus) ? "selected" : ""%>>Open</option>
                                <option value="Assigned" <%= "Assigned".equals(pStatus) ? "selected" : ""%>>Assigned</option>
                                <option value="Ack" <%= "Ack".equals(pStatus) ? "selected" : ""%>>Acknowledged</option>
                                <option value="Resolved" <%= "Resolved".equals(pStatus) ? "selected" : ""%>>Resolved</option>
                            </select>

                            <select name="filterSeverity" class="form-select w-25">
                                <option value="">-- All Severities --</option>
                                <option value="Info" <%= "Info".equals(pSeverity) ? "selected" : ""%>>Info</option>
                                <option value="Warning" <%= "Warning".equals(pSeverity) ? "selected" : ""%>>Warning</option>
                                <option value="Critical" <%= "Critical".equals(pSeverity) ? "selected" : ""%>>Critical</option>
                            </select>

                            <input type="text" name="filterKeyword" class="form-control w-25" 
                                   placeholder="Search Keyword / Rule Name" value="<%= pKeyword != null ? pKeyword : ""%>">

                            <button type="submit" class="btn btn-danger px-4"><i class="bi bi-search"></i> Filter</button>
                            <a href="../ViewAlertController" class="btn btn-outline-secondary">Clear</a>
                        </form>
                    </div>
                </div>

                <div class="table-responsive shadow-sm bg-white rounded">
                    <table class="table table-hover table-bordered align-middle mb-0">
                        <thead class="table-dark text-center">
                            <tr>
                                <th style="width: 5%;">ID</th>
                                <th style="width: 25%;">Alert Details</th>
                                <th style="width: 15%;">Location & Device</th>
                                <th style="width: 10%;">Severity</th>
                                <th style="width: 10%;">Status</th>
                                <th style="width: 25%;">Timeline</th>
                                <th style="width: 10%;">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<AlertDTO> alertList = (List<AlertDTO>) request.getAttribute("ALERT_LIST");
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                                if (alertList != null && !alertList.isEmpty()) {
                                    for (AlertDTO a : alertList) {
                            %>
                            <tr>
                                <td class="text-center fw-bold"><%= a.getAlertId()%></td>
                                <td>
                                    <span class="fw-bold text-danger"><%= a.getAlertType()%></span><br>
                                    <small class="text-muted"><i class="bi bi-chat-dots"></i> <%= a.getMessage()%></small><br>
                                    <span class="badge bg-light text-dark border">Rule: <%= a.getRuleName() != null ? a.getRuleName() : "N/A"%></span>
                                </td>
                                <td>
                                    <div class="fw-bold"><%= a.getHomeName() != null ? a.getHomeName() : "Unknown Home"%></div>
                                    <small class="text-primary"><i class="bi bi-cpu"></i> <%= a.getDeviceName() != null ? a.getDeviceName() : "Unknown Device"%></small>
                                </td>
                                <td class="text-center">
                                    <%
                                        String sevClass = "bg-secondary";
                                        if ("Critical".equals(a.getSeverity()))
                                            sevClass = "bg-danger";
                                        else if ("Warning".equals(a.getSeverity()))
                                            sevClass = "bg-warning text-dark";
                                        else if ("Info".equals(a.getSeverity()))
                                            sevClass = "bg-info text-dark";
                                    %>
                                    <span class="badge <%= sevClass%>"><%= a.getSeverity()%></span>
                                </td>
                                <td class="text-center">
                                    <%
                                        String statClass = "bg-secondary";
                                        if ("Open".equals(a.getStatus()))
                                            statClass = "bg-danger";
                                        else if ("Assigned".equals(a.getStatus()))
                                            statClass = "bg-primary";
                                        else if ("Ack".equals(a.getStatus()))
                                            statClass = "bg-info text-dark";
                                        else if ("Resolved".equals(a.getStatus()) || "Closed".equals(a.getStatus()))
                                            statClass = "bg-success";
                                    %>
                                    <span class="badge <%= statClass%>"><%= a.getStatus()%></span>
                                </td>
                                <td class="text-muted small">
                                    <strong>Start:</strong> <%= a.getStartTs() != null ? sdf.format(a.getStartTs()) : "N/A"%><br>
                                    <strong>End:</strong> <%= a.getEndTs() != null ? sdf.format(a.getEndTs()) : "N/A"%>
                                </td>
                                <td class="text-center">
                                    <form action="../MainController" method="POST" style="display:inline;">
                                        <input type="hidden" name="alertId" value="<%= a.getAlertId()%>">
                                        <button type="submit" name="action" value="DeleteAlert" class="btn btn-outline-danger btn-sm" onclick="return confirm('Are you sure you want to delete this alert?');">
                                            <i class="bi bi-trash"></i> Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="7" class="text-center py-4 text-success">
                                    <i class="bi bi-shield-check fs-2 d-block mb-2"></i>
                                    <strong>All Clear!</strong> No alerts found in the system.
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div id="log_management_section" class="item_list" style="display: none;">
            <div class="container-fluid mt-4">
                <h4 class="text-primary border-bottom pb-2 mb-4">
                    <i class="bi bi-clock-history"></i> System Event Logs
                </h4>

                <div class="card shadow-sm mb-4">
                    <div class="card-body d-flex justify-content-between align-items-center">
                        <form action="../SearchEventController" method="POST" class="d-flex justify-content-center gap-3 flex-grow-1">
                            <input type="text" name="filterDevice" class="form-control w-25" placeholder="Enter Device ID or Event Type (e.g. DoorOpen)">
                            <input type="date" name="filterDate" class="form-control w-25">
                            <button type="submit" class="btn btn-primary px-4"><i class="bi bi-search"></i> Search Logs</button>
                            <a href="../SearchEventController" class="btn btn-outline-secondary">Clear</a>
                        </form>

                        <form action="../ExportCSVController" method="POST" class="m-0">
                            <input type="hidden" name="exportDevice" value="<%= request.getParameter("filterDevice") != null ? request.getParameter("filterDevice") : ""%>">
                            <input type="hidden" name="exportDate" value="<%= request.getParameter("filterDate") != null ? request.getParameter("filterDate") : ""%>">
                            <button type="submit" class="btn btn-success fw-bold">
                                <i class="bi bi-file-earmark-excel-fill"></i> Export CSV
                            </button>
                        </form>
                    </div>
                </div>



                <div class="table-responsive shadow-sm bg-white rounded">
                    <table class="table table-hover table-bordered align-middle mb-0">
                        <thead class="table-dark text-center">
                            <tr>
                                <th style="width: 5%;">Log ID</th>
                                <th style="width: 15%;">Location</th>
                                <th style="width: 20%;">Device</th>
                                <th style="width: 15%;">Event Type</th>
                                <th style="width: 15%;">Value</th>
                                <th style="width: 20%;">Timestamp</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<EventLogDTO> logList = (List<EventLogDTO>) request.getAttribute("EVENT_LIST");
                                SimpleDateFormat logSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                                if (logList != null && !logList.isEmpty()) {
                                    for (EventLogDTO log : logList) {
                            %>
                            <tr>
                                <td class="text-center fw-bold"><%= log.getEventId()%></td>
                                <td>
                                    <div class="fw-bold"><%= log.getHomeName() != null ? log.getHomeName() : "Unknown Home"%></div>
                                    <small class="text-muted"><%= log.getRoomName() != null ? log.getRoomName() : "Unknown Room"%></small>
                                </td>
                                <td>
                                    <span class="text-primary fw-bold"><%= log.getDeviceName() != null ? log.getDeviceName() : "Device Deleted"%></span><br>
                                    <span class="badge bg-secondary">ID: <%= log.getDeviceId()%></span>
                                </td>
                                <td class="text-center"><span class="badge bg-info text-dark"><%= log.getEventType()%></span></td>
                                <td class="text-center">
                                    <%
                                        String val = log.getEventValue();
                                        String badgeClass = "bg-secondary";
                                        if ("Open".equalsIgnoreCase(val) || "On".equalsIgnoreCase(val) || "Unlocked".equalsIgnoreCase(val))
                                            badgeClass = "bg-warning text-dark";
                                        else if ("Closed".equalsIgnoreCase(val) || "Off".equalsIgnoreCase(val) || "Locked".equalsIgnoreCase(val))
                                            badgeClass = "bg-success";
                                        else if ("Error".equalsIgnoreCase(val))
                                            badgeClass = "bg-danger";
                                    %>
                                    <span class="badge <%= badgeClass%>"><%= val%></span>
                                </td>
                                <td class="text-center text-muted"><%= logSdf.format(log.getTs())%></td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center py-4 text-muted">
                                    <i class="bi bi-inbox fs-3 d-block mb-2"></i>
                                    No event logs found matching your criteria.
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div id="system_settings_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container shadow-sm border-primary">
                    <h5 class="card-title text-primary">System Settings</h5>
                    <form class="d-flex submit_form mt-3">
                        <label class="fw-bold">Device Offline Threshold (minutes)</label>
                        <input type="number" class="form-control mx-auto mt-1 mb-3 input_data" value="30">

                        <label class="fw-bold">Alert Deduplication Window (minutes)</label>
                        <input type="number" class="form-control mx-auto mt-1 mb-3 input_data" value="15">

                        <button type="button" class="btn btn-primary mx-auto mt-2 w-50">Save Configuration</button>
                    </form>
                </div>
            </div>
        </div>

        <<div id="import_csv_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container shadow-sm border-success">
                    <h5 class="card-title text-success">Import CSV Data</h5>

                    <form action="../ImportCSVController" method="POST" enctype="multipart/form-data" class="d-flex submit_form mt-3">
                        <label class="text-center text-muted mb-2">Upload event logs for system ingestion</label>

                        <input type="file" name="csvFile" class="form-control mx-auto mb-4 input_data w-100" accept=".csv" required>

                        <button type="submit" class="btn btn-success mx-auto w-50">Import Data</button>
                    </form>

                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const currentSection = "${CURRENT_SECTION}";

                document.querySelectorAll(".item_list").forEach(item => {
                    item.style.display = "none";
                });

                if (currentSection && currentSection.trim() !== "") {
                    const target = document.getElementById(currentSection);
                    if (target) {
                        target.style.display = "block";

                        document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
                        const activeLink = document.querySelector(`[data-target="${currentSection}"]`);
                        if (activeLink)
                            activeLink.classList.add('active');
                    }
                } else {
                    document.getElementById("statistic_section").style.display = "block";

                    document.querySelectorAll('.nav-link').forEach(link => link.classList.remove('active'));
                    const statLink = document.querySelector(`[data-target="statistic_section"]`);
                    if (statLink)
                        statLink.classList.add('active');
                }
            });
        </script>

        <script type="module" src="${pageContext.request.contextPath}/admin/JS/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
        crossorigin="anonymous"></script>

        <script>
            const CHART_DATA = {
                rawEventStats: '<%= request.getAttribute("JSON_EVENT_STATS") != null ? request.getAttribute("JSON_EVENT_STATS") : "[]"%>',
                rawLightStats: '<%= request.getAttribute("JSON_LIGHT_STATS") != null ? request.getAttribute("JSON_LIGHT_STATS") : "[]"%>',
                rawAlertStats: '<%= request.getAttribute("JSON_ALERT_STATS") != null ? request.getAttribute("JSON_ALERT_STATS") : "[]"%>'
            };
        </script>

        <script src="${pageContext.request.contextPath}/admin/JS/statistics.js"></script>
    </body>
</html>