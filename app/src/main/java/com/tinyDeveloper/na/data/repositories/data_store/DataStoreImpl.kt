package com.tinyDeveloper.na.data.repositories.data_store

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.repositories.DataStore
import com.tinyDeveloper.na.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = Constants.APP_INFO_PREFERENCES)
class DataStoreImpl(context: Context): DataStore {
    private object PreferencesKey{
        val phoneKey = stringPreferencesKey(name = Constants.APP_INFO_PHONE_ARGUMENT)
        val passwordKey = stringPreferencesKey(name = Constants.APP_INFO_PASSWORD_ARGUMENT)
    }

    private val dataStore = context.dataStore

    override suspend fun setInfo(info: BasicRequest) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.phoneKey] = info.phone
            preferences[PreferencesKey.passwordKey] = info.password
        }
    }

    override val info: Flow<BasicRequest> = dataStore.data
        .catch {exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {preferences ->
            BasicRequest(
                preferences[PreferencesKey.phoneKey]?: "",
                preferences[PreferencesKey.passwordKey]?: ""
            )
        }
}