package com.msib.growsmart.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.msib.growsmart.data.UserModel
import com.msib.growsmart.network.ApiConfig
import com.msib.growsmart.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[AUTH_TOKEN] ?: "",
                preferences[ID_USER] ?: 0,
                preferences[NAME_USER] ?: "",
                preferences[EMAIL_USER] ?: "",
                preferences[ERROR] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel){
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = user.token
            preferences[ID_USER] = user.idUser
            preferences[NAME_USER] = user.name
            preferences[EMAIL_USER] = user.email
            preferences[ERROR] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = ""
            preferences[ID_USER] = 0
            preferences[NAME_USER] = ""
            preferences[EMAIL_USER] = ""
            preferences[ERROR] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val AUTH_TOKEN = stringPreferencesKey(Constant.AUTH_PREFERENCES)
        private val ID_USER = intPreferencesKey(Constant.ID_USER)
        private val NAME_USER = stringPreferencesKey(Constant.NAME_USER)
        private val EMAIL_USER = stringPreferencesKey(Constant.EMAIL_USER)
        private val ERROR = booleanPreferencesKey(Constant.STATE_PREFERENCES)
        private val THEME_KEY = booleanPreferencesKey(Constant.THEME_KEY)

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
        fun setToken(token: String) {
            ApiConfig.setToken(token)
        }
    }

}