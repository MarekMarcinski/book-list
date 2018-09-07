'use strict';

angular.module('app',['ngRoute','ngResource'])
.config(function($routeProvider){
    $routeProvider
        .when('/list',{
            templateUrl:'partials/list.html',
            controller:'ListController',
            controllerAs:'listCtrl'
        })
        .otherwise({
            redirectTo:'/list'
        })
})

.constant('BOOK_ENDPOINT','/api/books/:id')

.factory('Book', function ($resource,BOOK_ENDPOINT) {
    return $resource(BOOK_ENDPOINT);
})

.service('Books', function(Book) {
    this.getAll = function() {
        return Book.query();
    }
})

.controller('ListController', function (Books) {
    var variable = this;
    variable.books = Books.getAll();
});