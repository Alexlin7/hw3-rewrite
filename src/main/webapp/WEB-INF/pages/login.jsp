<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/layout/layout.jsp"/>
<% request.setAttribute("title", "登入"); %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<body>
<div class="container">
    <h2>會員登入</h2>

    <%-- 此處為錯誤報告 --%>

    <c:if test="${not empty errorMessages}">
        <c:forEach var="error" items="${errorMessages}">
            <div class="alert alert-danger" role="alert">
                    ${error}
            </div>
        </c:forEach>
    </c:if>

    <form:form method="POST" action="/hw4/login">
        <div class="mb-3">
            <form:label path="username" cssClass="form-label">使用者名稱</form:label>
            <form:input path="username" cssClass="form-control"/>
            <div id="groupHelp" class="form-text">輸入您的帳號</div>
        </div>
        <div class="mb-3">
            <form:label path="password" cssClass="form-label">密碼</form:label>
            <form:password path="password" cssClass="form-control"/>
            <div id="groupHelp" class="form-text">輸入您的密碼</div>
        </div>
        <input type="reset" class="btn btn-secondary" value="重置" />
        <input type="submit" value="登入" class="btn btn-primary" />
    </form:form>
    <a href="createMember">註冊帳號</a>
</div>

</body>
