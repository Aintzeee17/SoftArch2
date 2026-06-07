<%@page import="java.util.*"%>
<%@page import="dao.CourseDAO"%>
<%@page import="model.Course"%>
<%@page import="model.User"%>

<%
User user = (User) session.getAttribute("user");
String role = (String) session.getAttribute("role");

// ================= AUTH CHECK =================
if (user == null || role == null ||
    (!"admin".equalsIgnoreCase(role) && !"lecturer".equalsIgnoreCase(role))) {

    response.sendRedirect(request.getContextPath() + "/AuthController?action=logout");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Course List</title>
    <link rel="stylesheet" href="style.css">
</head>

<body>

<div class="navbar">
    <h2>Manage Courses (Hi, <%= user.getName() %>!)</h2>

    <!-- FIX: ikut AuthController logout flow -->
    <a href="<%=request.getContextPath()%>/AuthController?action=logout" class="logout-btn">
        Logout
    </a>
</div>

<div class="container">
    <div class="card">

        <!-- Optional: boleh control role if nak -->
        <a href="<%=request.getContextPath()%>/course-form.jsp" class="add-btn">
            + Add New Course
        </a>

        <br><br>

        <table>
            <tr>
                <th>No</th> 
                <th>Title</th>
                <th>Code</th>
                <th>Action</th>
            </tr>

            <%
                CourseDAO dao = new CourseDAO();
                List<Course> list = dao.getAllCourses();
                int no = 1;

                if(list != null && !list.isEmpty()) {
                    for(Course c : list){
            %>

            <tr>
                <td><%= no++ %></td>
                <td><%= c.getTitle() %></td>
                <td><%= c.getCourseCode() %></td>

                <td>
                    <a href="<%=request.getContextPath()%>/edit-course.jsp?id=<%=c.getCourseId()%>" class="btn-edit">
                        Edit
                    </a>

                    <form action="<%=request.getContextPath()%>/CourseServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="courseId" value="<%=c.getCourseId()%>">

                        <button type="submit" class="btn-delete"
                            onclick="return confirm('Delete this course?')">
                            Delete
                        </button>
                    </form>
                </td>
            </tr>

            <%
                    }
                } else {
            %>

            <tr>
                <td colspan="4" style="text-align:center;">No courses found.</td>
            </tr>

            <%
                }
            %>
        </table>

    </div>
</div>

</body>
</html>