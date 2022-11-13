package com.guoyang.androidutils

import android.os.Bundle
import com.guoyang.androidutils.databinding.ActivityInfoBinding
import com.guoyang.utils_helper.bundle
import com.guoyang.utils_helper.doOnClick

class InfoActivity : BaseActivity<ActivityInfoBinding>() {
    private val name: String? by bundle()
    private val age: Int? by bundle()

    override fun initView(savedInstanceState: Bundle?) {
        binding.apply {
            tvName.text = name.toString()
            tvAge.text = age.toString()
            btnSend.doOnClick {
            }
        }
    }
}