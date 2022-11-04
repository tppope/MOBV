package sk.stu.fei.mobv.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv.MainApplication
import sk.stu.fei.mobv.data.SettingsDataStore
import sk.stu.fei.mobv.database.FirmDatabase
import sk.stu.fei.mobv.database.firm.FirmDao
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.repository.FirmRepository
import java.io.IOException

class FirmViewModel(
    application: Application
): ViewModel() {

    private val firmRepository: FirmRepository = FirmRepository(FirmDatabase.getDatabase(application))

    private val settingsDataStore: SettingsDataStore = SettingsDataStore(application)

    val firmList: LiveData<List<Firm>> = firmRepository.firmList

    lateinit var firm: LiveData<Firm>

    fun getFirm(firmId: Long): LiveData<Firm> {
        firm = firmRepository.getFirm(firmId)
        return firm
    }

    fun addFirm(firm: Firm) {
        viewModelScope.launch {
            firmRepository.addFirm(firm.asDatabaseModel())
        }
    }

    fun editFirm(firm: Firm) {
        viewModelScope.launch {
            firmRepository.editFirm(firm.asDatabaseModel())
        }
    }

    fun deleteFirm(firm: Firm) {
        viewModelScope.launch {
            firmRepository.deleteFirm(firm.asDatabaseModel())
        }
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                firmRepository.refreshFirmList()
                settingsDataStore.saveIsFirmRefreshedPreferencesStore(true)

            } catch (networkError: IOException) {
                Log.d("AHOJ", "Refreshingerrr")
            }
        }
    }

}
