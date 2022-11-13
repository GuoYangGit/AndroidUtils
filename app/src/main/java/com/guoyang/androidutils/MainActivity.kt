package com.guoyang.androidutils

import android.os.Bundle
import com.guoyang.androidutils.databinding.ActivityMainBinding
import com.guoyang.utils_helper.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        immersive(binding.toolBar)
        binding.button.doOnDebouncingClick {
            startActivity<InfoActivity>(
                "name" to "guoyang",
                "age" to 18,
            )
        }

        AppForegroundObserver().observe(this) {
            toast("当前状态: $it")
        }
    }
}