<%@page import="com.smarthome.dto.AlertDTO"%>
<%@page import="com.smarthome.dto.EventLogDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.smarthome.dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@page import="com.smarthome.dto.DeviceDTO"%>

<%
    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%
    List<EventLogDTO> eventList = (List<EventLogDTO>) request.getAttribute("EVENT_LIST");
    String result = (String) request.getAttribute("SEARCH_RESULT");
%>

<%
    List<DeviceDTO> devices = (List<DeviceDTO>) request.getAttribute("DEVICE_LIST");
%>

<%
    List<AlertDTO> alerts = (List<AlertDTO>) request.getAttribute("ALERT_LIST");
%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Smart Door - Home Owner</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>

            body{
                background:#1e1e2f;
                color:white;
                min-height:100vh;
            }

            .section{
                display:none;
                padding-top:40px;
            }

            .section.active{
                display:flex;
                flex-direction:column;
                align-items:center;
            }

            .section h2{
                margin-bottom:30px;
            }

            .card{
                background:#2b2b3c;
                border:none;
                border-radius:10px;
                padding:25px;
                color:white;
                min-width:250px;
            }

            .row-center{
                display:flex;
                justify-content:center;
                gap:30px;
                flex-wrap:wrap;
            }

            .navbar{
                background:#111827 !important;
            }

            .navbar-nav .nav-link{
                padding:18px 25px;
                color:#d1d5db !important;
                transition:0.3s;
            }

            .navbar-nav .nav-link:hover{
                background:#2563eb;
                color:white !important;
            }

            .navbar-nav .nav-link.active{
                background:#2563eb;
            }

            .btn{
                width:100%;
            }

            .search-btn{
                width:auto !important;
            }

        </style>

    </head>


    <body>

        <!-- NAVBAR -->

        <nav class="navbar navbar-expand-lg navbar-dark">

            <div class="container-fluid">

                <a class="navbar-brand fw-bold">Smart Door</a>

                <ul class="navbar-nav me-auto">

                    <li class="nav-item">
                        <a class="nav-link tab-btn" data-target="event_section">
                            Event Simulator
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link tab-btn" data-target="log_section">
                            Search Logs
                        </a>
                    </li>

