<%-- 
    Document   : register
    Created on : 7 nov. 2013, 10:59:45
    Author     : Samy
--%>

<%@page import="java.util.Properties"%>
<%@page import="javax.rmi.PortableRemoteObject"%>
<%@page import="user.ClientImpl"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
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

    <body>
        <h1>Hello World!</h1>


        <form name="register" action="RegisterClient" method="POST">
            <label for="username">username</label>
            <input id="username" type="text" name="username" value="" size="30" /><br/>
            <label for="password">password</label> 
            <input id="password" type="password" name="password" value="" size="30" />   <br/>   
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



    </body>
</html>
