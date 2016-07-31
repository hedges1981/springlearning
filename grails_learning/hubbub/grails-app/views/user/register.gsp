<html>
<head>
    <title>Register New User</title>
    <meta name="layout" content="main"/>
</head>
<body>
<h1>Register New User</h1>

<!---NOTE **** here how to show errors at the top -->
<g:hasErrors>
    <div class="errors">
        <g:renderErrors bean="${user}" as="list" />
    </div>
</g:hasErrors>
<g:if test="${flash.message}">
    <div class="flash">${flash.message}</div>
</g:if>

<!-- NOTE ****** NOTE: how this form binds its fields to the user object, and also the nested profile object -->

<g:form action="register">
    <fieldset class="form">
        <div class="fieldcontain required">
            <label for="loginId">Login ID</label>
            <g:textField name="loginId" value="${user?.loginId}"/>
        </div>
        <div class="fieldcontain required">
            <label for="password">Password</label>
            <g:passwordField name="password"/>
        </div>
        <div class="fieldcontain required">
            <label for="profile.fullName">Full Name</label>
            <g:textField name="profile.fullName"
                         value="${user?.profile?.fullName}"/>
        </div>
        <div class="fieldcontain required">
            <label for="profile.bio">Bio</label>
            <g:textArea name="profile.bio"
                        value="${user?.profile?.bio}"/>
        </div>
        <div class="fieldcontain required">
            <label for="profile.email">Email</label>
            <g:textField name="profile.email"
                         value="${user?.profile?.email}"/>
        </div>
    </fieldset>
    <fieldset class="buttons">
        <g:submitButton name="register" value="Register"/>
    </fieldset>
</g:form>
</body>
</html>