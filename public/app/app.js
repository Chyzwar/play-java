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
        '$scope',
        function ($scope) {

        }
    ]);
