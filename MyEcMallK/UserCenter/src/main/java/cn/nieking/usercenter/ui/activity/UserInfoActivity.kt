package cn.nieking.usercenter.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import cn.nieking.baselibrary.common.BaseConstant
import cn.nieking.baselibrary.ext.onClick
import cn.nieking.baselibrary.ui.activity.BaseMvpActivity
import cn.nieking.baselibrary.utils.AppPrefsUtils
import cn.nieking.baselibrary.utils.DateUtils
import cn.nieking.baselibrary.utils.GlideUtils
import cn.nieking.provider.common.ProviderConstant
import cn.nieking.usercenter.R
import cn.nieking.usercenter.data.protocol.UserInfo
import cn.nieking.usercenter.injection.component.DaggerUserComponent
import cn.nieking.usercenter.injection.module.UserModule
import cn.nieking.usercenter.presenter.UserInfoPresenter
import cn.nieking.usercenter.presenter.view.UserInfoView
import cn.nieking.usercenter.utils.UserPrefsUtils
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.qiniu.android.storage.UploadManager
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import java.io.File

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, TakePhoto.TakeResultListener {

    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File
    private lateinit var mRxPermission: RxPermissions
    private var mLocalFileUrl: String? = null
    private var mRemoteFileUrl: String? = null
    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserMobile: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null
    private val mUploadManager: UploadManager by lazy { UploadManager() }

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
        mTakePhoto.onCreate(savedInstanceState)
        mRxPermission = RxPermissions(this)
        initView()
        initData()
    }

    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mRemoteFileUrl = mUserIcon
        if (mUserIcon != "") {
            GlideUtils.loadUrlImage(this, mUserIcon!!, mUserIconIv)
        }
        mUserNameEt.setText(mUserName)
        mUserMobileTv.text = mUserMobile
        if (mUserGender == "0") {
            mGenderMaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = true
        }
        mUserSignEt.setText(mUserSign)
    }

    /**
     * 初始化视图
     */
    private fun initView() {
        mUserIconView.onClick {
            showAlertView()
        }

        mHeaderBar.getRightView().onClick {
            mPresenter.editUser(
                    mRemoteFileUrl!!,
                    mUserNameEt.text?.toString() ?: "",
                    if (mGenderMaleRb.isChecked) "0" else "1",
                    mUserSignEt.text?.toString() ?: "")
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
                OnItemClickListener { _, position ->
                    mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                    when (position) {
                        0 -> {
                            mRxPermission.request(
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

    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
        mLocalFileUrl = result?.image?.compressPath
        mPresenter.getUploadToken()
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

    private fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }
        this.mTempFile = File(filesDir, tempFileName)
    }

    override fun onGetUploadTokenResult(result: String) {
        mUploadManager.put(mLocalFileUrl, null, result, { _, _, response ->
            mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response.get("hash")
            Log.i("TakePhoto", mRemoteFileUrl)
            GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl!!, mUserIconIv)
        }, null)
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }
}
