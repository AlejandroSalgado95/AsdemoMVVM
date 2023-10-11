package com.example.asdemoapp.utils

sealed class ErrorType {
    class ServerError : ErrorType()
    class NetworkError : ErrorType()
    class UnknownError : ErrorType()
}
