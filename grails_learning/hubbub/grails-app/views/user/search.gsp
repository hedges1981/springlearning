<html>
<head>
    <title>Search Hubbub</title>
    <meta name="layout" content="main"/>
</head>

<body>
<formset>
    <legend>Search for Friends</legend>
    <g:form action="results">      <!-- NOTE::: this ends up hitting the results method on the user controller.-->
        <label for="loginId">Login ID</label>
        <g:textField name="loginId" />
        <g:submitButton name="search" value="Search"/>
    </g:form>
</formset>
</body>
</html>