<%@ include file="init.jsp" %>

<p>
    <c:out value="User ID: ${requestedUser.getId()}"/></p>
<p>
    <c:out value="User Full name: ${requestedUser.fullName}"/></p>
<p>
    <c:out value="User Email: ${requestedUser.eMail}"/></p>
<p>
    <c:out value="User Job Title: ${requestedUser.position}"/></p>
<p>
    <c:out value="User BirthDate: ${birthDate}"/></p>
<p>
    <c:out value="User Phones: "/></p><p>
<c:forEach var="phones" items="${requestedUser.phones}">
    <p><c:out value="${phones}"/></p>
</c:forEach>
<c:out value="User Organizations: "/></p><p>
<c:forEach var="orgs" items="${requestedUser.organizations}">
    <p><c:out value="${orgs}"/></p>
</c:forEach>
</p>
<br>

<portlet:renderURL var="Render">
    <portlet:param name="mvcRenderCommandName" value="/renderAllUsers/render"/>
</portlet:renderURL>
<aui:button cssClass="btn-lg" name="Back" href="<%=Render%>" value="back"/>