package sk.stu.fei.mobv.ui.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.repository.FirmRepository
import java.io.IOException


class FirmViewModel(
    private val firmRepository: FirmRepository
) : ViewModel() {

    private var _networkError: MutableLiveData<Boolean?> = MutableLiveData(null)
    val networkError: LiveData<Boolean?>
        get() = _networkError

    private var _isSortAsc: MutableLiveData<Boolean> = MutableLiveData(true)

    val firmList: LiveData<List<Firm>> = Transformations.switchMap(_isSortAsc) { sort ->
        if (sort) firmRepository.getFirmListAsc() else firmRepository.getFirmListDesc()
    }

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

    fun sortFirmList(){
        _isSortAsc.value = (_isSortAsc.value)?.not()
    }

    fun validateEntry(latitude: String, longitude: String): MutableList<Int> {
        val entryErrorResources = mutableListOf<Int>()

        if (latitude.isBlank()) {
            entryErrorResources.add(R.string.latitude)
        }

        if (longitude.isBlank()) {
            entryErrorResources.add(R.string.longitude)
        }

        return entryErrorResources
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                firmRepository.refreshFirmList()
                this@FirmViewModel._networkError.value = false
            } catch (networkError: IOException) {
                this@FirmViewModel._networkError.value = true
            }
        }
    }

}
