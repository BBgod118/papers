<%--
  Created by IntelliJ IDEA.
  User: 龙大侠
  Date: 2019/6/27
  Time: 0:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/fileUpload" method="post"
      enctype="multipart/form-data" onsubmit="return check()">
    请选择文件：<input id="file" type="file" name="uploadFile" multiple="multiple"/><br/>
    <input type="submit" value="上传"/>
</form><br/><br/><br/><br/>


<a href="javascript:void(0);" onclick="finPdf()">查看pdf</a>

<script>
    function finPdf() {
        window.open("http://localhost:8080/papers_war_exploded/docToPdf?upload_user_id=43&" +
            "fileName=水果报告.doc", "_blank", "top=200,left=200,height=600,width=800," +
            "status=yes,toolbar=1,menubar=no,location=no,scrollbars=yes");
    }
</script>
</body>
</html>
