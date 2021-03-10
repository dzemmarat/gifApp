package com.rodionov.gifapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivItemGifList.setOnClickListener {
            getRandom()
        }
    }

    override fun onResume() {
        super.onResume()
        getRandom()
    }

    private fun getRandom() {
        buildNewRetrofit().create(ApiService::class.java).getRandom(Settings.API_KEY).subscribeOn(
            Schedulers.newThread()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                url = it.gifObjectModel.toGifItemModel().stillUrl ?: ""
                fitToScreenSize(it)
            }.subscribeBy(
                onNext = {
                    GlideApp.with(this)
                        .load(url)
                        .into(ivItemGifList)
                }, onError = {
                    Log.d("testGif", "onError url = $url")
                }
            )
    }

    fun buildNewRetrofit(): Retrofit {

        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Settings.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit

    }

    private fun fitToScreenSize(it: RandomResponse) {
        val layoutParams = ivItemGifList.layoutParams
        layoutParams.width =
            UIUtils.getScreenWidthInPx(this) - UIUtils.convertDpToPixel(8F).toInt()
        val scaleFactor: Float =
            (UIUtils.getScreenWidthInPx(this) - UIUtils.convertDpToPixel(8F).toInt()).toFloat() / Settings.FIXED_WIDTH
        layoutParams.height =
            (it.gifObjectModel.toGifItemModel().height.toFloat() * scaleFactor).toInt()
        ivItemGifList.layoutParams = layoutParams
    }
}
