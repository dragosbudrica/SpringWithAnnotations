<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"
         contentType="text/html; charset=utf-8"
%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="/css/table.css"/>
    <link rel="stylesheet" href="/css/allCourses.css"/>
    <script type="text/javascript" src="/js/common.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/js/allCourses.js" charset="UTF-8"></script>
</head>
<body>
<div id="warning">
    <img alt="" src="/resources/images/icon-warning-png-11.png"/>
    <h1></h1>
</div>
<div id="allCourses">
    <h1><spring:message code="global.allCourses"/></h1>
    <table id="grid">
        <thead>
        <tr>
            <th><spring:message code="global.courseName"/></th>
            <th><spring:message code="global.category"/></th>
            <th><spring:message code="global.professor"/></th>
            <th></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <td colspan="4" id="paginationCourses"></td>
        </tr>
        </tfoot>
        <tbody id="tbodyCourses"></tbody>
    </table>
    <div id="message"></div>
</div>

<div id="enrollDialog">
    <span class="close">&times;</span>
    <p><spring:message code="enrollment.confirmEnrollment" javaScriptEscape="false"/></p>
    <div class="options">
        <button id="enroll" data-courseCode=""><spring:message code="enrollment.button"/></button>
        <button class="cancel"><spring:message code="global.cancel"/></button>
    </div>
</div>
<div class="cover"></div>
</body>
</html>

