package test.lab.dmm.com.mytestapp

import android.app.Application
import test.lab.dmm.com.mytestapp.db.AppDatabase

class BasicApp : Application() {

    lateinit var mAppExecutors: AppExecutors

    val database: AppDatabase
        get() = AppDatabase.getInstance(this, mAppExecutors)!!

    override fun onCreate() {
        super.onCreate()

        mAppExecutors = AppExecutors()
    }
}
