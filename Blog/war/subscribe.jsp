<%@ page import="javax.servlet.http.Cookie"%>
<html>
<%
	Cookie[] cookies = request.getCookies();
	boolean notSubscribed = false;
	if (cookies != null)
	{
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals("notSubscribed"))
			{
				notSubscribed = true;
				break;
			}
		}
	}
	if (notSubscribed)
	{
%>
<body>
	<!--  error saving email -->
	.
	<%
		}
	%>

	<!-- normal form display -->
</body>

</html>