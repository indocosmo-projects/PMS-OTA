pmsApp.requires.push('ui.toggle');
pmsApp
		.controller(
				'checkOutController',
				[
						'$scope',
						'$http',
						'$mdDialog',
						'$window',
						'$filter',
						function($scope, $http, $mdDialog, $window, $filter) {
							$scope.dateFormat = $("#dateFormat").val();
							$scope.billingAddress = {
								salutation : "Mr.",
								firstName : "",
								lastName : "",
								guestName : "",
								gender : "",
								address : "",
								email : "",
								phone : "",
								nationality : "",
								state : "",
								gstno : ""
							};
							$scope.salutations = [ "Mr.", "Mrs.", "Ms.",
									"M/s.", "Dr.", "C/o." ];
							$scope.checkOutDetails = null;
							$('#imgloader').show();
							$scope.showBillAdrs = false;
							var data = $.param({
								folioBindNo : parseInt($('#hdnFolioBindNo')
										.val())
							});
							var config = {
								headers : {
									'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
									'X-Requested-With' : 'XMLHttpRequest'
								}
							}
							$http
									.post('../checkOut/getCheckOutDetails',
											data, config)
									.success(
											function(data, status, headers,
													config) {
												$scope.checkOutDetails = data;
												$('#imgloader').hide();
												$scope.expDepartDate = new Date(
														$scope.checkOutDetails[0].expDepartDate);
											})
									.error(
											function(data, status, header,
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
																.ok('Ok')
																.parent(
																		angular
																				.element(document.body)));
											});
							$scope.selected = [];
											
							
							$scope.toggleSelection = function toggleSelection(
									item) {
								var idx = $scope.selected
										.indexOf(item.encryCheckinNo);

								if (idx > -1) {
									$scope.selected.splice(idx, 1);

									$scope.billingAddress.salutation = "";
									$scope.billingAddress.firstName = "";
									$scope.billingAddress.lastName = "";
									$scope.billingAddress.guestName = "";
									$scope.billingAddress.email = "";
									$scope.billingAddress.phone = "";
									$scope.billingAddress.address = "";
									$scope.billingAddress.nationality = "";
									$scope.billingAddress.state = "";
									$scope.billingAddress.gstno = "";
									$scope.showBillAdrs = false;
								} else {
									$scope.showBillAdrs = true;
									$scope.selected.push(item.encryCheckinNo);
									$http(
											{
												url : "../checkOut/addressWithCheckinNo",
												method : "POST",
												params : {
													checkinNo : item.encryCheckinNo
												}
											})
											.then(
													function(response) {
														$scope.billingAddress.salutation = response.data.salutation;
														$scope.billingAddress.firstName = response.data.first_name;
														$scope.billingAddress.lastName = response.data.last_name;
														$scope.billingAddress.guestName = response.data.guest_name;
														$scope.billingAddress.email = response.data.email;
														$scope.billingAddress.phone = response.data.phone;
														$scope.billingAddress.address = response.data.address;
														$scope.billingAddress.nationality = response.data.nationality;
														$scope
																.loadState($scope.billingAddress.nationality);
														$scope.billingAddress.state = response.data.state;
														$scope.billingAddress.gstno = response.data.gstno;
													});
								}
							};
							$scope.checkoutCheck = function(chkitem) {
								$scope["payment" + chkitem.checkinNo] = {
									pay : true,
									transfer : true,
									post : true
								};
								$scope.status = 0;
								if (!chkitem.rcPostStatus) {
									$scope.status = 2;
									$scope["payment" + chkitem.checkinNo].post = false;
								} else if (chkitem.folioBalance != 0.0
										&& ("folioBalance" in chkitem)) {
									$scope.status = 1;
									$scope["payment" + chkitem.checkinNo].pay = false;
									$scope["payment" + chkitem.checkinNo].transfer = false;
								}
								if (chkitem.status != 5) {
									$scope["payment" + chkitem.checkinNo] = {
										pay : true,
										transfer : true,
										post : true
									};
								}

								return $scope.status;
							}

							/*
							 * $scope.updateCheckIn = function(){
							 * 
							 * $scope.checkOuts=[]; if($scope.confirmCheck){
							 * if($scope.selected.length>0){ $scope.btn_Disable =
							 * true; for(x in $scope.selected){ for(y in
							 * $scope.checkOutDetails){
							 * $scope.checkOutDetails[y].expDepartDate=$filter('date')(new
							 * Date($scope.checkOutDetails[y].expDepartDate),
							 * "yyyy-MM-dd"); var idx =
							 * $scope.checkOutDetails[y].encryCheckinNo.indexOf($scope.selected[x]);
							 * if (idx == 0) {
							 * $scope.checkOuts.push($scope.checkOutDetails[y]); } } }
							 * $scope.checkoutIds=[]; var data =
							 * $.param({checkOutDtls :
							 * angular.toJson($scope.checkOuts)}); var config =
							 * {headers:{'Content-Type':
							 * 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
							 * $http.post('../checkOut/updateCheckIn', data,
							 * config) .success(function (data, status, headers,
							 * config) { var msg=data.substring(0).split("_");
							 * for(i=1;i<=msg.length-1;i++){
							 * $scope.checkoutIds.push(msg[i]); } $http({
							 * url:'../checkOut/mailCheckOut', method:"POST",
							 * data:{data:$scope.checkoutIds}
							 * 
							 * }).then(function(response){ var
							 * confirm=$mdDialog.confirm()
							 * .title("send").ok('OK').parent(angular.element(document.body));
							 *  }) if(msg[0]=="success"){ var confirm =
							 * $mdDialog.alert() .title("Checkout done
							 * successfully").ok('OK').parent(angular.element(document.body));
							 * $mdDialog.show(confirm).then(function(){
							 * window.location='../reception/receptionList'; });
							 * }else{$mdDialog.show($mdDialog.alert()
							 * .clickOutsideToClose(true).title('Error!!').textContent("Some
							 * error occured please try again
							 * later!").ok('Ok!').parent(angular.element(document.body)));}
							 * }).error(function (data, status, header, config){
							 * $mdDialog.show($mdDialog.alert()
							 * .clickOutsideToClose(true).title('Some Error
							 * Occured
							 * !!').textContent(data).ok('Ok!').parent(angular.element(document.body)));
							 * }); }else{ $mdDialog.show($mdDialog.alert()
							 * .clickOutsideToClose(true).title('Alert!!').textContent("Add
							 * Atleast One Room To Check Out
							 * List").ok('Ok!').parent(angular.element(document.body))); }
							 * }else{ $mdDialog.show($mdDialog.alert()
							 * .clickOutsideToClose(true).title('Alert!!').textContent("Please
							 * confirm before proceeding
							 * !").ok('Ok!').parent(angular.element(document.body))); } }
							 */
							$scope.countryList = [];
							$http({
								url : '../reservation_test/getCountries',
								method : 'POST'
							}).then(function(response) {
								$scope.countryList = response.data;
							});

							$scope.loadState = function(nationality) {
								$scope.billingAddress.state = ""
								$scope.id = "0";
								for (var j = 0; j < $scope.countryList.length; j++) {
									if (nationality === $scope.countryList[j].name) {
										$scope.id = $scope.countryList[j].id;
										break;
									}
								}
								var data = $.param({
									data : JSON.stringify($scope.id)
								});
								var config = {
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
										'X-Requested-With' : 'XMLHttpRequest'
									}
								}
								$http
										.post('../common/getState', data,
												config)
										.success(
												function(data, status, headers,
														config) {
													$scope.stateList = data;
												})
										.error(
												function(data, status, header,
														config) {
													$mdDialog
															.show($mdDialog
																	.alert()
																	.clickOutsideToClose(
																			true)
																	.title(
																			'Error')
																	.textContent(
																			"Operation failed.")
																	.ok('Ok')
																	.parent(
																			angular
																					.element(document.body)));
												});

							}

							$scope.simpleSearchByMail = function() {
								if ($scope.billingAddress.email != undefined
										&& $scope.billingAddress.email != "") {
									var data = $.param({
										data : $scope.billingAddress.email
									});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post('../checkIn/loadDataByMail',
													data, config)
											.success(
													function(data, status,
															headers, config) {

														if (data.salutation != null) {
															$scope.billingAddress.salutation = data.salutation;
														}

														if (!data.last_name) {
															$scope.billingAddress.lastName = "";
														} else {
															$scope.billingAddress.lastName = data.last_name;
														}

														if (!data.guest_name) {
															$scope.billingAddress.guestName = "";
														} else {
															$scope.billingAddress.guestName = data.guest_name;
														}

														if (!data.gstno) {
															$scope.billingAddress.gstno = "";
														} else {
															$scope.billingAddress.gstno = data.gstno;
														}
														$scope.billingAddress.firstName = data.first_name;
														$scope.billingAddress.phone = data.phone;
														$scope.billingAddress.address = data.address;
														$scope.billingAddress.nationality = data.nationality;
														$scope
																.loadState($scope.billingAddress.nationality);
														$scope.billingAddress.state = data.state;

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
																				'Error')
																		.textContent(
																				"Operation failed.")
																		.ok(
																				'Ok')
																		.parent(
																				angular
																						.element(document.body)));
													});
								}

							}

							$scope.simpleSearchReception = function() {

								$scope.phone = $scope.billingAddress.phone;
								$http({
									url : '../reception/getDetailViaPhonenum',
									method : "POST",
									data : $scope.phone

								})
										.then(
												function(response) {
													// $scope.roomListData[0].lastName=response.data[response.data.length-1].last_name;
													if (response.data[0].salutation !== null) {
														$scope.billingAddress.salutation = response.data[0].salutation;
													} else {
														$scope.billingAddress.salutation = "Mr.";
													}
													if (response.data[0].last_name !== null) {
														$scope.billingAddress.lastName = response.data[0].last_name;
													} else {
														$scope.billingAddress.lastName = "";
													}
													if (response.data[0].guest_name !== null) {
														$scope.billingAddress.guestName = response.data[0].guest_name;
													} else {
														$scope.billingAddress.guestName = "";
													}

													$scope.billingAddress.firstName = response.data[0].first_name
													if (response.data[0].email !== null) {
														$scope.billingAddress.email = response.data[0].email;
													} else {
														$scope.billingAddress.email = "";
													}
													$scope.billingAddress.address = response.data[0].address;
													if (response.data[0].gstno !== null) {
														$scope.billingAddress.gstno = response.data[0].gstno;
													} else {
														$scope.billingAddress.gstno = "";
													}
													$scope.billingAddress.nationality = response.data[0].nationality;
													$scope
															.loadState(
																	$index,
																	$scope.billingAddress.nationality);
													$scope.billingAddress.state = response.data[0].state;

												},
												function(response) {
													$mdDialog
															.show($mdDialog
																	.alert()
																	.clickOutsideToClose(
																			true)
																	.title(
																			'Error')
																	.textContent(
																			"Operation failed.")
																	.ok('Ok')
																	.parent(
																			angular
																					.element(document.body)));
												});
							}

							$scope.loadCustomerData = function() {
								var data = $.param({
									data : $scope.billingAddress.firstName
								});
								var config = {
									headers : {
										'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
										'X-Requested-With' : 'XMLHttpRequest'
									}
								}
								$http
										.post('../checkIn/loadData', data,
												config)
										.success(
												function(data, status, headers,
														config) {
													$scope.customerList = data;
													if ($scope.customerList.length > 1) {
														$('#myModal').modal(
																'show');
													} else if ($scope.customerList.length == 1) {
														$scope
																.copyData(
																		$scope.customerList[0],
																		index)
													} else {
														$scope.billingAddress.lastName = "";
														$scope.billingAddress.guestName = "";
														$scope.billingAddress.email = "";
														$scope.billingAddress.gstno = "";
														$scope.billingAddress.phone = "";
														$scope.billingAddress.address = "";
														$scope.billingAddress.nationality = "";
														$scope.billingAddress.state = "";
													}
												})
										.error(
												function(data, status, header,
														config) {
													$mdDialog
															.show($mdDialog
																	.alert()
																	.clickOutsideToClose(
																			true)
																	.title(
																			'Error')
																	.textContent(
																			"Operation failed.")
																	.ok('Ok')
																	.parent(
																			angular
																					.element(document.body)));
												});

							}

							$scope.copyData = function(data) {

								if (data.salutation != null) {
									$scope.billingAddress.salutation = data.salutation;
								}
								if (data.last_name !== null) {
									$scope.billingAddress.lastName = data.last_name;
								} else {
									$scope.billingAddress.lastName = "";
								}
								if (data.guest_name !== null) {
									$scope.billingAddress.guestName = data.guest_name;
								} else {
									$scope.billingAddress.guestName = "";
								}
								if (data.mail !== null) {
									$scope.billingAddress.email = data.mail;
								} else {
									$scope.billingAddress.email = "";
								}
								if (data.passport_no !== null) {
									$scope.billingAddress.passportNo = data.passport_no;
								} else {
									$scope.billingAddress.passportNo = "";
								}
								if (data.passport_expiry_on !== null) {
									$scope.billingAddress.passportExpiryOn = new Date(
											data.passport_expiry_on);
								} else {
									$scope.billingAddress.passportExpiryOn = "";
								}
								if (data.gstno !== null) {
									$scope.billingAddress.gstno = data.gstno;
								} else {
									$scope.billingAddress.gstno = "";
								}
								$scope.billingAddress.firstName = data.first_name;
								$scope.billingAddress.gender = data.gender
								$scope.billingAddress.phone = data.phone;
								$scope.billingAddress.address = data.address;
								$scope.billingAddress.nationality = data.nationality;
								$scope
										.loadState($scope.billingAddress.nationality);
								$scope.billingAddress.state = data.state;

								$('#myModal').modal('hide');
							}

							$scope.updateCheckIn = function() {

								$scope.checkOuts = [ {
									billingAddress : {},
									checkOutsDtls : []
								} ];
								if ($scope.confirmCheck) {
									if ($scope.selected.length > 0) {
										$scope.btn_Disable = true;
										$scope.checkOutDetails.forEach(function(v)
										{
											delete v.isDiscount 
											});
										for (x in $scope.selected) {
											for (y in $scope.checkOutDetails) {
												$scope.checkOutDetails[y].expDepartDate = $filter(
														'date')
														(
																new Date(
																		$scope.checkOutDetails[y].expDepartDate),
																"yyyy-MM-dd");
												
												var idx = $scope.checkOutDetails[y].encryCheckinNo
														.indexOf($scope.selected[x]);
												if (idx == 0) {
													$scope.checkOuts[0].checkOutsDtls
															.push($scope.checkOutDetails[y]);
												}
											}
										}
										$scope.checkOuts[0].billingAddress = $scope.billingAddress;
										$scope.checkoutIds = [];
										console
												.log($scope.checkOuts[0].checkOutsDtls);

										$http(
												{
													url : "../checkOut/updateCheckIn",
													method : "POST",
													params : {
														checkOutDtls : angular
																.toJson($scope.checkOuts)
													}
												})
												.then(
														function(data) {
															if (data.data == "Financial Year not added") {
																$mdDialog
																		.show($mdDialog
																				.alert()
																				.clickOutsideToClose(
																						true)
																				.title(
																						'Alert')
																				.textContent(
																						"Add financial year")
																				.ok(
																						'Ok')
																				.parent(
																						angular
																								.element(document.body)));
															} else {
																var msg = data.data
																		.substring(
																				0)
																		.split(
																				"_");
																for (i = 1; i <= msg.length - 1; i++) {
																	$scope.checkoutIds
																			.push(msg[i]);
																}
																$http(
																		{
																			url : '../checkOut/mailCheckOut',
																			method : "POST",
																			data : {
																				data : $scope.checkoutIds
																			}

																		})
																		.then(
																				function(
																						response) {
																					var confirm = $mdDialog
																							.confirm()
																							.title(
																									"send")
																							.ok(
																									'OK')
																							.parent(
																									angular
																											.element(document.body));

																				})
																if (msg[0] == "success") {
																	var confirm = $mdDialog
																			.alert()
																			.title(
																					"Checkout done successfully")
																			.ok(
																					'OK')
																			.parent(
																					angular
																							.element(document.body));
																	$mdDialog
																			.show(
																					confirm)
																			.then(
																					function() {
																						window.location = '../reception/receptionList';
																					});
																} else {
																	$mdDialog
																			.show($mdDialog
																					.alert()
																					.clickOutsideToClose(
																							true)
																					.title(
																							'Error')
																					.textContent(
																							"Operation failed.")
																					.ok(
																							'Ok')
																					.parent(
																							angular
																									.element(document.body)));
																}
															}
														},
														function(data) {
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
									} else {
										$mdDialog
												.show($mdDialog
														.alert()
														.clickOutsideToClose(
																true)
														.title('Alert')
														.textContent(
																"Add atleast one Room to check out list")
														.ok('Ok')
														.parent(
																angular
																		.element(document.body)));
									}
								} else {
									$mdDialog
											.show($mdDialog
													.alert()
													.clickOutsideToClose(true)
													.title('Alert')
													.textContent(
															"Please confirm before proceeding")
													.ok('Ok')
													.parent(
															angular
																	.element(document.body)));
								}
							}

							$scope.goTopayment = function(checkInNo,
									folioBindNo) {
								$scope.chk = checkInNo;
								$scope.folioBind = folioBindNo;
								window.location = "../payment/list?checkInNo="
										+ $scope.chk + "&folioBindNo="
										+ $scope.folioBind;
							}
							$scope.goToDiscount = function(checkInNo,
									folioBindNo) {
								$scope.chk = checkInNo;
								$scope.folioBind = folioBindNo;
								
								$http({
									url:'../payment/checkDiscount',
									method : "GET",
									params : {"checkinNo":$scope.chk}
								}).success(function (data, status, headers, config) {
									if(data == "yes" ){
										$mdDialog.show($mdDialog.alert()
												.clickOutsideToClose(true).title('Discount Already Applied').ok('Ok').parent(angular.element(document.body)));
								    }
									else {
										
										window.location = "../payment/discountlist?checkInNo="
											+ $scope.chk + "&folioBindNo="
											+ $scope.folioBind;
	
									}
								});
								
								
							}
							$scope.goToposting = function(checkInNo,
									folioBindNo) {
								$scope.chk = checkInNo;
								$scope.folioBind = folioBindNo;
								window.location = "../transaction/recieptDetails?checkinNo="
										+ $scope.chk
										+ "&folioBindNo="
										+ $scope.folioBind;
							}

							$scope.goTotransfer = function(folioNo, folioBindNo) {
								$scope.folio = folioNo;
								$scope.folioBind = folioBindNo;
								window.location = "../transaction/transfer?folioNo="
										+ $scope.folio
										+ "&folioBindNo="
										+ $scope.folioBind;
							}

							$scope.printOut = function(folioNo) {
								$scope.folioNo = folioNo;
								window.open("../checkOut/invoice2?folioNo="+$scope.folioNo+"&printMode=1&sysAccType=0&billDtls=1&billSeparate=0&mailType=0");
								/*window.open("../checkOut/invoice?folioNo="
										+ $scope.folioNo);*/
							}
						} ]);
