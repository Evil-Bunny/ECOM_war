<%-- 
    Document   : TestManufacturerList
    Created on : 21 nov. 2013, 14:28:37
    Author     : bousky
--%>

<%@page import="java.util.List"%>
<%@page import="ejb.AbstractFacade"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="product.Manufacturer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <ul id="manufacturers">
            <jsp:include page="ManufacturerList"/>
        </ul>
    </body>
</html>
