var pmsApp = angular.module('pmsApp', ['datatables','ngMaterial','ngMessages','ngAnimate', 'ngSanitize', 'ui.bootstrap','ngMessages','datatables.scroller','ngResource','multipleDatePicker']);

pmsApp.directive('numbersOnly', function () {
	return {
		require: 'ngModel',
		link: function (scope, element, attr, ngModelCtrl) {
			function fromUser(text) {
				if (text) {
					var transformedInput = text.replace(/[^0-9]/g, '');

					if (transformedInput !== text) {
						ngModelCtrl.$setViewValue(transformedInput);
						ngModelCtrl.$render();
					}
					return transformedInput;
				}
				return undefined;
			}            
			ngModelCtrl.$parsers.push(fromUser);
		}
	};
});

pmsApp.directive("limitZero", [function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			var limit = parseInt(attrs.limitZero);
			angular.element(elem).on("keyup change focusout", function(e) {
				if (parseInt(this.value) > limit || parseInt(this.value) <=0 || this.value=="") {e.preventDefault();
				this.value=0;
				};
			});
		}
	}
}]);
pmsApp.directive('slideToggle', function() {  
	return {
		restrict: 'A',      
		scope:{
			isOpen: "=slideToggle"
		},  
		link: function(scope, element, attr) {
			var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;      
			scope.$watch('isOpen', function(newIsOpenVal, oldIsOpenVal){
				if(newIsOpenVal !== oldIsOpenVal){ 
					element.stop().slideToggle(slideDuration);
				}
			});	      
		}
	};  
});
pmsApp.directive("limitTo", [function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			var limit = parseInt(attrs.limitTo);
			angular.element(elem).on("keyup change focusout", function(e) {
				if (parseInt(this.value) > limit || parseInt(this.value) <=0 || this.value=="") {e.preventDefault();
				this.value=1;
				};
			});
		}
	}
}]);
pmsApp.factory('discountService', function ($http) {
	 return {
		    getDiscounts : function(assignedRateIdList){
		return $http({
			url : '../reservation_test/getDiscountDetails',
			method : "POST",
			data : assignedRateIdList
		});
		}
	 }
});