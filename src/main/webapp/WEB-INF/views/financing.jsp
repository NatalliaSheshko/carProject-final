<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Финансирование</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        body {
            background-color: #f2f2f2; /* фон всей страницы — светло-серый */
            font-family: Arial, sans-serif; /* основной шрифт */
            color: #1a1a1a; /* цвет основного текста — почти чёрный */
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

        /* === Модальное окно === */
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
<h2>Финансирование: кредит и лизинг</h2>
<p>Мы предлагаем выгодные условия кредитования и лизинга автомобилей. Вы можете рассчитать ориентировочный ежемесячный платеж и оставить заявку.</p>

<div class="card p-3 mb-4" >
    <h4>Калькулятор платежа</h4>

    <div class="form-group mb-2">
        <label for="price">Стоимость авто (BYN):</label>
        <input type="number" id="price" class="form-control" style="max-width: 300px;">
        <div class="invalid-feedback d-block" id="priceError"></div>
    </div>

    <div class="form-group mb-2">
        <label for="months">Срок (в месяцах):</label>
        <input type="number" id="months" class="form-control" style="max-width: 300px;">
        <div class="invalid-feedback d-block" id="monthsError"></div>
    </div>

    <div class="form-group mb-2">
        <label for="rate">Процентная ставка (% годовых):</label>
        <input type="number" id="rate" class="form-control" style="max-width: 300px;">
        <div class="invalid-feedback d-block" id="rateError"></div>
    </div>

    <button id="calculateBtn" class="btn btn-primary mt-2" style="max-width: 300px;" onclick="calculatePayment()">Рассчитать</button>
    <p id="result" class="mt-3 font-weight-bold" ></p>
</div>

<div class="d-flex justify-content-between mt-3">
    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#financingModal"> Оставить заявку </button>
    <a href="/" class="btn btn-danger">На главную</a>
</div>

<div class="modal fade" id="financingModal" tabindex="-1" aria-labelledby="financingModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content" style="border-radius: 15px;">
            <div class="modal-header bg-dark text-white">
                <h5 class="modal-title" id="financingModalLabel">Заявка на финансирование</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
            </div>
            <form id="financingForm">
                <div class="modal-body">
                    <div class="form-group mb-2">
                        <label for="fullName">Имя:</label>
                        <input type="text" class="form-control" id="fullName" name="fullName" required style="max-width: 400px;"
                               placeholder="Введите ваше имя">
                        <div class="invalid-feedback d-block" id="fullNameError"></div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="phone">Телефон:</label>
                        <input type="tel" class="form-control" id="phone" name="phone" required style="max-width: 400px;"
                               placeholder="+375...">
                        <div class="invalid-feedback d-block" id="phoneError"></div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="email">Email:</label>
                        <input type="email" class="form-control" id="email" name="email" required style="max-width: 400px;"
                               placeholder="Введите адрес электронной почты">
                        <div class="invalid-feedback d-block" id="emailError"></div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="amount">Сумма (BYN)</label>
                        <input type="number" class="form-control" id="amount" name="amount" required style="max-width: 400px;">
                        <div class="invalid-feedback d-block" id="amountError"></div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="termMonths">Срок (в месяцах)</label>
                        <input type="number" class="form-control" id="termMonths" name="termMonths" required style="max-width: 400px;">
                        <div class="invalid-feedback d-block" id="termMonthsError"></div>
                    </div>
                    <div class="form-group mb-2">
                        <label for="financingType">Тип финансирования</label>
                        <select class="form-control" id="financingType" name="financingType" required style="max-width: 400px;">
                            <option value="">Выберите</option>
                            <option value="Кредит">Кредит</option>
                            <option value="Лизинг">Лизинг</option>
                        </select>
                        <div class="invalid-feedback d-block" id="financingTypeError"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Отправить заявку</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Модальное окно -->
    <div class="modal fade" id="financingSuccessModal" tabindex="-1" aria-labelledby="financingSuccessLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="financingSuccessLabel">✅ Заявка отправлена</h5>
                </div>
                <div class="modal-body">
                    <p>Ваша заявка на финансирование успешно отправлена! Мы свяжемся с вами для уточнения деталей.</p>
                </div>
                <div class="modal-footer">
                    <a href="/financing" class="btn btn-primary">Закрыть</a>
                </div>
            </div>
        </div>
    </div>

<script>
    function calculatePayment() {
        // Очистка ошибок и классов
        const fields = ["price", "months", "rate"];
        fields.forEach(id => {
            document.getElementById(id).classList.remove("is-invalid");
            document.getElementById(id + "Error").innerText = "";
        });
        document.getElementById("result").innerText = "";

        let isValid = true;

        const price = document.getElementById("price");
        const months = document.getElementById("months");
        const rate = document.getElementById("rate");

        // Проверка стоимости
        if (price.value.trim() === "" || parseFloat(price.value) <= 0) {
            price.classList.add("is-invalid");
            document.getElementById("priceError").innerText = "Введите корректную стоимость авто";
            isValid = false;
        }

        // Проверка срока
        if (months.value.trim() === "" || parseInt(months.value) <= 0) {
            months.classList.add("is-invalid");
            document.getElementById("monthsError").innerText = "Введите срок больше 0";
            isValid = false;
        }

        // Проверка ставки
        if (rate.value.trim() === "" || parseFloat(rate.value) < 0 || parseFloat(rate.value) > 100) {
            rate.classList.add("is-invalid");
            document.getElementById("rateError").innerText = "Введите ставку от 0 до 100";
            isValid = false;
        }

        if (isValid) {
            const principal = parseFloat(price.value);
            const monthsVal = parseInt(months.value);
            const annualRate = parseFloat(rate.value);
            const monthlyRate = annualRate / 12 / 100;

            let payment;

            if (monthlyRate === 0) {
                payment = principal / monthsVal;
            } else {
                const denominator = 1 - Math.pow(1 + monthlyRate, -monthsVal);
                payment = denominator !== 0 ? (principal * monthlyRate) / denominator : NaN;
            }

            if (isNaN(payment) || !isFinite(payment)) {
                document.getElementById("result").innerText = "Ошибка при расчёте. Проверьте введённые данные.";
            } else {
                const rounded = Math.round(payment);
                document.getElementById("result").textContent = "Ориентировочный платёж: " + rounded + " BYN/мес";

                const btn = document.getElementById("calculateBtn");
                btn.classList.remove("btn-primary");
                btn.classList.add("btn-dark");
            }
        }
    }
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("financingForm");
        const financingModalEl = document.getElementById("financingModal");

        // Копируем значения из калькулятора
        financingModalEl.addEventListener("show.bs.modal", function () {
            document.getElementById("amount").value = document.getElementById("price").value;
            document.getElementById("termMonths").value = document.getElementById("months").value;
        });


        console.log(form, financingModalEl)
        form.addEventListener("submit", function (e) {
            e.preventDefault();

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
            if (email.value.trim() === "") {
                document.getElementById("emailError").innerText = "Пожалуйста, введите email";
                isValid = false;
            } else if (!email.checkValidity()) {
                document.getElementById("emailError").innerText = "Некорректный email";
                isValid = false;
            }

            // Сумма
            const amount = document.getElementById("amount");
            if (amount.value.trim() === "" || parseFloat(amount.value) <= 0) {
                document.getElementById("amountError").innerText = "Введите сумму больше 0";
                isValid = false;
            }

            // Срок
            const termMonths = document.getElementById("termMonths");
            if (termMonths.value.trim() === "" || parseInt(termMonths.value) <= 0) {
                document.getElementById("termMonthsError").innerText = "Введите срок больше 0";
                isValid = false;
            }

            // Тип финансирования
            const financingType = document.getElementById("financingType");
            if (financingType.value === "") {
                document.getElementById("financingTypeError").innerText = "Выберите тип финансирования";
                isValid = false;
            }

            // Если всё валидно — отправляем
            if (isValid) {
                const formData = new FormData(form);

                fetch("/financing/apply", {
                    method: "POST",
                    body: formData
                })
                    .then(response => {
                        if (response.ok) {
                            const modal = bootstrap.Modal.getInstance(financingModalEl);
                            modal.hide();
                            form.reset();

                            const successModal = new bootstrap.Modal(document.getElementById("financingSuccessModal"));
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
