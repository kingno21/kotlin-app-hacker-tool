package test.lab.dmm.com.mytestapp.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import test.lab.dmm.com.mytestapp.R
import test.lab.dmm.com.mytestapp.db.entity.IPEntity
import test.lab.dmm.com.mytestapp.models.Tool

class CustomToolAdapter(context: Context, data: MutableList<Tool>): ArrayAdapter<Tool>(context, R.layout.tool_row, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val tool = getItem(position) as Tool
        val view = LayoutInflater.from(context).inflate(R.layout.tool_row, parent, false)
        view.findViewById<TextView>(R.id.textView4).text = tool.tool_name
        return view
    }
}

class CustomListAdapter(context: Context, data: MutableList<IPEntity>): ArrayAdapter<IPEntity>(context, R.layout.tool_row, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val host = getItem(position) as IPEntity
        val view = LayoutInflater.from(context).inflate(R.layout.tool_row, parent, false)
        view.findViewById<TextView>(R.id.textView4).text = host.toShow()
        return view
    }
}