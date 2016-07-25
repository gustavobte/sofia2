var app = angular.module("app", [ 'ngMaterial' ]);

app.controller("controller", function($scope, $http) {
	var apiEnderecos = 'http://PROJVS201608W7:8080/Sofia2/api/enderecos/';
	var JSONenderecosCandidatos = 'resources/json/enderecos_candidatos.json';
	var JSONenderecosComparacao = 'resources/json/enderecos_1000.json';
	
	$scope.enderecosCandidatosSelecionados = {};

	$scope.enderecosCandidatos = [];
	
	$scope.enderecosComparacao = [];

	$http.get(JSONenderecosComparacao).then(function(result) {
		$scope.enderecosComparacao = result.data;
	});

	$http.get(JSONenderecosCandidatos).then(function(result) {
		$scope.enderecosCandidatos = result.data;
	});

	$scope.calcularScoreCandidatos = function() {
		$scope.bancoEnderecos = [ {
			'candidatos' : $scope.enderecosCandidatos
		}, {
			'comparacao' : $scope.enderecosComparacao
		} ]
		$http.post(apiEnderecos + 'calculaScoreCandidatos', $scope.bancoEnderecos).then(
				function(result) {
					$scope.enderecosCandidatos = result.data;
				});
	}
	
	$scope.calcularScoreComparacao = function() {
		$http.post(apiEnderecos + 'calculaScoreComparacao', $scope.bancoEnderecos).then(
				function(result) {
					$scope.enderecosComparacao = result.data;
				});
	}

	$scope.indexarEnderecos = function() {
		$http.get(apiEnderecos + 'indexar');
	}
	
});
