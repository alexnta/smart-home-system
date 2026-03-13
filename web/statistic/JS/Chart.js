/* ================= EVENT DISTRIBUTION ================= */

new Chart(document.getElementById("eventChart"), {

    type: "pie",

    data: {
        labels: eventLabels,
        datasets: [{
                data: eventData,
                backgroundColor: [
                    "#3498db",
                    "#ff6384",
                    "#ff9f40",
                    "#f1c40f",
                    "#2ecc71"
                ]
            }]
    },

    plugins: [ChartDataLabels],

    options: {
        responsive: true,
        maintainAspectRatio: false,

        plugins: {
            legend: {
                position: "bottom"
            },

            datalabels: {
                color: "#ffffff",
                font: {
                    weight: "bold",
                    size: 14
                },
                formatter: (value) => value > 0 ? value.toFixed(1) + "%" : ""
            }
        }
    }

});


/* ================= LIGHT STATUS ================= */

new Chart(document.getElementById("lightChart"), {

    type: "doughnut",

    data: {
        labels: ["LightOn", "LightOff"],

        datasets: [{
                data: [
                    lightData[0] || 0,
                    lightData[1] || 0
                ],

                backgroundColor: [
                    "#3498db",
                    "#ff6384"
                ]
            }]
    },

    plugins: [ChartDataLabels],

    options: {
        responsive: true,
        maintainAspectRatio: false,

        plugins: {
            legend: {
                position: "bottom"
            },

            datalabels: {
                color: "#ffffff",
                font: {
                    weight: "bold",
                    size: 14
                },
                formatter: (value) => value > 0 ? value.toFixed(1) + "%" : ""
            }
        }
    }

});


/* ================= ALERT SEVERITY ================= */

new Chart(document.getElementById("alertChart"), {

    type: "bar",

    data: {
        labels: ["Warning", "Critical"],

        datasets: [{
                label: "Alert Severity (%)",

                data: [
                    alertData[0] || 0,
                    alertData[1] || 0
                ],

                backgroundColor: [
                    "#f39c12",
                    "#e74c3c"
                ]
            }]
    },

    options: {

        responsive: true,
        maintainAspectRatio: false,

        plugins: {
            legend: {
                display: false
            }
        },

        scales: {
            y: {
                beginAtZero: true,
                max: 100,

                ticks: {
                    callback: function (value) {
                        return value + "%";
                    }
                }
            }
        }
    }

});