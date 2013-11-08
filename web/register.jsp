<%-- 
    Document   : register
    Created on : 7 nov. 2013, 10:59:45
    Author     : Samy
--%>

<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.ejb.EJB"%>
<%@page import="ejb.ClientImplFacade"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html xmlns:h="http://xmlns.jcp.org/jsf/html" xmlns:f="http://xmlns.jcp.org/jsf/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register to Evil Bunny</title>
    </head>
    <jsp:useBean id="client" scope="session" class="user.ClientImpl" />
    <jsp:useBean id="address" scope="session" class="user.data.AddressImpl" />
    <jsp:useBean id="clients" scope="session" class="ejb.ClientImplFacade" />

    <body>
        <h1>Hello World!</h1>
        

                <%
   Enumeration paramNames = request.getParameterNames();
   if (!paramNames.hasMoreElements()){
  %>     
        <form name="register" action="test" method="POST">
            <label for="name">first name</label>
            <input id="name" type="text" name="name" value="" size="30" /><br/>
            <label for="surname">surname</label>
            <input id="surname" type="text" name="surname" value="" size="30" />   <br/>     
            <label for="addressnb">address number</label>
            <input id="addressnb" type="text" name="addressnb" value="" size="10" />  <br/>
            <label for="address">address</label>
            <input id="address" type="text" name="address" value="" size="100" />  <br/>  
            
<input type="submit" value="Submit" />
  </form>
  <%                 
   }
   else{
       
   %>
   <jsp:setProperty name="client" property="firstname" value="<%= request.getParameter("name")%>" />
   <jsp:setProperty name="client" property="surname" value="<%= request.getParameter("surname")%>" />
   <jsp:setProperty name="address" property="number" value="<%= Integer.parseInt(request.getParameter("addressnb"))%>" />
   <jsp:setProperty name="address" property="name" value="<%= request.getParameter("address")%>" />
   <jsp:setProperty name="client" property="address" value="${address}" /> 
   
   
  <%     
        //clients.create(client);
            
   }       
   %>
   
  </body>
</html>
