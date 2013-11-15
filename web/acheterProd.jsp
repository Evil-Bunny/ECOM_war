<%-- 
    Document   : acheterProd
    Created on : 14 nov. 2013, 15:49:22
    Author     : Samy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>Hello World!</h1>
         <form name="register" action="testCart" method="POST">
            <label for="name">product name</label>
            <input id="name" type="text" name="name" value="" size="30" /><br/>
            <label for="surname">brand</label>
            <input id="surname" type="text" name="brand" value="" size="30" />   <br/>     
            <label for="addressnb">price</label>
            <input id="addressnb" type="text" name="price" value="" size="10" />  <br/>

            
<input type="submit" value="Submit" />
  </form>
    </body>
</html>
