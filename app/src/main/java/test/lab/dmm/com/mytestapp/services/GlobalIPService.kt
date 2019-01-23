package test.lab.dmm.com.mytestapp.services

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GlobalIPService {
    @GET("/")
    fun myGIP(@Query("format") format: String = "json"): Call<IP>
}

object gService {
    fun GetGlobalIP(callback: (myGIP: IP?) -> Unit) {
        val req = Retrofit.Builder()
                .baseUrl("https://api.ipify.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = req.create(GlobalIPService::class.java)
        val res: Call<IP> = service.myGIP()
        res.enqueue(object: Callback<IP> {
            override fun onFailure(call: Call<IP>?, t: Throwable?) {
                Log.e("Network Error", "fail to get ip")
            }

            override fun onResponse(call: Call<IP>?, response: Response<IP>?) {
                response?.body()?.let {
                    callback(response?.body())
                }
            }

        })
    }
}
