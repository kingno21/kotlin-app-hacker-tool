package test.lab.dmm.com.mytestapp.db.entity

import android.arch.persistence.room.*

@Entity(
        tableName = "iphost",
        foreignKeys = [(ForeignKey(entity = MyMobileInfoEntity::class, parentColumns = ["id"], childColumns = ["mymobileinfo_id"]))],
        indices = [(Index("host", unique = true))]
)
class IPEntity(
        @ColumnInfo(name = "host")
        val host: String,
        @ColumnInfo(name = "ports")
        val ports: String,
        @ColumnInfo(name = "mymobileinfo_id")
        val myMobileInfoId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

    override fun toString(): String {
        return """
            from: $myMobileInfoId,to:${this.host}/${this.ports}
        """.trimIndent()
    }

    fun toShow(): String {
        return "${this.host}/${this.ports}"
    }
}