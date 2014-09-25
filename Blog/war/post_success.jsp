<%@ page import="blog.entity.*"%>
<%@ page import="blog.dao.*"%>
<%@ page import="java.util.List"%>
<%@ page import="blog.entity.*"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>New Post</title>
<link rel="stylesheet" type="text/css" href="Blog.css" media="screen" />
</head>
<body id="background">
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	%>
	<div style="float: right;">
		<table class="darkBackground">
			<tr>
				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="blog.jsp">home</a></b></td>
				<%
					if (user != null) {
				%>
				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="new_post.jsp">new post</a></b></td>
				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="subscribe.jsp">subscribe</a></b></td>
				<%
					pageContext.setAttribute("user_name", user.getNickname());
				%>
				<td class="lesserWhiteText">${fn:escapeXml(user_name)} |<b><a
						style="color: #FFFFFF"
						href="<%=userService.createLogoutURL(request.getRequestURI())%>">
							sign out</a></b>
				</td>
				<%
					} else {
				%><td class="lesserWhiteText"><b><a style="color: #FFFFFF;"
						href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
							in</a></b></td>
				<%
					}
				%>
			</tr>
		</table>
	</div>
	<h1 class="titleWhiteText">Travel Blog</h1>

	<div class="darkBackground" style="padding-bottom: 20px;">
		<h1 class="headingWhiteText" style="color: #FFFFFF">Thank You</h1>
		<p class="lesserWhiteText" style="text-align: left; padding: 0px;">You
			have successfully posted to the Travel Blog. Have you <b><a style="color: #FFFFFF"
						href="subscribe.jsp">subscribed</a></b> for
			our daily emails?</p>
	</div>
</body>
</html>