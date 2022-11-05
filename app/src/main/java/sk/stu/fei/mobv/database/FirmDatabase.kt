package sk.stu.fei.mobv.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sk.stu.fei.mobv.database.firm.DatabaseFirm
import sk.stu.fei.mobv.database.firm.FirmDao

@Database(entities = [DatabaseFirm::class], version = 2, exportSchema = false)
abstract class FirmDatabase : RoomDatabase() {
    abstract fun firmDao(): FirmDao

    companion object {
        @Volatile
        private var INSTANCE: FirmDatabase? = null
        fun getDatabase(context: Context): FirmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FirmDatabase::class.java,
                    "firm_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}