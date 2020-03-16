<jsp:useBean id="user" scope="session" class="obs.User"/>
<%@ page import="java.sql.*" %>

<h3>List Of Books </h3>

<table border=1 width=100%>
    <tr style="background-color:green;color:beige;font:700 10pt verdana">
        <th>&nbsp;

        <th>ISBN
        <th>Title

        <th>Author
        <th>Publisher
        <th>Cateogry

        <th>Price
    </tr>
    <%

        String cond = (String) request.getAttribute("cond");
        if (cond == null)
            cond = "1 = 1";

        Connection con = user.getConnection();
        Statement statement = con.createStatement();

        ResultSet results = statement.executeQuery("select isbn, title, author, pub, cat, price from books where " + cond);

        while (results.next()) {

    %>
    <tr>
        <td>
            <a href=addbook.jsp?isbn=<%=results.getString("isbn")%>>Add TO Cart </a>
        <td><%=results.getInt(1)%>
        <td><%=results.getString(2)%>
        <td><%=results.getString(3)%>
        <td><%=results.getString(4)%>

        <td><%=results.getString(5)%>
        <td><%=results.getInt(6)%>
    </tr>

    <%
        }
        results.close();
        statement.close();
        con.close();
    %>

</table>





  
  