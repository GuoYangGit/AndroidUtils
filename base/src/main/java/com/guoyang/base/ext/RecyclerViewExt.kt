package com.guoyang.base.ext

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.guoyang.base.weight.decoration.DefaultDecoration

// <editor-fold desc="[RecyclerView]的扩展类">

/**
 * 创建线性布局
 * @param orientation 列表方向
 * @param reverseLayout 是否反转列表
 */
fun RecyclerView.linear(
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    this.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    return this
}

/**
 * 创建网格布局
 * @param spanCount 网格数量
 * @param orientation 列表方向
 * @param reverseLayout 是否反转列表
 */
fun RecyclerView.grid(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
): RecyclerView {
    layoutManager = GridLayoutManager(context, spanCount, orientation, reverseLayout)
    return this
}

/**
 * 创建瀑布流布局
 * @param spanCount 网格数量
 * @param orientation 列表方向
 */
fun RecyclerView.staggered(
    spanCount: Int = 1,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL
): RecyclerView {
    val staggeredGridLayoutManager = StaggeredGridLayoutManager(spanCount, orientation)
    //解决加载下一页后重新排列的问题
    staggeredGridLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
    val checkForGapMethod = StaggeredGridLayoutManager::class.java.getDeclaredMethod("checkForGaps")
    val markItemDecorInsetsDirtyMethod =
        RecyclerView::class.java.getDeclaredMethod("markItemDecorInsetsDirty")
    checkForGapMethod.isAccessible = true
    markItemDecorInsetsDirtyMethod.isAccessible = true
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val result = checkForGapMethod.invoke(recyclerView.layoutManager) as Boolean
            //如果发生了重新排序，刷新itemDecoration
            if (result) {
                markItemDecorInsetsDirtyMethod.invoke(recyclerView)
            }
        }
    })
    layoutManager = staggeredGridLayoutManager
    return this
}

/**
 * 创建分割线
 * @param block 具体配置查看[DefaultDecoration]
 */
fun RecyclerView.divider(block: DefaultDecoration.() -> Unit): RecyclerView {
    val itemDecoration = DefaultDecoration(context).apply(block)
    addItemDecoration(itemDecoration)
    return this
}

// </editor-fold>