<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
 </head>

<body>
<%
    String guestbookName = request.getParameter("guestbookName");
    if (guestbookName == null) {
        guestbookName = "default";
    }
    pageContext.setAttribute("guestbookName", guestbookName);

%>

<%
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
    // Run an ancestor query to ensure we see the most up-to-date
    // view of the Greetings belonging to the selected Guestbook.
    Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
    List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(5));
    if (greetings.isEmpty()) {
%>
<p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
<%
} else {
%>
<p>Status in CometPark '${fn:escapeXml(guestbookName)}'.</p>
<%
    for (Entity greeting : greetings) {
        pageContext.setAttribute("greeting_content",
                greeting.getProperty("content"));
        if (greeting.getProperty("user") == null) {
%>
<p>Parking Spot 1 status change to:</p>
<%
} else {
    pageContext.setAttribute("greeting_user",
            greeting.getProperty("user"));
%>
<p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
<%
    }
%>
<blockquote>${fn:escapeXml(greeting_content)}</blockquote>
<%
        }
    }
%>

<form action="/guestbook.jsp" method="get">
    <div><input type="text" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/></div>
    <div><input type="submit" value="Switch Guestbook"/></div>
</form>
 

</body>
</html>
