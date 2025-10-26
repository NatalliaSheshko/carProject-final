<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Управление автомобилями</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }
        h2 {
            margin-top: 30px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        .table th {
            background-color: #343a40;
            color: white;
        }
        .table td {
            vertical-align: middle;
        }
        .btn-action {
            margin-right: 5px;
        }
        .status-badge {
            font-size: 0.9em;
            padding: 5px 10px;
            border-radius: 12px;
        }
        .status-ACTIVE {
            background-color: #198754;
            color: white;
        }
        .status-INACTIVE {
            background-color: #ffc107;
            color: black;
        }
        .status-ARCHIVED {
            background-color: #6c757d;
            color: white;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">🚗 Управление автомобилями</h2>

    <form method="get" action="/admin/cars" class="row g-2 mb-4">
        <div class="col-auto">
            <label for="brand" class="form-label">Марка:</label>
            <input type="text" id="brand" name="brand" class="form-control" value="${param.brand}" style="max-width: 180px;">
        </div>

        <div class="col-auto">
            <label for="model" class="form-label">Модель:</label>
            <input type="text" id="model" name="model" class="form-control" value="${param.model}" style="max-width: 180px;">
        </div>

        <div class="col-auto">
            <label for="year" class="form-label">Год:</label>
            <input type="number" id="year" name="year" class="form-control" value="${param.year}" style="max-width: 120px;">
        </div>

        <div class="col-auto">
            <label for="status" class="form-label">Статус:</label>
            <select name="status" id="status" class="form-select" style="max-width: 150px;">
                <option value="" ${empty param.status ? 'selected' : ''}>Все</option>
                <option value="ACTIVE" ${param.status == 'ACTIVE' ? 'selected' : ''}>Активен</option>
                <option value="INACTIVE" ${param.status == 'INACTIVE' ? 'selected' : ''}>Неактивен</option>
                <option value="ARCHIVED" ${param.status == 'ARCHIVED' ? 'selected' : ''}>Архив</option>
            </select>
        </div>

        <div class="col-auto">
            <label for="sort" class="form-label">Сортировка:</label>
            <select name="sort" id="sort" class="form-select" style="max-width: 180px;">
                <option value="id" ${param.sort == 'id' ? 'selected' : ''}>По ID</option>
                <option value="brand" ${param.sort == 'brand' ? 'selected' : ''}>По марке</option>
                <option value="model" ${param.sort == 'model' ? 'selected' : ''}>По модели</option>
                <option value="year" ${param.sort == 'year' ? 'selected' : ''}>По году</option>
                <option value="price" ${param.sort == 'price' ? 'selected' : ''}>По цене</option>
            </select>
        </div>

        <div class="col-auto d-flex align-items-end">
            <button type="submit" class="btn btn-primary me-2">Применить</button>
            <a href="/admin/cars" class="btn btn-outline-secondary">Снять фильтрацию</a>
        </div>
    </form>

    <div class="d-flex justify-content-between mb-3">
        <a href="/admin/cars/add" class="btn btn-success">➕ Добавить автомобиль</a>

    </div>

    <table class="table table-bordered table-hover shadow-sm">
        <thead>
        <tr>
            <th>ID</th>
            <th>Марка</th>
            <th>Модель</th>
            <th>Год</th>
            <th>Цена</th>
            <th>Статус</th>
            <th class="text-center">Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="car" items="${cars}">
            <c:if test="${car.status != 'ARCHIVED'}">
                <tr>
                    <td>${car.id}</td>
                    <td>${car.brand}</td>
                    <td>${car.model}</td>
                    <td>${car.year}</td>
                    <td>${car.price}</td>
                    <td><span class="status-badge status-${car.status}">${car.status}</span></td>
                    <td class="text-center">
                        <a href="/admin/cars/edit/${car.id}" class="btn btn-sm btn-outline-primary btn-action">✏️ Редактировать</a>
                        <a href="/admin/cars/delete/${car.id}" class="btn btn-sm btn-outline-danger btn-action"
                           onclick="return confirm('Удалить автомобиль?')">🗑️ Удалить</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <div class="d-flex justify-content-between mt-3 mb-5">
        <a href="/admin/" class="btn btn-dark me-2">Админ-панель</a>
        <a href="/" class="btn btn-primary">На главную</a>
    </div>

    <c:if test="${not empty success}">
        <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
            <div id="toastSuccess" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        <c:choose>
                            <c:when test="${success == 'added'}">✅ Автомобиль успешно добавлен.</c:when>
                            <c:when test="${success == 'updated'}">✏️ Автомобиль успешно обновлён.</c:when>
                        </c:choose>
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Закрыть"></button>
                </div>
            </div>
        </div>
        <script>
            window.addEventListener('DOMContentLoaded', () => {
                const toastEl = document.getElementById('toastSuccess');
                const toast = new bootstrap.Toast(toastEl, { delay: 4000 });
                toast.show();
            });
        </script>
    </c:if>

    <c:if test="${not empty error}">
        <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
            <div id="toastError" class="toast align-items-center text-white bg-danger border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        ❌ ${error}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Закрыть"></button>
                </div>
            </div>
        </div>
        <script>
            window.addEventListener('DOMContentLoaded', () => {
                const toastEl = document.getElementById('toastError');
                const toast = new bootstrap.Toast(toastEl, { delay: 5000 });
                toast.show();
            });
        </script>
    </c:if>
</div>
</body>
</html>