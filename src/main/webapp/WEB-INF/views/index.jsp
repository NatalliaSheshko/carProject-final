<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>DRAGONDRIVE</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f2f2f2; /* фон всей страницы — светло-серый */
            font-family: Arial, sans-serif; /* основной шрифт */
            color: #1a1a1a; /* цвет основного текста — почти чёрный */
            position: static !important;
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

        .modal {
            z-index: 1055 !important;
        }
        .modal-backdrop {
            z-index: 1050 !important;
        }

        .carousel-inner {
            height: 200px; /* фиксированная высота для всех фото */
        }

        .carousel-inner .carousel-item img {
            height: 100%;
            object-fit: cover;
        }
</style>
</head>
<body>

<!-- Верхняя панель -->
<div class="container-fluid bg-dark text-white py-2">
    <div class="row align-items-center">
        <div class="col-12 col-md-6 d-flex align-items-center mb-2 mb-md-0">
            <img src="/images/logo.png" alt="DRAGONDRIVE" class="logo">
            <div class="contact-info">
                <div>Автоцентр DRAGONDRIVE</div>
                <div>Адрес: г. Минск</div>
                <div>Телефон: <strong>8 (029) 111-22-33</strong></div>
            </div>
        </div>
        <div class="col-12 col-md-6 d-flex justify-content-md-end justify-content-start gap-2">
            <button type="button" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#callbackModal">
                Заказать обратный звонок
            </button>
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal == null}">
                    <a href="/login" class="btn btn-primary mb-2 mb-md-0">Войти</a>
                </c:when>
                <c:otherwise>
                    <form action="<c:url value='/logout' />" method="post" style="margin: 0;">
                        <button type="submit" class="btn btn-danger mb-2 mb-md-0">Выйти</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<main class="container">
    <h1 class="section-title text-center">Лучшие авто из Китая</h1>

    <!-- Категории -->
    <div class="row">
        <c:forEach var="category" items="${categories}">
            <div class="col-12 col-sm-6 col-md-4 col-lg-3 mb-3">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center">
                        <h5 class="card-title">${category.name}</h5>
                        <c:choose>
                            <c:when test="${category.id == 32}">
                                <a href="/cars/available" class="btn btn-primary">Посмотреть</a>
                            </c:when>
                            <c:when test="${category.name == 'Финансирование'}">
                                <a href="/financing" class="btn btn-primary">Перейти</a>
                            </c:when>
                            <c:when test="${category.name == 'Запись на тест-драйв'}">
                                <a href="/testdrive/form" class="btn btn-primary">Перейти</a>
                            </c:when>
                            <c:when test="${category.name == 'Запись на сервис'}">
                                <a href="/service" class="btn btn-primary">Перейти</a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Автомобили -->
    <h3 class="section-title text-center">Автомобили</h3>
    <div class="row">
        <c:forEach var="car" items="${cars}">
            <div class="col-12 col-sm-6 col-md-4 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${car.brand} ${car.model}</h5>
                        <!-- Фото-слайдер -->
                        <c:if test="${not empty car.photos}">
                            <div id="carousel-${car.id}" class="carousel slide mb-3" data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="photo" items="${car.photos}" varStatus="loop">
                                        <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                                            <img src="${photo.url}" class="d-block w-100 rounded" alt="Фото ${car.brand} ${car.model}" />
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${car.id}" data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon"></span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carousel-${car.id}" data-bs-slide="next">
                                    <span class="carousel-control-next-icon"></span>
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${empty car.photos}">
                            <img src="/images/no-photo.jpg" class="img-fluid rounded mb-3" alt="Нет фото" style="height: 200px; object-fit: cover;"/>
                        </c:if>
                        <p class="card-text mb-1">${car.year}</p>

                        <p>Цена: ${car.price} BYN</p>

                        <p class="card-text text-uppercase fw-semibold mb-3">
                                ${car.status}
                        </p>

                        <a href="/cars/details/${car.id}" class="btn btn-primary w-100">Подробнее</a>
                        <a href="/testdrive/request?carId=${car.id}" class="btn btn-warning w-100 mt-2">Записаться на тест-драйв</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</main>

<footer>
    &copy; 2025 DRAGONDRIVE. Все права защищены.
</footer>

<div class="modal fade" id="callbackModal" tabindex="-1" aria-labelledby="callbackModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="border-radius: 15px; background-color: #ffffff;">
            <div class="modal-header" style="background-color: #2c2c2c; color: #ffffff; border-top-left-radius: 15px; border-top-right-radius: 15px;">
                <h5 class="modal-title" id="callbackModalLabel">Перезвоните мне</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <form id="callbackForm">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name">Имя</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Телефон</label>
                        <input type="tel" class="form-control" id="phone" name="phone" required>
                    </div>
                    <div class="form-group">
                        <label for="comment">Комментарий</label>
                        <textarea class="form-control" id="comment" name="comment" rows="3"></textarea>
                    </div>
                    <hr>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="consentPersonal" name="consentPersonal" required>
                        <label class="form-check-label" for="consentPersonal">
                            Отправляя данную форму, я даю согласие на обработку своих персональных данных.
                        </label>
                    </div>
                    </div>
                <div class="modal-footer" style="border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">
                    <button type="submit" class="btn btn-primary">Отправить</button>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${param.callbackSuccess == 'true'}">
    <div class="modal fade show" id="callbackSuccessModal" tabindex="-1" role="dialog" style="display:block; background-color: rgba(0,0,0,0.5);">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">✅ Заявка отправлена</h5>
                </div>
                <div class="modal-body">
                    <p>Ваша заявка на обратный звонок успешно отправлена! Мы свяжемся с вами в ближайшее время.</p>
                </div>
                <div class="modal-footer">
                    <a href="/" class="btn btn-primary">Закрыть</a>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${param.success == 'true'}">
    <div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">✅ Заявка отправлена</h5>
                </div>
                <div class="modal-body">
                    <p>Ваша заявка на тест-драйв успешно отправлена! Мы свяжемся с вами для подтверждения.</p>
                </div>
                <div class="modal-footer">
                    <a href="/" class="btn btn-primary">Закрыть</a>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("callbackForm");

        form.addEventListener("submit", function (e) {
            e.preventDefault();
            const formData = new FormData(form);

            fetch("/callback/send", {
                method: "POST",
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        const modal = bootstrap.Modal.getInstance(document.getElementById("callbackModal"));
                        modal.hide();
                        window.location.href = "/?callbackSuccess=true";
                        form.reset();
                    } else {
                        alert("Ошибка при отправке заявки");
                    }
                })
                .catch(() => {
                    alert("Ошибка сети");
                });
        });
    });
</script>


</body>

</html>