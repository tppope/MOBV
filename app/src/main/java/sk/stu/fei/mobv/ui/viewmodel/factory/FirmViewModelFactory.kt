package sk.stu.fei.mobv.ui.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.stu.fei.mobv.ui.viewmodel.FirmViewModel


class FirmViewModelFactory(private val mainApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FirmViewModel(mainApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}