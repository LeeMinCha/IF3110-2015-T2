<%-- 
    Document   : index
    Created on : Nov 12, 2015, 12:00:30 PM
    Author     : User
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Simple StackExchange</title>
    </head>

    <body>
        <div class="link-normalizer"><a class='title' href="index.jsp">Simple StackExchange</a></div>

        <br><br><br><br>

        <form name='searchForm' action='search.php' method='post'>
            <input class='form-search' type="text" name="search_key" size='120%'>
            <button class='button-search' type='submit'> Search </button>
        </form>
        <%
        out.println("<div class='smalltitle-center'>Cannot find what you are looking for? "
                     + "<a id = 'color-orange' href='ask.jsp?token=" + 
                        request.getParameter("token") + "'>Ask here</a></div>");
        %>
        <br>
        <div class="smalltitle-center">Not registered yet? <a id = "color-orange" href="register.jsp" >Register here</a></div>
        <br>

        <div class="smalltitle-left"> Recently Asked Questions </div>
        <hr class='line'>
    <%
    try {
	
	 // TODO initialize WS operation arguments here
	int qid = 0;
	// TODO process result here
	
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>
   
        
    <%
    try {
	com.wbd.qst.QuestionWS_Service service = new com.wbd.qst.QuestionWS_Service();
	com.wbd.qst.QuestionWS port = service.getQuestionWSPort();
        com.wbd.ans.AnswerWS_Service service2 = new com.wbd.ans.AnswerWS_Service();
	com.wbd.ans.AnswerWS port2 = service2.getAnswerWSPort();
	// TODO process result here
	java.util.List<com.wbd.qst.Question> result = port.retrieveQ();        
	for(int i = 0; i < result.size() ; i++){
            String vote;
            java.util.List<com.wbd.ans.Answer> result2 = port2.getAnswerByQID(result.get(i).getIDQ());
            int count = result2.size();
            if(result.get(i).getVote() > 1 || result.get(i).getVote() < -1){
                vote = "Votes";
            }
            else{
                vote = "Vote";
            }
            String question =
                "<div class='block-question'>"
                    +"<div class='bquestion-vote'>" 
                        +result.get(i).getVote()
                        +"<br>"
	    		+vote
                    +"</div>"
                    +"<div class='bquestion-answer'>" 
		    	+ count
		    	+"<br>"
		    	+"answer"
		    +"</div>"
		    +"<div class='bquestion-content'>" 
                        +"<a id='color-black' href=question.jsp?id=" + result.get(i).getIDQ() + "&token=" + request.getParameter("token") + ">" + result.get(i).getQuestionTopic() + "</a>"
                        +"<br>"
                        +result.get(i).getContent()
                        +"<br><br>"
                    +"</div>"
                    +"<div class='bquestion-identity'>" 
                        +"asked by "
                        +"<a id='color-blue'>"
                            +"$row['name']"
                        +"</a>"
                        +" | "
                        +"<a id='color-orange' href=edit.jsp?id=" + result.get(i).getIDQ() +"&token=" + request.getParameter("token")
                            + ">edit"
                        +"</a>"
			+" | "
                        +"<a id='color-red' href=delete.jsp?id=" + result.get(i).getIDQ() +" onclick='deleteconfirm(" + "$row['question_id']" + ")' " + "'>"
                            +"delete"
			+"</a>"
                    +"</div>"
                +"</div>"
                +"<hr class='line'>"
                ;
            out.write(question);
        }
    } catch (Exception ex) {
	// TODO handle custom exceptions here
    }
    %>


    </body>
</html>