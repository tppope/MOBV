package sk.stu.fei.mobv

import android.app.Application
import sk.stu.fei.mobv.database.FirmDatabase
import sk.stu.fei.mobv.repository.FirmRepository

class MainApplication : Application(){
    val database: FirmDatabase by lazy { FirmDatabase.getDatabase(this) }
    val repository by lazy { FirmRepository(database.firmDao()) }
}