<li class="nav-item">
        <a class="nav-link tab-btn <%= devices != null ? "active" : "" %>"
           data-target="device_section"
           href="MainController?action=ViewDevice">
            My Devices
        </a>
    </li>

                    <li class="nav-item">
                        <a class="nav-link tab-btn" data-target="alert_section">
                            My Alerts
                        </a>
                    </li>

                </ul>

                <form action="MainController">
                    <button class="btn btn-outline-light" name="action" value="Logout">
                        Logout
                    </button>
                </form>

            </div>

        </nav>


        <!-- EVENT SIMULATOR -->

       <div id="event_section" class="section <%= devices == null ? "active" : "" %>">

            <h2>Event Simulator</h2>

            <div class="row-center">

                <!-- Door Sensor -->

                <div class="card text-center">

                    <h4>Door Sensor</h4>

                    <form action="MainController" method="post" class="mb-2">

                        <input type="hidden" name="action" value="CreateEvent">
                        <input type="hidden" name="deviceId" value="1">
                        <input type="hidden" name="eventType" value="DoorOpen">

                        <button class="btn btn-success">
                            Door Open
                        </button>

                    </form>

                    <form action="MainController" method="post">

                        <input type="hidden" name="action" value="CreateEvent">
                        <input type="hidden" name="deviceId" value="1">
                        <input type="hidden" name="eventType" value="DoorClose">

                        <button class="btn btn-danger">
                            Door Close
                        </button>

                    </form>

                </div>


                <!-- Light -->

                <div class="card text-center">

                    <h4>Light</h4>

                    <form action="MainController" method="post" class="mb-2">

                        <input type="hidden" name="action" value="CreateEvent">
                        <input type="hidden" name="deviceId" value="3">
                        <input type="hidden" name="eventType" value="LightOn">

                        <button class="btn btn-warning">
                            Light On
                        </button>

                    </form>

                    <form action="MainController" method="post">

                        <input type="hidden" name="action" value="CreateEvent">
                        <input type="hidden" name="deviceId" value="3">
                        <input type="hidden" name="eventType" value="LightOff">

                        <button class="btn btn-secondary">
                            Light Off
                        </button>

                    </form>

                </div>

            </div>

        </div>


        <!-- SEARCH LOGS -->

        <div id="log_section" class="section">

            <h2>Search Logs</h2>



            <form action="SearchEventController" method="get">

                <div class="row g-3 align-items-end">

                    <div class="col">
                        <label class="form-label text-white">Device ID</label>
                        <input 
                            type="text"
                            name="txtDeviceId"
                            class="form-control"
                            placeholder="Device ID">
                    </div>

                    <div class="col">
                        <label class="form-label text-white">Event Type</label>
                         <input 
                            type="text"
                            name="txtEventType"
                            class="form-control"
                            placeholder="Event Type"
                            list="eventTypes">
                        <datalist id="eventTypes">

                            <option value="DoorOpen">
                            <option value="DoorClose">
                            <option value="LightOn">
                            <option value="LightOff">

                        </datalist>
                    </div>

                    <div class="col">
                        <label class="form-label text-white">Start Date</label>
                        <input 
                            type="date"
                            name="txtStartTime"
                            class="form-control">
                    </div>

                    <div class="col">
                        <label class="form-label text-white">End Date</label>
                        <input 
                            type="date"
                            name="txtEndTime"
                            class="form-control">
                    </div>

                    <div class="col-auto">
                        <button class="btn btn-primary px-4">
                            Search
                        </button>
                    </div>

                </div>

            </form>


            <!-- RESULT -->

            <% if (eventList != null) {%>

            <h5><%= result%></h5>



            <tr>
                <th>ID</th>
                <th>Device</th>
                <th>Event</th>
                <th>Value</th>
                <th>Time</th>
            </tr>

            <% for (EventLogDTO e : eventList) {%>

            <tr>

                <td><%= e.getEventId()%></td>
                <td><%= e.getDeviceId()%></td>
                <td><%= e.getEventType()%></td>
                <td><%= e.getEventValue()%></td>
                <td><%= e.getTs()%></td>

            </tr>

            <% } %>



            <% }%>



        </div>





        <!-- MY DEVICES -->

        <div id="device_section" class="section <%= devices != null ? "active" : "" %>">

            <h2>My Devices</h2>

            <div class="row-center">

                <%
                    if (devices != null && !devices.isEmpty()) {
                        for (DeviceDTO d : devices) {
                %>

                <div class="card text-center">

                    <h4><%= d.getName()%></h4>

                    <p>Type: <%= d.getDeviceType()%></p>

                    <p>
                        Status: 
                        <span class="text-info">
                            <%= d.getStatus()%>
                        </span>
                    </p>

                    <p style="font-size:13px;color:#9ca3af">
                        Serial: <%= d.getSerialNo()%>
                    </p>

                </div>

                <%
                    }
                } else {
                %>

                <p>No devices found.</p>

                <% }%>

            </div>

        </div>






        <!-- MY ALERTS -->

        <div id="alert_section" class="section">
                            <form action="MainController" method="post" style="display: flex;">
                      <input class="form-control mt-3 mb-0 input_data input_home_id" aria-label="Name"
                        type="text" placeholder="Enter home ID" name = "homeId">
                        <button type="submit" name="action" value="ViewAlert" class="btn btn-danger mt-3 mb-0">
                            View alert
                        </button>
                    </form>
            <h2>My Alerts</h2>

            <div class="row-center">

                <%
                    if (alerts != null && !alerts.isEmpty()) {
                        for (AlertDTO a : alerts) {
                %>

                <div class="card text-center">

                    <h4><%= a.getMessage()%></h4>

                    <p>
                        Severity:
                        <span class="text-warning">
                            <%= a.getSeverity()%>
                        </span>
                    </p>

                    <p>
                        Status:
                        <span class="text-danger">
                            <%= a.getStatus()%>
                        </span>
                    </p>

                    <p style="font-size:13px;color:#9ca3af">
                        Time: <%= a.getCreatedAt()%>
                    </p>

                    <!-- ACKNOWLEDGE BUTTON -->

                    <form action="MainController" method="post">

                        <input type="hidden" name="action" value="AckAlert">

                        <input type="hidden" 
                               name="alertId" 
                               value="<%= a.getAlertId()%>">

                        <button class="btn btn-warning mt-2">
                            Acknowledge
                        </button>

                    </form>

                </div>

                <%
                    }
                } else {
                %>

                <p>No alerts found.</p>

                <%
                    }
                %>

            </div>

        </div>



        <script>

            const buttons = document.querySelectorAll(".tab-btn");

            buttons.forEach(btn => {

                btn.addEventListener("click", () => {

                    document.querySelectorAll(".section").forEach(sec => {
                        sec.classList.remove("active");
                    });

                    document.querySelectorAll(".nav-link").forEach(link => {
                        link.classList.remove("active");
                    });

                    const target = btn.getAttribute("data-target");

                    document.getElementById(target).classList.add("active");

                    btn.classList.add("active");

                });

            });
            
        </script>


    </body>
</html>