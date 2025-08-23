package io.github.dkexception.ghiblix.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private class AndroidDataStoreCreator : DataStoreCreator(), KoinComponent {

    val context: Context by inject()

    override fun createDataStore(): DataStore<Preferences> = dataStoreCreationHelper {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    }
}

internal actual val dataStoreCreator: DataStoreCreator = AndroidDataStoreCreator()
