<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${car.brand} ${car.model}</title>
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

        .container {
            max-width: 700px;
            margin: 0 auto;
        }

        .card-body p {
            margin-bottom: 10px;
            font-size: 1rem;
        }

        .table th {
            background-color: #f9f9f9;
            font-weight: bold;
            width: 30%;
        }

        .table td {
            background-color: #ffffff;
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
<body style="background-color: #f2f2f2; font-family: Arial, sans-serif; color: #1a1a1a;">
<div class="row justify-content-center">
    <h2 class="text-center mb-4">${car.brand} ${car.model}</h2>
    <div class="col-12 col-lg-8">
        <div class="card shadow-lg">
            <div class="card-body">
                <div class="d-flex flex-wrap">
                    <!-- Характеристики слева -->
                    <div class="car-info" style="flex: 0 0 35%; max-width: 35%;">
                        <h4 class="mb-3">Характеристики</h4>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><strong>Цвет:</strong> ${car.color}</li>
                            <li class="list-group-item"><strong>Год:</strong> ${car.year}</li>
                            <li class="list-group-item"><strong>Статус:</strong> ${car.status}</li>
                            <li class="list-group-item"><strong>Кузов:</strong> ${car.bodyType}</li>
                            <li class="list-group-item"><strong>Привод:</strong> ${car.driveType}</li>
                            <li class="list-group-item"><strong>Двигатель:</strong> ${car.engineType}</li>
                            <li class="list-group-item"><strong>Мощность:</strong> ${car.enginePower} л.с.</li>
                            <li class="list-group-item"><strong>Объём:</strong> ${car.engineVolume} л</li>
                            <li class="list-group-item"><strong>Скорость:</strong> ${car.maxSpeed} км/ч</li>
                            <li class="list-group-item"><strong>Коробка:</strong> ${car.transmission}</li>
                            <c:if test="${car.engineType == 'электро'}">
                                <li class="list-group-item"><strong>Зарядка:</strong> ${car.chargingTime} мин</li>
                                <li class="list-group-item"><strong>Батарея:</strong> ${car.battery}</li>
                                <li class="list-group-item"><strong>Запас хода:</strong> ${car.range} км</li>
                            </c:if>
                            <li class="list-group-item"><strong>Комплектация:</strong> ${car.configuration}</li>
                            <li class="list-group-item"><strong>Габариты:</strong> ${car.dimensions}</li>
                            <li class="list-group-item"><strong>Разгон:</strong> ${car.accelerationTime} сек</li>
                            <li class="list-group-item"><strong>Мест:</strong> ${car.seats}</li>
                        </ul>
                        <div class="mt-3">
                            <h5>Описание</h5>
                            <p>${car.description}</p>
                        </div>

                        <!-- Кнопки -->
                        <div class="d-flex justify-content-between flex-wrap mt-4">
                            <!-- Левая группа кнопок -->
                            <div class="d-flex flex-wrap gap-2">
                                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#bookingModal">
                                    Забронировать
                                </button>
                                <a href="/testdrive/request?carId=${car.id}" class="btn btn-warning">
                                    Записаться на тест-драйв
                                </a>
                            </div>


                        </div>
                    </div>

                    <!-- Фото справа -->
                    <div class="col-md-6">
                        <c:if test="${not empty car.photos}">
                            <div id="carousel-${car.id}" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach var="photo" items="${car.photos}" varStatus="loop">
                                        <div class="carousel-item ${loop.index == 0 ? 'active' : ''}">
                                            <img src="${photo.url}" class="d-block w-100 rounded clickable-photo" alt="Фото ${car.brand} ${car.model}" data-index="${loop.index}" />
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
                            <small class="text-muted d-block mt-2 text-center">Нажмите на фото для увеличения</small>
                        </c:if>
                        <c:if test="${empty car.photos}">
                            <img src="/images/no-photo.jpg" class="img-fluid rounded" alt="Нет фото" />
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <!-- Кнопка вне карточки, но внутри контейнера  -->
        <div class="text-end mt-4" style="padding-right: 1rem;">
            <a href="/" class="btn btn-outline-dark">
                Вернуться на главную
            </a>
        </div>
    </div>


</div>
<div class="modal fade" id="bookingModal" tabindex="-1" aria-labelledby="bookingModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="bookingForm">
                <div class="modal-header">
                    <h5 class="modal-title" id="bookingModalLabel">Заявка на бронирование</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="carId" value="${car.id}" />
                    <div class="mb-3">
                        <label for="name" class="form-label">Ваше имя</label>
                        <input type="text" class="form-control" id="name" name="name" required />
                    </div>
                    <div class="mb-3">
                        <label for="phone" class="form-label">Телефон</label>
                        <input type="tel" class="form-control" id="phone" name="phone" required />
                    </div>
                    <div class="mb-3">
                        <label for="comment" class="form-label">Комментарий</label>
                        <textarea class="form-control" id="comment" name="comment" rows="3"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Отправить заявку</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${param.bookingSuccess == 'true'}">
    <div class="modal fade show" id="bookingSuccessModal" tabindex="-1" role="dialog" style="display:block; background-color: rgba(0,0,0,0.5);">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">✅ Заявка отправлена</h5>
                </div>
                <div class="modal-body">
                    <p>Ваша заявка на бронирование успешно отправлена! Мы свяжемся с вами для подтверждения.</p>
                </div>
                <div class="modal-footer">
                    <a href="/cars/details/${car.id}" class="btn btn-primary">Закрыть</a>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const photos = Array.from(document.querySelectorAll(".clickable-photo"));
        const photoUrls = photos.map(p => p.src);

        photos.forEach(photo => {
            photo.addEventListener("click", function () {
                let currentIndex = parseInt(photo.dataset.index);

                const overlay = document.createElement("div");
                overlay.style.position = "fixed";
                overlay.style.top = 0;
                overlay.style.left = 0;
                overlay.style.width = "100vw";
                overlay.style.height = "100vh";
                overlay.style.backgroundColor = "rgba(0,0,0,0.9)";
                overlay.style.display = "flex";
                overlay.style.alignItems = "center";
                overlay.style.justifyContent = "center";
                overlay.style.zIndex = 9999;

                const img = document.createElement("img");
                img.src = photoUrls[currentIndex];
                img.style.maxWidth = "90%";
                img.style.maxHeight = "90%";
                img.style.borderRadius = "8px";
                img.style.boxShadow = "0 0 20px rgba(255,255,255,0.3)";
                img.style.transition = "opacity 0.3s ease";
                overlay.appendChild(img);

                // Стрелка «назад»
                const prevBtn = document.createElement("div");
                prevBtn.innerHTML = "&#10094;";
                prevBtn.style.position = "absolute";
                prevBtn.style.left = "30px";
                prevBtn.style.fontSize = "48px";
                prevBtn.style.color = "#fff";
                prevBtn.style.cursor = "pointer";
                overlay.appendChild(prevBtn);

                // Стрелка «вперёд»
                const nextBtn = document.createElement("div");
                nextBtn.innerHTML = "&#10095;";
                nextBtn.style.position = "absolute";
                nextBtn.style.right = "30px";
                nextBtn.style.fontSize = "48px";
                nextBtn.style.color = "#fff";
                nextBtn.style.cursor = "pointer";
                overlay.appendChild(nextBtn);

                // Закрытие по клику вне изображения
                overlay.addEventListener("click", function (e) {
                    if (e.target === overlay || e.target === img) {
                        document.body.removeChild(overlay);
                    }
                });

                // Листание
                prevBtn.addEventListener("click", function (e) {
                    e.stopPropagation();
                    currentIndex = (currentIndex - 1 + photoUrls.length) % photoUrls.length;
                    img.src = photoUrls[currentIndex];
                });

                nextBtn.addEventListener("click", function (e) {
                    e.stopPropagation();
                    currentIndex = (currentIndex + 1) % photoUrls.length;
                    img.src = photoUrls[currentIndex];
                });

                document.body.appendChild(overlay);
            });
        });
    });
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("bookingForm");
        const toastEl = document.getElementById("bookingToast");
        const toast = new bootstrap.Toast(toastEl);

        form.addEventListener("submit", function (e) {
            e.preventDefault();

            const formData = new FormData(form);

            fetch("/booking/request", {
                method: "POST",
                body: formData
            })
                .then(response => {
                    if (response.ok) {
                        const modal = bootstrap.Modal.getInstance(document.getElementById("bookingModal"));
                        modal.hide();
                        form.reset();
                        window.location.href = "/cars/details/" + formData.get("carId") + "?bookingSuccess=true";
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
