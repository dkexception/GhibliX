package io.github.dkexception.ghiblix.storage.contract

interface IGhiblixDatastore {

    suspend fun setInt(key: String, value: Int)

    suspend fun setString(key: String, value: String)

    suspend fun setBoolean(key: String, value: Boolean)

    suspend fun getInt(key: String, default: Int = 0): Int

    suspend fun getString(key: String, default: String = ""): String

    suspend fun getBoolean(key: String, default: Boolean = false): Boolean
}
