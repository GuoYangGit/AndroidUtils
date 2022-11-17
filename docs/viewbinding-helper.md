## Gradle

在根目录的 `build.gradle` 添加：

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

添加依赖：

```groovy
api "com.github.GuoYangGit.AndroidUtils:viewbinding-helper:xxx"
```

## 使用扩展函数

在 `Adapter` 里使用 `BindingViewHolder`，无需再写个类继承 `RecycleView.ViewHolder`。

### Adapter

```kotlin
class TextAdapter : ListAdapter<String, BindingViewHolder<ItemTextBinding>>(DiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    BindingViewHolder<ItemTextBinding>(parent)
      .withBinding {
        root.setOnClickListener { ... }
        tvText.setOnClickListener { ... }
      }

  override fun onBindViewHolder(holder: BindingViewHolder<ItemTextBinding>, position: Int) {
    holder.binding.tvText.text = currentList[position]
  }

  class DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
  }
}

```

### TabLayout

以下是 `TabLayout + ViewPager2` 实现自定义底部导航栏的示例。

```kotlin
TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
  tab.setCustomView<LayoutBottomTabBinding> {
    tvTitle.setText(titleList[position])
    tvTitle.textSize = if (position == 0) 12f else 10f
    ivIcon.setImageResource(iconList[position])
    ivIcon.contentDescription = tvTitle.text.toString()
  }
}.attach()

tabLayout.doOnCustomTabSelected<LayoutBottomTabBinding>(
  onTabSelected = {
    tvTitle.textSize = 12f
  },
  onTabUnselected = {
    tvTitle.textSize = 10f
  })
```

## 封装基类

```kotlin
给基类增加一个 ViewBinding 泛型；
Activity 基类实现 ActivityBinding<VB> by ActivityBindingDelegate()，Fragment 基类实现 FragmentBinding<VB> by FragmentBindingDelegate()；
在 Activity 基类调用 setContentViewWithBinding() 函数，在 Fragment 基类调用 createViewWithBinding(inflater, container)；
```

### Activity

`Activity` 基类的核心改造代码：

```kotlin
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity(),
  ActivityBinding<VB> by ActivityBindingDelegate() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentViewWithBinding()
  }
}
```

`Activity` 基类改造后的使用示例：

```kotlin
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.tvHelloWorld.text = "Hello Android!"
  }
}
```

### Fragment

`Fragment` 基类的核心改造代码：

```kotlin
abstract class BaseBindingFragment<VB : ViewBinding> : Fragment(),
  FragmentBinding<VB> by FragmentBindingDelegate() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return createViewWithBinding(inflater, container)
  }
}
```

`Fragment` 基类改造后的使用示例：

```kotlin
class HomeFragment: BaseBindingFragment<FragmentHomeBinding>() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.tvHelloWorld.text = "Hello Android!"
  }
}
```

## Api

[具体查看](https://guoyanggit.github.io/AndroidUtils/api/viewbinding-helper/)
