pmsApp.requires.push('textAngular');
pmsApp
		.controller(
				'commtnEditCtrl',
				[
						'$scope',
						'$q',
						'$http',
						'$mdDialog',
						'DTOptionsBuilder',
						'DTColumnBuilder',
						'$log',
						'$compile',
						function($scope, $q, $http, $mdDialog,
								DTOptionsBuilder, DTColumnBuilder, $log,
								$compile) {
							onloadValidator(".validator");
							OnloadValidationEdit('.validator');
							var cmEdit = this;
							cmEdit.dtInstance = {};
							cmEdit.isDisabled = false;
							$scope.responseData = [];

							cmEdit.selectedItemChange = selectedItemChange;
							cmEdit.querySearch = querySearch;
							cmEdit.searchTextChange = searchTextChange;
							$scope.communicationMail = window.comtnPurposeEnumEmail;
							var communicationPurposes = window.comtnPurposeEnum;
							cmEdit.simulateQuery = false;
							cmEdit.repos = loadAll();
							$scope.sub = $scope.selectedSubject;
							var mailSubject = window.comtnPurposeEnum;
							cmEdit.dtOptions = DTOptionsBuilder
									.fromFnPromise(
											function() {
												var defer = $q.defer();
												$http(
														{
															url : "../communication/getCommunicationData",
															type : "POST",
														})
														.then(
																function(
																		response) {
																	$scope.responseData = response.data;
																	defer
																			.resolve($scope.responseData);
																},
																function(
																		response) {
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
												return defer.promise;
											}).withPaginationType(
											'full_numbers').withOption(
											'deferRender', true).withOption(
											'rowCallback', rowCallback)
									.withOption(
											'createdRow',
											function(row, data, dataIndex) {
												$compile(
														angular.element(row)
																.contents())(
														$scope);
											});

							cmEdit.dtColumns = [
									DTColumnBuilder.newColumn(
											"communicationType", "Type")
											.renderWith(
													function(data, type, full) {
														var type = '';
														if (data == 1) {
															type = "Email"
														} else if (data == 2) {
															type = "SMS"
														} else if (data == 3) {
															type = "Telephone"
														}
														return type;
													}),
									DTColumnBuilder
											.newColumn("purpose", "Purpose")
											.renderWith(
													function(data, type, full) {
														var purpose = '';
														purpose = (data == 0) ? full.subject
																: communicationPurposes[data];
														if (full.subject == "") {
															purpose = communicationPurposes[data];
														}
														return purpose;
													}),
									DTColumnBuilder
											.newColumn("status", "Status")
											.renderWith(
													function(data, type, full) {
														if (data == true) {
															return '<span class="statSuccess"> Success</span>'
														} else {
															return '<span class="statFailure">Failure</span>'
														}
													}),
									,
									DTColumnBuilder.newColumn("description",
											"Description"),
									DTColumnBuilder.newColumn("lastUpdatedTS",
											"Date").renderWith(
											function(data, type, full) {
												return new Date(data)
														.toDateString()
											}), ];

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
																			"#purposeLabelId")
																			.css(
																					"display",
																					"block");
																	$(
																			"#purposeText")
																			.css(
																					"display",
																					"none");
																	// $("#resendemilTemp").show();
																	// $("#saveSmsTemp").hide();

																	$(
																			"#codePurpose")
																			.css(
																					"display",
																					"none");
																	$scope.purposecode = window.comtnPurposeEnum[aData.purpose];
																	$scope.purposeLabel = window.comtnPurposeEnumEmail[aData.purpose];
																	if (aData.emailcc == undefined) {

																	}
																	$scope.ToEmail = {
																		id : aData.id,
																		txtToEmail : aData.emailto,
																		SubjectMail : aData.subject,
																		content : aData.content,
																		txtCcEmail : aData.emailcc,
																		selectedSubject : window.comtnPurposeEnumEmail[aData.purpose],
																		baction : "edit"
																	};
																	if (aData.emailcc == undefined) {
																		$scope.ToEmail.txtCcEmail = ""
																	}
																	$scope.commtnEdit = {
																		id : aData.id,
																		description : aData.description,
																		status : aData.status,
																		purpose : aData.purpose,
																		phone : $scope.phone,
																		subject : window.comtnPurposeEnum[aData.purpose],
																		baction : "edit"
																	};
																	resetValidators('.validator');
																	(aData.communicationType == 1) ? $(
																			"#newEmailmyModalCommunication")
																			.modal(
																					{
																						backdrop : "static"
																					})
																			: $(
																					"#newCommunicationmyModal")
																					.modal(
																							{
																								backdrop : "static"
																							});

																})
													})
								}
								return nRow;

							}
							function validateEmail(emailField) {
								if (emailField == "")
									return true;

								var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

								if (reg.test(emailField) == false) {
									$mdDialog
											.show($mdDialog
													.alert()
													.clickOutsideToClose(true)
													.title('Alert')
													.textContent(
															"Invalid email address")
													.ok('Ok')
													.parent(
															angular
																	.element(document.body)));
									return false;
								}
								return true;
							}

							$scope.newCommunication = function() {
								$("#newCommunicationmyModal").modal({
									backdrop : "static"
								});

								if ($('#cmGroup').val() == 'resvn') {
									$scope.phone = $("#phoneCustmr").val();

									$scope.commtnEdit = {
										id : 0,
										description : "",
										status : true,
										purpose : "1",
										phone : $scope.phone,
										subject : "",
										baction : "save"
									};
								} else {
									$scope.phone = $("#checkinPhone").val();

									$scope.commtnEdit = {
										id : 0,
										description : "",
										status : true,
										purpose : "6",
										phone : $scope.phone,
										subject : "",
										baction : "save"
									};
								}

							}

							$scope.newCommunicationMail = function() {
								// $("#resendemilTemp").hide();
								// $("#saveSmsTemp").show();

								$("#purposeText").css("display", "block");
								$("#purposeLabelId").css("display", "none");
								$("#codePurpose").css("display", "block");
								$("#newEmailmyModalCommunication").modal({
									backdrop : "static"
								});
								if ($('#cmGroup').val() == 'resvn') {
									$scope.phone = $("#phoneCustmr").val();

									$scope.ToEmail = {
										id : 0,
										txtToEmail : $("#resvMail").val(),
										txtCcEmail : "",
										selectedSubject : window.comtnPurposeEnumEmail
												.indexOf("OTHER"),
										content : "",
										SubjectMail : "",
										baction : "save"
									};
								} else {
									$scope.phone = $("#checkinPhone").val();

									$scope.ToEmail = {
										id : 0,
										txtToEmail : $("#checkinMail").val(),
										txtCcEmail : "",
										selectedSubject : window.comtnPurposeEnumEmail
												.indexOf("WELCOME"),
										content : "",
										SubjectMail : "",
										baction : "save"
									};

								}
							}

							function querySearch(query) {
								return query ? cmEdit.repos
										.filter(createFilterFor(query))
										: cmEdit.repos;
							}

							$scope.cancelEmailPopUp = function() {
								$("#newEmailmyModalCommunication").modal(
										'toggle');

							}

							function loadAll() {
								var repos = $scope.communicationMail;
								$scope.reposWindow = repos;
								return repos.map(function(repo) {
									return {
										value : repo.toLowerCase(),
										display : repo
									};
								});
							}

							function createFilterFor(query) {
								var lowercaseQuery = angular.lowercase(query);
								return function filterFn(repo) {
									return (repo.value.indexOf(lowercaseQuery) === 0);
								};

							}

							cmEdit.cancel = function($event) {
								$mdDialog.cancel();
							};
							cmEdit.finish = function($event) {
								$mdDialog.hide();
							};
							function selectedItemChange(repo) {
								if (repo != null) {
									$scope.ToEmail.selectedSubject = window.comtnPurposeEnumEmail
											.indexOf(repo.display);
									$scope.subjectCode = repo.display;
								}
							}
							function searchTextChange(text) {
								$scope.TextSubject = text;
								$scope.subjectCode = $scope.TextSubject;
								$scope.ToEmail.selectedSubject = "0";
							}

							$scope.saveEmail = function() {

								if ($('#cmGroup').val() == 'resvn') {
									$scope.phone = $("#phoneCustmr").val();
								} else {
									$scope.phone = $("#checkinPhone").val();
								}
								if ($scope.subjectCode == undefined) {
									$scope.subjectCode = 'OTHER'
								}

								$scope.save = {
									id : $scope.ToEmail.id,
									email : $scope.ToEmail.txtToEmail,
									cc : $scope.ToEmail.txtCcEmail,
									selectedPurpose : $scope.ToEmail.selectedSubject,
									content : $scope.ToEmail.content,
									phone : $scope.phone,
									description : "",
									subjectCode : $scope.subjectCode,
									mailSubject : $scope.ToEmail.SubjectMail
								};

								$scope.bAction = $scope.ToEmail.baction;
								if ($scope.bAction == 'save') {

									if (validateEmail($scope.save.email)) {
										if (validateEmail($scope.ToEmail.txtCcEmail)) {
											$('#imgloader').show();

											var data = $.param({
												newCommunicationMail : JSON
														.stringify($scope.save)
											});
											/*
											 * var config =
											 * {headers:{'Content-Type':
											 * 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
											 * $http.post('../communication/saveCommunictnMail',
											 * data, config) .success(function
											 * (data, status, headers, config) {
											 * $('#imgloader').hide();
											 * 
											 * if(data=='success'){
											 * $("#newEmailmyModalCommunication").modal('toggle');
											 * var confirm = $mdDialog.confirm()
											 * .title("Mail sent
											 * successfully").ok('OK').parent(angular.element(document.body));
											 * $mdDialog.show(confirm).then(function(){
											 * window.location = " "; });
											 * 
											 * }else{
											 * $mdDialog.show($mdDialog.alert()
											 * .clickOutsideToClose(true).title('Sending
											 * failed.').textContent(data).ok('Ok').parent(angular.element(document.body))); }
											 * 
											 * }).error(function (data, status,
											 * header, config) {
											 * $scope.recptnData=[];
											 * $mdDialog.show($mdDialog.alert()
											 * .clickOutsideToClose(true).title('Operation
											 * failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
											 * });
											 */

											$http(
													{
														url : "saveCommunictnMail",
														method : "POST",
														params : {
															newCommunicationMail : $scope.save
														}
													})
													.then(
															function(response) {
																if (response.data) {
																	$(
																			"#newEmailmyModalCommunication")
																			.modal(
																					'toggle');
																	var confirm = $mdDialog
																			.confirm()
																			.title(
																					"Mail sent successfully")
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
																						window.location = " ";
																					});

																} else {
																	$mdDialog
																			.show($mdDialog
																					.alert()
																					.clickOutsideToClose(
																							true)
																					.title(
																							'Sending failed.')
																					.ok(
																							'Ok')
																					.parent(
																							angular
																									.element(document.body)));
																}
															});

										}
									}

								} else if ($scope.bAction == 'edit') {

									$scope.save.subjectCode = $scope.ToEmail.selectedSubject;
									$scope.save.selectedPurpose = window.comtnPurposeEnumEmail
											.indexOf($scope.ToEmail.selectedSubject);
									if (validateEmail($scope.save.email)) {
										if (validateEmail($scope.save.cc)) {

											var data = $.param({
												resendCommunicationMail : JSON
														.stringify($scope.save)
											});
											var config = {
												headers : {
													'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
													'X-Requested-With' : 'XMLHttpRequest'
												}
											}
											$http
													.post(
															'../communication/ResendCommunictnMail',
															data, config)
													.success(
															function(data,
																	status,
																	headers,
																	config) {
																if (data == 'success') {
																	$(
																			"#newEmailmyModalCommunication")
																			.modal(
																					'toggle');
																	var confirm = $mdDialog
																			.confirm()
																			.title(
																					"Mail  send successfully")
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
																						window.location = " ";
																					});

																} else {
																	$mdDialog
																			.show($mdDialog
																					.alert()
																					.clickOutsideToClose(
																							true)
																					.title(
																							'Sending failed.')
																					.textContent(
																							data)
																					.ok(
																							'Ok')
																					.parent(
																							angular
																									.element(document.body)));
																}

															})
													.error(
															function(data,
																	status,
																	header,
																	config) {
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
								}
							}

							$scope.saveCommunication = function() {
								if ($('#cmGroup').val() == 'resvn') {
									$scope.phone = $("#phoneCustmr").val();
								} else {
									$scope.phone = $("#checkinPhone").val();
								}

								$scope.commtnEdit.subject = window.comtnPurposeEnum[$scope.commtnEdit.purpose];

								$scope.saveCommunication = {
									id : $scope.commtnEdit.id,
									description : $scope.commtnEdit.description,
									status : true,
									purpose : $scope.commtnEdit.purpose,
									phone : $scope.phone,
									subject : $scope.commtnEdit.subject
								};

								$scope.bAction = $scope.commtnEdit.baction;
								if ($scope.bAction == 'save') {

									var data = $
											.param({
												newCommunication : JSON
														.stringify($scope.saveCommunication)
											});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post(
													'../communication/saveCommunication',
													data, config)
											.success(
													function(data, status,
															headers, config) {
														if (data.substring(7) == 'success') {
															$(
																	"#newCommunicationmyModal")
																	.modal(
																			'toggle');
															var confirm = $mdDialog
																	.confirm()
																	.title(
																			"Communication  saved Successfully")
																	.ok('OK')
																	.parent(
																			angular
																					.element(document.body));
															$mdDialog
																	.show(
																			confirm)
																	.then(
																			function() {
																				window.location = " ";
																			});
														} else {
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
								} else if ($scope.bAction == 'edit') {

									var data = $
											.param({
												editCommunication : JSON
														.stringify($scope.saveCommunication)
											});
									var config = {
										headers : {
											'Content-Type' : 'application/x-www-form-urlencoded;charset=utf-8;',
											'X-Requested-With' : 'XMLHttpRequest'
										}
									}
									$http
											.post(
													'../communication/editCommunication',
													data, config)
											.success(
													function(data, status,
															headers, config) {
														if (data.substring(7) == 'success') {
															$(
																	"#newCommunicationmyModal")
																	.modal(
																			'toggle');
															var confirm = $mdDialog
																	.confirm()
																	.title(
																			"Communication  successfully saved")
																	.ok('OK')
																	.parent(
																			angular
																					.element(document.body));
															$mdDialog
																	.show(
																			confirm)
																	.then(
																			function() {
																				window.location = " ";
																			});

														} else {
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

							$scope.cancel = function() {
								window.location = "../communication/list";
							}
							$scope.cancelPopUp = function() {
								$("#newCommunicationmyModal").modal('toggle');
							}
						} ]);
