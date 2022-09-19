package com.emrekuru.example.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.emrekuru.example.base.BaseActivity
import com.emrekuru.example.databinding.ActivityHomeBinding
import com.emrekuru.example.network.response.EmployeeResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var adapterEmployeeListAdapter : EmployeeAdapter? = null
    private var employees : ArrayList<EmployeeResponse> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerViewEmplooyees.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.getEmployees()

        lifecycleScope.launch {
                getEmployeesCallBack()
        }

    }

    private fun setEmployeeAdapter() {
        adapterEmployeeListAdapter = EmployeeAdapter(
            employees
        )
        binding.recyclerViewEmplooyees.adapter = adapterEmployeeListAdapter

    }

    private suspend fun getEmployeesCallBack(){
        viewModel.viewState.collect {
            when(it){
                HomeViewModel.EmployeeStateSealed.LOADING -> {
                    showLoading()
                }
                HomeViewModel.EmployeeStateSealed.ERROR -> {
                    hideLoading()
                    Toast.makeText(this,"Hata Meydana Geldi.",Toast.LENGTH_LONG).show()
                }
                is HomeViewModel.EmployeeStateSealed.SUCCESS -> {
                    employees = it.employeeList
                    setEmployeeAdapter()
                    modifyAdapter(employees)
                    hideLoading()
                }
                else -> {

                }
            }
        }
    }

    private fun modifyAdapter(list : ArrayList<EmployeeResponse>){
        adapterEmployeeListAdapter?.changeItems(list)

    }

    override fun onBackPressed() {
        finishAffinity()
    }
}