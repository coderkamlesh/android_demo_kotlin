package com.example.demo.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// DataStore instance (extension on Context)
private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {

    // Keys (DataStore ke andar storage keys)
    private object Keys {
        val TOKEN = stringPreferencesKey("token")
        val USER_ID = stringPreferencesKey("user_id")
        val NAME = stringPreferencesKey("name")
        val MOBILE = stringPreferencesKey("mobile")
        val ROLE_ID = intPreferencesKey("role_id")
    }

    // 🔐 Session save (login ke baad call hoga)
    suspend fun saveSession(
        token: String,
        userId: String,
        name: String,
        mobile: String,
        roleId: Int
    ) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = token
            prefs[Keys.USER_ID] = userId
            prefs[Keys.NAME] = name
            prefs[Keys.MOBILE] = mobile
            prefs[Keys.ROLE_ID] = roleId
        }
    }

    // 📤 Token read (Interceptor me use hoga)
    val token: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Keys.TOKEN] }

    // 👤 UserId
    val userId: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Keys.USER_ID] }

    // 👤 Name
    val name: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Keys.NAME] }

    // 📱 Mobile
    val mobile: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[Keys.MOBILE] }

    // 🧑 Role
    val roleId: Flow<Int?> = context.dataStore.data
        .map { prefs -> prefs[Keys.ROLE_ID] }

    // 🚪 Logout
    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}