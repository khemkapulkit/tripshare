<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="assets/css/bootstrap-united.css" rel="stylesheet" />

<style>
.error {
	color: #ff0000;
	font-size: 0.9em;
	font-weight: bold;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
.green {
	font-weight: bold;
	color: green;
}

.message {
	margin-bottom: 10px;
}
</style>
<title>Tripville - Manage Trip</title>
</head>
<body>
	<script src="jquery-1.8.3.js">
		
	</script>

	<script src="bootstrap/js/bootstrap.js">
		
	</script>

	<div class="navbar navbar-default">

		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-responsive-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>

		<div class="navbar-collapse collapse navbar-responsive-collapse">
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="Search">
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/tripville/home.html">Home</a></li>
				<li><a href="#">My profile</a></li>
				<li class="active"><a href="login.html">Logout</a></li>
			</ul>
		</div>
		<!-- /.nav-collapse -->
	</div>

	<div class="col-lg-6 col-lg-offset-3">
	<h3>Manage your trips as a Driver</h3>
	</div>
	
		
	<c:if test="${not empty message}">
		<div class="message green" font-size="175%">${message}</div>
	</c:if>
	
	<div class="col-lg-6 col-lg-offset-3" style="margin-left: 10%; width:75%">
		<div class="well">
			<div class="container">
				<div class="row">
					<div class="col-lg-6" style="width:75%">
						<form:form id="myForm" method="post"
							class="bs-example form-horizontal" commandName="trip">
							
							<c:if test="${not empty driverTripList}">
								<br>
								
								<table class="table table-striped">
								 <tr>
								    <th>From</th>
								    <th>Destination</th>
								    <th>Passenger</th>
								    <!-- <th> AvailableSeats</th> -->
								    <th>Start date</th>
								    <th>Rent</th>
								  </tr>
								
								
								<c:forEach var="trip" items="${driverTripList}">
								<tr>
									<td><a href="http://localhost:8080/tripville/manageTrip.html?tripId=${trip.getTripId()}"> ${trip.fromAddress}</a>
									</td>
									<td>${trip.toAddress} </td>
									<td>${trip.numofcopassengers}</td>
									<!-- <td>${trip.availableSeats}</td> -->
									<td><fmt:formatDate value="${trip.startDate}" pattern="MM/dd/yyyy"/></td>
									<td>${trip.rent}</td>
								</tr>
								</c:forEach>
								
								</table>

						</c:if>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="col-lg-6 col-lg-offset-3">
	<h3>Manage your trips as a Passenger</h3>
	</div>
	
		
	<c:if test="${not empty message}">
		<div class="message green" font-size="175%">${message}</div>
	</c:if>
	
	<div class="col-lg-6 col-lg-offset-3" style="margin-left: 10%; width:75%">
		<div class="well">
			<div class="container">
				<div class="row">
					<div class="col-lg-6" style="width:75%">
						<form:form id="myForm" method="post"
							class="bs-example form-horizontal" commandName="trip">
							
							<c:if test="${not empty passengerTripList}">
								<br>
								<br>
								
								<table class="table table-striped">
								 <tr>
								    <th>From</th>
								    <th>Destination</th>
								    <th>Trip date</th>
								    <th>Trip owner</th>
								    <th>Status</th>
								    <th>Rating</th>
								  </tr>
								
								
								<c:forEach var="tripReq" items="${passengerTripList}">
								
								<tr>
									<td>${tripReq.trip.fromAddress}</td>
									
									<td>${tripReq.trip.toAddress} </td>
									<td><fmt:formatDate value="${tripReq.trip.startDate}" pattern="MM/dd/yyyy"/></td>
									<td>${tripReq.status}</td>
									
									<td>${tripReq.trip.user.getLastName()}, ${tripReq.trip.user.getFirstName()} </td>
									
									<p>
    									<jsp:useBean id="today" class="java.util.Date" />
									</p>
									<td>
									<c:if test="${tripReq.status == 'Approved' && tripReq.trip.startDate < today}">
										<a href="http://localhost:8080/tripville/updateDriverHistory.html?tripId=${tripReq.trip.getTripId()}">Rate this trip</a>
									</c:if>
									</td>
									
									
									
									
								</tr>
								</c:forEach>
								
								</table>

						</c:if>
						
						<div class="col-lg-9 col-lg-offset-3">
									<input class="btn btn-primary" type="submit" name="btnClk" value="Go Back" style="margin-left:25%">
						</div>
							
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	

</body>
</html>