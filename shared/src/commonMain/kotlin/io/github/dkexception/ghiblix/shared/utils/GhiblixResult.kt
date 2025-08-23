@file:OptIn(ExperimentalContracts::class)

package io.github.dkexception.ghiblix.shared.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed class GhiblixResult<out R> {

    data class Success<out T>(val data: T) : GhiblixResult<T>()

    data class Failure(val exception: Exception) : GhiblixResult<Nothing>()

    data object Loading : GhiblixResult<Nothing>()
}

inline fun <R, T> GhiblixResult<T>.fold(
    onLoading: () -> R,
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R {
    contract {
        callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
        callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        GhiblixResult.Loading -> onLoading()
        is GhiblixResult.Failure -> onFailure(this.exception)
        is GhiblixResult.Success<T> -> onSuccess(this.data)
    }
}
