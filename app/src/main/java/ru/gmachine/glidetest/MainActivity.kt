package ru.gmachine.glidetest

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        download_inages.setOnClickListener {
            fromIntHiddenThanFromCache()
        }

        download_inages_cool.setOnClickListener {
            fromIntThanFromCache()
        }

    }

    var i = 0


    val imagesUrls = listOf<String>("https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074__340.jpg","https://cdn.pixabay.com/photo/2020/06/30/22/34/dog-5357794__340.jpg","https://i.ytimg.com/vi/MPV2METPeJU/maxresdefault.jpg","https://i.insider.com/5df126b679d7570ad2044f3e?width=1100&format=jpeg&auto=webp","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTNUIaFH6ORRHr5Rrr89Jy4nfPimlV4-bXt4A&usqp=CAU")

    val catImageUrl = "https://cdn.pixabay.com/photo/2017/09/25/13/12/dog-2785074__340.jpg"


    override fun onResume() {
        super.onResume()



    }



    fun fromIntHiddenThanFromCache(){

        only_internet.setImageResource(R.drawable.ic_launcher_foreground)
        only_cache.setImageResource(R.drawable.ic_launcher_foreground)

        val curVal = Random.nextInt(1,999999)



        Glide.with(only_internet)
                .load(imagesUrls[i])
                .skipMemoryCache(true)
                .signature(ObjectKey(curVal))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Log.e("InetImage","dataSource is ${dataSource}")
                        return false
                    }

                })
                .into(object : CustomViewTarget<ImageView, Drawable> (only_internet){
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.e("InetImage","loadFailed")
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                        Log.e("InetImage","resCleared")
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        Log.e("InetImage","res Ready")
                    }

                });







        Handler(Looper.getMainLooper()).postDelayed({
            Glide.with(only_cache)
                    .load(imagesUrls[i])
                    .onlyRetrieveFromCache(true)
                    .signature(ObjectKey(curVal))
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            Log.e("CacheImage","failed reason is ${e}")
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            Log.e("CacheImage","dataSource is ${dataSource}")
                            return false
                        }

                    })
                    .into(only_cache);
            i+=1
        }, 3500)

    }

    fun fromIntThanFromCache(){

        only_internet.setImageResource(R.drawable.ic_launcher_foreground)
        only_cache.setImageResource(R.drawable.ic_launcher_foreground)


        val curVal = Random.nextInt(1,999999)

        Glide.with(only_internet)
            .load(imagesUrls[i])
            .skipMemoryCache(true)
            .signature(ObjectKey(curVal))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    Log.e("InetImage","dataSource is ${dataSource}")
                    return false
                }

            })
            .into(only_internet);

        Handler(Looper.getMainLooper()).postDelayed({
            Glide.with(only_cache)
                .load(imagesUrls[i])
                .onlyRetrieveFromCache(true)
                .signature(ObjectKey(curVal))
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("CacheImage","failed reason is ${e}")
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        Log.e("CacheImage","dataSource is ${dataSource}")
                        return false
                    }

                })
                .into(only_cache);
            i+=1
        }, 3500)

    }


}