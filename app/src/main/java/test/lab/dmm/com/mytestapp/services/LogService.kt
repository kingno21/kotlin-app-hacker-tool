package test.lab.dmm.com.mytestapp.services

import android.annotation.SuppressLint
import android.util.Log

object LogService {
    @SuppressLint("LongLogTag")
    fun li(x: String, msg: String) {
        Log.i("------------- $x -------------", msg)
    }
}