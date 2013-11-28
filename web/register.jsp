<%-- 
    Document   : register
    Created on : 7 nov. 2013, 10:59:45
    Author     : Samy
--%>

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
            <label for="address">address</label>
            <input id="address" type="text" name="address" value="" size="100" />  <br/>  


            <input type="submit" value="Submit" />
        </form>



    </body>
</html>
