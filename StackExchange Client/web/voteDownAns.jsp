<%-- 
    Document   : voteDownAns
    Created on : Nov 23, 2015, 8:38:37 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- start web service invocation --%><hr/>
    <%
    try {
	com.wbd.ans.AnswerWS_Service service = new com.wbd.ans.AnswerWS_Service();
	com.wbd.ans.AnswerWS port = service.getAnswerWSPort();
	 // TODO initialize WS operation arguments here
	java.lang.String accessToken = request.getParameter("token");
	java.lang.String ansid = request.getParameter("ansid");
	// TODO process result here
	int result = port.voteDown(accessToken, ansid);
	out.println("Result = "+result);
        if (result == 1 || result == -5){
            String site = "question.jsp?id=" + request.getParameter("id") + "&token=" + accessToken;
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        } else if (result == -1){
            String site = "error.jsp?id=-1&token=" + accessToken;
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        } else if (result == -2){
            String site = "error.jsp?id=-2&token=" + accessToken;
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        } else if (result == -3){
            String site = "error.jsp?id=3&token=" + accessToken;
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
        }
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
    <%-- end web service invocation --%><hr/>


    </body>
</html>
