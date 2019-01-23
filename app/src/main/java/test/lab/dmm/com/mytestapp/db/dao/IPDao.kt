package test.lab.dmm.com.mytestapp.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import test.lab.dmm.com.mytestapp.db.entity.IPEntity

@Dao
interface IPDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ips: List<IPEntity>)

    @Query("select * from iphost where mymobileinfo_id = :id order by id;")
    fun showAll(id: Int): List<IPEntity>

    @Query("select * from iphost where mymobileinfo_id = :id order by id;")
    fun liveAll(id: Int): LiveData<List<IPEntity>>
}