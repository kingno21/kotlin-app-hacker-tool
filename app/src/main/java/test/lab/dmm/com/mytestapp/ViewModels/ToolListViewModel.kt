package test.lab.dmm.com.mytestapp.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.NonNull
import test.lab.dmm.com.mytestapp.services.ScanService

class ToolListViewModel(@NonNull application: Application): AndroidViewModel(application){
    var currentIP = MutableLiveData<String>()
    lateinit var scanService: ScanService
}