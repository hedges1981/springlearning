<html>
<head>
    <title>Random Quote</title>
    <g:javascript library="jquery"/>
    <r:layoutResources/>
</head>

<body>
<div id="quote">
    <q>${quote.content}</q>

    <p>${quote.author}</p>
</div>

<ul id="menu">
    <li>
        <!--NOTE: remote link is grails for an Ajax hyperLink, g:link is tag for a standard hyper link -->
        <!-- NOTE: couldn't do this example as in grails 3.x, remoteLink has been deprecated-->
        %{--<g:remoteLink action="ajaxRandom" update="quote">--}%
            %{--Next Quote--}%
        %{--</g:remoteLink>--}%
    </li>
    <li>
        <g:link action="index">
            Admin
        </g:link>
    </li>
</ul>

</body>
</html>