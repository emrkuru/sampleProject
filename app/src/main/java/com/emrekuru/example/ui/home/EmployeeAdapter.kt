package com.emrekuru.example.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emrekuru.example.databinding.AdapterEmployeeRecyclerViewBinding
import com.emrekuru.example.network.response.EmployeeResponse

class EmployeeAdapter(
    private var employeeList : ArrayList<EmployeeResponse>
): RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    @SuppressLint("SetTextI18n")
    inner class EmployeeViewHolder(val binding: AdapterEmployeeRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        var holderItem : EmployeeResponse? = null
        init {
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(employeeList: ArrayList<EmployeeResponse>) {
        this.employeeList =employeeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeAdapter.EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterEmployeeRecyclerViewBinding.inflate(inflater,parent,false)
        return  EmployeeViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: EmployeeAdapter.EmployeeViewHolder,
        position: Int
    ) {
        holder.holderItem = employeeList[position]
        holder.binding.txtEmployee.text = holder.holderItem?.name + ' '+ holder.holderItem?.lastName
    }

    override fun getItemCount(): Int {
        return  employeeList.size
    }
}
