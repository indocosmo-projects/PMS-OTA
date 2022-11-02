pmsApp
		.controller(
				'facilitiesCtrl',
				[
						'$scope',
						'$http',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						'$mdDialog',
						function($scope, $http, DTOptionsBuilder,
								DTColumnBuilder, $mdDialog) {
							onloadValidator(".validator");
							OnloadValidationEdit('.validator');
							numericalValidation('#amount');
							$scope.dtInstance = {};
							$scope.codeCheck = {};
							var facilityTypes = window.facilityTypeEnums;
							$scope.dtColumns = [
									DTColumnBuilder
											.newColumn("code", "Code")
											.renderWith(
													function(data, type, full) {
														$scope.codeCheck[full.id] = full.code;
														return '<a style="color:#0000ff;">'
																+ data + '</a>';
													}),
									DTColumnBuilder.newColumn("name", "Name"),
									DTColumnBuilder.newColumn("facilityType",
											"Facility").renderWith(
											function(data, type, full) {
												return facility(data, full);
											}),
									DTColumnBuilder.newColumn("isPayable",
											"Payment").renderWith(
											function(data, type, full) {
												var p = "Payable";
												if (!data) {
													p = "Non Payable";
												}
												return p;
											}) ];
							$scope.dtOptions = DTOptionsBuilder
									.newOptions()
									.withOption(
											'ajax',
											{
												url : "../facilities/facilityDetails",
												type : "POST",
												error : function(response) {
													$mdDialog
															.show($mdDialog
																	.alert()
																	.clickOutsideToClose(
																			true)
																	.title(
																			'Operation failed.')
																	.textContent(
																			response.responseText)
																	.ok('Ok')
																	.parent(
																			angular
																					.element(document.body)));
												}
											}).withPaginationType(
											'full_numbers').withDisplayLength(
											10).withOption('rowCallback',
											rowCallback);

							function rowCallback(nRow, aData, iDisplayIndex,
									iDisplayIndexFull) {
								if (window.cp_isCanEdit) {
									$('td', nRow).unbind('click');
									$('td', nRow)
											.bind(
													'click',
													function() {
														$scope
																.$apply(function() {
																	$(
																			"#acc_check")
																			.css(
																					"display",
																					"none");
																	$(
																			'#acc_warning')
																			.css(
																					"display",
																					"none");
																	resetValidators('.validator');
																	$(
																			"#deleteFacilityBtn")
																			.show();
																	$scope.facility = {
																		id : aData.id,
																		code : aData.code,
																		name : aData.name,
																		facilityType : aData.facilityType
																				.toString(),
																		description : aData.description,
																		isPayable : aData.isPayable,
																		amount : aData.amount,
																		baction : "edit",
																		disbl : true,
																		accMstId : aData.accMstId
																	};
																	$(
																			"#newFacilitiesmyModal")
																			.modal(
																					{
																						backdrop : "static"
																					});
																})
													})
								}
							}

							$scope.clickOutSide = function() {
								if (!$(".md-select-menu-container").is(
										':visible')) {
									$(document)
											.on(
													'mouseup.dohide',
													function(e) {
														if ($(e.target)
																.closest(
																		'.md-select-menu-container').length === 0) {
															$(
																	".md-select-menu-container")
																	.hide();
															$(document).off(
																	'dohide');
														} else {
															$(
																	".md-select-menu-container")
																	.show();
														}
													});
								}
							}

							function facility(data, full) {
								var type = "";
								type = facilityTypes[data];
								return type;
							}

							$http({
								url : '../facilities/getAccountMasterDetails',
								method : "POST",
							})
									.then(
											function(response) {
												$scope.acclist = response.data;
											},
											function(response) {
												$mdDialog
														.show($mdDialog
																.alert()
																.clickOutsideToClose(
																		true)
																.title('Error')
																.textContent(
																		"Operation failed.")
																.ok('Ok')
																.parent(
																		angular
																				.element(document.body)));
											});

							$scope.newFacility = function() {
								$("#acc_check").css("display", "none");
								$('#acc_warning').css("display", "none");
								resetValidators('.validator');
								numericalValidation('#amount');
								$("#deleteFacilityBtn").hide();
								$("#saveFacilityBtn").show();
								$scope.facility = {
									id : 0,
									code : "",
									name : "",
									facilityType : "5",
									description : "",
									isPayable : true,
									amount : "",
									baction : "save",
									disbl : false,
									accMstId : 0
								};
								$("#newFacilitiesmyModal").modal({
									backdrop : "static"
								});
							}

							$scope.save = function() {
								if (validation('.validator') == "false") {
									return false;
								}

								$scope.bAction = $scope.facility.baction;
								if ($scope.bAction == 'save') {
									var data = $.param({
										facilityJson : JSON
												.stringify($scope.facility)
									});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post('../facilities/save', data,
													config)
											.success(
													function(data, status,
															headers, config) {
														if (data.substring(7) == 'success') {
															$scope.dtInstance
																	.reloadData();
															$(
																	"#newFacilitiesmyModal")
																	.modal(
																			'toggle');
														} else {
															alert("error"
																	+ data);
														}
													})
											.error(
													function(data, status,
															header, config) {
														$mdDialog
																.show($mdDialog
																		.alert()
																		.clickOutsideToClose(
																				true)
																		.title(
																				'Operation failed.')
																		.textContent(
																				data)
																		.ok(
																				'Ok')
																		.parent(
																				angular
																						.element(document.body)));
													});

								} else if ($scope.bAction == 'edit') {
									var data = $.param({
										facilityJson : JSON
												.stringify($scope.facility)
									});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post('../facilities/update', data,
													config)
											.success(
													function(data, status,
															headers, config) {
														if (data.substring(7) == 'success') {
															$scope.dtInstance
																	.reloadData();
															$(
																	"#newFacilitiesmyModal")
																	.modal(
																			'toggle');
														} else {
															alert("error!!");
														}
													})
											.error(
													function(data, status,
															header, config) {
														$mdDialog
																.show($mdDialog
																		.alert()
																		.clickOutsideToClose(
																				true)
																		.title(
																				'Operation failed.')
																		.textContent(
																				data)
																		.ok(
																				'Ok')
																		.parent(
																				angular
																						.element(document.body)));
													});
								}

							}

							function isNumber(evt) {
								evt = (evt) ? evt : window.event;
								var charCode = (evt.which) ? evt.which
										: evt.keyCode;
								if (charCode > 31
										&& (charCode < 48 || charCode > 57)) {
									return false;
								}
								return true;
							}

							function validateForm() {

								var valid = "true";
								if (valid == 'VALID')
									valid = checkGroupForNull('mail-form');

								if (valid == 'VALID')
									valid = checkEmail('txtMailId');
								return "true";
							}

							$scope.deleteFacility = function() {
								var confirm = $mdDialog
										.confirm()
										.title("Alert")
										.textContent(
												"Are you sure you want to delete this Facility?")
										.ok('Yes').cancel('No').parent(
												angular.element(document.body));
								$mdDialog
										.show(confirm)
										.then(
												function() {
													var data = $.param({
														id : $scope.facility.id
													});
													var config = {
														headers : {
															'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
															'X-Requested-With' : 'XMLHttpRequest'
														}
													}
													$http
															.post(
																	'../facilities/delete',
																	data,
																	config)
															.success(
																	function(
																			data,
																			status,
																			headers,
																			config) {
																		if (data
																				.substring(7) == 'success') {
																			$scope.dtInstance
																					.reloadData();
																			$(
																					"#newFacilitiesmyModal")
																					.modal(
																							'toggle');
																		} else {
																			alert("error");
																		}
																	})
															.error(
																	function(
																			data,
																			status,
																			header,
																			config) {
																		$mdDialog
																				.show($mdDialog
																						.alert()
																						.clickOutsideToClose(
																								true)
																						.title(
																								'Operation failed.')
																						.textContent(
																								data)
																						.ok(
																								'Ok')
																						.parent(
																								angular
																										.element(document.body)));
																	});
												});
							}

							$scope.numericalValidation = function($event) {
								if (isNaN(String.fromCharCode($event.keyCode))) {
									$event.preventDefault();
								}
							};

							$("#code")
									.bind(
											"focusout",
											function() {
												var code = $scope.facility.code
												var stat = "notExist";
												for ( var id in $scope.codeCheck) {
													if ($scope.codeCheck
															.hasOwnProperty(id)) {
														if ($scope.codeCheck[id] == code) {
															stat = "exists";
														}
													}
												}
												if (stat == 'exists') {
													$mdDialog
															.show($mdDialog
																	.alert()
																	.clickOutsideToClose(
																			true)
																	.title(
																			'Alert')
																	.textContent(
																			"Code exists")
																	.ok('Ok')
																	.parent(
																			angular
																					.element(document.body)));
													$scope.facility.code = '';
													$('#code_check').hide();
													$('#code_warning').show();
												} else {
													$('#code_check').show();
													$('#code_warning').hide();
												}
											});
							$scope.cancelPopUp = function() {
								$("#newFacilitiesmyModal").modal('toggle');
							}
						} ]);