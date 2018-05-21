package cn.nieking.usercenter.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.usercenter.R
import cn.nieking.usercenter.injection.component.DaggerUserComponent
import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.presenter.UserInfoPresenter
import cn.nieking.usercenter.presenter.view.UserInfoView
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.DateUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import java.io.File

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File

    override fun injectComponent() {
        DaggerUserComponent.builder()
                .activityComponent(activityComponent)
                .userModule(UserModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        mTakePhoto = TakePhotoImpl(this, this)
        initView()
        mTakePhoto.onCreate(savedInstanceState)
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }
    }

    private fun showAlertView() {
        AlertView("选择照片",
                "",
                "取消",
                null,
                arrayOf("拍照", "相册"),
                this,
                AlertView.Style.ActionSheet,
                OnItemClickListener { o, position ->
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> {
                            createTempFile()
                            mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                        }
                        1 -> mTakePhoto.onPickFromGallery()
                    }
                }).show()
    }

    override fun takeSuccess(result: TResult?) {
        toast(result?.image?.originalPath.toString())
        toast(result?.image?.compressPath.toString())
    }

    override fun takeCancel() {

    }

    override fun takeFail(result: TResult?, msg: String?) {
        toast("""takePhoto$msg""")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }
}
