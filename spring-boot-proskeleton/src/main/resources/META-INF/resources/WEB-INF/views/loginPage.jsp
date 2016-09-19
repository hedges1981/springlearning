<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: rhall
  Date: 11/08/2016
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta name='layout' content='main'/>
    <title>Login</title>
    <style type='text/css' media='screen'>
        #login {
            margin: 15px 0px;
            padding: 0px;
            text-align: center;
        }

        #login .inner {
            width: 340px;
            padding-bottom: 6px;
            margin: 60px auto;
            text-align: left;
            border: 1px solid #aab;
            background-color: #f0f0fa;
            -moz-box-shadow: 2px 2px 2px #eee;
            -webkit-box-shadow: 2px 2px 2px #eee;
            -khtml-box-shadow: 2px 2px 2px #eee;
            box-shadow: 2px 2px 2px #eee;
        }

        #login .inner .fheader {
            padding: 18px 26px 14px 26px;
            background-color: #f7f7ff;
            margin: 0px 0 14px 0;
            color: #2e3741;
            font-size: 18px;
            font-weight: bold;
        }

        #login .inner .cssform p {
            clear: left;
            margin: 0;
            padding: 4px 0 3px 0;
            padding-left: 105px;
            margin-bottom: 20px;
            height: 1%;
        }

        #login .inner .cssform input[type='text'] {
            width: 120px;
        }

        #login .inner .cssform label {
            font-weight: bold;
            float: left;
            text-align: right;
            margin-left: -105px;
            width: 110px;
            padding-top: 3px;
            padding-right: 10px;
        }

        #login #remember_me_holder {
            padding-left: 120px;
        }

        #login #submit {
            margin-left: 15px;
        }

        #login #remember_me_holder label {
            float: none;
            margin-left: 0;
            text-align: left;
            width: 200px
        }

        #login .inner .login_message {
            padding: 6px 25px 20px 25px;
            color: #c33;
        }

        #login .inner .text_ {
            width: 120px;
        }

        #login .inner .chk {
            height: 12px;
        }
        #disclaimer{
            width:80%;
            margin:0 auto;
        }
        #disclaimer p strong{
            font-size:11px;

        }
        #disclaimer p{
            font-size:9px;
        }

        .forgot {
            text-align: center;
        }

    </style>
</head>

<body>
<div id='login'>
    <div class='inner'>
        <c:if test="${not empty message}">
            <div class="msg">${message}</div>
        </c:if>
        <div class='fheader'>Please Login</div>



        <form action='/doLogin' method='POST' id='loginForm' class='cssform' autocomplete='off'>
            <p>
                <label for='username'>Username:</label>
                <input type='text' class='text_' name='username' id='username'/>
            </p>

            <p>
                <label for='password'>Password:</label>
                <input type='password' class='text_' name='password' id='password'/>
            </p>

            <p id="remember_me_holder">
            <input type='checkbox' class='chk' name='remember-me' id='remember-me'/>
            <label for='remember-me'>Remember me</label>
            </p>

            <p>
                <input type='submit' id="submit" value="Login"/>
            </p>

            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>

        </form>
    </div>
</div>

<div id="disclaimer">
    <p><strong>Copyright &copy; Satellite Information Services Limited 2014.</strong><p>
    The copyright of this web interface and all material contained herein (including without limitation the computer code, graphics, images, layout and any content accessed via this interface) is owned by Satellite Information Services Limited (&ldquo;sis&rdquo;) and its licensors.
    Only a licensed user of the SIS Race Cards Service (&ldquo;The Service&rdquo;) has the permission to use this interface for the sole purpose of accessing the service under licence terms issued by SIS. If you are not a licensee of the service you do not have the permission to use this interface or access the service and if you continue to do so SIS may bring proceedings against you seeking damages for breach of its proprietary rights. If you are a licensee you must comply with security and other usage protocol issued by SIS. No distribution or sharing of the access is permitted save in accordance with the terms issued by SIS or a written authorisation by SIS.
</div>

<script type='text/javascript'>
    <!--
    (function () {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>

