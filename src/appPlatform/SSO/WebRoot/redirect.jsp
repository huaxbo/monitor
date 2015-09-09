<%@ page contentType="text/html; charset=UTF-8" %>
<%
 String referer = request.getParameter("referer") ;

 response.sendRedirect(referer) ;
%>