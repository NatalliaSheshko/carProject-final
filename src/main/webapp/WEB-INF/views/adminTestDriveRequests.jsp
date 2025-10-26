<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Заявки на тест-драйв</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>📋 Заявки на тест-драйв</h2>

    <form method="get" action="/admin/testdrive/requests" class="row g-2 mb-4">
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
            <a href="/admin/testdrive/requests" class="btn btn-outline-secondary">Снять фильтрацию</a>
        </div>
    </form>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Имя</th>
            <th>Телефон</th>
            <th>Email</th>
            <th>Автомобиль</th>
            <th>Дата заявки</th>
            <th>Статус</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="req" items="${requests}">
            <tr>
                <td>${req.fullName}</td>
                <td>${req.phone}</td>
                <td>${req.email}</td>
                <td>${req.car.brand} ${req.car.model}</td>
                <td>${req.requestedAt}</td>
                <td>
    <span class="badge ${req.status == 'CONFIRMED' ? 'bg-success' : req.status == 'CANCELLED' ? 'bg-danger' : 'bg-secondary'}">
            ${req.status}
    </span>

                    <c:if test="${req.status == 'PENDING'}">
                        <button type="button" class="btn btn-sm btn-outline-success ms-2 update-status-btn"
                                data-id="${req.id}" data-action="CONFIRMED">Подтвердить</button>

                        <button type="button" class="btn btn-sm btn-outline-danger ms-1 update-status-btn"
                                data-id="${req.id}" data-action="CANCELLED">Отклонить</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="d-flex justify-content-between mt-3 mb-5">
        <a href="/admin/" class="btn btn-dark">Админ-панель</a>
        <a href="/" class="btn btn-primary">На главную</a>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const buttons = document.querySelectorAll(".update-status-btn");

        buttons.forEach(btn => {
            btn.addEventListener("click", function () {
                const id = this.getAttribute("data-id");
                const action = this.getAttribute("data-action");

                fetch("/admin/testdrive/update", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: "requestId=" + encodeURIComponent(id) + "&action=" + encodeURIComponent(action)
                })
                    .then(response => {
                        if (response.ok) {
                            this.closest("td").innerHTML =
                                '<span class="badge ' + (action === 'CONFIRMED' ? 'bg-success' : 'bg-danger') + '">' + action + '</span>';
                        } else {
                            alert("Ошибка при обновлении статуса");
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