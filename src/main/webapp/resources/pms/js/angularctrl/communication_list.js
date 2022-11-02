pmsApp
		.controller(
				'communicationCtrl',
				[
						'$scope',
						'$q',
						'$http',
						'$mdDialog',
						'$window',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						function($scope, $q, $http, $mdDialog, $window,
								DTOptionsBuilder, DTColumnBuilder, $resource) {
							var comtn = this;
							var hotelDate = $("#hotelDate").val();
							$scope.showcheckout = false;
							$scope.showresvn = true;
							$scope.showinhouse = false;

							comtn.dtInstance1 = {};
							comtn.dtInstance = {};
							$scope.resvtnDataTable = false;
							$scope.recptnDataTable = false;
							$scope.recptnData = [];
							$scope.resvnData = [];
							comtn.getReceptionData = getReceptionData;
							comtn.getReservationData = getReservationData;

							$scope.comList = {
								group : "1",
								resvtn : "cutoff",
								checkOut : "on",
								resvArrvlOn : new Date(hotelDate),
								resvCutOff : "",
								checkOutOn : new Date(hotelDate),
								checkOutBefore1 : new Date(new Date(hotelDate)
										.getFullYear(), new Date(hotelDate)
										.getMonth(), new Date(hotelDate)
										.getDate() - 1),
								checkOutBefore2 : new Date(hotelDate)
							}
							$scope.commtnData = {};

							$scope.showCriterias = function() {
								if ($scope.comList.group == "2") {
									$scope.showcheckout = false;
									$scope.showresvn = false;
									$scope.showinhouse = true;
								} else if ($scope.comList.group == "3") {
									$scope.showcheckout = true;
									$scope.showresvn = false;
									$scope.showinhouse = false;
								} else {
									$scope.showcheckout = false;
									$scope.showresvn = true;
									$scope.showinhouse = false;
								}
								$scope.resvtnDataTable = false;
								$scope.recptnDataTable = false;
							}

							$scope.showCriteriasemail = function() {
								if ($scope.comList.group == "2") {
									$scope.showcheckout = false;
									$scope.showresvn = false;
									$scope.showinhouse = true;
								} else if ($scope.comList.group == "3") {
									$scope.showcheckout = true;
									$scope.showresvn = false;
									$scope.showinhouse = false;
								} else {
									$scope.showcheckout = false;
									$scope.showresvn = true;
									$scope.showinhouse = false;
								}
								$scope.resvtnDataTable = false;
								$scope.recptnDataTable = false;

							}

							$scope.maxDate = new Date(hotelDate);
							$scope.minDate = new Date(hotelDate);

							$scope.loadTable = function() {
								if (validate()) {
									if ($scope.comList.group == "2") {
										$scope.commtnData = {
											group : parseInt($scope.comList.group)
										};
									} else if ($scope.comList.group == "3") {
										if ($scope.comList.checkOut == "on") {
											$scope.commtnData = {
												group : parseInt($scope.comList.group),
												checkOut : $scope.comList.checkOut,
												checkOutOn : new Date(
														$scope.comList.checkOutOn)
														.getTime()
											};
										} else {
											$scope.commtnData = {
												group : parseInt($scope.comList.group),
												checkOut : $scope.comList.checkOut,
												checkOutBefore1 : new Date(
														$scope.comList.checkOutBefore1)
														.getTime(),
												checkOutBefore2 : new Date(
														$scope.comList.checkOutBefore2)
														.getTime()
											};
										}
									} else {
										if ($scope.comList.resvtn == "cutoff") {
											$scope.commtnData = {
												group : parseInt($scope.comList.group),
												resvtn : $scope.comList.resvtn,
												cutoffBefore : parseInt($scope.comList.resvCutOff)
											};
										} else {
											$scope.commtnData = {
												group : parseInt($scope.comList.group),
												resvtn : $scope.comList.resvtn,
												resvArrvlOn : new Date(
														$scope.comList.resvArrvlOn)
														.getTime()
											};
										}
									}

									var data = $.param({
										communication : JSON
												.stringify($scope.commtnData)
									});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post('../communication/getData',
													data, config)
											.success(
													function(data, status,
															headers, config) {
														$scope.recptnData = [];
														if ($scope.comList.group == "2"
																|| $scope.comList.group == "3") {
															$scope.recptnData = data;
															comtn.dtInstance1
																	.reloadData();
															$scope.recptnDataTable = true;
															$scope.resvtnDataTable = false;
														} else {
															$scope.resvnData = data;
															comtn.dtInstance
																	.reloadData();
															$scope.resvtnDataTable = true;
															$scope.recptnDataTable = false;
														}
													})
											.error(
													function(data, status,
															header, config) {
														$scope.recptnData = [];
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
							function validate() {
								var stat = true;
								if ($scope.comList.group == "1") {
									if ($scope.comList.resvtn == "cutoff") {
										if ($scope.comList.resvCutOff == ""
												|| $scope.comList.resvCutOff == null) {
											$mdDialog
													.show($mdDialog
															.alert()
															.clickOutsideToClose(
																	true)
															.textContent(
																	"Please enter days")
															.ok('Ok')
															.parent(
																	angular
																			.element(document.body)));
											stat = false;
										}
									}
								} else if ($scope.comList.group == "3") {
									if ($scope.comList.checkOut == "btwn") {
										if (Date
												.parse($scope.comList.checkOutBefore1) >= Date
												.parse($scope.comList.checkOutBefore2)) {
											$mdDialog
													.show($mdDialog
															.alert()
															.clickOutsideToClose(
																	true)
															.textContent(
																	"Start date should be less than end date")
															.ok('Ok')
															.parent(
																	angular
																			.element(document.body)));
											stat = false;
										}
									}
								}
								return stat;
							}
							comtn.dtOptions1 = DTOptionsBuilder.fromFnPromise(
									getReceptionData).withPaginationType(
									'full_numbers').withOption('rowCallback',
									rowCallback1);
							comtn.dtColumns1 = [
									DTColumnBuilder
											.newColumn("checkin_no",
													"CheckIn No")
											.withOption('width', '30%')
											.renderWith(
													function(data, type, full) {
														return '<a style="color:#0000ff;">'
																+ data + '</a>';
													}),
									DTColumnBuilder.newColumn("name", "Name"),
									DTColumnBuilder.newColumn("phone", "phone"),
									DTColumnBuilder.newColumn("room_type_code",
											"Room Type"),
									DTColumnBuilder.newColumn("room_number",
											"Room"),
									DTColumnBuilder
											.newColumn(null, "Depart Date")
											.renderWith(
													function(data, type, full) {
														if ($scope.comList.group == "3") {
															return full.act_depart_date;
														} else if ($scope.comList.group == "2") {
															return full.exp_depart_date
														}
													}) ];
							function rowCallback1(nRow, aData, iDisplayIndex,
									iDisplayIndexFull) {
								$('td', nRow).unbind('click');
								$('td', nRow)
										.bind(
												'click',
												function() {
													if (window.cp_isCanEdit) {
														$scope
																.$apply(function() {
																	var data = $
																			.param({
																				group : 'checkIn',
																				id : aData.checkin_no
																			});
																	var config = {
																		headers : {
																			'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
																			'X-Requested-With' : 'XMLHttpRequest'
																		}
																	}
																	$http
																			.post(
																					'../communication/getCommunicationDetail',
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
																							window.location.href = "../communication/getCommunicationDetailPage";
																						} else {
																							alert("error!!");
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
												})
								return nRow;
							}

							function getReceptionData() {
								var defer = $q.defer();
								defer.resolve($scope.recptnData);
								return defer.promise;
							}

							comtn.dtOptions = DTOptionsBuilder.fromFnPromise(
									getReservationData).withPaginationType(
									'full_numbers').withOption('rowCallback',
									rowCallback);
							comtn.dtColumns = [
									DTColumnBuilder
											.newColumn("resv_no", "Resv No")
											.withOption('width', '70px')
											.renderWith(
													function(data, type, full) {
														return '<a style="color:#0000ff;">'
																+ data + '</a>';
													}),
									DTColumnBuilder.newColumn("reserved_by",
											"Reserved By"),
									DTColumnBuilder.newColumn("resv_by_phone",
											"Phone"),
									DTColumnBuilder.newColumn("arr_date",
											"Arrival Date"),
									DTColumnBuilder.newColumn("cut_off_date",
											"Cut Off Date"),
									DTColumnBuilder.newColumn("num_nights",
											"Nights"),
									DTColumnBuilder.newColumn("resv_status",
											"Status") ];
							function rowCallback(nRow, aData, iDisplayIndex,
									iDisplayIndexFull) {
								$('td', nRow).unbind('click');
								$('td', nRow)
										.bind(
												'click',
												function() {
													if (window.cp_isCanEdit) {
														$scope
																.$apply(function() {

																	var data = $
																			.param({
																				group : 'resvn',
																				id : aData.resv_no
																			});
																	var config = {
																		headers : {
																			'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
																			'X-Requested-With' : 'XMLHttpRequest'
																		}
																	}
																	$http
																			.post(
																					'../communication/getCommunicationDetail',
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
																							window.location.href = "../communication/getCommunicationDetailPage";
																						} else {
																							alert("error!!");
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
												})
								return nRow;
							}

							function getReservationData() {
								var defer = $q.defer();
								defer.resolve($scope.resvnData);
								return defer.promise;
							}

							$scope.cancel = function() {
								window.location = "../dashboard";
							}

						} ]);