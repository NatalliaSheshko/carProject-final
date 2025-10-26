<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>Запись на тест-драйв</title>
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
        .container {
            max-width: 600px;
            margin: 0 auto;
        }

        .form-card {
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
        }

        .form-group input {
            padding: 10px 15px;
            font-size: 1rem;
            width: 100%;
        }

        .btn-block {
            display: block;
            width: 100%;
        }

        .btn-lg {
            padding: 15px 25px;
            font-size: 1.2rem;
        }
        /* === Квадратный чекбокс === */
        input[type="checkbox"].form-check-input {
            width: 20px;
            height: 20px;
            border-radius: 0; /* убираем скругление */
            border: 2px solid #999999; /* серый контур */
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            background-color: #fff;
            cursor: pointer;
            position: relative;
            margin-top: 0.2rem;
        }

        /* === Подписи к чекбоксам в форме === */
        .form-check-label {
            font-size: 0.9rem;
            color: #333333;
        }

        /* Зелёный чекбокс при активации */
        input[type="checkbox"]:checked {
            background-color: #28a745;
            border-color: #28a745;
        }

        /* Для современных браузеров */
        input[type="checkbox"] {
            accent-color: #28a745;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="section-title text-center">Запись на тест-драйв: ${car.brand} ${car.model}</h2>

    <form id="testDriveForm" action="/testdrive/submit?carId=${car.id}" method="post" class="form-card p-4">
        <div class="form-group mb-3">
            <label for="fullName">Имя:</label>
            <input type="text" name="fullName" id="fullName" class="form-control" placeholder="Введите ваше имя"
                   value="${request.fullName}" required maxlength="100"
                   pattern="^[А-Яа-яЁёA-Za-z\\s\\-]+$"
                   title="Введите ваше имя (только буквы и пробелы)" />
            <div class="invalid-feedback d-block" id="fullNameError"></div>
        </div>

        <div class="form-group mb-3">
            <label for="phone">Телефон:</label>
            <input type="text" name="phone" id="phone" class="form-control" placeholder="+375..."
                   value="${request.phone}" required maxlength="20"
                   pattern="^\+?\d{7,15}$"
                   title="Введите номер телефона (от 7 до 15 цифр, можно с +)" />
            <div class="invalid-feedback d-block" id="phoneError"></div>
        </div>

        <div class="form-group mb-3">
            <label for="phone">Email:</label>
            <input type="email" name="email" id="email" class="form-control" placeholder="Введите адрес электронной почты"
                   value="${request.email}" maxlength="100" />
            <div class="invalid-feedback d-block" id="emailError"></div>
        </div>

        <div class="form-group mb-3">
            <label for="phone">Желаемая дата:</label>
            <input type="date" name="preferredDate" id="preferredDate" class="form-control"
                   value="${request.preferredDate}" min="${today}" />
            <div class="invalid-feedback d-block" id="preferredDateError"></div>
        </div>

        <div class="form-group mb-4">
            <label for="phone">Желаемое время:</label>
            <input type="text" name="preferredTime" id="preferredTime" class="form-control" placeholder="Введите время в формате ЧЧ:ММ"
                   value="${request.preferredTime}" maxlength="20"
                   pattern="^([01]?[0-9]|2[0-3]):[0-5][0-9]$"
                   title="Введите время в формате ЧЧ:ММ (например, 14:30)" />
            <div class="invalid-feedback d-block" id="preferredTimeError"></div>
        </div>

        <div class="form-group mb-4">
            <div class="form-check">
                <label class="form-check-label d-flex align-items-center" for="consentCheckbox">
                    <input type="checkbox" name="consent" class="form-check-input me-2" value="true"
                    ${request.consent ? "checked" : ""} id="consentCheckbox" />
                    Я согласен на обработку персональных данных
                </label>
                <div class="invalid-feedback d-block" id="consentError"></div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary btn-block btn-lg">Отправить заявку</button>
    </form>
</div>

<!-- Модальное окно успешной отправки -->
<div class="modal fade" id="testDriveSuccessModal" tabindex="-1" aria-labelledby="testDriveSuccessLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="testDriveSuccessLabel">✅ Заявка отправлена</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <div class="modal-body">
                <p>Спасибо! Ваша заявка на тест-драйв успешно отправлена. Мы свяжемся с вами для подтверждения времени.</p>
            </div>
            <div class="modal-footer">
                <a href="/" class="btn btn-primary">К выбору авто</a>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("testDriveForm");

        form.addEventListener("submit", function (e) {
            e.preventDefault(); // блокировка стандартной отправки

            let isValid = true;

            // Очистка ошибок
            document.querySelectorAll(".invalid-feedback").forEach(el => el.innerText = "");

            // Имя
            const fullName = document.getElementById("fullName");
            if (fullName.value.trim() === "") {
                document.getElementById("fullNameError").innerText = "Пожалуйста, введите ваше имя";
                isValid = false;
            }

            // Телефон
            const phone = document.getElementById("phone");
            const phonePattern = /^\+?\d{7,15}$/;
            if (phone.value.trim() === "") {
                document.getElementById("phoneError").innerText = "Пожалуйста, введите номер телефона";
                isValid = false;
            } else if (!phonePattern.test(phone.value.trim())) {
                document.getElementById("phoneError").innerText = "Некорректный формат телефона";
                isValid = false;
            }

            // Email
            const email = document.getElementById("email");
            if (email.value.trim() !== "" && !email.checkValidity()) {
                document.getElementById("emailError").innerText = "Некорректный email";
                isValid = false;
            }

            // Дата
            const preferredDate = document.getElementById("preferredDate");
            if (preferredDate.value && preferredDate.value < preferredDate.min) {
                document.getElementById("preferredDateError").innerText = "Дата не может быть в прошлом";
                isValid = false;
            }

            // Время
            const preferredTime = document.getElementById("preferredTime");
            const timePattern = /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/;
            if (preferredTime.value.trim() !== "" && !timePattern.test(preferredTime.value.trim())) {
                document.getElementById("preferredTimeError").innerText = "Введите время в формате ЧЧ:ММ";
                isValid = false;
            }

            // Согласие
            const consent = document.getElementById("consentCheckbox");
            if (!consent.checked) {
                document.getElementById("consentError").innerText = "Вы должны дать согласие";
                isValid = false;
            }

            // Если всё валидно — отправка через fetch
            if (isValid) {
                const formData = new FormData(form);

                fetch(form.getAttribute("action"), {
                    method: "POST",
                    body: formData
                })
                    .then(response => {
                        if (response.ok) {
                            form.reset();
                            const successModal = new bootstrap.Modal(document.getElementById("testDriveSuccessModal"));
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
