package sk.stu.fei.mobv.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.stu.fei.mobv.database.firm.DatabaseFirm
import sk.stu.fei.mobv.database.firm.FirmDao
import sk.stu.fei.mobv.database.firm.asDomainModel
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.network.*

class FirmRepository(private val firmDao: FirmDao) {
    fun getFirmListAsc(): LiveData<List<Firm>> = Transformations.map(firmDao.getAllFirmsAsc().asLiveData()) {
        it.asDomainModel()
    }

    fun getFirmListDesc(): LiveData<List<Firm>> = Transformations.map(firmDao.getAllFirmsDesc().asLiveData()) {
        it.asDomainModel()
    }

    fun getFirm(firmId: Long): LiveData<Firm> {
        return Transformations.map(firmDao.getFirm(firmId).asLiveData()) {
            it.asDomainModel()
        }
    }

    suspend fun addFirm(databaseFirm: DatabaseFirm) {
        firmDao.insert(databaseFirm)
    }

    suspend fun editFirm(databaseFirm: DatabaseFirm) {
        firmDao.update(databaseFirm)
    }

    suspend fun deleteFirm(databaseFirm: DatabaseFirm) {
        firmDao.delete(databaseFirm)
    }


    suspend fun refreshFirmList() {
        withContext(Dispatchers.IO) {
            val firmContainer: FirmContainerDto = FirmApi.retrofitService.getFirms()
            firmDao.insertAll(firmContainer.asDatabaseModel())
        }
    }
}