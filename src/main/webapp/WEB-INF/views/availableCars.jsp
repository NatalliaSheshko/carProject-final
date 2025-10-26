<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Автомобили в наличии</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f2f2f2; /* фон всей страницы — светло-серый */
            font-family: Arial, sans-serif; /* основной шрифт */
            color: #1a1a1a; /* цвет основного текста — почти чёрный */
        }

        /* === Логотип в верхней панели === */
        .logo {
            height: 50px; /* высота логотипа */
            margin-right: 15px; /* отступ справа от логотипа */
        }

        /* === Контактная информация рядом с логотипом === */
        .contact-info {
            font-size: 0.9rem; /* уменьшенный размер текста */
            line-height: 1.4; /* межстрочный интервал */
            color: #ffffff; /* белый текст на красном фоне */
        }

        /* === Заголовки секций (например, "Лучшие авто из Китая") === */
        .section-title {
            margin-top: 30px;
            margin-bottom: 20px;
            font-weight: bold;
            color: #000000; /* чёрный цвет заголовка */
        }

        /* === Карточки категорий и автомобилей === */
        .card {
            border-radius: 15px; /* скруглённые углы */
            border: none; /* без рамки */
            background-color: #ffffff; /* белый фон карточки */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05); /* лёгкая тень */
        }

        /* === Заголовок внутри карточки (например, название авто) === */
        .card-title {
            color: #000000; /* чёрный заголовок */
        }

        /* === Текст внутри карточки (год, цена и т.д.) === */
        .card-text {
            color: #000000; /* чёрный текст */
        }

        /* === Общие стили для всех кнопок === */
        .btn {
            border-radius: 25px; /* скруглённые кнопки */
            font-weight: bold; /* жирный текст */
        }

        /* === Красные кнопки (например, "Посмотреть") === */
        .btn-primary {
            background-color: #cc0000; /* красный фон */
            border-color: #cc0000; /* красная рамка */
            color: #ffffff; /* белый текст */
        }
        .btn-primary:hover {
            background-color: #333333; /* тёмно-серый при наведении */
            border-color: #333333;
        }

        /* === Чёрные кнопки (например, "Выйти") === */
        .btn-danger {
            background-color: #000000;
            border-color: #000000;
            color: #ffffff;
        }
        .btn-danger:hover {
            background-color: #333333;
            border-color: #333333;
        }

        /* === Серые кнопки (например, "Записаться на тест-драйв") === */
        .btn-warning {
            background-color: #999999;
            border-color: #999999;
            color: #fff;
        }
        .btn-warning:hover {
            background-color: #7a7a7a;
            border-color: #7a7a7a;
        }

        /* === Контурные белые кнопки (например, "Заказать обратный звонок") === */
        .btn-outline-light {
            border-color: #ffffff;
            color: #ffffff;
        }
        .btn-outline-light:hover {
            background-color: #ffffff;
            color: #4D4D4D;
        }

        /* === Верхняя панель навигации (navbar) === */
        .bg-dark {
            background-color: #4D4D4D; /* тёмно-серый фон */
        }

        /* === Нижний колонтитул (footer) === */
        footer {
            margin-top: 40px;
            padding: 20px 0;
            background-color: #1a1a1a; /* чёрный фон */
            text-align: center;
            font-size: 0.9rem;
            color: #cccccc; /* светло-серый текст */
        }

        /* === Модальное окно обратного звонка === */
        .modal-content {
            border-radius: 15px;
            background-color: #ffffff;
            color: #1a1a1a;
        }

        /* === Заголовок модального окна === */
        .modal-header {
            background-color: #2c2c2c;
            color: #ffffff;
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
        }

        /* === Нижняя часть модального окна (кнопки) === */
        .modal-footer {
            border-bottom-left-radius: 15px;
            border-bottom-right-radius: 15px;
        }

        /* === Поля ввода в форме обратного звонка === */
        .form-control {
            border-radius: 10px;
            border: 1px solid #cccccc;
        }

        /* === Подписи к чекбоксам в форме === */
        .form-check-label {
            font-size: 0.9rem;
            color: #333333;
        }

        .zoom-preview {
            overflow: hidden;
            position: relative;
        }

        .zoom-preview img {
            transition: transform 0.3s ease;
            width: 100%;
            height: auto;
            object-fit: cover;
        }

        .zoom-preview:hover img {
            transform: scale(1.1);
        }

        .card-img-top {
            height: 200px;
            object-fit: cover;
        }
        .carousel-inner {
            height: 200px;
        }
    </style>
