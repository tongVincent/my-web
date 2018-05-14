<%--
  Created by IntelliJ IDEA.
  User: vincent.tong
  Date: 2016/7/6
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <form action="/test/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <input type="file" name="file">
        <input type="text" name="name">
        <input type="text" name="school">
        <input type="submit">
    </form>
</body>
</html>
