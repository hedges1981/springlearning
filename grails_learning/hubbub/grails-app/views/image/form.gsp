<html>
<head>
    <title>Upload Image</title>
    <meta name="layout" content="main">
</head>
<body>
<h1>Upload an image</h1>
<!--NOTE that this is an upload form, necessary for it to work with a file-upload -->
<g:uploadForm action="upload">
    User Id:
    <g:select name="loginId" from="${userList}" optionKey="loginId" optionValue="loginId" /> <!--NOTE how it is easy to do a select on the list in the model -->
    <p/>
    Photo: <input name="photo" type="file" />
    <g:submitButton name="upload" value="Upload"/>
</g:uploadForm>
</body>
</html>