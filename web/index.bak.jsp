<%-- 
    Document   : index
    Created on : 4 oct. 2013, 15:40:36
    Author     : Arno
--%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Evil Bunny - <jsp:include page="test"><jsp:param name="param" value="title"/></jsp:include></title>
    </head>
    <body>
        <h1><jsp:include page="test"><jsp:param name="param" value="title"/></jsp:include></h1>
    <div>
        <jsp:include page="test"/>
    </div>
    </body>
</html>
