package sk.stu.fei.mobv.data

import android.content.Context
import android.util.Log
import sk.stu.fei.mobv.MainActivity
import java.io.IOException

abstract class Datasource {
    fun readJson(context: Context, path: String): String? {
        return try {
            context.assets.open(path)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.e("Datasource", ioException.toString())
            null
        }
    }
}