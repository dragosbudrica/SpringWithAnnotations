<%@ page isELIgnored="false" %>
<%@ page session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="role" value="${sessionScope.user.role.roleName}"/>
<c:if test="${not empty sessionScope.user}">
    <nav>
        <img class="logo" src="/resources/images/elearning.png"/>
        <ul id="menu">
            <c:if test="${role eq 'Admin'}">
                <li><a href="/newAccount"><spring:message code="navbar.newAccount" /></a></li>
                <li><a href="/courseScheduling"><spring:message code="navbar.scheduling" /></a></li>
                <li><a href="/validation"><spring:message code="navbar.validation" /></a></li>
            </c:if>

            <c:if test="${role eq 'Professor'}">
                <li><a href="/professorCourses"><spring:message code="global.myCourses" /></a></li>
                <li><a href="/newCourse"><spring:message code="navbar.newCourse" /></a></li>
                <li><a href="/timetable"><spring:message code="navbar.timetable" /></a></li>
                <li><a href="/grades"><spring:message code="navbar.grades" /></a></li>
            </c:if>

            <c:if test="${role eq 'Student'}">
                <li><a href="/allCourses"><spring:message code="global.allCourses" /></a></li>
                <li><a href="/studentCourses"><spring:message code="global.myCourses" /></a></li>
                <li><a href="/timetable"><spring:message code="navbar.timetable" /></a></li>
                <li><a href="/myGrades"><spring:message code="navbar.myGrades" /></a></li>
            </c:if>
        </ul>
    </nav>
</c:if>