<%@ page import="java.util.Date" %>
<%@ page import="servlets.Methods" %>

<!ECOTYPE html>
<html>
    <head>
        <title>FilePointer.jsp</title>
    </head>

    <body>
        <p> * Date: <%= new Date().toString() %>;</p>
        <p> * Path: <%= request.getParameter("path") %>; </p>

        <div>
            <%
                String[] strs = Methods.getFilePoints0(request.getParameter("path"),request);

                for (String str : strs) {
                    out.println(str);
                }
            %>
        </div>

        <button><a href="/relog">Выход</a></button>

    </body>
</html>