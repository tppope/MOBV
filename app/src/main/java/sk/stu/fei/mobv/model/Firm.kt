package sk.stu.fei.mobv.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Firm(
    @SerializedName("lat") @Expose val latitude: String? = null,
    @SerializedName("lon") @Expose val longitude: String? = null,
    @SerializedName("tags") @Expose val tags: Tags? = null
) {
    data class Tags(
        @SerializedName("operator") @Expose var ownerName: String? = null,
        @SerializedName("name") @Expose val firmName: String? = null,
        @SerializedName("phone") @Expose val phoneNumber: String? = null
    )
}

