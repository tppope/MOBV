package sk.stu.fei.mobv.domain

import sk.stu.fei.mobv.database.firm.DatabaseFirm

data class Firm(
    val id: Long = 0,
    val name: String?,
    val type: String,
    val ownerName: String?,
    val latitude: String?,
    val longitude: String?,
    val phoneNumber: String?,
    val webPage: String?,
){
    fun asDatabaseModel(): DatabaseFirm {
        return DatabaseFirm(
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
}