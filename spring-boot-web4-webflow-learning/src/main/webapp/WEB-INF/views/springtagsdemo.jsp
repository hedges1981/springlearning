<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>

      <!--demo use of messages.properties, note that it picks up by default a file under resources/messages.properties,
      else u can define a bean of type ResourceBundleMessageSource -->

      Language: <a href="?language=en">English</a> <a href="?language=es">Espanol</a>
      <br/>
<spring:message code="demonstration.messages"/>
<!-- TODO: demo other spring:tags -->

</body>
</html>