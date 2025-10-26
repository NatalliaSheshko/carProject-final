<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Заявки на обратный звонок</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container mt-4">
    <h2>📞 Заявки на обратный звонок</h2>

    <form method="get" action="/admin/callback" class="row g-2 mb-4">
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
            <label for="statusFilter" class="form-label">Статус:</label>
            <select name="statusFilter" id="statusFilter" class="form-select" style="max-width: 180px;">
                <option value="" ${empty param.statusFilter ? 'selected' : ''}>Все</option>
                <option value="PENDING" ${param.statusFilter == 'PENDING' ? 'selected' : ''}>Ожидает</option>
                <option value="CONFIRMED" ${param.statusFilter == 'CONFIRMED' ? 'selected' : ''}>Подтверждено</option>
                <option value="CANCELLED" ${param.statusFilter == 'CANCELLED' ? 'selected' : ''}>Отклонено</option>
            </select>
        </div>

        <div class="col-auto">
            <label for="marketingFilter" class="form-label">Согласие:</label>
            <select name="marketingFilter" id="marketingFilter" class="form-select" style="max-width: 150px;">
                <option value="" ${empty param.marketingFilter ? 'selected' : ''}>Все</option>
                <option value="true" ${param.marketingFilter == 'true' ? 'selected' : ''}>Да</option>
                <option value="false" ${param.marketingFilter == 'false' ? 'selected' : ''}>Нет</option>
            </select>
        </div>

        <div class="col-auto d-flex align-items-end">
            <button type="submit" class="btn btn-primary me-2">Применить</button>
            <a href="/admin/callback" class="btn btn-outline-secondary">Снять фильтрацию</a>
        </div>
    </form>

    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>Имя</th>
            <th>Телефон</th>
            <th>Комментарий</th>
            <th>Дата</th>
            <th>Согласие</th>
            <th>Статус</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="req" items="${requests}">
            <tr>
                <td>${req.name}</td>
                <td>${req.phone}</td>
                <td>${req.comment}</td>
                <td>${req.submittedAt}</td>
                <td>
                    <c:choose>
                        <c:when test="${req.consentPersonal}">Да</c:when>
                        <c:otherwise>Нет</c:otherwise>
                    </c:choose>
                </td>
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

                fetch("/admin/callback/update", {
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