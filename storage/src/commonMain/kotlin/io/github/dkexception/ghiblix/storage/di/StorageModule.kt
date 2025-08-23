package io.github.dkexception.ghiblix.storage.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.dkexception.ghiblix.storage.contract.IGhiblixDatastore
import io.github.dkexception.ghiblix.storage.dataStoreCreator
import io.github.dkexception.ghiblix.storage.impl.GhiblixDatastore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val storageModule = module {

    single<DataStore<Preferences>> {
        dataStoreCreator.createDataStore()
    }

    singleOf(::GhiblixDatastore) bind IGhiblixDatastore::class
}
