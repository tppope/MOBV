package sk.stu.fei.mobv.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Firm(
    @SerializedName("id") @Expose val id: Long,
    @SerializedName("lat") @Expose val latitude: String? = null,
    @SerializedName("lon") @Expose val longitude: String? = null,
    @SerializedName("tags") @Expose val tags: Tags
) {
    data class Tags(
        @SerializedName("operator") @Expose var ownerName: String = "",
        @SerializedName("name") @Expose val firmName: String? = null,
        @SerializedName("phone") @Expose val phoneNumber: String? = null,
        @SerializedName("website") @Expose val webUrl: String? = "google.com"
    )
}

