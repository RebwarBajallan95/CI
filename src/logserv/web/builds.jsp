<%-- 
Get list from request.getAttribute("buildList") and assign it to list
--%>

<%@page import="bean.Build"%>
<%@page import="java.util.ArrayList"%>
<%
ArrayList<Build> list = (ArrayList<Build>) request.getAttribute("buildList");
%>


<html>
<head>
<link rel="shortcut icon" href="" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Build Factory KTH</title>

<link rel="icon" type="image/x-icon" href="favicon.ico">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<link
rel="stylesheet"
href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
/>

<%-- 
Get list items from the servlet and loop the array with its items and html elements
--%>

</head>
    <body>
    <header>
        <nav class="navbar navbar-expand-sm navbar-toggleable-sm navbar-light bg-white border-bottom box-shadow mb-3">
        <div  class="container">
        <ul class="navbar-nav flex-grow-1">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Build Factory KTH</a>
        </div>
        <li class="nav-item"><a class="nav-link text-dark" href="#">Home</a></li>
        </ul>

        </div>
        </nav>
    </header>
    <div class="container">
        
        <table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Status</th>
      <th scope="col">Log</th>
      <th scope="col">Time Created</th>
    </tr>
  </thead>
  <tbody>
      
    
    <% for(int j=0; j<list.size(); j++) { %>
        
      <tr>
        <th scope="row"><%=list.get(j).getIdentifier()%></th>
        <td><%=list.get(j).getStatus()%></td>
        <td><%=list.get(j).getBuildlog()%></td>
        <td><%=list.get(j).getTimecreated()%></td>
      </tr>
    <%}; %>
  </tbody>
</table>
        
        




    </div> 

    <footer class="border-top footer text-muted">
        <div class="container">
        &copy; 2021 - Build Factory KTH
        </div>
    </footer>
    </body>
</html>




