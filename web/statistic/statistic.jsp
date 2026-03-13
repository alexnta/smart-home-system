<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.smarthome.dto.StatisticDTO"%>

<%
    List<StatisticDTO> eventStats = (List<StatisticDTO>) request.getAttribute("eventStats");
    List<StatisticDTO> lightStats = (List<StatisticDTO>) request.getAttribute("lightStats");
    List<StatisticDTO> alertStats = (List<StatisticDTO>) request.getAttribute("alertStats");
%>

<!DOCTYPE html>
<html>

    <head>

        <title>Smart Home Statistics</title>

        <style>

            body{
                font-family: Arial;
                background:#f4f6f9;
                margin:0;
                text-align:center;
            }

            h1{
                margin-top:30px;
            }

            .dashboard{
                width:85%;
                margin:40px auto;

                display:grid;
                grid-template-columns:1fr 1fr;
                gap:35px;
            }

            .chart-box{
                background:white;
                padding:25px;
                border-radius:20px;
                box-shadow:0 4px 15px rgba(0,0,0,0.1);
            }

            .chart-center{
                grid-column:span 2;
                width:55%;
                margin:auto;
            }

            canvas{
                width:350px !important;
                height:350px !important;
                margin:auto;
            }

        </style>

    </head>

    <body>

        <h1>Smart Home Statistics</h1>

        <div class="dashboard">

            <div class="chart-box">
                <h3>Event Distribution</h3>
                <canvas id="eventChart"></canvas>
            </div>

            <div class="chart-box">
                <h3>Light Status</h3>
                <canvas id="lightChart"></canvas>
            </div>

            <div class="chart-box chart-center">
                <h3>Alert Severity</h3>
                <canvas id="alertChart"></canvas>
            </div>

        </div>


        <script>

            /* ===== EVENT DATA ===== */

            let eventLabels = [];
            let eventData = [];

            <%
            if (eventStats != null) {
                for (StatisticDTO s : eventStats) {
            %>
            eventLabels.push("<%=s.getName()%>");
            eventData.push(<%=s.getPercentage()%>);
            <%
                }
            }
            %>


            /* ===== LIGHT DATA ===== */

            let lightLabels = [];
            let lightData = [];

            <%
            if (lightStats != null) {
                for (StatisticDTO s : lightStats) {
            %>
            lightLabels.push("<%=s.getName()%>");
            lightData.push(<%=s.getPercentage()%>);
            <%
                }
            }
            %>


            /* ===== ALERT DATA ===== */

            let alertLabels = [];
            let alertData = [];

            <%
            if (alertStats != null) {
                for (StatisticDTO s : alertStats) {
            %>
            alertLabels.push("<%=s.getName()%>");
            alertData.push(<%=s.getPercentage()%>);
            <%
                }
            }
            %>

        </script>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>
        <script src="statistic/JS/Chart.js"></script>

    </body>
</html>