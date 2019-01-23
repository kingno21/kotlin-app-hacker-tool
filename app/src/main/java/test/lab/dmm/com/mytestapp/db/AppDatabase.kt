package test.lab.dmm.com.mytestapp.db

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import test.lab.dmm.com.mytestapp.AppExecutors
import test.lab.dmm.com.mytestapp.db.converter.DateConverter
import test.lab.dmm.com.mytestapp.db.dao.IPDao
import test.lab.dmm.com.mytestapp.db.dao.MyMobuileInfoDao
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.db.entity.MyMobileInfoEntity


@Database(entities = [(MyMobileInfoEntity::class), (IPEntity::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()

    abstract val myMobileInfoDao: MyMobuileInfoDao
    abstract val ipDao: IPDao

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated()
        }
    }

    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }

    companion object {
        private var sInstance: AppDatabase? = null
        const val DATABASE_NAME = "testApp.db"

        fun getInstance(context: Context, executors: AppExecutors): AppDatabase? {
            if (sInstance == null) {
                synchronized(AppDatabase::class.java) {
                    if (sInstance == null) {
                        sInstance = buildDatabase(context, executors)
                        sInstance?.let {
                            it.updateDatabaseCreated(context)
                        }
                    }
                }
            }
            return sInstance
        }

        private fun buildDatabase(appContext: Context,
                                  executors: AppExecutors): AppDatabase {
            return Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            executors.diskIO().execute {
                                // Add a delay to simulate a long-running operation
                                addDelay()
                                // Generate the data for pre-population
                                val database = AppDatabase.getInstance(appContext, executors)

                                // notify that the database was created and it's ready to be used
                                database?.setDatabaseCreated()
                            }
                        }
                    }).build()
        }

        private fun addDelay() {
            try {
                Thread.sleep(4000)
            } catch (ignored: InterruptedException) {
            }
        }
    }
}