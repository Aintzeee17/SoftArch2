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

// ================= BACK URL =================
String backUrl = request.getContextPath() + "/course-list.jsp";

if ("lecturer".equalsIgnoreCase(role)) {
    backUrl = request.getContextPath() + "/lecturer-dashboard.jsp";
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Course</title>
    <link rel="stylesheet" href="style.css">
</head>

<body class="course-form-body">

<div class="navbar">
    <h2>Create Course</h2>
</div>

<div class="container">
    <div class="course-form-card">

        <form action="<%=request.getContextPath()%>/CourseServlet" method="post">

            <input type="hidden" name="action" value="create">

            <label>Course Title</label>
            <input type="text" name="title" required>

            <label>Description</label>
            <textarea name="description" rows="5" required></textarea>

            <label>Course Code</label>
            <input type="text" name="courseCode" placeholder="CSF3433" required>

            <div class="button-group">
                <button type="submit">Create Course</button>

                <a href="<%=backUrl%>" class="edit-course-back-link">
                    Back to List
                </a>
            </div>

        </form>

    </div>
</div>

</body>
</html>