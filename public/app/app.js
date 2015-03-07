'use strict';

/**
 * Define a Angular Module
 * Create a basic routes
 */
angular.module('test-app', ['ngRoute'])
    .config([
        '$routeProvider',
        function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: '/assets/app/views/main.html',
                    controller: 'MainCtrl'
                });
        }
    ]);

/**
 * Main Controller
 * Normally these are in separe folders
 */
angular.module('test-app')
    .controller('MainCtrl', [
        '$scope', 'Search',
        function ($scope, Search) {

            $scope.search = {
                'registration': '',
                'stock_ref': ''
            };

            $scope.searchResults = {};

            $scope.$watchGroup(['registration', 'stock_ref'], function () {
                Search.get($scope.search).success(function (data) {
                    $scope.searchResults = data;
                    console.log(data);
                });
            }, true);


        }
    ]);

angular.module('test-app')
    .factory('Search', [
        '$http',
        function ($http) {
            var url = '/api/v1/search';

            return {
                get: function (search) {
                    return $http.post(url, search);
                }
            };
        }

    ]);
