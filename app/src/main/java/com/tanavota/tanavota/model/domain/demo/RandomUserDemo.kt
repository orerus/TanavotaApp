package com.tanavota.tanavota.model.domain.demo

data class RandomUserDemo(var info: Info,
                          var results: List<Result>)

data class Info(var seed: String,
                var results: Int,
                var page: Int,
                var version: String)

data class Result(var gender: String,
                  var email: String,
                  var registered: String,
                  var dob: String,
                  var phone: String,
                  var cell: String)