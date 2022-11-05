package sk.stu.fei.mobv.domain

import sk.stu.fei.mobv.database.firm.DatabaseFirm

data class Firm(
    val id: Long = 0,
    var name: String?,
    var type: String,
    var ownerName: String?,
    val latitude: String,
    val longitude: String,
    var phoneNumber: String?,
    var webPage: String?,
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