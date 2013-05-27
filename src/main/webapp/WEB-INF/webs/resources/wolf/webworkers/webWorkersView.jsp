<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>webWorkers test</title>

<script type='text/javascript' src='<c:url value="/resources/common/js/jquery/jquery-1.5.2.min.js" />'></script>
<script type="text/javascript" src='<c:url value="/resources/customer/js/edit_tbl.js"/>'></script>
<link type="text/css" rel="stylesheet" href='<c:url value="/resources/customer/css/style_tbl.css"/>'/>

    <script type="text/javascript">
         
     $(document).ready(function(){
       //找到所有的td节点
        var tds=$("td");
    //给所有的td节点添加双击事件
    tds.dblclick(tbl_dbclick);
});
     var worker = new Worker('<c:url value="/resources/customer/js/edit_tbl.js"/>');
     worker.onmessage = function(event) {
         console.log("Called back by the worker!\n");
     };      
     worker.addEventListener('message', function(event) {

             console.log("Called back by the worker!\n");
         }, false);

         worker.postMessage();// start the worker.
    </script>
</head>
<body>
<table>
      <tr>
          <td>双击这里</td>
      </tr>
      <tr>
          <td>双击这里</td>
      </tr>
      <tr>
          <td></td>
      </tr>
  </table>
</body>
</html>
