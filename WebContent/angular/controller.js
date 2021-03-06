var app = angular.module("app", [ 'ngMaterial' ]);

app.controller("controller", function($scope, $http) {
	var apiEnderecoSofia2 = 'http://sofia2.com/web/enderecoscore/sofia2-master/api/enderecos/';
	var apiEnderecoLocal = 'http://PROJVS201608W7:8080/Sofia2/api/enderecos/';
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

	$scope.calcularScore = function() {
		$scope.bancoEnderecos = {
			"candidato" : $scope.enderecosCandidatos,
			"comparacao" : $scope.enderecosComparacao
		}

		$http.post(apiEnderecoSofia2 + 'calculaScore', $scope.bancoEnderecos).then(
				function(result) {
					$scope.enderecos = result.data;
				});

	}

});
