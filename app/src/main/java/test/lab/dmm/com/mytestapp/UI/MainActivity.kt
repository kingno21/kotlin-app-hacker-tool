package test.lab.dmm.com.mytestapp.UI

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import test.lab.dmm.com.mytestapp.BasicApp
import test.lab.dmm.com.mytestapp.R
import test.lab.dmm.com.mytestapp.ViewModels.ToolListViewModel
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.models.Tool
import test.lab.dmm.com.mytestapp.services.LogService
import test.lab.dmm.com.mytestapp.services.ScanService
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), CommonAbility, BasicActivity {

    private lateinit var toolList: ListView
    private lateinit var grayLayout: ConstraintLayout
    private lateinit var cancelBth: Button
    private lateinit var viewModel: ToolListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        bindEvents()
    }

    override fun init() {
        this.loadWifiInfo(applicationContext)

        toolList = this.findViewById(R.id.listView)
        grayLayout = this.findViewById(R.id.constraintLayout1)
        cancelBth = this.findViewById(R.id.button2)

        viewModel = ViewModelProviders.of(this).get(ToolListViewModel::class.java)
        with(Tool.myMobileInfo) {
            viewModel.scanService = ScanService(this.ssid!!, this.bssid!!)
        }

        this.saveMobile()

        toolList.adapter = this.createAdapter()
        this.toolList.isEnabled = true

    }

    override fun bindEvents() {
        toolList.onItemClickListener = ToolListOnClick {
            showLoading()
            viewModel.scanService.StartScan()
        }

        cancelBth.setOnClickListener {
            viewModel.scanService.StopScan()
        }

        viewModel.currentIP.observe(this, Observer {
            LogService.li("changing", it!!)
        })


        viewModel.scanService.isDone.observe(this, Observer {
            if(it!!) {
                dismissLoading()

                val database = (application as BasicApp).database
                val result: List<IPEntity> = viewModel.scanService.resultList

                (application as BasicApp).mAppExecutors.diskIO().execute {
                    database.ipDao.insertAll(result)

                    viewModel.scanService.resultList.clear()
                    val intent = Intent(applicationContext, IPHostListActivity::class.java)
                    startActivity(intent)
                }
            }
        })

    }


    private fun saveMobile() {
        val database = (application as BasicApp).database

        thread {
            Tool.save(database)
        }
    }

    private fun showLoading() {
        this.toolList.isEnabled = false
        grayLayout.visibility = ConstraintLayout.VISIBLE

    }

    private fun dismissLoading() {
        this.toolList.isEnabled = true
        grayLayout.visibility = ConstraintLayout.INVISIBLE

    }

    private fun createAdapter(): ArrayAdapter<Tool> {
        val lists = mutableListOf(Tool.DeviceInfoTool(), Tool.IPScanTool())
        return CustomToolAdapter(this@MainActivity, lists)
    }

}

class ToolListOnClick(val callback: () -> Unit): AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        parent?.let {
            val item = it.getItemAtPosition(position) as Tool
            when(item) {
                is Tool.DeviceInfoTool -> {
                    val intent = Intent(it.context, DeviceInfoActivity::class.java)
                    parent.context.startActivity(intent)
                }
                is Tool.IPScanTool -> {
                    this.callback()
                }
            }
        }
    }

}