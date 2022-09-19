package com.emrekuru.example.ui.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.emrekuru.example.R
import com.emrekuru.example.base.BaseActivity
import com.emrekuru.example.databinding.ActivityLaunchBinding
import com.emrekuru.example.ui.home.HomeActivity
import com.emrekuru.example.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchActivity : BaseActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private val viewModel by viewModels<LaunchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        // Doing Version Check Etc.
        // Then Navigate to Home Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))

        }, 2000)

        viewModel.doSomeWork()

        lifecycleScope.launch {
            workCallBack()
        }

    }

    private suspend fun workCallBack(){
        viewModel.viewState.collect {
            when(it){
                LaunchViewModel.DoWorkSealed.LOADING -> {
                    showLoading()
                }
                is LaunchViewModel.DoWorkSealed.ERROR -> {
                    hideLoading()
                    Toast.makeText(this,"Hata Meydana Geldi.", Toast.LENGTH_LONG).show()
                }
                is LaunchViewModel.DoWorkSealed.SUCCESS -> {
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this,HomeActivity::class.java))
                    }, 2000)
                    hideLoading()
                }
                else -> {

                }
            }
        }
    }

}