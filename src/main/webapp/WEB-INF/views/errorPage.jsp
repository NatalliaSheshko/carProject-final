<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ошибка</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f8d7da; color: #721c24; padding: 40px; }
        .error-box { background-color: #f5c6cb; border: 1px solid #f1b0b7; padding: 20px; border-radius: 8px; max-width: 600px; margin: auto; }
        h2 { margin-top: 0; }
        a { color: #721c24; text-decoration: underline; }
    </style>
</head>
<body>
<div class="error-box">
    <h2>⚠️ Произошла ошибка</h2>
    <p><c:out value="${error}" default="Что-то пошло не так. Попробуйте позже." /></p>
    <p><a href="/">Вернуться на главную</a></p>
</div>
</body>
</html>

