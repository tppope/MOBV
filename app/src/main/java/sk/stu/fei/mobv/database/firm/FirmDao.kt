package sk.stu.fei.mobv.database.firm

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FirmDao {

    @Query("SELECT * from firms WHERE id = :id")
    fun getFirm(id: Long): Flow<DatabaseFirm>

    @Query("SELECT * from firms")
    fun getAllFirms(): Flow<List<DatabaseFirm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(firm: DatabaseFirm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( firms: List<DatabaseFirm>)

    @Update
    suspend fun update(firm: DatabaseFirm)

    @Delete
    suspend fun delete(firm: DatabaseFirm)
}
