package sk.stu.fei.mobv.database.firm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.stu.fei.mobv.domain.Firm

@Entity(tableName = "firms")
data class DatabaseFirm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "owner_name")
    val ownerName: String?,
    @ColumnInfo(name = "latitude")
    val latitude: String?,
    @ColumnInfo(name = "longitude")
    val longitude: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
    @ColumnInfo(name = "web")
    val webPage: String?,
)


fun DatabaseFirm.asDomainModel(): Firm {
    return Firm(
        id = id,
        name = name,
        type = type,
        ownerName = ownerName,
        latitude = latitude,
        longitude = longitude,
        phoneNumber = phoneNumber,
        webPage = webPage
    )
}

fun List<DatabaseFirm>.asDomainModel(): List<Firm> {
    return map {
        it.asDomainModel()
    }
}