<%-- 
    Document   : technician
    Created on : Mar 11, 2026, 6:26:03 PM
    Author     : ASUS
--%>

<%@page import="com.smarthome.dto.DeviceDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <a class="nav-link tab_display" href="#" role="button" data-target="devices_monitor_section" data-bs-toggle="dropdown"
                            aria-expanded="false">Devices monitor</a>
                    </li>
                    <li class="nav-item dropdown">
<a class="dropdown-item nav-link tab_display"
                                            data-target="device_management_section" href="MainController?action=ViewDevice">Device list</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link tab_display" href="#" data-target="assigned_alerts_section" role="button">
                            Assigned alerts
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

    <div class="container-fluid">
        <div id="devices_monitor_section" class="row g-3 item_list" style="display: none;">

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
                    <h5 class="card-title">Facility name: <%= d.getName() %></h5>
                    <p class="card-text">Facility type: Device</p>
                    <p class="card-text">Status: <%= d.getStatus() %></p>
                    <p class="card-text">Belong to room: <%= d.getRoomId() %></p>
                    <p class="card-text">Facility ID: <%= d.getSerialNo() %></p>
                    <p class="card-text">Device type: <%= d.getDeviceType() %></p>
                    <p class="card-text">Vendor: <%= d.getVendor() %></p>

                    <form action="MainController" method="post" style="display:inline;">
    <input type="hidden" name="txtDeviceId" value="<%= d.getDeviceId() %>">
    <input type="hidden" name="txtRoomId" value="<%= d.getRoomId() %>">
    <input type="hidden" name="txtStatus" value="<%= d.getStatus() %>">
<button type="button" class="btn btn-primary update_facility_button">
    Update
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

    <div class="container-fluid">
        <div id="assigned_alerts_section" class="row g-3 item_list" style="display: none;">

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
    
    <script type="module" src="${pageContext.request.contextPath}/technician/JS/main.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q"
        crossorigin="anonymous"></script>
</body>
</html>