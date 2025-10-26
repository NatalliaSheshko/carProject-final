<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Список автомобилей</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>Автомобили</h2>

<form action="/cars/model" method="get" class="mb-3">
    <div class="input-group">
        <input type="text" name="model" class="form-control" placeholder="Поиск по модели">
        <button type="submit" class="btn btn-primary">Найти</button>
    </div>
</form>

<form action="/cars/search" method="get" class="row g-3 mb-4">
    <div class="col-md-2">
        <input type="text" name="model" class="form-control" placeholder="Модель"/>
    </div>
    <div class="col-md-2">
        <select name="status" class="form-select">
            <option value="">-- Статус --</option>
            <option value="AVAILABLE">Доступен</option>
            <option value="SOLD">Продан</option>
            <option value="RESERVED">Зарезервирован</option>
        </select>
    </div>
    <div class="col-md-2">
        <input type="number" step="0.01" name="minPrice" class="form-control" placeholder="Мин. цена"/>
    </div>
    <div class="col-md-2">
        <input type="number" step="0.01" name="maxPrice" class="form-control" placeholder="Макс. цена"/>
    </div>
    <div class="col-md-2">
        <select name="sort" class="form-select">
            <option value="">-- Сортировка --</option>
            <option value="priceAsc">Цена ↑</option>
            <option value="priceDesc">Цена ↓</option>
            <option value="yearAsc">Год ↑</option>
            <option value="yearDesc">Год ↓</option>
            <option value="modelAsc">Модель A–Z</option>
            <option value="modelDesc">Модель Z–A</option>
        </select>
    </div>
    <div class="col-md-2">
        <button type="submit" class="btn btn-primary w-100">Поиск</button>
    </div>
</form>

<a href="/cars/add" class="btn btn-success mb-3">Добавить автомобиль</a>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Марка</th>
        <th>Модель</th>
        <th>Год</th>
        <th>Цена</th>
        <th>Статус</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>${car.id}</td>
            <td>${car.brand}</td>
            <td>${car.model}</td>
            <td>${car.year}</td>
            <td>${car.price}</td>
            <td>${car.status}</td>
            <td>
                <a href="/cars/edit/${car.id}" class="btn btn-sm btn-warning">Редактировать</a>
                <a href="/cars/delete/${car.id}" class="btn btn-sm btn-danger"
                   onclick="return confirm('Удалить автомобиль?')">Удалить</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
