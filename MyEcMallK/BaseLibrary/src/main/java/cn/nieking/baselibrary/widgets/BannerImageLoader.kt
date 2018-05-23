package cn.nieking.baselibrary.widgets

import android.content.Context
import android.widget.ImageView
import cn.nieking.baselibrary.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

class BannerImageLoader : ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}