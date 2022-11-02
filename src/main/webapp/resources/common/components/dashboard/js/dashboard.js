 var dashApp = angular.module('dashboardApp', ['ngMaterial','ngSanitize']);
dashApp.controller('dashboardCtrl', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	var hdate = $("#hotelDate").val();
	$scope.hotelDate =new Date(hdate);

	var monthList = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

	$("#logout_drpdwn").click(function(){
		if($("#logout_drpdwn").hasClass("open")){
			$("#logout_drpdwn").removeClass("open");	
		}else{
			$("#logout_drpdwn").addClass("open");
		}
	})

	//visitors
	/*Morris.Donut({
		element: 'dashboard-donut-1',
		data: [
		       {label: "Check Out", value: 2513},
		       {label: "New", value: 764},
		       {label: "Registred", value: 311}
		       ],
		       colors: ['#33414E', '#1caf9a', '#FEA223'],
		       resize: true
	});*/



	/* reportrange */
	if($("#reportrange").length > 0){   
		$("#reportrange").daterangepicker({                    
			ranges: {
				'Today': [moment(), moment()],
				'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
				'Last 7 Days': [moment().subtract(6, 'days'), moment()],
				'Last 30 Days': [moment().subtract(29, 'days'), moment()],
				'This Month': [moment().startOf('month'), moment().endOf('month')],
				'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
			},
			opens: 'left',
			buttonClasses: ['btn btn-default'],
			applyClass: 'btn-small btn-primary',
			cancelClass: 'btn-small',
			format: 'MM.DD.YYYY',
			separator: ' to ',
			startDate: moment().subtract('days', 29),
			endDate: moment()            
		},function(start, end) {
			$('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
		});

		$("#reportrange span").html(moment().subtract('days', 29).format('MMMM D, YYYY') + ' - ' + moment().format('MMMM D, YYYY'));
	}
	/* end reportrange */

	/* Rickshaw dashboard chart */
	// var seriesData = [ [], [] ];
	// var random = new Rickshaw.Fixtures.RandomData(1000);

	// for(var i = 0; i < 100; i++) {
	// random.addData(seriesData);
	// }

	// var rdc = new Rickshaw.Graph( {
	// element: document.getElementById("dashboard-chart"),
	// renderer: 'area',
	// width: $("#dashboard-chart").width(),
	// height: 250,
	// series: [{color: "#33414E",data: seriesData[0],name: 'New'}, 
	// {color: "#1caf9a",data: seriesData[1],name: 'Returned'}]
	// } );

	// rdc.render();

	// var legend = new Rickshaw.Graph.Legend({graph: rdc, element: document.getElementById('dashboard-legend')});
	// var shelving = new Rickshaw.Graph.Behavior.Series.Toggle({graph: rdc,legend: legend});
	// var order = new Rickshaw.Graph.Behavior.Series.Order({graph: rdc,legend: legend});
	// var highlight = new Rickshaw.Graph.Behavior.Series.Highlight( {graph: rdc,legend: legend} );        

	// var rdc_resize = function() {                
	// rdc.configure({
	// width: $("#dashboard-area-1").width(),
	// height: $("#dashboard-area-1").height()
	// });
	// rdc.render();
	// }

	// var hoverDetail = new Rickshaw.Graph.HoverDetail({graph: rdc});

	// window.addEventListener('resize', rdc_resize);        

	// rdc_resize();
	/* END Rickshaw dashboard chart */

	/* Donut dashboard chart */
	$scope.departshow = true;
	$scope.cutoffshow = true;
	$scope.checkinshow = true;
	$scope.resvshow = true;
	$scope.resvtodayshow = true;
	$scope.lastReservation={};
	$scope.counts={};
	$.ajax({
		url: "dashboard/getDetails",
		type : 'POST',
		contentType: "application/json",
		success: function (response) {
			var object=JSON.parse(response);
			$scope.counts=object.dashboard_data;
			checkInDonut();
			checkoutDonut();
			checkInLast30Donut();
			$scope.lastReservation=object.lastReservations;
			$scope.counts.free_rooms=0;
			$scope.reservationToday=[];
			for(var i=0;i<$scope.lastReservation.length;i++){
				if(new Date($scope.lastReservation[i].resv_date).getTime()===$scope.hotelDate.getTime()){
					$scope.reservationToday.push($scope.lastReservation[i]);
				}
			}
			for(var i= 0;i<$scope.counts.avail_room_list.length;i++){
				$scope.counts.free_rooms=$scope.counts.free_rooms+$scope.counts.avail_room_list[i].availRoom;
			}
			$scope.counts.blocked_rooms=$scope.counts.total_rooms_in_inventory-$scope.counts.free_rooms-($scope.counts.occupied_rooms-$scope.counts.checkout_pending);
			$scope.counts.free_rooms=$scope.counts.free_rooms-$scope.counts.dirty_cleaning-$scope.counts.checkout_pending;
			
			
			if($scope.lastReservation.length!=0){
				$scope.resvshow = false;				
			}
			if($scope.reservationToday.length!=0){
				$scope.resvtodayshow = false;				
			}
			bargraph1();
			$scope.$digest();
			nvd3Charts.init();
		}
	});


	$.ajax({
		url: "dashboard/getListData",
		type : 'POST',
		contentType: "application/json",
		success: function (response) {
			var object=JSON.parse(response);
			$scope.departToday= object.departToday;
			$scope.cutoffwithin5day=object.cutoffwithin5day;
			$scope.checkinwithin2days=object.checkinwithin2days;
			if($scope.departToday.length!=0){
				$scope.departshow = false;				
			}
			if($scope.cutoffwithin5day.length!=0){
				$scope.cutoffshow = false;				
			}
			if($scope.checkinwithin2days.length!=0){
				$scope.checkinshow = false;				
			}
			$scope.$digest();	
		}
	});	


	getAddOnData();
	function getAddOnData(){
		$scope.addonNewList=[];
		$scope.newAddonCount=0;
		$.ajax({
			url: "dashboard/getAddOnDetails",
			type : 'POST',
			contentType: "application/json",
			success: function (response) {
				$scope.addonNewList=JSON.parse(response);
				$scope.addonNewListToday={};
				$scope.addonNewListTomorrow={};
				$scope.newAddonCount=0;
				$scope.newAddonCountToday=0;
				$scope.newAddonCountTomorrow=0;

				for(var i = 0; i<$scope.addonNewList.length;i++){
					$scope.addonNewList[i].chreq_req_time=new Date($scope.addonNewList[i].chreq_req_time);
					if(new Date($scope.addonNewList[i].process_date).getTime()==new Date($scope.hotelDate).getTime() && $scope.addonNewList[i].process_status){
						$scope.addonNewList[i].status='<span class="fa fa-check"></span>';
					}else if(new Date(new Date($scope.addonNewList[i].process_date).getFullYear(),new Date($scope.addonNewList[i].process_date).getMonth(),new Date($scope.addonNewList[i].process_date).getDate()).getTime()===new Date(new Date($scope.hotelDate).getFullYear(),new Date($scope.hotelDate).getMonth(),new Date($scope.hotelDate).getDate()).getTime() && !$scope.addonNewList[i].process_status){
						$scope.addonNewList[i].status='<span class="fa fa-times"></span>';
						$scope.addonNewList[i].setAsInactive = true;
					}else{
						$scope.addonNewList[i].status='<span class="fa fa-hourglass-start"></span>';}

					if($scope.addonNewList[i].facility_is_payable){
						$scope.addonNewList[i].payment='Payable';
					}else{
						$scope.addonNewList[i].payment='Not Payable';
					}

					if(new Date(new Date($scope.addonNewList[i].chreq_req_date).getFullYear(),new Date($scope.addonNewList[i].chreq_req_date).getMonth(),new Date($scope.addonNewList[i].chreq_req_date).getDate()).getTime()===new Date(new Date($scope.hotelDate).getFullYear(),new Date($scope.hotelDate).getMonth(),new Date($scope.hotelDate).getDate()+1).getTime() && $scope.addonNewList[i].chreq_is_one_time_request){
						$scope.addonNewListTomorrow[$scope.addonNewList[i].chreq_id]=$scope.addonNewList[i];
						$scope.newAddonCountTomorrow=$scope.newAddonCountTomorrow+1;
					}else {
						$scope.addonNewListToday[$scope.addonNewList[i].chreq_id]=$scope.addonNewList[i];
						$scope.newAddonCountToday=$scope.newAddonCountToday+1;
						if(!$scope.addonNewList[i].chreq_is_one_time_request && !$scope.addonNewList[i].is_req_completed){
							$scope.addonNewListTomorrow[$scope.addonNewList[i].chreq_id]=$scope.addonNewList[i];
							$scope.newAddonCountTomorrow=$scope.newAddonCountTomorrow+1;
						}
						if(!$scope.addonNewList[i].process_status && !$scope.addonNewList[i].setAsInactive){
							$scope.newAddonCount=$scope.newAddonCount+1;
						}
					}
				}
				if($scope.newAddonCount!=0){
					$(".req_hdr").addClass("blink");
				}else{$(".req_hdr").removeClass("blink");}
				$scope.$digest();
			}
		});
	}
	$("#addon_refresh").click(function(){
		getAddOnData();
		
	});
	$scope.processRequest = function(key){
		$scope.reqToProcess = $scope.addonNewListToday[key];
		$scope.reqToProcess.canProcess=false;
		if((new Date($scope.reqToProcess.chreq_req_time).toLocaleTimeString()<=new Date().toLocaleTimeString()) && new Date($scope.reqToProcess.process_date).getTime()!=$scope.hotelDate.getTime()){
			$scope.reqToProcess.canProcess=true;
		}
		$scope.process={remarks:"",checkInReequestId:$scope.reqToProcess.chreq_id};
		$("#process_box").addClass("open");
	}
	$scope.processSave=function(){
		$("#process_box").removeClass("open");
		var data = $.param({addon:JSON.stringify($scope.process)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('dashboard/processAddon', data, config)
		.success(function (data, status, headers, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title(data.substring(7)).textContent(data.substring(7)).ok('Ok').parent(angular.element(document.body)));
			getAddOnData();
		}).error(function (data, status, header, config){
			
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	
	
	
	setInterval(getAddOnData, 3600000);
	function checkInDonut(){
		Morris.Donut({
			element: 'dashboard-donut-checkinToday',
			data: [
			       {label: "Today", value: $scope.counts.chin_today},
			       {label: "Yesterday", value: $scope.counts.chin_yesterday}
			       ],
			       colors: ['#33414E', '#1caf9a'],
			       resize: true
		});
	}

	function checkoutDonut(){
		Morris.Donut({
			element: 'dashboard-donut-checkoutToday',
			data: [
			       {label: "Today", value: $scope.counts.chout_today},
			       {label: "Yesterday", value: $scope.counts.chout_yesterday}
			       ],
			       colors: ['#33414E', '#1caf9a'],
			       resize: true
		});
	}
	function checkInLast30Donut(){
		Morris.Donut({
			element: 'dashboard-donut-checkinlast30days',
			data: [
			       {label: "Last 30 days", value: $scope.counts.chin_in_30_days},
			       {label: "Previous 30 days", value: $scope.counts.chin_before_30_days}
			       ],
			       colors: ['#33414E', '#1caf9a'],
			       resize: true
		});
	}

	/*Morris.Donut({
		element: 'dashboard-donut-1',
		data: [
		       {label: "Check Out", value: 2513},
		       {label: "New", value: 764},
		       {label: "Registred", value: 311}
		       ],
		       colors: ['#33414E', '#1caf9a', '#FEA223'],
		       resize: true
	});*/
	/* END Donut dashboard chart */





	/* Bar dashboard chart */
	function bargraph1(){
		Morris.Bar({
			element: 'dashboard-bar-1',
			data: [
			       { y: monthList[new Date($scope.hotelDate).getMonth()-2], a: $scope.counts.confirm_bfr_prev_month, b: $scope.counts.cancel_bfr_prev_month },
			       { y: monthList[new Date($scope.hotelDate).getMonth()-1], a: $scope.counts.confirm_prev_month, b: $scope.counts.cancel_prev_month },
			       { y: monthList[new Date($scope.hotelDate).getMonth()], a: $scope.counts.confirm_this_month, b: $scope.counts.cancel_this_month },
			       { y: monthList[new Date($scope.hotelDate).getMonth()+1], a: $scope.counts.confirm_next_month, b: $scope.counts.cancel_next_month },
			       { y: monthList[new Date($scope.hotelDate).getMonth()+2], a: $scope.counts.confirm_aftr_next_month, b: $scope.counts.cancel_aftr_next_month }
			       ],
			       xkey: 'y',
			       ykeys: ['a', 'b'],
			       labels: ['Confirmation', 'Cancellation'],
			       barColors: ['#33414E', '#1caf9a'],
			       gridTextSize: '10px',
			       hideHover: true,
			       resize: true,
			       gridLineColor: '#E5E5E5'
		});
	}
	/* END Bar dashboard chart */

	/* Line dashboard chart */
	Morris.Line({
		element: 'dashboard-line-1',
		data: [
		       { y: '2014-10-10', a: 2,b: 4},
		       { y: '2014-10-11', a: 4,b: 6},
		       { y: '2014-10-12', a: 7,b: 10},
		       { y: '2014-10-13', a: 5,b: 7},
		       { y: '2014-10-14', a: 6,b: 9},
		       { y: '2014-10-15', a: 9,b: 12},
		       { y: '2014-10-16', a: 18,b: 20}
		       ],
		       xkey: 'y',
		       ykeys: ['a','b'],
		       labels: ['Sales','Event'],
		       resize: true,
		       hideHover: true,
		       xLabels: 'day',
		       gridTextSize: '10px',
		       lineColors: ['#1caf9a','#33414E'],
		       gridLineColor: '#E5E5E5'
	});   
	/* EMD Line dashboard chart */
	/* Moris Area Chart */
	Morris.Area({
		element: 'dashboard-area-1',
		data: [
		       { y: '2014-10-10', a: 17,b: 19},
		       { y: '2014-10-11', a: 19,b: 21},
		       { y: '2014-10-12', a: 22,b: 25},
		       { y: '2014-10-13', a: 20,b: 22},
		       { y: '2014-10-14', a: 21,b: 24},
		       { y: '2014-10-15', a: 34,b: 37},
		       { y: '2014-10-16', a: 43,b: 45}
		       ],
		       xkey: 'y',
		       ykeys: ['a','b'],
		       labels: ['Sales','Event'],
		       resize: true,
		       hideHover: true,
		       xLabels: 'day',
		       gridTextSize: '10px',
		       lineColors: ['#1caf9a','#33414E'],
		       gridLineColor: '#E5E5E5'
	});
	/* End Moris Area Chart */
	/* Vector Map */
	var jvm_wm = new jvm.WorldMap({container: $('#dashboard-map-seles'),
		map: 'world_mill_en', 
		backgroundColor: '#FFFFFF',                                      
		regionsSelectable: true,
		regionStyle: {selected: {fill: '#B64645'},
			initial: {fill: '#33414E'}},
			markerStyle: {initial: {fill: '#1caf9a',
				stroke: '#1caf9a'}},
				markers: [{latLng: [50.27, 30.31], name: 'Kyiv - 1'},                                              
				          {latLng: [52.52, 13.40], name: 'Berlin - 2'},
				          {latLng: [48.85, 2.35], name: 'Paris - 1'},                                            
				          {latLng: [51.51, -0.13], name: 'London - 3'},                                                                                                      
				          {latLng: [40.71, -74.00], name: 'New York - 5'},
				          {latLng: [35.38, 139.69], name: 'Tokyo - 12'},
				          {latLng: [37.78, -122.41], name: 'San Francisco - 8'},
				          {latLng: [28.61, 77.20], name: 'New Delhi - 4'},
				          {latLng: [39.91, 116.39], name: 'Beijing - 3'}]
	});    
	/* END Vector Map */


	$(".x-navigation-minimize").on("click",function(){
		setTimeout(function(){
			rdc_resize();
		},200);    
	});

	var myColors = ["#a7a7a7","#FF702A","#00BFDD","#8DCA35","#80CDC2","#DA3610",
	                "#A6D969","#D9EF8B","#FFFF99","#F7EC37","#F46D43",
	                "#E08215","#D73026","#A12235","#8C510A","#14514B","#4D9220",
	                "#542688", "#4575B4", "#74ACD1", "#B8E1DE", "#FEE0B6","#FDB863",                                                
	                "#C51B7D","#DE77AE","#EDD3F2"];
	d3.scale.myColors = function() {
		return d3.scale.ordinal().range(myColors);
	};

	var nvd3Charts = function() {     
		var startChart9 = function() {
			//Regular pie chart example
			nv.addGraph(function() {
				var chart = nv.models.pieChart().x(function(d) {
					return d.label;
				}).y(function(d) {
					return d.value;
				}).showLabels(true).color(d3.scale.myColors().range());;

				d3.select("#chart-9 svg").datum(exampleData()).transition().duration(350).call(chart);

				return chart;
			});

			//Donut chart example
			nv.addGraph(function() {
				var chart = nv.models.pieChart().x(function(d) {
					return d.label;
				}).y(function(d) {
					return d.value;
				}).showLabels(true)//Display pie labels
				.labelThreshold(.05)//Configure the minimum slice size for labels to show up
				.labelType("value")//Configure what type of data to show in the label. Can be "key", "value" or "percent"
				.donut(true)//Turn on Donut mode. Makes pie chart look tasty!
				.donutRatio(0.35)//Configure how big you want the donut hole size to be.
				.color(d3.scale.myColors().range());;

				d3.select("#chart-10 svg").datum(exampleData()).transition().duration(350).call(chart);

				return chart;
			});

			//Pie chart example data. Note how there is only a single array of key-value pairs.
			function exampleData() {
				
				return [{
					"label" : "Cleaning/Dirty",
					"value" : $scope.counts.dirty_cleaning
				}, {
					"label" : "Occupied",
					"value" : $scope.counts.occupied_rooms
				}, {
					"label" : "Out of inv",
					"value" : $scope.counts.outofinventory
				}, {
					"label" : "Free",
					"value" : $scope.counts.free_rooms
				},/*{
					"label" : "Blocked for today",
					"value" : $scope.counts.blocked_rooms
				}*/];
			}

		};
		return {		
			init : function() {
				startChart9();
			}
		};
	}();


}]);

