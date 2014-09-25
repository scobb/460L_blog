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
<title>Blog Posts</title>
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

	<%
		List<BlogPost> blogPosts = BlogDAO.INSTANCE.getBlogPosts();
		if (!blogPosts.isEmpty()) {
			for (BlogPost blogPost : blogPosts) {
				pageContext.setAttribute("post_body", blogPost.getBody().getValue());

				if (blogPost.getAuthor() == null) {
					// not good--shouldn't be able to post
				}

				else {
					pageContext.setAttribute("post_author",
							blogPost.getAuthor());
					pageContext.setAttribute("post_title", blogPost
							.getTitle().getName());
	%>
	<div class="post">
		<h3 class="emphasized">${fn:escapeXml(post_title)}</h3>
		<blockquote>
			<p class="postBody">${fn:escapeXml(post_body)}</p>
		</blockquote>
		<p style="text-align: right; font-size: 12pt;">
			-<i>${fn:escapeXml(post_author)}</i>
		</p>
	</div>
	<%
		}
			}

		} else {
	%>
	<h3>There are no posts.</h3>
	<%
		}
	%>
</body>
</html>