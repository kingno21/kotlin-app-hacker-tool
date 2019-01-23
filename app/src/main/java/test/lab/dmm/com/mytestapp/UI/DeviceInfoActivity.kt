package test.lab.dmm.com.mytestapp.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import test.lab.dmm.com.mytestapp.BasicApp
import test.lab.dmm.com.mytestapp.R
import test.lab.dmm.com.mytestapp.models.Tool
import test.lab.dmm.com.mytestapp.services.gService

class DeviceInfoActivity : AppCompatActivity(), CommonAbility, BasicActivity {

    private lateinit var contentText: TextView
    private lateinit var refreshBth: Button
    private lateinit var processBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)
        init()
        bindEvents()
    }

    override fun init() {
        refreshBth = findViewById(R.id.button)
        processBar = findViewById(R.id.progressBar3)
        contentText = findViewById(R.id.textView2)

        contentText.text = Tool.myMobileInfo.toString()

    }

    override fun bindEvents() {
        refreshBth.setOnClickListener {
            Toast.makeText(this@DeviceInfoActivity, "Start tracking", Toast.LENGTH_SHORT).show()
            refreshBth.isClickable = false
            processBar.visibility = ProgressBar.VISIBLE

            val database = (application as BasicApp).database

            gService.GetGlobalIP {
                refreshBth.isClickable = true
                processBar.visibility = ProgressBar.INVISIBLE

                this.loadWifiInfo(applicationContext)

                Tool.myMobileInfo.myGIP = it
                Tool.update(database)

                contentText.text = Tool.myMobileInfo.toString()
            }
        }
    }


}