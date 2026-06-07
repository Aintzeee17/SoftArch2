<%@page import="java.util.*"%>
<%@page import="model.User"%>
<%@page import="model.Course"%>
<%@page import="dao.CourseDAO"%>

<%
User user = (User) session.getAttribute("user");
String role = (String) session.getAttribute("role");

// ================= AUTH CHECK =================
if (user == null || role == null || !"student".equalsIgnoreCase(role)) {
    response.sendRedirect(request.getContextPath() + "/AuthController?action=logout");
    return;
}

CourseDAO dao = new CourseDAO();
List<Course> list = dao.getCoursesByStudent(user.getUserId());
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

<div class="navbar">
    <h2>Student Dashboard (Hi, <%= user.getName() %>!)</h2>

    <div class="nav-right">
        <!-- FIX: ikut AuthController -->
        <a href="<%=request.getContextPath()%>/AuthController?action=logout" class="logout-btn">
            Logout
        </a>
    </div>
</div>

<div class="container">

    <div class="card">
        <h2>Join Course</h2>

        <form action="<%=request.getContextPath()%>/JoinCourseServlet" method="post" autocomplete="off">

            <input type="hidden" name="studentId" value="<%= user.getUserId() %>">

            <label style="font-weight:bold;">Course Code</label>
            <input type="text" name="courseCode"
                   placeholder="Enter Course Code (e.g. BITM1113)"
                   required autocomplete="off">

            <button type="submit" class="join-btn">Join Course</button>
        </form>
    </div>

    <div class="card">
        <h2>My Enrolled Courses</h2>

        <%
        int no = 1;

        if (list != null && !list.isEmpty()) {
            for(Course c : list){
        %>

            <div class="course-card">
                <h3><%= no++ %>. <%= c.getTitle() %></h3>
                <p>Course Code: <%= c.getCourseCode() %></p>
            </div>

        <%
            }
        } else {
        %>

            <p style="color: #666;">You haven't joined any courses yet.</p>

        <%
        }
        %>

    </div>

</div>

</body>
</html>