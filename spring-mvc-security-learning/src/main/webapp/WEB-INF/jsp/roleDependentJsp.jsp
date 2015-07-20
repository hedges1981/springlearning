<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>

<body>

<!--************** THIS PAGE: has a good lay out of what you can do with this tag:
http://howtodoinjava.com/2013/04/18/spring-security-at-view-layer-using-jsp-taglibs/ -->

<!--USE in the access=.... any expression that you would also put in the intercept-url tag in security-context.xml -->
<sec:authorize access="hasRole('ROLE_admin')">
    Content only visible to ROLE_admin
</sec:authorize>

<sec:authorize access="hasRole('ROLE_guest')">
    Content only visible to ROLE_guest
</sec:authorize>

<%--*********************NOTE************
 These expressions ifAnyGranted and ifAllGranted are in the spring 3 learning guide, but it looks like for spring 4, you need
 to use the hasRole etc expressions, i.e. the same ones you need to use in the intecept-url tags in the security-context.xml file
 -->

<sec:authorize access="ifAnyGranted('ROLE_guest')">
    Content only visible to ROLE_guest
</sec:authorize>

<sec:authorize access="ifAllGranted('ROLE_user,ROLE_admin')">
    Content only visible to some one with ROLE_user AND ROLE_admin
</sec:authorize>

--%>

</body>
</html>