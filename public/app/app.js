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
        '$scope', '$timeout', 'Search',
        function ($scope, $timeout, Search) {

            $scope.search = {
                'registration': 'WX14YSC',
                'stock_ref': 'ARNAF-U-25606'
            };

            $scope.sizeSelect = {
                small: true,
                big: false
            };

            $scope.errorMessage = '';

            $scope.searchResults = {};
            $scope.searchResultsCopy = {};

            /**
 			* This could be done a bit more elegant way but iI dont know validation rules.
             */
            $scope.$watchGroup(['search.registration', 'search.stock_ref'],
                function () {
                    var reLen = $scope.search.registration.length;
                    var stockLen = $scope.search.stock_ref.length;

                    if (reLen > 0 && stockLen > 0) {
                    	 $scope.searchResults = {};
                        Search.get($scope.search).success(function (data) {
                            if (data.length > 1) {
                                $scope.searchResults = data;
                                $scope.searchResultsCopy = angular.copy(data);
                                filterSize();
                            } else {
                                $scope.errorMessage = data[0];
                            }

                        });
                    }
                }, true);


            $scope.$watchGroup(['sizeSelect.small', 'sizeSelect.big'],
                function () {
                    filterSize();
                });

            /**
             * Filter function depends on big/smalls checkboxes
             * Use lodash for filter
             */
            function filterSize() {
                if ($scope.sizeSelect.small === false && $scope.sizeSelect.big === true) {
                    $scope.searchResults = _.filter($scope.searchResultsCopy,
                        function (url) {
                            return url.indexOf('/350/') === -1;
                        });
                }

                if ($scope.sizeSelect.big === false && $scope.sizeSelect.small === true) {
                    $scope.searchResults = _.filter($scope.searchResultsCopy,
                        function (url) {
                            return url.indexOf('/800/') === -1;
                        });
                }

                if ($scope.sizeSelect.big === false && $scope.sizeSelect.small === false) {
                    $scope.searchResults = {};
                }

                if ($scope.sizeSelect.big === true && $scope.sizeSelect.small === true) {
                    $scope.searchResults = $scope.searchResultsCopy;
                }
            }


        }
    ]);

/**
 * Facotory that manage Search with Java backend
 */
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
