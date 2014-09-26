<%@ page import="javax.servlet.http.Cookie"%>
<html>
<%
	Cookie[] cookies = request.getCookies();
	boolean badEmail = false;
	if (cookies != null)
	{
		for (Cookie cookie : cookies)
		{
			if (cookie.getName().equals("badEmail"))
			{
				badEmail = true;
				break;
			}
		}
	}
	if (badEmail)
	{
%>
<body>
	<!--  bad e-mail message -->
	.
	<%
		}
	%>

	<!-- normal form display -->
</body>

</html>