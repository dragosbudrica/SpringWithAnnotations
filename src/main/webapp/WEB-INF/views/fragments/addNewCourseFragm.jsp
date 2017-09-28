<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Dragos
  Date: 07.08.2017
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="module form-module">
    <div class="toggle">
    </div>
    <div class="form">
        <h2><spring:message code="newCourse.message"/></h2>
        <form id="newCourse">
            <!--Course Name-->
            <div class="input_with_error">
                <label for="newCourse_courseName"><spring:message code="global.courseName"/></label>
                <input type="text" id="newCourse_courseName" name="courseName"/>
                <span class="error"><spring:message code="global.required"/></span>
            </div>

            <!--Category-->
            <label for="categoryAddCourse"><spring:message code="global.category"/></label>
            <select id="categoryAddCourse" name="categoryAddCourse"></select>

            <!--Number of lectures-->
            <label for="numberOfLectures"><spring:message code="newCourse.numberOfLectures"/></label>
            <input type="number" value="3" min="3" max="12" id="numberOfLectures" name="numberOfLectures"/>
            <span class="error"><spring:message code="global.required"/></span>

            <!--Description-->
            <div class="input_with_error">
                <label for="newCourse_description"><spring:message code="newCourse.description"/></label>
                <textarea id="newCourse_description" rows="5" cols="57" name="description" maxlength="255"
                          style="resize: none" onkeyup="Utils.countChar(this)"></textarea>
                <span class="error"><spring:message code="global.required"/></span>
                <div id="charNum"></div>
            </div>

            <button type="button" id="submit"><spring:message code="newCourse.add"/></button>
        </form>
    </div>
    <div id="message"></div>
</div>