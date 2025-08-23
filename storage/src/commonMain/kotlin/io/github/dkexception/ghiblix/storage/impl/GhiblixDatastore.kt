package io.github.dkexception.ghiblix.storage.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.dkexception.ghiblix.storage.contract.IGhiblixDatastore
import kotlinx.coroutines.flow.first

internal class GhiblixDatastore(
    private val datastore: DataStore<Preferences>
) : IGhiblixDatastore {

    override suspend fun setInt(key: String, value: Int) {
        val intKey = intPreferencesKey(key)
        datastore.edit { it[intKey] = value }
    }

    override suspend fun setString(key: String, value: String) {
        val stringKey = stringPreferencesKey(key)
        datastore.edit { it[stringKey] = value }
    }

    override suspend fun setBoolean(key: String, value: Boolean) {
        val boolKey = booleanPreferencesKey(key)
        datastore.edit { it[boolKey] = value }
    }

    override suspend fun getInt(key: String, default: Int): Int {
        val intKey = intPreferencesKey(key)
        val prefs = datastore.data.first()
        return prefs[intKey] ?: default
    }

    override suspend fun getString(key: String, default: String): String {
        val stringKey = stringPreferencesKey(key)
        val prefs = datastore.data.first()
        return prefs[stringKey] ?: default
    }

    override suspend fun getBoolean(key: String, default: Boolean): Boolean {
        val boolKey = booleanPreferencesKey(key)
        val prefs = datastore.data.first()
        return prefs[boolKey] ?: default
    }
}
