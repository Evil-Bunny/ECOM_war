<%-- 
    Document   : addProd
    Created on : 22 nov. 2013, 10:16:17
    Author     : msi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajout de produit</title>
    </head>
    <body>
        <h1>Ajout de produit</h1>
            <form name="addProduct" action="addProd" method="POST">
            <label for="name">Product name</label>
            <input id="name" type="text" name="name" value="" size="30" /><br/>
            <label for="brand">Brand</label>
            <input id="brand" type="text" name="brand" value="" size="30" />   <br/>     
            <label for="price">Price</label>
            <input id="price" type="text" name="price" value="" size="10" />  <br/>

            
<input type="submit" value="Submit" />
  </form>

    </body>
</html>
