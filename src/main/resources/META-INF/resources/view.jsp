<%@ include file="init.jsp" %>
<%
    String keyword = com.liferay.portal.kernel.util.ParamUtil.getString(request, "keywords");
%>
<portlet:actionURL name="findByKeyword" var="findbykeywordURL"></portlet:actionURL>
<aui:form action="<%= findbykeywordURL %>" name="<portlet:namespace />fm">
    <div class="search-form">
        <span class="aui-search-bar">
            <aui:input inlineField="<%= true %>" label="" name="keyword" size="30" title="search-vacancies" type="text" />

            <aui:button type="submit" value="search" />
        </span>
    </div>
</aui:form>
<p>
    <jsp:useBean id="users" class="java.util.ArrayList" scope="request"/>
    <liferay-ui:search-container delta="5"
                                 emptyResultsMessage="No users are available for you"
                                 total="<%=PortletMVC.getUserModels().size()%>"
    >
        <liferay-ui:search-container-results
                results="<%=PortletMVC.getUserModels(searchContainer.getStart(), searchContainer.getEnd())%>"

        />
        <liferay-ui:search-container-row
                className="myapp.models.UserModel"
                modelVar="userModel" escapedModel="<%=true%>"
        >
            <portlet:renderURL var="Render">
                <portlet:param name="mvcRenderCommandName" value="/renderUserInfo/render"/>
                <portlet:param name="userId" value="<%=userModel.getId().toString()%>"/>
            </portlet:renderURL>
            <liferay-ui:search-container-column-text property="fullName" name="Users" href="<%=Render%>"/>
        </liferay-ui:search-container-row>
        <liferay-ui:search-iterator/>
    </liferay-ui:search-container>
</p>




