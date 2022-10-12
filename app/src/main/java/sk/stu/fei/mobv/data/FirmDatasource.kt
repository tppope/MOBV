package sk.stu.fei.mobv.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sk.stu.fei.mobv.model.Firm

class FirmDatasource: Datasource() {

    fun loadFirms(context: Context): List<Firm> {
        val listFirmType = object : TypeToken<List<Firm>>() {}.type
        return Gson().fromJson(readJson(context, "firms/pubs.json"), listFirmType)
    }

}