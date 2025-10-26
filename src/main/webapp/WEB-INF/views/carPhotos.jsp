<form action="/cars/uploadPhoto" method="post" enctype="multipart/form-data">
    <input type="hidden" name="carId" value="${car.id}" />
    <input type="file" name="photoFile" accept="image/*" required />
    <button type="submit" class="btn btn-primary">Загрузить фото</button>
</form>