package test.lab.dmm.com.mytestapp.db.entity

import android.arch.persistence.room.*
import android.arch.persistence.room.Index
import test.lab.dmm.com.mytestapp.models.MyMobileInfo
import test.lab.dmm.com.mytestapp.services.IP

@Entity(tableName = "mymobileinfo", indices = [Index("ssid", "bssid", unique = true)])
class MyMobileInfoEntity : MyMobileInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
    @ColumnInfo(name = "macid")
    var MACID: String? = null
    @ColumnInfo(name = "ip")
    var _IP: Int? = null
    @ColumnInfo(name = "ssid")
    var ssid: String? = null
    @ColumnInfo(name = "bssid")
    var bssid: String? = null
    @ColumnInfo(name = "frequency")
    var frequency: Int? = null
    @ColumnInfo(name = "link_speed")
    var linkSpeed: Int? = null
    @ColumnInfo(name = "gateway")
    var gateway: Int? = null
    @ColumnInfo(name = "netmask")
    var netmask: Int? = null
    @Ignore
    var myGIP: IP? = null

    constructor() {}

    constructor(MACID: String, _IP: Int, ssid: String, bssid: String, frequency: Int, linkSpeed: Int) {
        this.MACID = MACID
        this._IP = _IP
        this.ssid = ssid
        this.bssid = bssid
        this.frequency = frequency
        this.linkSpeed = linkSpeed
        this.gateway = gateway
        this.netmask = netmask
    }

    override fun getIP(): String{
        return ipToString(this._IP ?: 0)
    }


    private fun ipToString(i: Int): String {
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)

    }

    override fun toString(): String {
        return """
            MACID: ${this.MACID}
            GateWay: ${this.ipToString(this.gateway?: 0)}
            IP: ${this.getIP()}
            NETMASK: ${this.ipToString(this.netmask?: 0)}
            Frequency: ${this.frequency}
            linkSpeed: ${this.linkSpeed}
            SSID: ${this.ssid}
            BSSID: ${this.bssid}
            GIP: ${this.myGIP?.ip ?: "Need active scan"}
            """
    }
}