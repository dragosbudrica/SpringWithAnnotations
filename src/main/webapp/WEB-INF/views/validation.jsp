<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dragos
  Date: 18.08.2017
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id="cons" class="com.kepler.rominfo.utils.Constants"/>
<jsp:useBean id="valid" class="com.kepler.rominfo.web.ValidationController"/>
<script>
    var noGrade = '${cons.NO_GRADE}';
    var validateAll = "<spring:message code="validation.message1"/>";
    var invalidateAll = "<spring:message code="validation.message2"/>";
    var nothingToValidate = "<spring:message code="validation.message3"/>";
    var nothingToInvalidate = "<spring:message code="validation.message4"/>";
</script>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="/css/table.css"/>
    <link rel="stylesheet" href="/css/validation.css"/>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/validation.js" charset="UTF-8"></script>
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
            <th><spring:message code="global.courseName" /></th>
            <th><spring:message code="global.category" /></th>
            <th><spring:message code="global.professor" /></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <td colspan="3" id="paginationCourses"></td>
        </tr>
        </tfoot>
        <tbody id="tbodyCourses"></tbody>
    </table>
    <div id="message"></div>
</div>

<div id="warning2">
    <h1></h1>
</div>

<div id="validation" data-courseCode="">
    <div id="aboveTable">
        <h3 id="courseTitle"></h3>
        <div id="validate">
            <button class="tooltip" type="button" id="validateBtn"><img src="/resources/images/rsz_1thumbs_up.png">
                <span class="tooltiptext" id="validateTooltip"></span>
            </button>
            <button class="tooltip" type="button" id="invalidateBtn"><img src="/resources/images/rsz_1thumbs_down.png">
                <span class="tooltiptext" id="invalidateTooltip"></span>
            </button>
        </div>
    </div>
    <table id="validationGrid">
        <thead>
        <tr>
            <th>
                <label for="options"><spring:message code="validation.options"/></label>
                <select id="options" name="options" onchange="Validation.executeAction()">
                    <option value="Choose An Option"><spring:message code="validation.chooseOption"/></option>
                    <option value="Select All"><spring:message code="validation.selectAll"/></option>
                    <option value="Deselect All"><spring:message code="validation.deselectAll"/></option>
                    <option value="Select Only Absents"><spring:message code="validation.selectOnlyAbsents"/></option>
                    <option value="Select Only Grades"><spring:message code="validation.selectOnlyGrades"/></option>
                    <option value="Select Validated Results"><spring:message code="validation.selectValidatedResults"/></option>
                    <option value="Select Invalidated Results"><spring:message code="validation.selectInvalidatedResults"/></option>
                </select>
            </th>
            <th><spring:message code="global.lastName"/></th>
            <th><spring:message code="global.firstName"/></th>
            <th><spring:message code="global.grade"/></th>
            <th><spring:message code="validation.validated"/></th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <td colspan="5" id="paginationValidation"></td>
        </tr>
        </tfoot>
        <tbody id="tbodyValidation"></tbody>
    </table>
</div>
</body>
</html>
