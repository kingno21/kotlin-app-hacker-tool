package test.lab.dmm.com.mytestapp.services

//import test.lab.dmm.com.mytestapp.ViewModels.ToolListViewModel
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.models.Tool
import java.net.InetAddress
import java.net.Socket
import java.net.SocketException

class ScanService(private val ssid: String, private val bssid: String) {
    private val PortList = arrayListOf(22, 23, 80, 443, 3000, 3304, 3389, 8000, 8080, 8888)
    private var tasksQueue = arrayListOf<AsyncHttpTasks>()
    val resultList: MutableList<IPEntity> = arrayListOf()
    var isDone = MutableLiveData<Boolean>()

    fun StartScan() {
        val netmask = IPAddress(Tool.myMobileInfo.netmask!!)
        val netAddress = IPAddress(Tool.myMobileInfo._IP!!) and netmask

        for (i in 1..netmask.countAddresses()) {
            tasksQueue.add(AsyncHttpTasks((netAddress.min() + i).toString()))
        }

        isDone.value = false
        tasksQueue.forEach { it.execute() }
    }

    fun StopScan() {
        tasksQueue.forEach { it.cancel(true) }
        tasksQueue.clear()
        isDone.value = true
    }

    inner class AsyncHttpTasks(private val host: String): AsyncTask<String, String, Unit>() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun doInBackground(vararg params: String?) {
            LogService.li("ping", host)

            try {
                val inet: InetAddress = InetAddress.getByName(host)
                if (inet.isReachable(200)) {
                    LogService.li("response", "alive")
                    var so: Socket? = null


                    val ports: MutableList<Int> = arrayListOf()
                    for (i in PortList) {
                        try {
                            so = Socket(inet!!, i)
                            ports.add(i)
                        } catch (e: SocketException) {
                        } finally {
                            so?.close()
                        }
                    }

                    resultList.add(IPEntity(host, ports.toString(), Tool.myMobileInfo.id!!))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            tasksQueue.remove(this)
            if (tasksQueue.size == 0) {
                isDone.value = true
            }
        }
    }
}