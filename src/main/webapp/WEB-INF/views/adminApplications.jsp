<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>📄 Заявки на финансирование</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body { background-color: #f8f9fa; }
        h2 { margin-top: 30px; margin-bottom: 20px; font-weight: bold; }
        .card { background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.05); }
        .table th { background-color: #343a40; color: white; }
        .table td { vertical-align: middle; }
        .form-inline .form-control { margin-right: 10px; }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center">📄 Заявки на финансирование</h2>

    <!-- Форма фильтрации -->
    <div class="card mb-4">
        <form method="get" action="/admin/financing/applications" class="row g-3">
            <div class="col-md-3">
                <label for="type" class="form-label">Тип финансирования:</label>
                <select name="type" id="type" class="form-select">
                    <option value="">Все</option>
                    <option value="Кредит" ${param.type == 'Кредит' ? 'selected' : ''}>Кредит</option>
                    <option value="Лизинг" ${param.type == 'Лизинг' ? 'selected' : ''}>Лизинг</option>
                </select>
            </div>
            <div class="col-md-3">
                <label for="from" class="form-label">С даты:</label>
                <input type="date" name="from" id="from" class="form-control" value="${param.from}">
            </div>
            <div class="col-md-3">
                <label for="to" class="form-label">По дату:</label>
                <input type="date" name="to" id="to" class="form-control" value="${param.to}">
            </div>
            <div class="col-md-3 d-flex align-items-end">
                <button type="submit" class="btn btn-primary me-2">Фильтровать</button>
                <a href="/admin/financing/applications" class="btn btn-outline-secondary">Снять фильтрации</a>
            </div>
        </form>
    </div>

    <!-- Таблица заявок -->
    <div class="table-responsive">
        <table class="table table-bordered table-hover shadow-sm">
            <thead>
            <tr>
                <th>ID</th>
                <th>ФИО</th>
                <th>Телефон</th>
                <th>Email</th>
                <th>Сумма</th>
                <th>Срок (мес)</th>
                <th>Тип</th>
                <th>Дата подачи</th>
                <th>Статус</th>
                <th>Обновить</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="app" items="${applications}">
                <tr>
                    <td>${app.id}</td>
                    <td>${app.fullName}</td>
                    <td>${app.phone}</td>
                    <td>${app.email}</td>
                    <td>${app.amount}</td>
                    <td>${app.termMonths}</td>
                    <td>${app.financingType}</td>
                    <td>${app.submittedAt}</td>
                    <td>${app.status}</td>
                    <td>
                        <span class="badge
                            ${app.status == 'APPROVED' ? 'bg-success' :
                              app.status == 'IN_PROGRESS' ? 'bg-warning text-dark' :
                              app.status == 'NEW' ? 'bg-secondary' : 'bg-light'}">
                                ${app.status}
                        </span>

                        <c:choose>
                            <c:when test="${app.status == 'NEW'}">
                                <form method="post" action="/admin/financing/updateStatus" class="d-inline ms-2">
                                    <input type="hidden" name="id" value="${app.id}" />
                                    <input type="hidden" name="status" value="IN_PROGRESS" />
                                    <button type="submit" class="btn btn-sm btn-outline-warning">В работу</button>
                                </form>

                                <form method="post" action="/admin/financing/updateStatus" class="d-inline ms-1">
                                    <input type="hidden" name="id" value="${app.id}" />
                                    <input type="hidden" name="status" value="APPROVED" />
                                    <button type="submit" class="btn btn-sm btn-outline-success">Одобрить</button>
                                </form>
                            </c:when>

                            <c:when test="${app.status == 'IN_PROGRESS'}">
                                <form method="post" action="/admin/financing/updateStatus" class="d-inline ms-1">
                                    <input type="hidden" name="id" value="${app.id}" />
                                    <input type="hidden" name="status" value="APPROVED" />
                                    <button type="submit" class="btn btn-sm btn-outline-success">Одобрить</button>
                                </form>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="d-flex justify-content-between mt-3 mb-5">
        <a href="/admin/" class="btn btn-dark">Админ-панель</a>
        <a href="/" class="btn btn-primary">На главную</a>
    </div>
</div>
</body>
</html>