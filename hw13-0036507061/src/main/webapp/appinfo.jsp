<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	String color=(String)session.getAttribute("pickedBgCol");if(color==null){color="white";}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=<%out.write(color);%>>
	<%
		int milisInDay=86_400_000;int milisInHour=3_600_000;int milisInMinute=60_000;int milisInSecond=1_000;

			ServletContext sc=request.getServletContext();long startTime=(Long)sc.getAttribute("time");long currentTime=System.currentTimeMillis();

			long diff=currentTime-startTime;int days=(int)(diff/milisInDay);int hours=(int)(diff/milisInHour);int minutes=(int)(diff/milisInMinute);int second=(int)(diff/milisInSecond);int milis=(int)(diff%milisInSecond);

			out.write(String.valueOf(days)+" days "+String.valueOf(hours)+" hours "+String.valueOf(minutes)+" minutes "+String.valueOf(second)+" seconds and "+String.valueOf(milis)+"miliseconds");
	%>
</body>
</html>