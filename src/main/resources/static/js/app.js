var senfApp = angular.module("sistemaDeEmissaoDeNotasFiscaisApp", ['ngRoute', 'ngAnimate', 'ui.bootstrap']);


senfApp.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

	$locationProvider.hashPrefix('');
		
	$routeProvider	
	
	.when('/notasFiscais', {

	    templateUrl: 'pages/notafiscal.html',
	    
	    controller: 'notaFiscalController'
	})	
	
	.when('/mercadorias', {

	    templateUrl: 'pages/mercadoria.html',
	    
	    controller: 'mercadoriaController'
	})	
	
	.otherwise({
	
		redirectTo: '/'
	
	});
	
	$locationProvider.html5Mode(true); 
	
}]);
	


