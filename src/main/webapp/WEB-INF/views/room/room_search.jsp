<jsp:directive.include file="../common/includes/page_directives.jsp" />

<!-- Search Fields -->
<div class="form-group">
	<label for="exampleInputEmail1">Number:</label> <input id="number"
		type="text" data-tag="Number"
		class="form-control form-control-inline input-medium searchCls"
		size="16" value="">
</div>
<div class="form-group">
	<label for="exampleInputEmail1">Name:</label> <input id="name"
		type="text" data-tag="Name" class="form-control searchCls"
		placeholder="">
</div>
<div class="form-group">
	<label for="exampleInputEmail1">Room Type:</label> <select
		class="form-control input-sm searchCls" data-tag="Room Type"
		id="room_type_id">
		<option value=""></option>
		<c:forEach var="roomType" items="${roomTypes}">
			<option value="${roomType.key}">${roomType.value}</option>
		</c:forEach>
	</select>
</div>
