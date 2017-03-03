senfApp.controller('notaFiscalController', function ($scope, $http) {
	
	//$scope.nome = 'Alexandre  Porto';
	$scope.toggle=true;
	$scope.notasFiscais = [];
	$scope.notaFiscal = {}; // bind com o form
	//$http.defaults.headers.post["Content-Type"] = "application/json";

	
	// ação interna, não necessita estar no escopo
	findAllNotasFiscais = function findAllNotasFiscais() {   
       
        $http( { method:'GET', url:'http://localhost:8080/notasFiscais'})
        .then(function(response){
        	
        	$scope.notasFiscais	= response.data;  
        	
        }, function(response){
        	
        	console.log(response.data);
        	console.log(response.status);
        });
    };         
	findAllNotasFiscais();

      
	
	$scope.addNotaFiscal = function addNotaFiscal() {   
	       
        $http( { method:'POST', url:'http://localhost:8080/notasFiscais/', data:$scope.notaFiscal })
        .then(function(response){
        	
        	//$scope.notasFiscais.push(response.data);  
        	
        	findAllNotasFiscais();
        	$scope.notaFiscal = {};
        	
        }, function(response){
        	
        	Console.log(response.data);
        	Console.log(response.status);
        });
    };
    
    
    
    $scope.removeNotaFiscal = function removeNotaFiscal(nfiscal) {
    	 $http( { method:'DELETE', url:'http://localhost:8080/notasFiscais/' + nfiscal.id })
         .then(function(response){
        	 
         	pos = $scope.notasFiscais.indexOf(nfiscal);         	
         	$scope.notasFiscais.splice(pos, 1);
         	
         }, function(response){
         	
         	Console.log(response.data);
         	Console.log(response.status);
         });    	
    };
    
    
    
    $scope.updateNotaFiscal = function (nfiscal) {
    	$scope.notaFiscal = angular.copy(nfiscal);
    };
    
    
    
    $scope.cancelUpdateNotaFiscal = function () {
    	$scope.notaFiscal = {};
    };
        
    //
	
   /* $scope.detailNotaFiscal = function detailNotaFiscal(nfiscal) {
   	 $http( { method:'GET', url:'http://localhost:8080/notasFiscais/' + nfiscal.id })
        .then(function(response){
       	 
       	    $scope.notaFiscal = response.data;		 
        	
        	//pos = $scope.notasFiscais.indexOf(nfiscal);         	
        	//$scope.notasFiscais.splice(pos, 1);
        	
        }, function(response){
        	
        	Console.log(response.data);
        	Console.log(response.status);
        });    	
   };	*/
	
	
	// 	utilizando o méstodo atalho
	
	/* $http.get("notasFiscais/"+$routeParam.notaFiscalId, function(response){
		 
		 $scope.notaFiscal = response.data;		 
		 
	 }, function(response){
		 
      	Console.log(response.data);
     	Console.log(response.status);		 
		 
	 });*/	
	
	
});