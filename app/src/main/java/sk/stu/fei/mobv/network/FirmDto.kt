package sk.stu.fei.mobv.network

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import sk.stu.fei.mobv.database.firm.DatabaseFirm
import sk.stu.fei.mobv.domain.Firm

data class FirmContainerDto(@Json(name = "documents") val firmList: List<FirmDto>)

data class FirmDto(
    @Json(name = "id") val id: Long,
    @Json(name = "lat") val latitude: String,
    @Json(name = "lon") val longitude: String,
    @Json(name = "tags") val tags: FirmTagDto,
)

data class FirmTagDto(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amenity")
    val type: String,
    @Json(name = "operator")
    val ownerName: String?,
    @Json(name = "phone")
    val phoneNumber: String?,
    @Json(name = "website")
    val webPage: String?,
)


fun FirmContainerDto.asDomainModel(): List<Firm> {
    return firmList.map {
        Firm(
            id = it.id,
            name = it.tags.name,
            type = it.tags.type,
            ownerName = it.tags.ownerName,
            latitude = it.latitude,
            longitude = it.longitude,
            phoneNumber = it.tags.phoneNumber,
            webPage = it.tags.webPage
        )
    }
}

fun FirmContainerDto.asDatabaseModel(): List<DatabaseFirm> {
    return firmList.map {
        DatabaseFirm(
            id = it.id,
            name = it.tags.name,
            type = it.tags.type,
            ownerName = it.tags.ownerName,
            latitude = it.latitude,
            longitude = it.longitude,
            phoneNumber = it.tags.phoneNumber,
            webPage = it.tags.webPage
        )
    }
}