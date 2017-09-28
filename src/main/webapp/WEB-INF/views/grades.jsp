<%--
  Created by IntelliJ IDEA.
  User: Dragos
  Date: 14.08.2017
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java"
         pageEncoding="windows-1256"
%>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="cons" class="com.kepler.rominfo.utils.Constants"/>
<script>
    var noGrade = '${cons.NO_GRADE}';
</script>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="/css/table.css"/>
    <link rel="stylesheet" href="/css/grades.css"/>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/grades.js"></script>
</head>
<body>

<div id="warning">
    <img alt="" src="/resources/images/icon-warning-png-11.png"/>
    <h1></h1>
</div>
<div id="myCourses">
    <h1><spring:message code="global.myCourses"/></h1>
    <table id="coursesGrid">
        <thead>
        <tr>
            <th><spring:message code="global.courseName"/></th>
            <th><spring:message code="global.category"/></th>
            <th><spring:message code="grades.students"/></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <td colspan="3" id="paginationCourses"></td>
        </tr>
        </tfoot>
        <tbody id="myCoursesBody"></tbody>
    </table>
</div>

<div id="warning2">
    <h1></h1>
</div>

<div id="grades" data-courseCode="">
    <h3 id="courseTitle"></h3>
    <table id="gradesGrid">
        <thead>
        <tr>
            <th>#</th>
            <th><spring:message code="global.lastName"/></th>
            <th><spring:message code="global.firstName"/></th>
            <th><spring:message code="global.grade"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <td colspan="6" id="paginationGrades"></td>
        </tr>
        </tfoot>
        <tbody id="tbodyGrades"></tbody>
    </table>
</div>

<div id="removeDialog">
    <span class="close">&times;</span>
    <p><spring:message code="grades.confirmRemoveMark"/></p>
    <div class="options">
        <button id="remove" data-courseCode="" data-userId=""><spring:message code="global.delete"/></button>
        <button class="cancel"><spring:message code="global.cancel"/></button>
    </div>
</div>
<div class="cover"></div>
</body>
</html>
