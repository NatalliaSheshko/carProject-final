<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Заявки на бронирование</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container mt-4">
<h2>Заявки на бронирование</h2>

<form method="get" action="/admin/bookings" class="row g-2 mb-4">
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
        <label for="carModelFilter" class="form-label">Модель авто:</label>
        <input type="text" id="carModelFilter" name="carModelFilter" class="form-control" style="max-width: 180px;"
               value="${param.carModelFilter}">
    </div>

    <div class="col-auto d-flex align-items-end">
        <div class="form-check me-3">
            <input type="checkbox" class="form-check-input" id="unprocessedOnly" name="unprocessedOnly" value="true"
            ${param.unprocessedOnly == 'true' ? 'checked' : ''} />
            <label class="form-check-label" for="unprocessedOnly">Только необработанные</label>
        </div>
        <button type="submit" class="btn btn-primary me-2">Применить</button>
        <a href="/admin/bookings" class="btn btn-outline-secondary">Снять фильтрацию</a>
    </div>
</form>

<table class="table table-bordered table-striped">
    <thead class="table-dark">
    <tr>
        <th>Имя</th>
        <th>Телефон</th>
        <th>Комментарий</th>
        <th>Дата</th>
        <th>Машина</th>
        <th>Статус</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="r" items="${bookingRequests}">
        <tr>
            <td>${r.name}</td>
            <td>${r.phone}</td>
            <td>${r.comment}</td>
            <td>${r.createdAt}</td>
            <td>${r.car.brand} ${r.car.model}</td>
            <td>
                <c:choose>
                    <c:when test="${r.processed}">
                        <span class="badge bg-success">Обработано</span>
                    </c:when>
                    <c:otherwise>
                        <button type="button" class="btn btn-sm btn-outline-primary mark-processed-btn" data-id="${r.id}">
                            Отметить
                        </button>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="d-flex justify-content-between mt-3 mb-5">
    <a href="/admin/" class="btn btn-dark">Админ-панель</a>
    <a href="/" class="btn btn-primary">На главную</a>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const buttons = document.querySelectorAll(".mark-processed-btn");

        buttons.forEach(btn => {
            btn.addEventListener("click", function () {
                const id = this.getAttribute("data-id");

                fetch("/admin/bookings/markProcessed", {
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
</html>