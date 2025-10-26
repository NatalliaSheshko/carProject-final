<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>👤 Управление клиентами</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body { background-color: #f8f9fa; }
        h2 { margin-top: 30px; margin-bottom: 20px; font-weight: bold; }
        .table th { background-color: #343a40; color: white; }
        .table td { vertical-align: middle; }
        .btn-action { margin-right: 5px; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Автосалон</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="/admin">Админ-панель</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout">Выход</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="text-center">👤 Управление клиентами</h2>

    <!-- Форма фильтрации -->
    <form method="get" action="/admin/customers" class="row g-2 mb-4">
        <div class="col-auto">
            <label for="nameFilter" class="form-label">Имя:</label>
            <input type="text" id="nameFilter" name="nameFilter" class="form-control" value="${param.nameFilter}" style="max-width: 200px;">
        </div>
        <div class="col-auto">
            <label for="emailFilter" class="form-label">Email:</label>
            <input type="text" id="emailFilter" name="emailFilter" class="form-control" value="${param.emailFilter}" style="max-width: 200px;">
        </div>
        <div class="col-auto d-flex align-items-end">
            <button type="submit" class="btn btn-primary me-2">Применить</button>
            <a href="/admin/customers" class="btn btn-outline-secondary">Снять фильтрацию</a>
        </div>
    </form>

    <!-- Таблица клиентов -->
    <table class="table table-bordered table-hover shadow-sm">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Адрес</th>
            <th class="text-center">Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.username}</td>
                <td>${customer.email}</td>
                <td>${customer.address}</td>
                <td class="text-center">
                    <form action="/admin/customers/delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${customer.id}">
                        <button type="submit" class="btn btn-sm btn-outline-danger btn-action" onclick="return confirm('Удалить клиента?')">🗑️ Удалить</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="d-flex justify-content-between mt-3 mb-5">
        <a href="/admin/" class="btn btn-dark">Админ-панель</a>
        <a href="/" class="btn btn-primary">На главную</a>
    </div>

    <!-- Уведомления -->
    <c:if test="${not empty success}">
        <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
            <div id="toastSuccess" class="toast align-items-center text-white bg-success border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">✅ ${success}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>
        <script>
            window.addEventListener('DOMContentLoaded', () => {
                new bootstrap.Toast(document.getElementById('toastSuccess')).show();
            });
        </script>
    </c:if>

    <c:if test="${not empty error}">
        <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
            <div id="toastError" class="toast align-items-center text-white bg-danger border-0" role="alert">
                <div class="d-flex">
                    <div class="toast-body">❌ ${error}</div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </div>
        <script>
            window.addEventListener('DOMContentLoaded', () => {
                new bootstrap.Toast(document.getElementById('toastError')).show();
            });
        </script>
    </c:if>
</div>
</body>
</html>