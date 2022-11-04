package sk.stu.fei.mobv.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val REFRESH_DATA_PREFERENCES_NAME = "refresh_data_preferences"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = REFRESH_DATA_PREFERENCES_NAME
)

class SettingsDataStore(private val context: Context) {
    private val isFirmsRefreshed = booleanPreferencesKey("is_firm_refreshed")

    suspend fun saveIsFirmRefreshedPreferencesStore(refreshedFirms: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isFirmsRefreshed] = refreshedFirms
        }
    }

    val refreshDataPreferences: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[isFirmsRefreshed] ?: false
        }
}