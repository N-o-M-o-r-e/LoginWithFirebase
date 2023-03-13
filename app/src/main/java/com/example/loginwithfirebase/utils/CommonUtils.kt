package com.example.loginwithfirebase.utils

import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.example.loginwithfirebase.R
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CommonUtils {
    fun pxToDp(px: Int): Float {
        return px / Resources.getSystem().displayMetrics.density
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun ImageView.loadImageFromBase64(base64: String) {
        try {
            Glide.with(this).load(stringToBitmap(base64))
                .centerCrop()
                .override(dpToPx(42), dpToPx(42))
                .transform(RoundedCorners(dpToPx(21)))
                .into(object : CustomViewTarget<ImageView, Drawable>(this) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        setImageResource(R.color.white)
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
//                        setImageResource(R.color.white)
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        setImageDrawable(resource)
                    }
                })
        } catch (ex: PackageManager.NameNotFoundException) {
        }
    }

    fun stringToBitmap(encodedString: String): Bitmap? {
        val imageBytes = try {
            android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun CharSequence.containVN(other: CharSequence, ignoreCase: Boolean) =
        this.removeVietnameseTones().contains(
            other.removeVietnameseTones(),
            ignoreCase
        )

    private fun CharSequence.removeVietnameseTones(): String {
        var str: String = this.toString()
        str = str.replace("/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g".toRegex(), "a")
        str = str.replace("/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g".toRegex(), "e")
        str = str.replace("/ì|í|ị|ỉ|ĩ/g".toRegex(), "i")
        str = str.replace("/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g".toRegex(), "o")
        str = str.replace("/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g".toRegex(), "u")
        str = str.replace("/ỳ|ý|ỵ|ỷ|ỹ/g".toRegex(), "y")
        str = str.replace("/đ/g".toRegex(), "d")
        str = str.replace("/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g".toRegex(), "A")
        str = str.replace("/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g".toRegex(), "E")
        str = str.replace("/Ì|Í|Ị|Ỉ|Ĩ/g".toRegex(), "I")
        str = str.replace("/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g".toRegex(), "O")
        str = str.replace("/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g".toRegex(), "U")
        str = str.replace("/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g".toRegex(), "Y")
        str = str.replace("/Đ/g".toRegex(), "D")
        str = str.replace(
            "/\u0300|\u0301|\u0303|\u0309|\u0323/g".toRegex(),
            ""
        ) // ̀ ́ ̃ ̉ ̣  huyền, sắc, ngã, hỏi, nặng
        str = str.replace("/\u02C6|\u0306|\u031B/g".toRegex(), "") // ˆ ̆ ̛  Â, Ê, Ă, Ơ, Ư
        str = str.replace("/ + /g".toRegex(), " ")
        str = str.trim()

        return str
    }

    fun Int.isPortDevice() =
        (this == 3000) || (this == 3001)
                || (this == 8001) || (this == 8002)
                || (this == 3005) || (this == 8080)
                || (this == 8060) || (this == 8061)

    fun Int.isPortDeviceCast() =
        (this == 3000) || (this == 3001)
                || (this == 8001) || (this == 8002)
                || (this == 3005) || (this == 8080)
                || (this == 9197)

    fun getCertificate(): Array<TrustManager> {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
                override fun checkServerTrusted(
                    chain: Array<X509Certificate>,
                    authType: String
                ) {
                }
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return emptyArray()
                }
            }
        )
        return trustAllCerts
    }

    const val KEY_TYPE = "type"
    const val PHOTO_TYPE = "photo"
    const val VIDEO_TYPE = "video"
    const val MUSIC_TYPE = "music"


    fun String.isImage(): Boolean {
        return (contains("jpg", true) ||
                contains("gif", true) ||
                contains("webp", true) ||
                contains("png", true) ||
                contains("tiff", true) ||
                contains("psd", true) ||
                contains("raw", true) ||
                contains("bmp", true) ||
                contains("heif", true) ||
                contains("indd", true) ||
                contains("jpeg", true) ||
                contains("svg", true))
    }

    fun String.isVideo(): Boolean {
        return (contains("mp4", true) ||
                contains("mov", true) ||
                contains("wmv", true) ||
                contains("avi", true) ||
                contains("mkv", true) ||
                contains("webm", true) ||
                contains("mpeg", true))
    }

    fun String.isAudio(): Boolean {
        return (contains("mp3", true) ||
                contains("aac", true) ||
                contains("ogg", true) ||
                contains("wav", true) ||
                contains("dsd", true) ||
                contains("pcm", true) ||
                contains("aiff", true))
    }
}