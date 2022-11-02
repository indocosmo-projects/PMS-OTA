
pmsApp
		.controller(
				'depositController',
				function($scope, $http, $window, $mdDialog) {
					onloadValidator(".validator");
					OnloadValidationEdit('.validator');
					$scope.dateFormat = $("#dateFormat").val();
					modeValidation();
					$scope.pageName = "Deposit";
					$scope.deposit = {
						payment_mode : "1",
						amount : "",
						receivedForm : ""
					};
					// ng-init="somethingHere = options[0]"
					$scope.depositFrom = $window.depositFrom1;

					$('#txnmode').val(1);

					$scope.sortType = 'txn_date'; // set the default sort type
					$scope.sortReverse = false; // set the default sort order

					// numericalValidation()
					$scope.numericalValidation = function($event) {
						$("#depositAmount").on("keypress keyup", function() {
							if ($(this).val() == '0') {
								$(this).val('');
							}
						});
						if (isNaN(String.fromCharCode($event.keyCode))) {
							$event.preventDefault();

						}
					};

					// numericalValidation()
					$scope.timeFormat = function(txn_time) {
						return new Date(txn_time);
					}

					// showDeposit()
					$scope.showDeposit = function(folioBindNo, sortCol,
							sortStatus) {
						var folio = $("#show_deposit_folio").val();

						$http
								.get(
										"../deposit/paymentList?folioBindNo="
												+ folio
												+ "&sortCol=&sortStatus=")
								.success(function(response) {
									$scope.depositList = response;
								});

						// call bootstrap model
						$("#myModal").modal({
							backdrop : "static"
						});

					}

					$scope.showAlert = function(msg) {
						$mdDialog.show($mdDialog.alert().clickOutsideToClose(
								true).title('Alert').textContent(msg).ok('Ok')
								.parent(angular.element(document.body)));
					};

					// newDeposit()
					$scope.newDeposit = function(deposit) {
						if (validation('.validator') == "false") {
							return false;
						}
						if ($("#txnmode").val() != 1 && $("#remarks").show()
								&& $("#remarks").val() == "") {
							$("#remarks").focus();
						} else {
							var confirm = $mdDialog
									.confirm()
									.title("Alert")
									.textContent(
											"Are you sure you want to update this deposit?")
									.ok('Yes').cancel('No').parent(
											angular.element(document.body));
							$mdDialog
									.show(confirm)
									.then(
											function() {

												deposit.depositFrom = $scope.depositFrom;
												if (deposit.depositFrom == 1) {
													deposit.resvNo = $window.resvNoforDeposit;
													deposit.checkInNo = 0;
												} else {
													deposit.resvNo = 0;
													deposit.checkInNo = $window.checkInNoforDeposit;
												}

												if (deposit.payment_mode == 1) {
													deposit.remarks = "";
												}

												jsonObjDeposit = angular
														.toJson(deposit);

												$http
														.post(
																"../deposit/addDeposit",
																jsonObjDeposit)
														.success(
																function(
																		response) {
																	if (response == 1) {
																		var confirm = $mdDialog
																				.confirm()
																				.title(
																						"Alert")
																				.textContent(
																						"Deposit Added Successfully")
																				.ok(
																						'Ok')
																				.parent(
																						angular
																								.element(document.body));
																		$mdDialog
																				.show(
																						confirm)
																				.then(
																						function() {
																							$scope.deposit = {};
																							$window.location
																									.reload();
																						});
																	}

																});

											});
						}
					}
					$scope.cancelDeposit = function(deposit, reservId) {
						if (deposit == 1) {

							window.location = "../reservation_test/tools?reservationNo="
									+ reservId;

						} else {
							window.location = "../reception/receptionList";
						}

					}

					$scope.print = function(index) {
						jsonObjDeposit = angular
								.toJson($scope.depositList[index]);
						
						window.open("../deposit/print?deposit="
								+ JSON.stringify($scope.depositList[index]));
						$("#myModal").modal("toggle");
					}

				});

// modeValidation

function modeValidation() {
	var mode = $('#txnmode').val();
	if (mode == 1 || mode == "") {
		$('#rem_label').hide();
		$('#remarks').hide();
		$('#remarks').val("");
	} else {
		$('#rem_label').show();
		$('#remarks').show();
	}
}

// reservationList

function reservationList() {
	window.location = "../reservation/reservationList";
}

// depositList
function depositList(resvId, sortCol, sortStatus) {
	$
			.ajax({
				url : '../deposit/paymentList',
				data : {
					resvNo : resvId,
					sortCol : sortCol,
					sortStatus : sortStatus
				},
				success : function(response) {
					var trs = "";
					var count = 0;

					$
							.each(
									response,
									function(index, obj) {
										var dateTime = obj.txn_time.split(' ');
										var time;
										var date;
										if (obj.txn_time != '') {
											var tempDate = obj.txn_time
													.split(' ');
											time = tempDate[1].split(':');
											var timeSet = time[0] + ":"
													+ time[1];
											date = $.datepicker.formatDate(
													dateFormat, new Date(
															tempDate[0]));
										}

										trs += "<tr><td class='control-label pymnt-border-rit'>"
												+ date
												+ "</td><td class='control-label pymnt-border-rit'>"
												+ timeSet
												+ "</td><td class='control-label pymnt-border-rit amount-align'>"
												+ obj.amount
												+ "</td><td class='control-label pymnt-border-rit '>"
												+ obj.remarks
												+ "</td><td class='control-label pymnt-border-rit '>"
												+ obj.receivedForm
												+ "</td>"
												+ "</tr>";
										count++;
									});

					$('#tblTxnLst tbody').html(trs);
					$('#txnCount').html(count);

					$("#myModal").modal({
						backdrop : "static"
					});
				}
			});

}



