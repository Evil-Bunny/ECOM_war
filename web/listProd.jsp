<%-- 
    Document   : testjsp
    Created on : 21 nov. 2013, 14:36:55
    Author     : msi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Liste des produits!</h1>

            <jsp:include page="listProd" flush="true" />
      <%  
//            ProductEntityFacade pef = new ProductEntityFacade();
//            ProductEntity pe = pef.find(1);
//            out.println(pe.getBrand());
            

       // new InitialContext();
%>
    </body>
</html>
