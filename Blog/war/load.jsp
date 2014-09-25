<%@page import="blog.util.TestBlogPostLoad,blog.util.TestSubscriber"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Blogzilla</title>
</head>

<body>
	<h1>Initial Data Loader</h1>

	<%-- <%=TestBlogPostLoad.deleteAllPosts() %> --%>
	<br>
	<%=TestBlogPostLoad.addPosts()%>
	<hr>


	<%-- <%=TestBlogPostLoad.getBlogPosts()%> --%>
	<br>
	<%-- <%=TestBlogPostLoad.getBlogPost()%> --%>
	<%=TestBlogPostLoad.getNewPosts() %>
 	<%=TestSubscriber.addSubscriber("Steve Cobb",
					"scobb1@utexas.edu")%>
	<br>
	<a href="blog.jsp">Back to Home</a>
</body>
</html>