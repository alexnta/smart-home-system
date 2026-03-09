<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quản lý người dùng - Smart Home</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .error { color: red; font-weight: bold; }
        .form-container { border: 1px solid #ccc; padding: 15px; margin-bottom: 20px; width: 50%; }
        .status-active { color: green; font-weight: bold; }
        .status-inactive { color: gray; font-style: italic; }
    </style>
</head>
<body>
    <h2>Quản lý Người Dùng</h2>
    
    <c:if test="${not empty requestScope.ERROR_MSG}">
        <p class="error">${requestScope.ERROR_MSG}</p>
    </c:if>

    <div class="form-container">
        <h3>Thêm người dùng mới</h3>
        <form action="MainController" method="POST">
            <input type="hidden" name="action" value="CreateUser">
            Username: <input type="text" name="username" required> <br><br>
            Password: <input type="password" name="password" required> <br><br>
            Họ Tên: <input type="text" name="fullName" required> <br><br>
            Email: <input type="email" name="email"> <br><br>
            Vai trò: 
            <select name="roleId">
                <option value="1">Admin</option>
                <option value="2">Home Owner</option>
                <option value="3">Technician</option>
                <option value="4">Viewer</option>
            </select> <br><br>
            Hoạt động: <input type="checkbox" name="status" checked value="ON"> <br><br>
            <input type="submit" value="Thêm mới">
        </form>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Họ Tên</th>
                <th>Email</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${not empty requestScope.USER_LIST}">
                <c:forEach var="user" items="${requestScope.USER_LIST}">
                    <tr>
                        <form action="MainController" method="POST">
                            <input type="hidden" name="action" value="UpdateUser">
                            <input type="hidden" name="userId" value="${user.userId}">
                            
                            <td>${user.userId}</td>
                            <td>${user.username}</td>
                            <td><input type="text" name="fullName" value="${user.fullName}"></td>
                            <td><input type="email" name="email" value="${user.email}"></td>
                            <td>
                                <select name="roleId">
                                    <option value="1" ${user.roleName == 'Admin' ? 'selected' : ''}>Admin</option>
                                    <option value="2" ${user.roleName == 'Home Owner' ? 'selected' : ''}>Home Owner</option>
                                    <option value="3" ${user.roleName == 'Technician' ? 'selected' : ''}>Technician</option>
                                    <option value="4" ${user.roleName == 'Viewer' ? 'selected' : ''}>Viewer</option>
                                </select>
                            </td>
                            <td>
                                <input type="checkbox" name="status" value="ON" ${user.status ? 'checked' : ''}>
                                <span class="${user.status ? 'status-active' : 'status-inactive'}">
                                    ${user.status ? 'Active' : 'Inactive'}
                                </span>
                            </td>
                            <td>
                                <input type="submit" value="Cập nhật">
                                <a href="MainController?action=DeleteUser&userId=${user.userId}" 
                                   onclick="return confirm('Bạn có chắc chắn muốn xóa user này?');">
                                    Xóa
                                </a>
                            </td>
                        </form>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty requestScope.USER_LIST}">
                <tr><td colspan="7">Không có dữ liệu người dùng.</td></tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>