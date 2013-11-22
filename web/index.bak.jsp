<%-- 
    Document   : index
    Created on : 4 oct. 2013, 15:40:36
    Author     : Arno
--%>

<%@page import="ejb.ProductEntity"%>
<%@page import="ejb.ProductEntityFacade"%>
<%@page import="javax.ejb.EJB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="date" class="java.util.Date" /> 

<jsp:useBean id="product" scope="session" class="ejb.ProductEntity" >
    <%!
       @EJB
    private ProductEntity product2 = new ProductEntity();
       
   %>
    <jsp:setProperty name="product" property="brand"
                     value="Test"/>
    <jsp:setProperty name="product" property="name" 
                     value="Tast"/>
    <jsp:setProperty name="product" property="price" 
                     value="10.5"/>
</jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evil Bunny</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/header.jspf" %>
        <h1>Hello Gitty World!</h1>
        <form>
            First name: <input type="text" name="firstname"><br>
            Last name: <input type="text" name="lastname">
        </form> 
        <p>The date/time is <%= date%></p>


       <p>brand: 
           <jsp:getProperty name="product" property="brand" />
        </p>
        <p>name: 
            <jsp:getProperty name="product" property="name"/>
        </p>
        <p>price: 
            <jsp:getProperty name="product" property="price"/>
        </p>
    </body>
</html>
