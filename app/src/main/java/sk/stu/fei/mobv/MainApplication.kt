package sk.stu.fei.mobv

import android.app.Application
import sk.stu.fei.mobv.database.FirmDatabase

class MainApplication : Application(){
    val database: FirmDatabase by lazy { FirmDatabase.getDatabase(this) }
}