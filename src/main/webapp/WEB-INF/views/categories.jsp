<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>📂 Управление категориями</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <style>
    body { background-color: #f8f9fa; }
    h2 { margin-top: 30px; margin-bottom: 20px; font-weight: bold; }
    .table th { background-color: #343a40; color: white; }
    .table td { vertical-align: middle; }
    .btn-action { margin-right: 5px; }
  </style>
</head>
<body>
<div class="container">
  <h2 class="text-center">📂 Управление категориями</h2>

  <!-- Форма фильтрации -->
  <form method="get" action="/admin/categories" class="row g-2 mb-4">
    <div class="col-auto">
      <label for="nameFilter" class="form-label">Название:</label>
      <input type="text" id="nameFilter" name="nameFilter" class="form-control" value="${param.nameFilter}" style="max-width: 200px;">
    </div>
    <div class="col-auto d-flex align-items-end">
      <button type="submit" class="btn btn-primary me-2">Применить</button>
      <a href="/admin/categories" class="btn btn-outline-secondary">Снять фильтрацию</a>
    </div>
  </form>

  <!-- Кнопка добавления -->
  <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addCategoryModal">
    ➕ Добавить категорию
  </button>

  <!-- Модальное окно добавления -->
  <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <form action="/admin/categories" method="post" class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addCategoryModalLabel">Добавить категорию</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
        </div>
        <div class="modal-body">
          <input type="text" name="categoryname" class="form-control" required placeholder="Название категории">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
          <button type="submit" class="btn btn-primary">Сохранить</button>
        </div>
      </form>
    </div>
  </div>

  <!-- Таблица категорий -->
  <table class="table table-bordered table-hover shadow-sm">
    <thead>
    <tr>
      <th>ID</th>
      <th>Название</th>
      <th class="text-center">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="category" items="${categories}">
      <tr>
        <td>${category.id}</td>
        <td>${category.name}</td>
        <td class="text-center">
          <!-- Удаление -->
          <form action="/admin/categories/delete" method="post" style="display:inline;">
            <input type="hidden" name="id" value="${category.id}">
            <button type="submit" class="btn btn-sm btn-outline-danger btn-action" onclick="return confirm('Удалить категорию?')">🗑️ Удалить</button>
          </form>

          <!-- Обновление -->
          <button type="button" class="btn btn-sm btn-outline-warning btn-action" data-bs-toggle="modal" data-bs-target="#editModal${category.id}">
            ✏️ Редактировать
          </button>

          <!-- Модальное окно редактирования -->
          <div class="modal fade" id="editModal${category.id}" tabindex="-1" aria-labelledby="editModalLabel${category.id}" aria-hidden="true">
            <div class="modal-dialog">
              <form action="/admin/categories/update" method="post" class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="editModalLabel${category.id}">Редактировать категорию</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                </div>
                <div class="modal-body">
                  <input type="hidden" name="categoryid" value="${category.id}">
                  <input type="text" name="categoryname" class="form-control" value="${category.name}" required>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                  <button type="submit" class="btn btn-primary">Сохранить изменения</button>
                </div>
              </form>
            </div>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="d-flex justify-content-between mt-3 mb-5">
    <a href="/admin/" class="btn btn-dark">Админ-панель</a>
    <a href="/" class="btn btn-primary">На главную</a>
  </div>

  <!-- Уведомления -->
  <c:if test="${not empty success}">
    <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
      <div id="toastSuccess" class="toast align-items-center text-white bg-success border-0" role="alert">
        <div class="d-flex">
          <div class="toast-body">✅ ${success}</div>
          <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
      </div>
    </div>
    <script>
      window.addEventListener('DOMContentLoaded', () => {
        new bootstrap.Toast(document.getElementById('toastSuccess')).show();
      });
    </script>
  </c:if>

  <c:if test="${not empty error}">
    <div class="position-fixed top-0 end-0 p-3" style="z-index: 11">
      <div id="toastError" class="toast align-items-center text-white bg-danger border-0" role="alert">
        <div class="d-flex">
          <div class="toast-body">❌ ${error}</div>
          <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
      </div>
    </div>
    <script>
      window.addEventListener('DOMContentLoaded', () => {
        new bootstrap.Toast(document.getElementById('toastError')).show();
      });
    </script>
  </c:if>
</div>
</body>
</html>