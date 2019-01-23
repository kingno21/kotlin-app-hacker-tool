package test.lab.dmm.com.mytestapp.models

import test.lab.dmm.com.mytestapp.db.AppDatabase
import test.lab.dmm.com.mytestapp.db.entity.MyMobileInfoEntity
import test.lab.dmm.com.mytestapp.services.LogService
import kotlin.concurrent.thread

sealed class Tool {
    open val tool_name: String = ""

    companion object {
        var myMobileInfo = MyMobileInfoEntity()

        fun save(database: AppDatabase) {
            thread {
                if (database.myMobileInfoDao.exsit(Tool.myMobileInfo.ssid!!, Tool.myMobileInfo.bssid!!) == null) {
                    database.myMobileInfoDao.insert(this.myMobileInfo)
                }
                myMobileInfo = database.myMobileInfoDao.lastOne()
            }
        }

        fun update(database: AppDatabase) {
            thread {
                database.myMobileInfoDao.update(this.myMobileInfo)
                myMobileInfo = database.myMobileInfoDao.lastOne()
            }
        }
    }

    class DeviceInfoTool: Tool() {
        override val tool_name = "DeviceInfo"
    }
    class IPScanTool: Tool() {
        override val tool_name = "IPScan"
    }
}