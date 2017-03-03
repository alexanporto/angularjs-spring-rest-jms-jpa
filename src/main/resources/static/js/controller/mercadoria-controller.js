senfApp.controller('mercadoriaController', function ($scope, $http) {
	
	$scope.toggle=true;
	$scope.mercadorias = [];
	$scope.mercadoria = {}; // bind com o form
	//$http.defaults.headers.post["Content-Type"] = "application/json";

	

	findAllMercadorias = function findAllMercadorias() {   
       
        $http( { method:'GET', url:'http://localhost:8080/mercadorias'})
        .then(function(response){
        	
        	$scope.mercadorias	= response.data;  
        	
        }, function(response){
        	
        	console.log(response.data);
        	console.log(response.status);
        });
    };   
        
	$scope.addMercadoria = function addMercadoria() {   
	       
        $http( { method:'POST', url:'http://localhost:8080/mercadorias/', data:$scope.mercadoria })
        .then(function(response){
         	
        	findAllMercadorias();
        	$scope.mercadoria = {};
        	
        }, function(response){
        	
        	Console.log(response.data);
        	Console.log(response.status);
        });
    };
    
    $scope.removeMercadoria = function removeMercadoria(mer) {
    	 $http( { method:'DELETE', url:'http://localhost:8080/mercadorias/' + mer.id })
         .then(function(response){
        	 
         	pos = $scope.mercadorias.indexOf(mer);         	
         	$scope.mercadorias.splice(pos, 1);
         	
         }, function(response){
         	
         	Console.log(response.data);
         	Console.log(response.status);
         });    	
    };
        
    $scope.updateMercadoria = function (mer) {
    	$scope.mercadoria = angular.copy(mer);
    };
    
    $scope.cancelUpdateMercadoria = function () {
    	$scope.mercadoria = {};
    };
        
    //
    
    findAllMercadorias();
});