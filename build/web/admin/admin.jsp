<%@page import="com.smarthome.dto.RuleDTO"%>
<%@page import="com.smarthome.dto.UserDTO"%>
<%@page import="com.smarthome.dto.UserDTO"%>
<%@page import="com.smarthome.dto.HomeDTO"%>
<%@page import="com.smarthome.dto.RoomDTO"%>
<%@page import="com.smarthome.dto.DeviceDTO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
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

        .dropdown-menu-nested .dropdown-item:hover {
            color: #0c63e4;
            background-color: #f8f9fa;
        }

        .dropdown-item:hover {
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
            /*z-index: 9999;*/
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
                <a class="navbar-brand" href="#">Smart Door</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle tab_display" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">User</a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item nav-link tab_display active" data-target="create_user_section"
                                       href="#">Create user</a></li>
                                <li><a class="dropdown-item nav-link tab_display" data-target="user_management_section"
                                       href="MainController?action=ViewUser">User management</a></li>
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
                                               data-target="house_management_section" href="MainController?action=ViewHome">Houses</a></li>
                                        <li><a class="dropdown-item nav-link tab_display"
                                               data-target="room_management_section" href="MainController?action=ViewRoom">Rooms</a></li>
                                        <li><a class="dropdown-item nav-link tab_display"
                                               data-target="device_management_section" href="MainController?action=ViewDevice">Devices</a></li>
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
                                       href="#">Edit
                                        rule template</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link tab_display"
                               href="MainController?action=ViewStatistic"
                               data-target="statistic_section">
                                Statistics
                            </a>
                        </li>
                    </ul>
                    <div class="d-flex justify-content-center flex-grow-1">
                        <form class="d-flex" style="max-width: 300px; width: 100%;">
                            <input class="form-control" type="search" placeholder="Search">
                        </form>
                    </div>
                    <form action="MainController" method="post" class="d-flex" role="search">
                        <button class="btn btn-outline-success" type="submit" name = "action" value = "Logout">Log out</button>
                    </form>
                </div>
            </div>
        </nav>
        <!-- User management section -->
        <div id="create_user_section" class="item_list">
            <div class="container-fluid section_container">
                <div class="card card_container">
                    <h5 class="card-title">Create User</h5>

                    <form action="MainController" method="post" class="d-flex submit_form" id="create_user_form">
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
                            <option value="Home Owner">House owner</option>
                            <option value="Technician">Technician</option>
                            <option value="Viewer">Viewer</option>
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
            <div id="user_management_section" class="row g-3 item_list" style="display: none;">
                <%
                    List<UserDTO> userList = (List<UserDTO>) request.getAttribute("USER_LIST");
                    if (userList != null && !userList.isEmpty()) {
                        for (UserDTO u : userList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card user_card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><%= u.getFullName()%></h5>
                            <p class="card-text">Role: <%= u.getRoleName()%></p>
                            <p class="card-text">User ID: <%= u.getUserId()%></p>
                            <p class="card-text">Username: <%= u.getUsername()%></p>
                            <p class="card-text">Email: <%= u.getEmail()%></p>
                            <p class="card-text">Status: <%= u.isStatus() ? "Active" : "Inactive"%></p>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtUserId" value="<%= u.getUserId()%>">
                                <input type="hidden" name="txtFullName" value="<%= u.getFullName()%>">
                                <input type="hidden" name="txtEmail" value="<%= u.getEmail()%>">
                                <input type="hidden" name="txtRoleName" value="<%= u.getRoleName()%>">
                                <input type="hidden" name="txtStatus" value="<%= u.isStatus() ? "1" : "0"%>">
                                <button type="submit" name="action" value="UpdateUser" class="btn btn-primary update_user_button">
                                    Update
                                </button>
                            </form>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="userId" value="<%= u.getUserId()%>">
                                <button type="submit" name="action" value="DeleteUser" class="btn btn-danger delete_user_button mx-2">
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
                <p class="text-center">No users found</p>
                <%
                    }
                %>
            </div>
        </div>

        <div id="add_facilities_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container">
                    <h5 class="card-title">Add facility</h5>
                    <form action = "MainController" method = "post"; class="d-flex submit_form" role="submit" id="create_facility_form">
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_facility_name" aria-label="Name"
                               type="text" name ="txtFacilityName" placeholder="Enter facility name">
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_facility_ID" aria-label="Name"
                               type="text" name ="txtCode" placeholder="Enter facility ID">
                        <select class="form-select input_data mx-auto mt-3 mb-0 type_select" aria-label="Default select"
                                id="status_type_select" name ="txtStatus">
                            <option class="facility_options" selected>Choose status type</option>
                            <option value="Active">Activate</option>
                            <option value="Inactive">Nor activate</option>
                        </select>
                        <select class="form-select input_data mx-auto mt-3 mb-0 type_select" aria-label="Default select"
                                id="facility_type_select">
                            <option class="facility_options" selected>Choose facility type</option>
                            <option value="1">House</option>
                            <option value="2">Room</option>
                            <option value="3">Device</option>
                        </select>
                        <div id = "additionalInputContainer" class="d-flex justify-content-center flex-column mt-3 mb-0">

                        </div>
                        <div class="button_container" id="create_facility_button_container">
                            <button class="btn btn-outline-success mx-1 mt-3 mb-0" id="submit_facility_button"
                                    type="submit" name = "action">Create</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="container-fluid">
            <div id="house_management_section" class="row g-3 item_list" style="display: none;">
                <%
                    List<HomeDTO> homeList = (List<HomeDTO>) request.getAttribute("HOME_LIST");
                    if (homeList != null && !homeList.isEmpty()) {
                        for (HomeDTO h : homeList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Facility name: <%= h.getName()%></h5>
                            <p class="card-text">Facility type: House</p>
                            <p class="card-text">Status: <%= h.getStatus()%></p>
                            <p class="card-text">Belong to owner: <%= h.getOwnerUserId()%></p>
                            <p class="card-text">Facility ID: <%= h.getCode()%></p>
                            <p class="card-text">Address: <%= h.getAddressText()%></p>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtHomeId" value="<%= h.getHomeId()%>">
                                <input type="hidden" name="txtCode" value="<%= h.getCode()%>">
                                <input type="hidden" name="txtName" value="<%= h.getName()%>">
                                <input type="hidden" name="txtAddress" value="<%= h.getAddressText()%>">
                                <input type="hidden" name="txtStatus" value="<%= h.getStatus()%>">
                                <input type="hidden" name="txtOwnerId" value="<%= h.getOwnerUserId()%>">
                                <button type="submit" name="action" value="UpdateHome" class="btn btn-primary update_facility_button">
                                    Update
                                </button>
                            </form>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="homeId" value="<%= h.getHomeId()%>">
                                <button type="submit" name="action" value="DeleteHome" class="btn btn-danger delete_facility_button mx-2">
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
                <p class="text-center">No houses found</p>
                <%
                    }
                %>
            </div>
        </div>

        <div class="container-fluid">
            <div id="room_management_section" class="row g-3 item_list" style="display: none;">
                <%
                    List<RoomDTO> roomList = (List<RoomDTO>) request.getAttribute("ROOM_LIST");
                    if (roomList != null && !roomList.isEmpty()) {
                        for (RoomDTO r : roomList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Facility name: <%= r.getName()%></h5>
                            <p class="card-text">Facility type: Room</p>
                            <p class="card-text">Status: <%= r.getStatus()%></p>
                            <p class="card-text">Belong to house: <%= r.getHomeId()%></p>
                            <p class="card-text">Facility ID: <%= r.getRoomId()%></p>
                            <p class="card-text">Room type: <%= r.getRoomType()%></p>
                            <p class="card-text">Floor: <%= r.getFloor()%></p>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtRoomId" value="<%= r.getRoomId()%>">
                                <input type="hidden" name="txtHomeId" value="<%= r.getHomeId()%>">
                                <input type="hidden" name="txtName" value="<%= r.getName()%>">
                                <input type="hidden" name="txtFloor" value="<%= r.getFloor()%>">
                                <input type="hidden" name="txtRoomType" value="<%= r.getRoomType()%>">
                                <input type="hidden" name="txtStatus" value="<%= r.getStatus()%>">
                                <button type="submit" name="action" value="UpdateRoom" class="btn btn-primary update_facility_button">
                                    Update
                                </button>
                            </form>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="roomId" value="<%= r.getRoomId()%>">
                                <button type="submit" name="action" value="DeleteRoom" class="btn btn-danger delete_facility_button mx-2">
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
                <p class="text-center">No rooms found</p>
                <%
                    }
                %>
            </div>
        </div>

        <div class="container-fluid">
            <div id="device_management_section" class="row g-3 item_list" style="display: none;">
                <%
                    List<DeviceDTO> deviceList = (List<DeviceDTO>) request.getAttribute("DEVICE_LIST");
                    if (deviceList != null && !deviceList.isEmpty()) {
                        for (DeviceDTO d : deviceList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Facility name: <%= d.getName()%></h5>
                            <p class="card-text">Facility type: Device</p>
                            <p class="card-text">Status: <%= d.getStatus()%></p>
                            <p class="card-text">Belong to room: <%= d.getRoomId()%></p>
                            <p class="card-text">Facility ID: <%= d.getSerialNo()%></p>
                            <p class="card-text">Device type: <%= d.getDeviceType()%></p>
                            <p class="card-text">Vendor: <%= d.getVendor()%></p>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="txtDeviceId" value="<%= d.getDeviceId()%>">
                                <input type="hidden" name="txtRoomId" value="<%= d.getRoomId()%>">
                                <input type="hidden" name="txtName" value="<%= d.getName()%>">
                                <input type="hidden" name="txtDeviceType" value="<%= d.getDeviceType()%>">
                                <input type="hidden" name="txtSerialNo" value="<%= d.getSerialNo()%>">
                                <input type="hidden" name="txtVendor" value="<%= d.getVendor()%>">
                                <input type="hidden" name="txtStatus" value="<%= d.getStatus()%>">
                                <button type="submit" name="action" value="UpdateDevice" class="btn btn-primary update_facility_button">
                                    Update
                                </button>
                            </form>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="deviceId" value="<%= d.getDeviceId()%>">
                                <button type="submit" name="action" value="DeleteDevice" class="btn btn-danger delete_facility_button mx-2">
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
                <p class="text-center">No devices found</p>
                <%
                    }
                %>
            </div>
        </div>
        <!--Rule section-->
        <div id="create_rule_section" class="item_list" style="display: none;">
            <div class="container-fluid section_container">
                <div class="card card_container">
                    <h5 class="card-title">Create Rule</h5>
                    <form action="MainController" method="POST" class="d-flex submit_form" role="submit" id="create_rule_form">
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_rule_name" aria-label="Name"
                               type="text" placeholder="Enter rule name" name = "ruleName">
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_home_id" aria-label="Name"
                               type="text" placeholder="Enter home ID" name = "homeId">
                        <select class="form-select input_data mx-auto mt-3 mb-0 type_select" aria-label="Default select"
                                id="rule_options_select" name = "triggerType">
                            <option class="rule_options" selected>Choose trigger type</option>
                            <option value="1">Event</option>
                            <option value="2">Schedule</option>
                            <option value="3">Threshold</option>
                        </select>
                        <input class="form-control mx-auto mt-3 mb-0 input_data input_home_id" aria-label="Name"
                               type="text" placeholder="Enter condition" name = "conditionjson">
                        <select class="form-select input_data mx-auto mt-3 mb-0 type_select" aria-label="Default select"
                                id="severity_options_select" name = "severity">
                            <option class="rule_options" selected>Choose severity type</option>
                            <option value="1">Info</option>
                            <option value="2">Warning</option>
                            <option value="3">Critical</option>
                        </select> 

                        <div class="button_container" id="create_rule_button_container">
                            <button class="btn btn-outline-success mx-1 m   t-3 mb-0" id="submit_rule_button"
                                    type="submit" name = "action" value = "CreateRule">Create</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <div class="container-fluid">
            <div id="edit_rule_section" class="row g-3 item_list" style="display: none;">
                <form action="MainController" method="post" style="display: flex;">
                    <input class="form-control mt-3 mb-0 input_data input_home_id" aria-label="Name"
                           type="text" placeholder="Enter home ID" name = "homeId">
                    <button type="submit" name="action" value="ViewRule" class="btn btn-danger mt-3 mb-0">
                        View rule
                    </button>
                </form>

                <%
                    List<RuleDTO> ruleList = (List<RuleDTO>) request.getAttribute("RULE_LIST");
                    if (ruleList != null && !ruleList.isEmpty()) {
                        for (RuleDTO r : ruleList) {
                %>

                <div class="col-6 col-lg-3 card_container">
                    <div class="card facility_card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Rule name: <%= r.getRuleName()%></h5>
                            <h5 class="card-title">Rule id <%= r.getRuleId()%></h5>
                            <p class="card-text">Belong to home: <%= r.getHomeId()%></p>
                            <p class="card-text">
                                Trigger type:
                                <%= "1".equals(r.getTriggerType()) ? "Event"
                                        : "2".equals(r.getTriggerType()) ? "Schedule"
                                        : "3".equals(r.getTriggerType()) ? "Threshold"
                                        : r.getTriggerType()%>
                            </p>

                            <p class="card-text">
                                Severity:
                                <%= "1".equals(r.getSeverity()) ? "Info"
                                        : "2".equals(r.getSeverity()) ? "Warning"
                                        : "3".equals(r.getSeverity()) ? "Critical"
                                        : r.getSeverity()%>
                            </p>
                            <p class="card-text">Condition:  <%= r.getConditionJson()%></p>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="ruleName" value="<%= r.getRuleName()%>">
                                <input type="hidden" name="homeId" value="<%= r.getHomeId()%>">
                                <input type="hidden" name="triggerType" value="<%= r.getTriggerType()%>">
                                <input type="hidden" name="conditionjson" value="<%= r.getConditionJson()%>">
                                <input type="hidden" name="severity" value="<%= r.getSeverity()%>">
                                <input type="hidden" name="ruleId" value="<%= r.getRuleId()%>">
                                <button type="submit" name="action" value="UpdateRule" class="btn btn-primary update_rule_button">
                                    Update
                                </button>
                            </form>

                            <form action="MainController" method="post" style="display:inline;">
                                <input type="hidden" name="ruleId" value="<%= r.getRuleId()%>">
                                <input type="hidden" name="homeId" value="<%= r.getHomeId()%>">
                                <button type="submit" name="action" value="DeleteRule" class="btn btn-danger delete_rule_button mx-2">
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
                <p class="text-center">No devices found</p>
                <%
                    }
                %>
            </div>
        </div>
        <div id="statistic_section" class="item_list" style="display:none;">
            <div class="container mt-4">

                <h3>System Statistics</h3>

                <div class="row">

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
        <div id="creates_system_section" class="item_list" style="display: none;">

        </div>
        <div class="container-fluid">
            <div id="edit_system_section" class="row g-3 item_list" style="display: none;">

            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const currentSection = "${CURRENT_SECTION}";

                if (currentSection && currentSection.trim() !== "") {
                    document.querySelectorAll(".item_list").forEach(item => {
                        item.style.display = "none";
                    });

                    const target = document.getElementById(currentSection);
                    if (target) {
                        target.style.display = "block";
                    }
                }
            });
        </script>
        <script type="module" src="${pageContext.request.contextPath}/admin/JS/main.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
        crossorigin="anonymous"></script>
    </body>
</html>