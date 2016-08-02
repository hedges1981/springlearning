<html>
<head>
    <title>Hubbub &raquo; <g:layoutTitle default="Welcome" /></title>
    <g:external dir="css" file="hubbub.css"/>
    <g:external dir="css" file="main.css"/>
    <g:layoutHead />
    <r:layoutResources />
    <nav:resources/>
</head>
<body>
<div>
    <h1>NOTE CUSTOM POST>GSP IN LAYOUTS/post.gsp, having it there with the name post makes it get picked as the layout for anything hitting the post controller</h1>
    <div id="hd">
        <g:link uri="/">
            <g:img id="logo" uri="/images/headerlogo.png" alt="hubbub logo"/>
        </g:link>
    </div>
    <div id="bd"><!-- start body -->
    <nav:render group="tabs"/>
    <g:layoutBody/>
    </div> <!-- end body -->
    <div id="ft">
        <div id="footerText">Hubbub - Social Networking on Grails(using post.gsp)</div>
    </div>
    Version <g:meta name="app.version"/>
    on Grails <g:meta name="app.grails.version"/>
</div>
</body>
</html>