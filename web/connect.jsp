<%-- 
    Document   : connect
    Created on : 19 nov. 2013, 15:16:46
    Author     : Samy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Connection</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <form name="register" action="login" method="POST">
            <label for="username">username</label>
            <input id="username" type="text" name="username" value="" size="30" /><br/>
            <label for="pass">password</label>
            <input id="pass" type="password" name="pass" value="" size="30" />   <br/>     


            <input type="submit" value="Log in" />
        </form>
    </body>
</html>
