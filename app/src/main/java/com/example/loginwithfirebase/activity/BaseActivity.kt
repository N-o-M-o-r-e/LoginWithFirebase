package com.example.loginwithfirebase.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.loginwithfirebase.utils.CommonUtils


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseActivityWithoutDataBiding<Binding : ViewBinding>(private val inflate : Inflate<Binding>) :
    BaseActivity() {
    protected lateinit var binding: Binding

    override fun bindView() {
        binding = inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)
    }
}

abstract class BaseActivityWithDataBiding<Binding : ViewDataBinding>(private val inflate: Inflate<Binding>) :
    BaseActivity() {
    protected lateinit var binding: Binding

    override fun bindView() {
        binding = inflate(LayoutInflater.from(this), null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }
}

abstract class BaseActivity : AppCompatActivity(){
    protected lateinit var glide : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)

        bindView()
        glide = Glide.with(this)
        initView()
        listenLiveData()
        listeners()
        initData()
    }
    protected abstract fun bindView()
    protected abstract fun initData()
    protected abstract fun initView()
    protected abstract fun listenLiveData()
    protected abstract fun listeners()

    protected fun darkStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }

    open fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        result = if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else {
            200
        }
        return result
    }


    open fun marginStatusBar(listView: List<View>) {
        for (i in listView) {
            val params = i.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = params.topMargin + getStatusBarHeight()
            i.layoutParams = params
        }
    }
    open fun getNavigationBarHeight(): Int {
        var result = CommonUtils.dpToPx(40)
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        val hasNavBarId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        if ((ViewConfiguration.get(this).hasPermanentMenuKey() || (hasNavBarId > 0 && resources.getBoolean(hasNavBarId)))) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        Log.d("getNavigationBarHeight", "$result")
        return result
    }

    open fun marginNavigationBar(listView: List<View>) {
        for (i in listView) {
            val params = i.layoutParams as ConstraintLayout.LayoutParams
            params.bottomMargin = params.bottomMargin + getNavigationBarHeight()
            i.layoutParams = params
        }
    }

    open fun paddingNavigationBar(listView: List<View>) {
        if (Build.VERSION_CODES.P >= Build.VERSION.SDK_INT) {
            for (i in listView) {
                i.setPadding(i.paddingLeft, i.paddingTop, i.paddingRight, getNavigationBarHeight() + i.paddingBottom)
            }
        }
    }

    fun marginViewWithNavigationBar(view: View) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.bottomMargin = params.bottomMargin + getNavigationBarHeight()
        view.layoutParams = params
    }

    fun marginViewWithStatusBar(view: View) {
        val params = view.layoutParams as ConstraintLayout.LayoutParams
        params.topMargin = params.topMargin + getStatusBarHeight()
        view.layoutParams = params
    }


}