<%@page import="model.User"%>
<%@page import="dao.CourseDAO"%>
<%@page import="model.Course"%>

<%
    // 1. SEMAK SESSION
    User user = (User) session.getAttribute("user");

    if (user == null || (!"admin".equals(user.getRole()) && !"lecturer".equalsIgnoreCase(user.getRole()))) {
        out.clear();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    // 2. AMBIL ID DENGAN SELAMAT
    String idParam = request.getParameter("id");
    if (idParam == null || idParam.isEmpty()) {
        response.sendRedirect(request.getContextPath() + "/course-list.jsp");
        return;
    }

    int id = Integer.parseInt(idParam);
    CourseDAO dao = new CourseDAO();
    Course course = dao.getCourseById(id);

    // FIX: Semak jika course wujud dalam database
    if (course == null) {
        response.sendRedirect(request.getContextPath() + "/course-list.jsp?error=notfound");
        return;
    }

    // 3. URL DINAMIK
    String cancelUrl = request.getContextPath() + "/course-list.jsp";
    if ("lecturer".equalsIgnoreCase(user.getRole())) {
        cancelUrl = request.getContextPath() + "/lecturer-dashboard.jsp";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Course</title>
    <link rel="stylesheet" href="style.css">
</head>

<body class="edit-course-body">

<div class="navbar">
    <h2>Edit Course</h2>
</div>

<div class="container">
    <div class="edit-course-card">

        <form action="<%=request.getContextPath()%>/CourseServlet" method="post">

            <input type="hidden" name="action" value="update">
            <input type="hidden" name="courseId" value="<%=course.getCourseId()%>">

            <label>Title</label>
            <input type="text" name="title" value="<%=course.getTitle() != null ? course.getTitle() : ""%>" required>

            <label>Description</label>
            <textarea name="description" rows="5" required><%=course.getDescription() != null ? course.getDescription() : ""%></textarea>

            <label>Course Code</label>
            <input type="text" name="courseCode" value="<%=course.getCourseCode() != null ? course.getCourseCode() : ""%>" required>

            <div class="button-group">
                <button type="submit">Update Course</button>
                <a href="<%=cancelUrl%>" class="edit-course-back-link">Cancel</a>
            </div>
        </form>

    </div>
</div>

</body>
</html>