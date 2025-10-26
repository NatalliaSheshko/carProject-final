<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>🛠️ Админ-панель</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body { background-color: #f8f9fa; }
        h2 { margin-top: 30px; margin-bottom: 20px; font-weight: bold; }
        .card-title { font-weight: bold; }
        .card-text { font-size: 0.95rem; }
        .card { transition: transform 0.2s ease; }
        .card:hover { transform: scale(1.02); }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="/admin/">
            <img src="/images/logo.png" alt="Логотип" height="40" class="d-inline-block align-top">
        </a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="/">Главная</a></li>
                <li class="nav-item"><a class="nav-link" href="/">Выход</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="text-center">🛠️ Добро пожаловать в админ-панель</h2>
    <p class="text-center mb-4">Вы можете управлять категориями, автомобилями, пользователями и заявками.</p>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="entry" items="${cards}">
            <div class="col">
                <div class="card h-100 shadow-sm bg-white">
                    <div class="card-body text-center">
                        <h5 class="card-title">${entry.key}</h5>
                        <hr>
                        <p class="card-text">Управление разделом: ${entry.key}.</p>
                        <a href="${entry.value}" class="btn btn-primary">Открыть</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>