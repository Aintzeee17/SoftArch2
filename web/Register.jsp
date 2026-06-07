<!DOCTYPE html>
<html>
<head>
    <title>Register - SMARTLEARN</title>
    <link rel="stylesheet" href="style.css">

    <script>
        function toggleRegisterRole() {
            var roleSelect = document.getElementById("regRole");
            var matricField = document.getElementById("regMatricField");
            var matricInput = document.getElementById("regMatricNo");

            if (roleSelect.value === "student") {
                matricField.style.display = "block";
                matricInput.required = true;
            } else {
                matricField.style.display = "none";
                matricInput.required = false;
                matricInput.value = "";
            }
        }
    </script>
</head>

<body class="register-body" onload="toggleRegisterRole()">

<div class="box">

    <h2>CREATE ACCOUNT</h2>

    <!-- FIX: guna AuthController -->
    <form action="${pageContext.request.contextPath}/AuthController" method="post" autocomplete="off">

        <input type="hidden" name="action" value="register">

        <select name="role" id="regRole" onchange="toggleRegisterRole()" required>
            <option value="" disabled selected>-- Register As --</option>
            <option value="student">Student</option>
            <option value="lecturer">Lecturer</option>
            <option value="admin">Admin</option>
        </select>

        <div id="regMatricField" style="display:none;">
            <input type="text" name="matricNo" id="regMatricNo"
                   placeholder="Matric Number">
        </div>

        <input type="text" name="name" placeholder="Full Name" required autocomplete="off">

        <input type="email" name="email" placeholder="Email" required autocomplete="off">

        <input type="password" name="password" placeholder="Password" required autocomplete="new-password">

        <button type="submit">Register</button>

    </form>

    <div class="link">
        Already have account?
        <a href="${pageContext.request.contextPath}/Login.jsp">Login</a>
    </div>

</div>

</body>
</html>