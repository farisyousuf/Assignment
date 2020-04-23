package com.example.enbdassignment.data.models.common

/**
 * A wrapper for database and network states.
 */
sealed class ResultState<T> {
    /**
     * Success State
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * Error State
     */
    data class Error<T>(val error: String?) : ResultState<T>()
}