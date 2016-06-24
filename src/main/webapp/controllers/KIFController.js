app.controller('KIFController', ['$scope', '$window', 'tableService',
                function($scope, $window, tableService){

  function init() {
    $scope.data = {};
    tableService.getCompaniesForKIF().then(
      function(response) {
        if(response.data.rows && response.data.rows.length > 0) {
          $scope.companies = response.data.rows;
        } else {
          alert("Nema preduzeća za koje imaju fakture.");
          window.location.href = "#/";
        }
      }, function() {
        alert("Došlo je do greške prilikom dobavljanja preduzeća.");
        window.location.href = "#/";
      }
    );
  }

  init();

  $scope.downloadKIF = function() {
    if(tableService.isKIFFormValid($scope.dateFrom, $scope.dateTo, $scope.company)) {
      tableService.generateKIF($scope.dateFrom, $scope.dateTo, $scope.company).then(
        function(response) {
          $scope.genericDownload("KIF.pdf");
        }
      );
    } else {
      alert("Forma nije validna");
    }
  }

  $scope.genericDownload = function(fileName) {
  var name = "", path = "downloads/" + fileName;

  if(fileName) {
    name = fileName;
  }
  var downloadLink = angular.element('<a></a>');
  downloadLink.attr('href', path);
  downloadLink.attr('download', name);
  downloadLink[0].click();
}
}]);
