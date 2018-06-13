package cn.nieking.baselibrary.ext

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import cn.nieking.baselibrary.R
import cn.nieking.baselibrary.data.protocol.BaseResp
import cn.nieking.baselibrary.rx.BaseFunc
import cn.nieking.baselibrary.rx.BaseFuncBoolean
import cn.nieking.baselibrary.rx.BaseSubscriber
import cn.nieking.baselibrary.utils.GlideUtils
import cn.nieking.baselibrary.widgets.DefaultTextWatcher
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle.LifecycleProvider
import org.jetbrains.anko.find
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

fun <T> Observable<T>.execute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
    this.observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribeOn(Schedulers.io())
            .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

fun View.onClick(listener: View.OnClickListener) {
    this.setOnClickListener(listener)
}

fun View.onClick(method: () -> Unit) {
    this.setOnClickListener { method() }
}

fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

fun MultiStateView.startLoading() {
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

fun View.setVisible(visiable: Boolean) {
    visibility = if (visiable) View.VISIBLE else View.GONE
}

fun <T> T.snack(view: View, text: CharSequence) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}