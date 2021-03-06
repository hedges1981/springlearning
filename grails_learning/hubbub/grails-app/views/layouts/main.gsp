<html>
<head>
    <title>Hubbub &raquo; <g:layoutTitle default="Welcome" /></title>
    <g:external dir="css" file="hubbub.css"/>
    <g:external dir="css" file="main.css"/>
    <r:layoutResources />
    <nav:resources/>
    <g:layoutHead />

</head>
<body>
<div>
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
        <div id="footerText">Hubbub - Social Networking on Grails (using main.gsp)</div>
    </div>
    <r:layoutResources/>
    Version <g:meta name="app.version"/>
    on Grails <g:meta name="app.grails.version"/>
</div>
</body>
</html>