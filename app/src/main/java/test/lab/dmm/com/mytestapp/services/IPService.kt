package test.lab.dmm.com.mytestapp.services

data class IP(val ip: String, val ports: List<out Int> = arrayListOf())

data class IPAddress constructor(val ip: Int) {
    private val fix = 0xFF
    private val min = 0x01
    private var r1: Int = 0
    private var r2: Int = 0
    private var r3: Int = 0
    private var r4: Int = 0
    init {
        r1 = ip and fix
        r2 = ip shr 8 and fix
        r3 = ip shr 16 and fix
        r4 = ip shr 24 and fix
    }

    constructor(r1: Int, r2: Int, r3: Int, r4: Int): this(0) {
        this.r1 = r1
        this.r2 = r2
        this.r3 = r3
        this.r4 = r4
    }
    override fun toString(): String {
        return "${this.r1}.${this.r2}.${this.r3}.${this.r4}"
    }

    infix fun and(ip: IPAddress): IPAddress {
        val r1 = this.r1 and ip.r1 and fix
        val r2 = this.r2 and ip.r2 and fix
        val r3 = this.r3 and ip.r3 and fix
        val r4 = this.r4 and ip.r4 and fix
        return IPAddress(r1, r2, r3, r4)
    }

    fun min(): IPAddress {
        return IPAddress(
                this.r1,
                this.r2,
                this.r3,
                this.r4 or min
        )
    }

    fun countAddresses(): Int {
        return (this.r4 xor fix)  + (this.r3 xor fix) * 256 + (this.r2 xor fix) * 65536 + (this.r1 xor fix) * 16777216 - 1
    }

    operator fun plus(x: Int): IPAddress {
        this.r4 = (this.r4 + x) % 256
        this.r3 = (this.r3 + x / 256) % 256
        this.r2 = (this.r2 + x / 65536) % 256
        this.r1 = (this.r1 + x / 16777216) % 256
        return this
    }
}