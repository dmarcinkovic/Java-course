<%@ page import="java.util.List,java.util.ArrayList" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null) {
		color = "white";
	}
%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor=<%out.write(color);%>>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>The Beach Boys</td>
				<td>
					<%
						String id1 = (String) session.getAttribute("The Beach Boys");
						int score1 = (int) session.getAttribute(id1);
						out.write(String.valueOf(score1));
					%>
				</td>
			</tr>
			<tr>
				<td>The Beatles</td>
				<td>
					<%
						String id2 = (String) session.getAttribute("The Beatles");
						int score2 = (int) session.getAttribute(id2);
						out.write(String.valueOf(score2));
					%>
				</td>
			</tr>
			<tr>
				<td>The Platters</td>
				<td>
					<%
						String id3 = (String) session.getAttribute("The Platters");
						int score3 = (int) session.getAttribute(id3);
						out.write(String.valueOf(score3));
					%>
				</td>
			</tr>
			<tr>
				<td>The Marcels</td>
				<td>
					<%
						String id4 = (String) session.getAttribute("The Marcels");
						int score4 = (int) session.getAttribute(id4);
						out.write(String.valueOf(score4));
					%>
				</td>
			</tr>
			<tr>
				<td>The Mamas And The Papas</td>
				<td>
					<%
						String id5 = (String) session.getAttribute("The Mamas And The Papas");
						int score5 = (int) session.getAttribute(id5);
						out.write(String.valueOf(score5));
					%>
				</td>
			</tr>
			<tr>
				<td>The Everly Brothers</td>
				<td>
					<%
						String id6 = (String) session.getAttribute("The Everly Brothers");
						int score6 = (int) session.getAttribute(id6);
						out.write(String.valueOf(score6));
					%>
				</td>
			</tr>
			<tr>
				<td>The Four Seasons</td>
				<td>
					<%
						String id7 = (String) session.getAttribute("The Four Seasons");
						int score7 = (int) session.getAttribute(id7);
						out.write(String.valueOf(score7));
					%>
				</td>
			</tr>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<%
		int maxScore = 0;
		int numberOfBands = (int) session.getAttribute("numberOfBands");

		List<String> bestLinks = new ArrayList<>();
		List<String> bestNames = new ArrayList<>();
		for (int i = 1; i <= numberOfBands; i++) {
			int score = (int) session.getAttribute(String.valueOf(i));
			String bandName = (String) session.getAttribute("id" + String.valueOf(i));
			String link = (String) session.getAttribute("link" + String.valueOf(i));

			if (score > maxScore) {
				bestLinks.clear();
				bestNames.clear();
				maxScore = score;
			}

			if (score == maxScore) {
				bestLinks.add(link);
				bestNames.add(bandName);
			}
		}
	%>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<%
			for (int i = 0; i < bestNames.size(); i++) {
				String name = bestNames.get(i);
				String l = "\"" + bestLinks.get(i) + "\"";
		%><li><a href=<%=l%> target="_blank"><%=name%></a></li>
		<%
			}
		%>
	</ul>
</body>
</html>
