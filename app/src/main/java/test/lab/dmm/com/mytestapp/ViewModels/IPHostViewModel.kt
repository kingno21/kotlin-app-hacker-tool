package test.lab.dmm.com.mytestapp.ViewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.NonNull
import test.lab.dmm.com.mytestapp.BasicApp
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.models.Tool
import test.lab.dmm.com.mytestapp.services.ScanService
import kotlin.concurrent.thread

class IPHostViewModel constructor(@NonNull application: Application): AndroidViewModel(application){

    private val mObservableList: MediatorLiveData<List<IPEntity>> = MediatorLiveData()

    init {
        mObservableList.value = null
        val database = (application as BasicApp).database
        thread {
            val lists = database.ipDao.liveAll(Tool.myMobileInfo.id!!)
            mObservableList.addSource(lists, mObservableList::setValue)
        }
    }

    fun getList(): LiveData<List<IPEntity>> {
        return mObservableList
    }
}