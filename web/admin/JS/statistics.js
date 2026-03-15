document.addEventListener("DOMContentLoaded", function () {
    // 1. Nhận chuỗi JSON từ biến toàn cục (từ file JSP truyền qua)
    const rawEventStats = typeof CHART_DATA !== 'undefined' ? CHART_DATA.rawEventStats : "[]";
    const rawLightStats = typeof CHART_DATA !== 'undefined' ? CHART_DATA.rawLightStats : "[]";
    const rawAlertStats = typeof CHART_DATA !== 'undefined' ? CHART_DATA.rawAlertStats : "[]";
    
    // 2. Parse thành Mảng Đối tượng Javascript
    const eventStats = JSON.parse(rawEventStats);
    const lightStats = JSON.parse(rawLightStats);
    const alertStats = JSON.parse(rawAlertStats);

    // Hàm hỗ trợ tách mảng Data và Label
    const getLabels = (arr) => arr.map(item => item.label);
    const getValues = (arr) => arr.map(item => item.value);

    // --- BIỂU ĐỒ 1: EVENT DISTRIBUTION (Doughnut Chart) ---
    if (document.getElementById('eventChart') && eventStats.length > 0) {
        // Xóa biểu đồ cũ nếu có để không bị lỗi đè hình khi chuyển tab
        let chart1 = Chart.getChart('eventChart');
        if (chart1 != undefined) { chart1.destroy(); }

        new Chart(document.getElementById('eventChart').getContext('2d'), {
            type: 'doughnut',
            data: {
                labels: getLabels(eventStats),
                datasets: [{
                    data: getValues(eventStats),
                    backgroundColor: ['#0d6efd', '#6c757d', '#198754', '#ffc107', '#dc3545', '#0dcaf0']
                }]
            },
            options: { 
                animation: { duration: 300 }, // Chống delay
                plugins: { title: { display: true, text: 'Events Distribution (%)' } } 
            }
        });
    }

    // --- BIỂU ĐỒ 2: LIGHT STATUS (Pie Chart) ---
    if (document.getElementById('lightChart') && lightStats.length > 0) {
        let chart2 = Chart.getChart('lightChart');
        if (chart2 != undefined) { chart2.destroy(); }

        new Chart(document.getElementById('lightChart').getContext('2d'), {
            type: 'pie',
            data: {
                labels: getLabels(lightStats),
                datasets: [{
                    data: getValues(lightStats),
                    backgroundColor: ['#ffc107', '#212529'] // Vàng (On), Đen (Off)
                }]
            },
            options: { 
                animation: { duration: 300 }, // Chống delay
                plugins: { title: { display: true, text: 'Light Status Ratio (%)' } } 
            }
        });
    }

    // --- BIỂU ĐỒ 3: ALERT SEVERITY (Bar Chart) ---
    if (document.getElementById('alertChart') && alertStats.length > 0) {
        let chart3 = Chart.getChart('alertChart');
        if (chart3 != undefined) { chart3.destroy(); }

        new Chart(document.getElementById('alertChart').getContext('2d'), {
            type: 'bar',
            data: {
                labels: getLabels(alertStats),
                datasets: [{
                    label: 'Percentage (%)',
                    data: getValues(alertStats),
                    backgroundColor: ['#dc3545', '#ffc107', '#0dcaf0'] // Đỏ, Vàng, Xanh nhạt
                }]
            },
            options: { 
                animation: { duration: 1000 }, 
                plugins: { title: { display: true, text: 'Alert Severity (%)' } },
                scales: { y: { beginAtZero: true, max: 100 } }
            }
        });
    }
});