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
	<script type="text/javascript">
		function showMore() {
			toggleVisibility('moreButton');
			toggleVisibility('hiddenPosts');
		}

		function toggleVisibility(id) {
			var e = document.getElementById(id);
			if (e.style.display == 'block')
				e.style.display = 'none';
			else
				e.style.display = 'block';
		}
	</script>

	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	%>
	<div style="float: right;">
		<table class="darkBackground">
			<tr>
				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="blog.jsp">home</a></b></td>

				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="subscribe.jsp">subscribe</a></b></td>
				<%
					if (user != null)
					{
						String email = user.getEmail();
				%>
				<td class="lesserWhiteText"><b><a style="color: #FFFFFF"
						href="new_post.jsp">new post</a></b></td>
				<%
					pageContext.setAttribute("user_name", user.getNickname());
				%>
				<td class="lesserWhiteText">${fn:escapeXml(user_name)}|<b><a
						style="color: #FFFFFF"
						href="<%=userService.createLogoutURL(request.getRequestURI())%>">
							sign out</a></b>
				</td>
				<%
					} else
					{
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
		int last;
		if (blogPosts.size() > 5)
		{
			last = 5;
		} else
		{
			last = blogPosts.size();
		}
		if (!blogPosts.isEmpty())
		{

			for (int i = 0; i < last; i++)
			{
				pageContext.setAttribute("post_body", blogPosts.get(i).getBody()
						.getValue());

				if (blogPosts.get(i).getAuthor() == null)
				{
					// not good--shouldn't be able to post
				}

				else
				{
					pageContext.setAttribute("post_author", blogPosts.get(i)
							.getAuthor());
					pageContext.setAttribute("post_title", blogPosts.get(i)
							.getTitle().getName());
					pageContext.setAttribute("post_date", blogPosts.get(i)
							.getTimestamp().toString());
	%>
	<div class="post">
		<h3 class="emphasized">${fn:escapeXml(post_title)}</h3>
		<blockquote>
			<p class="postBody">${fn:escapeXml(post_body)}</p>
		</blockquote>
		<p style="text-align: right; font-size: 12pt;">
			-<i>${fn:escapeXml(post_author)}</i> (${fn:escapeXml(post_date)})
		</p>
	</div>
	<%
		}
			}
			if (last < blogPosts.size())
			{
	%>
	<div id="hiddenPosts" style="display: none">
		<%
			for (int i = last; i < blogPosts.size(); i++)
					{
						if (blogPosts.get(i).getAuthor() == null)
						{
							// not good--shouldn't be able to post
						}

						else
						{
							pageContext.setAttribute("post_body", blogPosts.get(i).getBody()
									.getValue());
							pageContext.setAttribute("post_author", blogPosts.get(i)
									.getAuthor());
							pageContext.setAttribute("post_title", blogPosts.get(i)
									.getTitle().getName());
							pageContext.setAttribute("post_date", blogPosts.get(i)
									.getTimestamp().toString());
		%>

		<div class="post">
			<h3 class="emphasized">${fn:escapeXml(post_title)}</h3>
			<blockquote>
				<p class="postBody">${fn:escapeXml(post_body)}</p>
			</blockquote>
			<p style="text-align: right; font-size: 12pt;">
				-<i>${fn:escapeXml(post_author)}</i> (${fn:escapeXml(post_date)})
			</p>
		</div>
		<%
			}
					}

				}
		%>
	</div>
	<%
		} else
		{
	%>
	<h3>There are no posts.</h3>
	<%
		}
	%>
	<%
		if (last != blogPosts.size())
		{
	%>
	<div id="moreButton"
		style="display: block; text-align: center; margin-bottom: 20px">
		<button onclick="showMore()" ; class="btn btn-default">View
			All</button>
	</div>
	<%
		}
	%>
	<div class="unsubFooter">
		<b><a style="color: #FFFFFF;" href="unsubscribe.jsp">unsubscribe</a></b>
	</div>
</body>
</html>