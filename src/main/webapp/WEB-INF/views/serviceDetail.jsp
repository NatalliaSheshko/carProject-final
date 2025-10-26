<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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

        .modal.fade.show {
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            z-index: 1050;
        }

        #successModal {
            z-index: 1055;
        }
        .modal-backdrop {
            z-index: 1050;
        }

        /* Зелёный чекбокс при нажатии */
        input[type="checkbox"]:checked {
            background-color: #28a745; /* зелёный фон */
            border-color: #28a745;     /* зелёная рамка */
        }

        /* Для браузеров, которые используют псевдоэлемент ::before */
        input[type="checkbox"] {
            accent-color: #28a745; /* работает в Chrome, Edge, Firefox */
        }
    </style>
</head>
<body class="container mt-4">
<h2 class="mb-4">${title}</h2>

<div class="container">
    <div class="row">
        <c:forEach var="block" items="${blocks}">
            <div class="col-lg-6 col-md-6 col-sm-12 mb-4 d-flex">
                <div class="card w-100 shadow-sm">
                    <c:url value="${block.imageUrl}" var="imgPath" />
                    <img src="${imgPath}" class="card-img-top img-fluid" alt="${block.title}" style="object-fit: cover; height: 200px;">
                    <div class="card-body">
                        <h5 class="card-title">${block.title}</h5>
                        <p class="card-text">${block.description}</p>
                        <p class="text-muted">${block.details}</p>
                        <a href="/calculate?service=${block.title}" class="btn btn-warning">Рассчитать стоимость</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<hr class="my-4">

<h4>Запишитесь на услугу в удобное для вас время:</h4>

<div class="card p-3 mb-4" >
<form:form modelAttribute="appointment" id="serviceForm" method="post" action="/service/submit" class="mt-3">
    <form:hidden path="serviceType" value="${title}" />

    <div class="form-group mb-3" style="max-width: 400px;">
        <label for="fullName">Имя:</label>
        <form:input path="fullName" cssClass="form-control" id="fullName"
                    htmlEscape="true" required="true" maxlength="100"
                    placeholder="Введите ваше имя" />
        <div class="invalid-feedback d-block" id="fullNameError"></div>
    </div>

    <div class="form-group mb-3" style="max-width: 400px;">
        <label for="phone">Телефон:</label>
        <form:input path="phone" cssClass="form-control" id="phone"
                    htmlEscape="true" required="true"
                    pattern="^\+?\d{7,15}$"
                    placeholder="+375..." />
        <div class="invalid-feedback d-block" id="phoneError"></div>
    </div>

    <div class="form-check mb-4" style="max-width: 600px;">
        <form:checkbox path="consent" cssClass="form-check-input" id="consent" required="true" />
        <label class="form-check-label" for="consent">
            Нажимая кнопку «Отправить», я принимаю условия пользовательского соглашения и даю согласие на обработку моих персональных данных.
        </label>
        <div class="invalid-feedback d-block" id="consentError"></div>
    </div>

    <div class="d-flex justify-content-between mt-3">
    <button type="submit" class="btn btn-primary">Отправить</button>
    <a href="/" class="btn btn-danger">На главную</a>
    </div>
</form:form>
</div>


<div class="modal fade" id="successModal" tabindex="-1" aria-labelledby="successModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title" id="successModalLabel">✅ Заявка принята</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <div class="modal-body">
                Спасибо! Ваша заявка на сервис успешно отправлена. Мы свяжемся с вами в ближайшее время.
            </div>
            <div class="modal-footer">
                <a href="/" class="btn btn-primary">На главную</a>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("serviceForm");

        form.addEventListener("submit", function (e) {
            e.preventDefault();

            let isValid = true;

            // Очистка ошибок
            document.querySelectorAll(".invalid-feedback").forEach(el => el.innerText = "");

            // Имя
            const fullName = document.getElementById("fullName");
            if (fullName.value.trim() === "") {
                document.getElementById("fullNameError").innerText = "Имя обязательно";
                isValid = false;
            }

            // Телефон
            const phone = document.getElementById("phone");
            const phonePattern = /^\+?\d{7,15}$/;
            if (phone.value.trim() === "") {
                document.getElementById("phoneError").innerText = "Телефон обязателен";
                isValid = false;
            } else if (!phonePattern.test(phone.value.trim())) {
                document.getElementById("phoneError").innerText = "Некорректный формат телефона";
                isValid = false;
            }

            // Согласие
            const consent = document.getElementById("consent");
            if (!consent.checked) {
                document.getElementById("consentError").innerText = "Необходимо согласие на обработку данных";
                isValid = false;
            }

            if (isValid) {
                const formData = new FormData(form);
                fetch("/service/submit", {
                    method: "POST",
                    body: formData
                })
                    .then(response => {
                        if (response.ok) {
                            form.reset();
                            const successModal = new bootstrap.Modal(document.getElementById("successModal"));
                            successModal.show();
                        } else {
                            alert("Ошибка при отправке заявки");
                        }
                    })
                    .catch(() => {
                        alert("Ошибка сети");
                    });
            }
        });
    });
</script>

</body>
</html>