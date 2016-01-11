<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>

<html>
<head>
    <title></title>
</head>
<body>

      <!--NOTE: demo use of messages.properties, note that it picks up by default a file under resources/messages.properties,
      else u can define a bean of type ResourceBundleMessageSource -->

      Language: <a href="?language=en">English</a> <a href="?language=es">Espanol</a>
      <br/>



<spring:message code="demonstration.messages"/>
<!-- TODO: demo other spring:tags -->


      Below is the person session attribute, is put in there due to the @SessionAttributes in the GeneralController <br/>
      Note how it gets put in there when you hit submit, a fresh load of the page causes the default initial person ob to go in<br/>
      <c:out value="${sessionScope.person}" />

      <!--NOTE: demo of the form tag lib -->
      <!-- NOTE: this form uses modelAttribute, this means that we have to have an obj under the key person in the
      model when we show the page. NOTE: that model attribute and commandName are Exactly the same thing, -->
      <form:form method="POST" action="#" modelAttribute="person" >
          <table>
              <tr>
                  <td><form:label path="name">Name</form:label></td>
                  <td><form:input path="name" /></td>
              </tr>

                  <td colspan="2">
                      <input type="submit" value="Submit"/>
                  </td>
              </tr>
          </table>
      </form:form>

</body>
</html>