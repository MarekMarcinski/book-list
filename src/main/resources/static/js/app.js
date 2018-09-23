'use strict';

angular.module('app',['ngRoute','ngResource'])
.config(function($routeProvider, $httpProvider){
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
        .when('/login',{
            templateUrl:'partials/login.html',
            controller:'AuthenticationController',
            controllerAs:'authCtrl'
        })
        .when('/register',{
            templateUrl:'partials/register.html',
            controller:'RegisterController',
            controllerAs:'regCtrl'
        })
        .otherwise({
            redirectTo:'/list'
        });
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})

.constant('BOOK_ENDPOINT','/api/books/:id')
.constant('LOGIN_ENDPOINT','/login')
.constant('REGISTER_ENDPOINT', '/api/users/:id')

.factory('Book', function ($resource,BOOK_ENDPOINT) {
    return $resource(BOOK_ENDPOINT);
})

.factory('User', function ($resource,REGISTER_ENDPOINT) {
    return $resource(REGISTER_ENDPOINT);
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

.service('Users', function(User) {
    this.addUser = function(user) {
        user.$save();
    }
})

.service('AuthenticationService',function ($http, LOGIN_ENDPOINT) {
    this.authenticate = function (credentials, successCallback) {
        var authHeader = {Authorization: 'Basic ' + btoa(credentials.username+':'+credentials.password)};
        var config = {headers:authHeader};
        $http.post(LOGIN_ENDPOINT,{},config)
            .then(function success(value) {
                successCallback();
            },function error(reason) {
                console.log('Login error');
                console.log(reason);
            });
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
})

.controller('AuthenticationController', function ($rootScope, $location, AuthenticationService) {
    var variable = this;
    variable.credentials = {};
    var loginSuccess = function () {
        $rootScope.authenticated = true;
        $location.path('/new');
    }
    variable.login = function () {
        AuthenticationService.authenticate(variable.credentials, loginSuccess);
    }
})

.controller('RegisterController', function (Users, User) {
    var variable = this;
    variable.user = new User();
    variable.register = function () {
        Users.addUser(variable.user);
        variable.user = new User();
    }
});