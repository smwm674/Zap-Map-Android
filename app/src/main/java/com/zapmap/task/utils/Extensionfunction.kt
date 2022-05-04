package com.zapmap.task.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.zapmap.task.R

/*
* extension function to load an images from the url in the imageView
* glide library is used to set an image in the imageView
* while loading image circularProgress will be displayed in the imageView as a placeholder
* if an error occur while loading an image from url static image (Pokemonball) from drawable is being set to imageView
* */
fun Context.setImage(imageview: ImageView, url: String) {

    val circularProgressDrawable = CircularProgressDrawable(this@setImage)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this@setImage)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                imageview.setImageDrawable(ContextCompat.getDrawable(this@setImage,R.drawable.pokeball))
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                imageview.setBackgroundColor(ContextCompat.getColor(this@setImage, R.color.white))
                return false
            }

        })
        .placeholder(circularProgressDrawable)
        .into(imageview)
}
