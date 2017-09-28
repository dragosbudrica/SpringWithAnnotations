<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Dragos
  Date: 21.07.2017
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"
         contentType="text/html; charset=windows-1256"
         pageEncoding="windows-1256"
%>
<%@ page session="true" %>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.min.css' rel='stylesheet'/>
    <link href='https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.print.css' rel='stylesheet'
          media='print'/>
    <%-- <link href='https://code.jquery.com/ui/1.11.2/jquery-ui.min.js' rel='stylesheet'/>--%>
    <link rel="stylesheet" href="/css/courseScheduling.css"/>

    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <%--<script type="text/javascript" src="/fullcalendar-3.4.0/lib/jquery-ui.min.js"></script>--%>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/fullcalendar/3.4.0/fullcalendar.min.js"></script>
    <script type="text/javascript" src="/js/courseScheduling.js"></script>
</head>
<body>
<div id="eventContent">
    <div id="dialogHead">
        <h3 style="display: inline"><spring:message code="scheduling.courseDetails"/></h3>
        <span style="display: inline" class="close">&times;</span>
    </div>
    <div id="dialogBody">
        <label for="courseName"><spring:message code="global.courseName"/>:</label>&nbsp;
        <select id="courseName"></select> <br/><br/>
        <label for="startTime"><spring:message code="scheduling.from"/>:</label>&nbsp;&nbsp;&nbsp;
        <input type="datetime-local" id="startTime"/><br/><br/><br/>
        <button type="button" id="submit"><spring:message code="scheduling.save"/></button>
    </div>
</div>

<div id="confirmDialog">
    <span class="close">&times;</span>
    <p><spring:message code="scheduling.confirmSchedule"/></p>
    <div class="options">
        <button id="schedule" data-courseCode=""><spring:message code="scheduling.button"/></button>
        <button class="cancel"><spring:message code="global.cancel"/></button>
    </div>
</div>
<div class="cover"></div>
<div id="content">
    <div id="calendar"></div>
    <div id="message"></div>
</div>
</body>
</html>
