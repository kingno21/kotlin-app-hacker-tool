package test.lab.dmm.com.mytestapp.db.dao

import android.arch.persistence.room.*
import test.lab.dmm.com.mytestapp.db.entity.MyMobileInfoEntity
import test.lab.dmm.com.mytestapp.models.MyMobileInfo

@Dao
interface MyMobuileInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(myMobileInfo: MyMobileInfoEntity)

    @Query("select * from mymobileinfo;")
    fun showAll(): List<MyMobileInfoEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(myMobileInfo: MyMobileInfoEntity)

    @Query("SELECT * FROM mymobileinfo ORDER BY id DESC LIMIT 1")
    fun lastOne(): MyMobileInfoEntity

    @Query("select * from mymobileinfo where ssid = :ssid and bssid = :bssid;")
    fun exsit(ssid: String, bssid: String): MyMobileInfoEntity?
}