</head>
<body class="container mt-4">
<h2>Автомобили в наличии</h2>
<!--форма фильтрации -->
<form method="get" action="/cars/available" class="card p-3 mb-4 shadow-sm">
    <div class="row g-3">
        <!-- Поиск по марке и модели -->
        <div class="col-md-3">
            <input type="text" name="brand" value="${selectedBrand}" class="form-control" placeholder="Марка" />
        </div>
        <div class="col-md-3">
            <input type="text" name="model" value="${selectedModel}" class="form-control" placeholder="Модель" />
        </div>

        <!-- Характеристики -->
        <div class="col-md-2">
            <select name="bodyType" class="form-select">
                <option value="">Кузов</option>
                <option value="Седан" ${selectedBodyType == 'Седан' ? 'selected' : ''}>Седан</option>
                <option value="Хэтчбек" ${selectedBodyType == 'Хэтчбек' ? 'selected' : ''}>Хэтчбек</option>
                <option value="Кроссовер" ${selectedBodyType == 'Кроссовер' ? 'selected' : ''}>Кроссовер</option>
            </select>
        </div>
        <div class="col-md-2">
            <select name="driveType" class="form-select">
                <option value="">Привод</option>
                <option value="Передний" ${selectedDriveType == 'Передний' ? 'selected' : ''}>Передний</option>
                <option value="Задний" ${selectedDriveType == 'Задний' ? 'selected' : ''}>Задний</option>
                <option value="Полный" ${selectedDriveType == 'Полный' ? 'selected' : ''}>Полный</option>
            </select>
        </div>
        <div class="col-md-2">
            <select name="engineType" class="form-select">
                <option value="">Двигатель</option>
                <option value="Бензин" ${selectedEngineType == 'Бензин' ? 'selected' : ''}>Бензин</option>
                <option value="Дизель" ${selectedEngineType == 'Дизель' ? 'selected' : ''}>Дизель</option>
                <option value="Гибрид" ${selectedEngineType == 'Гибрид' ? 'selected' : ''}>Гибрид</option>
                <option value="Электро" ${selectedEngineType == 'Электро' ? 'selected' : ''}>Электро</option>
            </select>
        </div>

        <!-- Цена -->
        <div class="col-md-2">
            <input type="number" name="minPrice" value="${minPrice}" class="form-control" placeholder="Мин. цена" />
        </div>
        <div class="col-md-2">
            <input type="number" name="maxPrice" value="${maxPrice}" class="form-control" placeholder="Макс. цена" />
        </div>

        <!-- Сортировка -->
        <div class="col-md-3">
            <select name="sort" class="form-select">
                <option value="price" ${selectedSort == 'price' ? 'selected' : ''}>Сортировать по цене</option>
                <option value="bodyType" ${selectedSort == 'bodyType' ? 'selected' : ''}>По кузову</option>
                <option value="driveType" ${selectedSort == 'driveType' ? 'selected' : ''}>По приводу</option>
                <option value="engineType" ${selectedSort == 'engineType' ? 'selected' : ''}>По двигателю</option>
            </select>
        </div>

        <!-- Кнопки -->
        <div class="col-md-3 d-grid">
            <button type="submit" class="btn btn-danger">Применить фильтр</button>
        </div>
        <div class="col-md-3 d-grid">
            <a href="/cars/available" class="btn btn-outline-secondary">Сбросить фильтры</a>
        </div>
    </div>
</form>
<!-- Сообщение при пустом списке -->
<c:if test="${empty cars}">
    <div class="alert alert-warning text-center">
        Ничего не найдено по заданным фильтрам.
        <a href="/cars/available" class="btn btn-outline-secondary btn-sm ms-3">Сбросить фильтры</a>
    </div>
</c:if>

<!-- Сетка карточек -->
<div class="row">
    <c:forEach var="car" items="${cars}">
        <div class="col-md-4 mb-4">
            <div class="card h-100 shadow-sm">
                <div class="zoom-preview">
                       <c:if test="${not empty car.photos}">
                            <div id="carousel-${car.id}" class="carousel slide mb-3" data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="photo" items="${car.photos}" varStatus="loop">
                                        <c:if test="${not empty photo.url}">
                                            <div class="carousel-item ${loop.first ? 'active' : ''}">
                                                <img src="${photo.url}" class="d-block w-100 rounded" alt="Фото ${car.brand} ${car.model}" />
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${car.id}" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carousel-${car.id}" data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                </button>
                            </div>
                        </c:if>
                    <c:if test="${empty car.photos}">
                        <img src="/images/no-photo.jpg" class="img-fluid rounded mb-3" alt="Нет фото" style="height: 200px; object-fit: cover;"/>
                    </c:if>

                </div>
                <div class="card-body">
                    <h5 class="card-title">${car.brand} ${car.model}</h5>
                    <p class="card-text">
                        Цвет: ${car.color}<br/>
                        Год: ${car.year}<br/>
                        Статус: ${car.status}
                    </p>
                    <a href="/cars/details/${car.id}" class="btn btn-primary">Подробнее</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- Выбор количества карточек -->
<div class="d-flex justify-content-end align-items-center mb-3">
    <label for="sizeSelect" class="me-2">Карточек на странице:</label>
    <form method="get" action="/cars/available" class="d-flex">
        <input type="hidden" name="brand" value="${selectedBrand}" />
        <input type="hidden" name="model" value="${selectedModel}" />
        <input type="hidden" name="sort" value="${selectedSort}" />
        <select name="size" id="sizeSelect" class="form-select form-select-sm me-2" onchange="this.form.submit()">
            <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
            <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
            <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
            <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
        </select>
    </form>
</div>
<!-- Пагинация -->
<c:if test="${carPage.totalPages > 0}">
    <nav>
        <ul class="pagination">
            <c:forEach var="i" begin="0" end="${carPage.totalPages - 1}">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link"
                       href="?page=${i}&size=${pageSize}&brand=${selectedBrand}&model=${selectedModel}&sort=${selectedSort}">
                            ${i + 1}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</c:if>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
