<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Заявки на сервис</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container mt-4">
<h2>Заявки на сервис</h2>

<form method="get" action="/admin/service/appointments" class="row g-2 mb-4">
    <div class="col-auto">
        <label for="from" class="form-label">С:</label>
        <input type="date" id="from" name="from" class="form-control" style="max-width: 180px;"
               value="${param.from}">
    </div>

    <div class="col-auto">
        <label for="to" class="form-label">По:</label>
        <input type="date" id="to" name="to" class="form-control" style="max-width: 180px;"
               value="${param.to}">
    </div>

    <div class="col-auto">
        <label for="sort" class="form-label">Сортировка:</label>
        <select name="sort" id="sort" class="form-select" style="max-width: 180px;">
            <option value="asc" ${param.sort == 'asc' ? 'selected' : ''}>По имени (А-Я)</option>
            <option value="desc" ${param.sort == 'desc' ? 'selected' : ''}>По имени (Я-А)</option>
        </select>
    </div>

    <div class="col-auto">
        <label for="consentFilter" class="form-label">Согласие:</label>
        <select name="consentFilter" id="consentFilter" class="form-select" style="max-width: 150px;">
            <option value="" ${empty param.consentFilter ? 'selected' : ''}>Все</option>
            <option value="true" ${param.consentFilter == 'true' ? 'selected' : ''}>Да</option>
            <option value="false" ${param.consentFilter == 'false' ? 'selected' : ''}>Нет</option>
        </select>
    </div>

    <div class="col-auto">
        <label for="processedFilter" class="form-label">Состояние:</label>
        <select name="processedFilter" id="processedFilter" class="form-select" style="max-width: 150px;">
            <option value="" ${empty param.processedFilter ? 'selected' : ''}>Все</option>
            <option value="true" ${param.processedFilter == 'true' ? 'selected' : ''}>Обработано</option>
            <option value="false" ${param.processedFilter == 'false' ? 'selected' : ''}>Не обработано</option>
        </select>
    </div>

    <div class="col-auto">
        <label for="serviceTypeFilter" class="form-label">Тип услуги:</label>
        <input type="text" id="serviceTypeFilter" name="serviceTypeFilter" class="form-control" style="max-width: 180px;"
               value="${param.serviceTypeFilter}">
    </div>

    <div class="col-auto d-flex align-items-end">
        <button type="submit" class="btn btn-primary me-2">Применить</button>
        <a href="/admin/service/appointments" class="btn btn-outline-secondary">Снять фильтрацию</a>
    </div>
</form>

<table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
        <th>Имя</th>
        <th>Телефон</th>
        <th>Дата</th>
        <th>Согласие</th>
        <th>Состояние</th>
        <th>Тип услуги</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${appointments}">
        <tr>
            <td>${a.fullName}</td>
            <td>${a.phone}</td>
            <td>${a.submittedAt}</td>
            <td><c:if test="${a.consent}">Да</c:if><c:if test="${!a.consent}">Нет</c:if></td>
            <td>
                <c:choose>
                    <c:when test="${a.processed}">
                        <span class="badge bg-success">Обработано</span>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-sm btn-outline-primary mark-processed-btn" data-id="${a.id}">
                            Отметить
                        </button>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${a.serviceType}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="d-flex justify-content-between mt-3 mb-5">
    <a href="http://localhost:8080/admin/" class="btn btn-dark">Админ-панель</a>
    <a href="/" class="btn btn-primary">На главную</a>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const buttons = document.querySelectorAll(".mark-processed-btn");

        buttons.forEach(btn => {
            btn.addEventListener("click", function () {
                const id = this.getAttribute("data-id");

                fetch("/admin/service/markProcessed", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "id=" + encodeURIComponent(id)
                })
                    .then(response => {
                        if (response.ok) {
                            this.outerHTML = '<span class="badge bg-success">Обработано</span>';
                        } else {
                            alert("Ошибка при обновлении");
                        }
                    })
                    .catch(() => {
                        alert("Ошибка сети");
                    });
            });
        });
    });
</script>

</body>
