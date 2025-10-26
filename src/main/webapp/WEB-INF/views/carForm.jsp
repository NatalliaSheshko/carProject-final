<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Форма автомобиля</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container mt-4">
<h2><c:if test="${car.id == null}">Добавить</c:if><c:if test="${car.id != null}">Редактировать</c:if> автомобиль</h2>

<form action="/admin/cars/save" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${car.id}"/>

    <div class="mb-3">
        <label>Марка</label>
        <input type="text" name="brand" class="form-control" value="${car.brand}" required maxlength="50"/>
    </div>

    <div class="mb-3">
        <label>Модель</label>
        <input type="text" name="model" class="form-control" value="${car.model}" required/>
    </div>

    <div class="mb-3">
        <label>Год</label>
        <input type="number" name="year" class="form-control" value="${car.year}" required min="2000" max="2100"/>
    </div>

    <div class="mb-3">
        <label>Цена</label>
        <input type="number" step="0.01" name="price" class="form-control" value="${car.price}" required min="0"/>
    </div>

    <div class="mb-3">
        <label>Цвет</label>
        <input type="text" name="color" class="form-control" value="${car.color}"/>
    </div>

    <div class="mb-3">
        <label>VIN</label>
        <input type="text" name="vin" class="form-control" value="${car.vin}" required maxlength="17"/>
    </div>

    <div class="mb-3">
        <label>Тип кузова</label>
        <select name="bodyType" class="form-control" required>
            <option value="">Выберите...</option>
            <option value="Седан" ${car.bodyType == 'Седан' ? 'selected' : ''}>Седан</option>
            <option value="Хэтчбек" ${car.bodyType == 'Хэтчбек' ? 'selected' : ''}>Хэтчбек</option>
            <option value="Универсал" ${car.bodyType == 'Универсал' ? 'selected' : ''}>Универсал</option>
            <option value="Купе" ${car.bodyType == 'Купе' ? 'selected' : ''}>Купе</option>
            <option value="Кабриолет" ${car.bodyType == 'Кабриолет' ? 'selected' : ''}>Кабриолет</option>
            <option value="Внедорожник" ${car.bodyType == 'Внедорожник' ? 'selected' : ''}>Внедорожник</option>
            <option value="Кроссовер" ${car.bodyType == 'Кроссовер' ? 'selected' : ''}>Кроссовер</option>
            <option value="Минивэн" ${car.bodyType == 'Минивэн' ? 'selected' : ''}>Минивэн</option>
            <option value="Пикап" ${car.bodyType == 'Пикап' ? 'selected' : ''}>Пикап</option>
            <option value="Фургон" ${car.bodyType == 'Фургон' ? 'selected' : ''}>Фургон</option>
        </select>
    </div>

    <div class="mb-3">
        <label>Коробка передач</label>
        <select name="transmission" class="form-control" required>
            <option value="">Выберите...</option>
            <option value="МКПП" ${car.transmission == 'МКПП' ? 'selected' : ''}>МКПП</option>
            <option value="АКПП" ${car.transmission == 'АКПП' ? 'selected' : ''}>АКПП</option>
            <option value="Робот" ${car.transmission == 'Робот' ? 'selected' : ''}>Робот</option>
            <option value="Вариатор" ${car.transmission == 'Вариатор' ? 'selected' : ''}>Вариатор</option>
            <option value="DCT" ${car.transmission == 'DCT' ? 'selected' : ''}>DCT</option>
        </select>
    </div>

    <div class="mb-3">
        <label>Привод</label>
        <select name="driveType" class="form-control" required>
            <option value="">Выберите...</option>
            <option value="Передний" ${car.driveType == 'Передний' ? 'selected' : ''}>Передний</option>
            <option value="Задний" ${car.driveType == 'Задний' ? 'selected' : ''}>Задний</option>
            <option value="Полный" ${car.driveType == 'Полный' ? 'selected' : ''}>Полный</option>
            <option value="Подключаемый полный" ${car.driveType == 'Подключаемый полный' ? 'selected' : ''}>Подключаемый полный</option>
            <option value="Постоянный полный" ${car.driveType == 'Постоянный полный' ? 'selected' : ''}>Постоянный полный</option>
            <option value="Гибридный" ${car.driveType == 'Гибридный' ? 'selected' : ''}>Гибридный</option>
        </select>
    </div>

    <div class="mb-3">
        <label>Тип двигателя</label>
        <select name="engineType" id="engineType" class="form-control" required>
            <option value="">Выберите...</option>
            <option value="бензин" ${car.engineType == 'бензин' ? 'selected' : ''}>Бензин</option>
            <option value="дизель" ${car.engineType == 'дизель' ? 'selected' : ''}>Дизель</option>
            <option value="электро" ${car.engineType == 'электро' ? 'selected' : ''}>Электро</option>
        </select>
    </div>

    <div id="electricFields">
        <div class="mb-3">
            <label>Время зарядки (мин)</label>
            <input type="number" name="chargingTime" class="form-control" value="${car.chargingTime}" />
        </div>

        <div class="mb-3">
            <label>Тип батареи</label>
            <select name="battery" class="form-control">
                <option value="">Выберите...</option>
                <option value="Литий-ионные (Li-ion)" ${car.battery == 'Литий-ионные (Li-ion)' ? 'selected' : ''}>Литий-ионные (Li-ion)</option>
                <option value="Литий-железо-фосфатные (LFP)" ${car.battery == 'Литий-железо-фосфатные (LFP)' ? 'selected' : ''}>Литий-железо-фосфатные (LFP)</option>
                <option value="Никель-металл-гидридные (NiMH)" ${car.battery == 'Никель-металл-гидридные (NiMH)' ? 'selected' : ''}>Никель-металл-гидридные (NiMH)</option>
                <option value="Никель-марганец-кобальтовые (NMC)" ${car.battery == 'Никель-марганец-кобальтовые (NMC)' ? 'selected' : ''}>Никель-марганец-кобальтовые (NMC)</option>
                <option value="Никель-кобальт-алюминиевые (NCA)" ${car.battery == 'Никель-кобальт-алюминиевые (NCA)' ? 'selected' : ''}>Никель-кобальт-алюминиевые (NCA)</option>
                <option value="Литий-кобальтовые" ${car.battery == 'Литий-кобальтовые' ? 'selected' : ''}>Литий-кобальтовые</option>
                <option value="Литий-марганцевые" ${car.battery == 'Литий-марганцевые' ? 'selected' : ''}>Литий-марганцевые</option>
                <option value="Свинцово-кислотные" ${car.battery == 'Свинцово-кислотные' ? 'selected' : ''}>Свинцово-кислотные</option>
                <option value="Натрий-никель-хлоридные (NaNiCl₂)" ${car.battery == 'Натрий-никель-хлоридные (NaNiCl₂)' ? 'selected' : ''}>Натрий-никель-хлоридные (NaNiCl₂)</option>
                <option value="Твёрдотельные (SSB)" ${car.battery == 'Твёрдотельные (SSB)' ? 'selected' : ''}>Твёрдотельные (SSB)</option>
            </select>
        </div>

        <div class="mb-3">
            <label>Запас хода (км)</label>
            <input type="number" name="range" class="form-control" value="${car.range}" />
        </div>
    </div>

    <div class="mb-3">
        <label>Мощность двигателя (л.с.)</label>
        <input type="number" name="enginePower" class="form-control" value="${car.enginePower}" />
    </div>

    <div class="mb-3">
        <label>Объём двигателя (л)</label>
        <input type="number" step="0.1" name="engineVolume" class="form-control" value="${car.engineVolume}" />
    </div>

    <div class="mb-3">
        <label>Максимальная скорость (км/ч)</label>
        <input type="number" name="maxSpeed" class="form-control" value="${car.maxSpeed}" />
    </div>



    <div class="mb-3">
        <label>Время разгона до 100 км/ч (сек)</label>
        <input type="number" step="0.1" name="accelerationTime" class="form-control" value="${car.accelerationTime}" />
    </div>

    <div class="mb-3">
        <label>Комплектация</label>
        <input type="text" name="configuration" class="form-control" value="${car.configuration}" />
    </div>

    <div class="mb-3">
        <label>Габариты</label>
        <input type="text" name="dimensions" class="form-control" value="${car.dimensions}" />
    </div>

    <div class="mb-3">
        <label>Кол-во посадочных мест</label>
        <input type="number" name="seats" class="form-control" value="${car.seats}" />
    </div>

    <div class="mb-3">
        <label>Статус</label>
        <select name="status" class="form-control">
            <option value="AVAILABLE" ${car.status == 'AVAILABLE' ? 'selected' : ''}>Доступен</option>
            <option value="SOLD" ${car.status == 'SOLD' ? 'selected' : ''}>Продан</option>
            <option value="RESERVED" ${car.status == 'RESERVED' ? 'selected' : ''}>Зарезервирован</option>
        </select>
    </div>

    <div class="mb-3">
        <label>Описание</label>
        <textarea name="description" class="form-control">${car.description}</textarea>
    </div>

    <!-- поле загрузки фото -->
    <label for="photoFile">Фото автомобиля:</label>
    <c:forEach var="photo" items="${car.photos}" varStatus="status">
        <div class="mb-3">
            <label>Фото ${status.index + 1}</label>
            <img src="${photo.url}" alt="Фото автомобиля" style="max-width: 200px; display: block; margin-bottom: 8px;" />

            <!-- скрытые поля для передачи обратно -->
            <input type="hidden" name="photos[${status.index}].id" value="${photo.id}" />
            <input type="hidden" name="photos[${status.index}].url" value="${photo.url}" />
            <input type="hidden" name="photos[${status.index}].car.id" value="${car.id}" />

            <label>
                <input type="checkbox" name="photosToDelete" value="${photo.id}" />
                Удалить это фото
            </label>

        </div>
    </c:forEach>
    <input type="file" name="photoFile" accept="image/*" multiple />

    <button type="submit" class="btn btn-primary">Сохранить</button>
    <a href="/cars" class="btn btn-secondary">Отмена</a>
</form>

<c:if test="${not empty success}">
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="toastSuccess" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
                <c:choose>
                    <c:when test="${success == 'added'}">Автомобиль успешно добавлен.</c:when>
                    <c:otherwise>Автомобиль успешно обновлён.</c:otherwise>
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

<script>
    function toggleElectricFields() {
        const engineType = document.getElementById("engineType").value;
        const electricFields = document.getElementById("electricFields");
        electricFields.style.display = engineType === "электро" ? "block" : "none";
    }

    document.getElementById("engineType").addEventListener("change", toggleElectricFields);
    window.addEventListener("DOMContentLoaded", toggleElectricFields); // при загрузке
</script>

</body>
</html>