package test.lab.dmm.com.mytestapp.UI

import android.content.Context
import android.net.wifi.WifiManager
import test.lab.dmm.com.mytestapp.AppExecutors
import test.lab.dmm.com.mytestapp.db.AppDatabase
import test.lab.dmm.com.mytestapp.db.entity.MyMobileInfoEntity
import test.lab.dmm.com.mytestapp.models.MyMobileInfo
import test.lab.dmm.com.mytestapp.models.Tool

interface CommonAbility {
    fun loadWifiInfo(context: Context) {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (manager.isWifiEnabled) {
            with(manager.connectionInfo) {
                Tool.myMobileInfo = MyMobileInfoEntity(
                        this.macAddress,
                        this.ipAddress,
                        this.ssid,
                        this.bssid,
                        this.frequency,
                        this.linkSpeed)
            }

            with(manager.dhcpInfo) {
                Tool.myMobileInfo.gateway = this.gateway
                Tool.myMobileInfo.netmask = this.netmask
            }
        }
    }
}