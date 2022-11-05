package sk.stu.fei.mobv.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.data.SettingsDataStore
import sk.stu.fei.mobv.database.FirmDatabase
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.repository.FirmRepository
import java.io.IOException


class FirmViewModel(
    private val application: Application
) : ViewModel() {

    private val firmRepository: FirmRepository =
        FirmRepository(FirmDatabase.getDatabase(application))

    private val settingsDataStore: SettingsDataStore = SettingsDataStore(application)

    private var _isSortAsc: MutableLiveData<Boolean> = MutableLiveData(true)
    val isSortAsc: MutableLiveData<Boolean>
        get() = _isSortAsc

    val firmList: LiveData<List<Firm>> = Transformations.switchMap(_isSortAsc) { sort ->
        val newFirmList =
            if (sort) firmRepository.getFirmListAsc() else firmRepository.getFirmListDesc()
        Transformations.map(newFirmList) {
            it.forEach {
                handleMissingFirmInformations(it)
            }
            it
        }
    }

    lateinit var firm: LiveData<Firm>


    fun getFirm(firmId: Long): LiveData<Firm> {
        firm = Transformations.map(firmRepository.getFirm(firmId)) {
            handleMissingFirmInformations(it)
        }
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

    fun handleMissingFirmInformations(firm: Firm): Firm {
        if (firm.name == null) {
            firm.name = application.getString(R.string.firm_without_name)
        }
        if (firm.ownerName == null) {
            firm.ownerName = application.getString(R.string.firm_without_owner)
        }
        return firm
    }

    fun validateEntry(latitude: String, longitude: String): String {
        var entryErrorMessage = ""

        if (latitude.isBlank()) {
            entryErrorMessage += application.getString(R.string.latitude).lowercase()
        }

        if (longitude.isBlank()) {
            entryErrorMessage += (if (entryErrorMessage.isNotEmpty()) ", " else "") +
                    application.getString(R.string.longitude).lowercase()
        }

        if (entryErrorMessage.isNotEmpty()) {
            entryErrorMessage = application.getString(R.string.is_required, entryErrorMessage)
        }

        return entryErrorMessage
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
