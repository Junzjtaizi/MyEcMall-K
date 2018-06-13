package cn.nieking.baselibrary.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import cn.nieking.baselibrary.common.BaseApplication
import cn.nieking.baselibrary.injection.component.ActivityComponent
import cn.nieking.baselibrary.injection.component.DaggerActivityComponent
import cn.nieking.baselibrary.injection.module.ActivityModule
import cn.nieking.baselibrary.injection.module.LifecycleProviderModule
import cn.nieking.baselibrary.presenter.BasePresenter
import cn.nieking.baselibrary.presenter.view.BaseView
import cn.nieking.baselibrary.utils.DateUtils
import cn.nieking.baselibrary.widgets.ProgressLoading
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.tbruyelle.rxpermissions2.RxPermissions
import org.jetbrains.anko.toast
import java.io.File
import javax.inject.Inject

abstract class BaseTakePhotoActivity<T : BasePresenter<*>> : BaseActivity(), BaseView, TakePhoto.TakeResultListener {

    @Inject
    lateinit var mPresenter: T
    lateinit var mActivityComponent: ActivityComponent

    lateinit var mLoadingDialog: ProgressLoading

    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)

        mLoadingDialog = ProgressLoading.create(this)
        ARouter.getInstance().inject(this)
    }

    private fun initActivityInjection() {
        mActivityComponent = DaggerActivityComponent.builder()
                .appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .lifecycleProviderModule(LifecycleProviderModule(this))
                .build()
    }

    override fun showLoading() {
        mLoadingDialog.showLoading()
    }

    override fun hideLoading() {
        mLoadingDialog.hideLoading()
    }

    fun showAlertView() {
        AlertView("选择照片",
                "",
                "取消",
                null,
                arrayOf("拍照", "相册"),
                this,
                AlertView.Style.ActionSheet,
                OnItemClickListener { _, position ->
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> {
                            RxPermissions(this).request(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA)
                                    .subscribe { granted ->
                                        if (granted) {
                                            createTempFile()
                                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                                        } else {
                                            toast("未授权")
                                        }
                                    }
                        }
                        1 -> mTakePhoto.onPickFromGallery()
                    }
                }).show()
    }

    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakePhoto", msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun onError(text: String) {
        toast(text)
    }

    abstract fun injectComponent()
}