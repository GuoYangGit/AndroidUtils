package androidx.lifecycle

import com.guoyang.interval_helper.Interval

/** 轮询器根据ViewModel生命周期自动取消 */
fun Interval.life(viewModel: ViewModel) = apply {
    viewModel.setTagIfAbsent(toString(), this)
}