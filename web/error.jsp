<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SmartHome - System Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; height: 100vh; display: flex; align-items: center; }
        .error-card { border: none; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
        .error-code { font-size: 6rem; font-weight: 800; color: #dc3545; }
    </style>
</head>
<body>

<div class="container text-center">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card error-card p-5">
                <div class="card-body">
                    <div class="mb-4">
                        <i class="bi bi-exclamation-octagon-fill text-danger" style="font-size: 4rem;"></i>
                    </div>
                    
                    <h1 class="error-code mb-0">
                        <%= (request.getAttribute("javax.servlet.error.status_code") != null) ? 
                             request.getAttribute("javax.servlet.error.status_code") : "Oops!" %>
                    </h1>
                    
                    <h2 class="mb-4 text-secondary">Hệ thống đã xảy ra sự cố</h2>
                    
                    <p class="text-muted mb-4">
                        Có vẻ như đã có lỗi xảy ra trong quá trình xử lý yêu cầu của bạn. 
                        Đừng lo lắng, đội ngũ kỹ thuật đã được thông báo.
                    </p>

                    <% 
                        String errorMsg = (String) request.getAttribute("ERROR_MSG");
                        if (errorMsg != null) { 
                    %>
                        <div class="alert alert-light border text-start mb-4">
                            <small class="text-danger fw-bold">Detail:</small><br>
                            <small class="text-muted"><%= errorMsg %></small>
                        </div>
                    <% } %>

                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <a href="MainController?action=Login" class="btn btn-primary btn-lg px-4 gap-3">
                            <i class="bi bi-house-door-fill"></i> Quay lại Trang chủ
                        </a>
                        <button onclick="history.back()" class="btn btn-outline-secondary btn-lg px-4">
                            <i class="bi bi-arrow-left"></i> Quay lại trang trước
                        </button>
                    </div>
                </div>
            </div>
            <p class="mt-5 text-muted">&copy; 2026 SmartHome System - FPT University</p>
        </div>
    </div>
</div>

</body>
</html>