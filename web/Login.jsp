<!DOCTYPE html>
<html>
<head>
    <title>Login - SMARTLEARN</title>
    <link rel="stylesheet" href="style.css">

    <script>
        function toggleLoginRole() {
            var roleSelect = document.getElementById("loginRole");
            var matricField = document.getElementById("matricField");
            var matricInput = document.getElementById("matricNo");

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

<body class="login-body" onload="toggleLoginRole()">

<div class="box">

    <h2>SMARTLEARN LOGIN</h2>

    <!-- FIX: guna AuthController + action -->
    <form action="${pageContext.request.contextPath}/AuthController" method="post" autocomplete="off">

        <input type="hidden" name="action" value="login">

        <select name="role" id="loginRole" onchange="toggleLoginRole()" required>
            <option value="" disabled selected>-- Select Role --</option>
            <option value="student">Student</option>
            <option value="lecturer">Lecturer</option>
            <option value="admin">Admin</option>
        </select>

        <div id="matricField" style="display:none;">
            <input type="text" name="matricNo" id="matricNo"
                   placeholder="Matric Number" autocomplete="off">
        </div>

        <input type="email" name="email" placeholder="Email" required autocomplete="off">

        <input type="password" name="password" placeholder="Password" required autocomplete="new-password">

        <button type="submit">Login</button>

    </form>

    <div class="link">
        No account? <a href="Register.jsp">Register here</a>
    </div>

</div>

</body>
</html>