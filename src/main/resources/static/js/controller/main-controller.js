senfApp.controller('mainController', function ($scope, $route, $routeParams, $location) {
	
	$scope.$location = $location;
	$scope.$route = $route;
	$scope.$routeParams = $routeParams;
});