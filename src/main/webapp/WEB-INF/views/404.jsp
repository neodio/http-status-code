<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <META http-equiv="Expires" content="-1">
    <META http-equiv="Pragma" content="no-cache">
    <META http-equiv="Cache-Control" content="No-Cache">
    <title>[error] 404페이지</title>
</head>
<body>
<script>
    var msg = "${msg}";
    if (!msg) {
        msg = "잘못된 접근입니다.";
    }
    alert(msg);
    location.href = '/';
</script>
</body>
</html>