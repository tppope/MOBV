package sk.stu.fei.mobv.data

import android.content.Context
import android.content.res.AssetManager
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import sk.stu.fei.mobv.model.Firm
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL


class FirmDatasourceTests {
    private val context: Context = mock(Context::class.java)
    private val assetManager: AssetManager = mock(AssetManager::class.java)

    @Test
    fun it_load_firm_data() {
        doReturn(assetManager).`when`(context).assets
        val resource: URL =
            FirmDatasourceTests::class.java.getClassLoader().getResource("parametersandroid.xml")

        val inputStream: InputStream = FileInputStream(resource.path)
        doReturn(inputStream).`when`(assetManager).open(anyString())

        val firmList: List<Firm> = FirmDatasource().loadFirms(context)
        firmList.forEach{ println(it.latitude) }
    }
}