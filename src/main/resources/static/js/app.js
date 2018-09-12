'use strict';

angular.module('app',['ngRoute','ngResource'])
.config(function($routeProvider){
    $routeProvider
        .when('/list',{
            templateUrl:'partials/list.html',
            controller:'ListController',
            controllerAs:'listCtrl'
        })
        .when('/new',{
            templateUrl:'partials/new.html',
            controller:'NewController',
            controllerAs:'newCtrl'
        })
        .when('/details/:id',{
            templateUrl:'partials/details.html',
            controller:'DetailsController',
            controllerAs:'detailsCtrl'
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
    this.add = function(book) {
        book.$save();
    }
    this.get = function (index) {
        return Book.get({id: index});
    }
})

.controller('NewController', function (Books, Book) {
    var variable = this;
    variable.book = new Book();
    variable.saveBook = function () {
        Books.add(variable.book);
        variable.book = new Book();
    }
})

.controller('ListController', function (Books) {
    var variable = this;
    variable.books = Books.getAll();
})

.controller('DetailsController', function ($routeParams, Books) {
    var variable = this;
    var index = $routeParams.id;
    variable.book = Books.get(index);
});