
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<!-- Search Fields -->
<div class="form-group">
	<label for="exampleInputEmail1">Code:</label> <input id="code"
		type="text" data-tag="Code"
		class="form-control form-control-inline input-medium searchCls"
		size="16" value="">
</div>
<div class="form-group">
	<label for="exampleInputEmail1">Meal PLan:</label> <input
		id="meal_plan" type="text" data-tag="Meal Plan"
		class="form-control searchCls" placeholder="">
</div>
<div class="form-group">
	<label for="exampleInputEmail1">Room Type:</label> <select
		class="form-control input-sm searchCls" data-tag="Room Type"
		id="room_type_id">
		<option value=""></option>
		<c:forEach var="roomType" items="${roomTypelist}">
			<option value="${roomType.key}">${roomType.value}</option>
		</c:forEach>
	</select>
</div>
