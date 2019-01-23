package test.lab.dmm.com.mytestapp.UI

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import test.lab.dmm.com.mytestapp.BasicApp
import test.lab.dmm.com.mytestapp.R
import test.lab.dmm.com.mytestapp.ViewModels.IPHostViewModel
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.models.Tool
import kotlin.concurrent.thread

class IPHostListActivity : AppCompatActivity(), BasicActivity {

    lateinit var listView: ListView
    lateinit var viewModel: IPHostViewModel

    override fun init() {
        listView = findViewById(R.id.listView)
        viewModel = ViewModelProviders.of(this).get(IPHostViewModel::class.java)
    }

    override fun bindEvents() {
        viewModel.getList().observe(this, Observer {
            it?.let {
                listView.adapter = CustomListAdapter(this@IPHostListActivity, it as MutableList<IPEntity>)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iphost_list)

        init()
        bindEvents()
    }
}
