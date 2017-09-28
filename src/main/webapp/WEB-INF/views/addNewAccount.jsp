<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Dragos
  Date: 28.07.2017
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
%>
<%@ page session="true" %>
<!DOCTYPE html>

<html>
<head>
    <link rel="stylesheet" href="/css/addNewAccount.css"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/js/addNewAccount.js"></script>
</head>
<body>
<div class="module form-module">
    <div class="toggle">
    </div>
    <div class="form">
        <h2><spring:message code="newAccount.message" /> </h2>
        <form id="newAccount">
            <!--First Name-->
            <div class="input_with_error">
                <label for="newAccount_firstName"><spring:message code="global.firstName" /></label>
                <input type="text" id="newAccount_firstName" name="firstName"/>
                <span class="error"><spring:message code="global.required"/></span>
            </div>

            <!--Last Name-->
            <div class="input_with_error">
                <label for="newAccount_lastName"><spring:message code="global.lastName" /></label>
                <input type="text" id="newAccount_lastName" name="lastName"/>
                <span class="error"><spring:message code="global.required"/></span>
            </div>

            <!--SSN-->
            <div class="input_with_error">
                <label for="newAccount_ssn"><spring:message code="newAccount.ssn" /></label>
                <input type="text" id="newAccount_ssn" name="ssn"/>
                <span class="error"><spring:message code="newAccount.error1" /></span>
            </div>

            <!--Email-->
            <div class="input_with_error">
                <label for="newAccount_email">Email</label>
                <input type="text" id="newAccount_email" name="email"/>
                <span class="error"><spring:message code="newAccount.error2" /></span>
            </div>

            <!--Password-->
            <div class="input_with_error">
                <label for="newAccount_password"><spring:message code="global.password" /></label>
                <input type="password" id="newAccount_password" name="password"/>
                <span class="error"><spring:message code="global.required"/></span>
            </div>

            <label for="role"><spring:message code="newAccount.role" /></label>
            <select id="role" name="role">
                <option value="Professor"><spring:message code="global.professor" /></option>
                <option value="Student"><spring:message code="newAccount.student" /></option>
            </select>
            <button type="button" id="submit"><spring:message code="newAccount.button" /></button>
        </form>
    </div>
    <div id="message"></div>
</div>
</body>
</html>
