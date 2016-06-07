pp.controller('appController', ['$scope', '$window', '$location',
								function($scope, $window, $location){

	function init(){
		if(localStorage.userInfo && !$scope.user){
			userService.getUserByEmail(localStorage.userInfo).then(
				function(response) {
					 $scope.user = response.data;
				}
			);
		}
	}

	init();

  $scope.logout = function() {
    $scope.user = undefined;
		localStorage.removeItem('userInfo')
    $window.location.href = "#/login";
  }

	$scope.downloadActPDF = function(act) {
		actService.generatePDF(act.name).then(
			function(response) {
				$scope.genericDownload(response.data.path, response.data.fileName);
			}
		);
	}

	$scope.downloadActXHTML = function(act) {
		actService.generateXHTML(act.name).then(
			function(response) {
				$scope.genericDownload(response.data.path, response.data.fileName);
			}
		);
	}

	$scope.downloadAmendmentPDF = function(amendment) {
		amendmentService.generatePDF(amendment.id).then(
			function(response) {
				$scope.genericDownload(response.data.path, response.data.fileName);
			}
		);
	}

	$scope.downloadAmendmentXHTML = function(amendment) {
		amendmentService.generateXHTML(amendment.id).then(
			function(response) {
				$scope.genericDownload(response.data.path, response.data.fileName);
			}
		);
	}

	$scope.genericDownload = function(path, fileName) {
		var name = "";

		if(fileName) {
			name = fileName;
		}
		var downloadLink = angular.element('<a></a>');
		downloadLink.attr('href', path);
		downloadLink.attr('download', name);
		downloadLink[0].click();
	}
}]);
