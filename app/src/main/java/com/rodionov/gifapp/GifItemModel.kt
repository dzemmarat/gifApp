package com.rodionov.gifapp

/**
 * Created by mJafarinejad on 8/13/2018.
 */
data class GifItemModel(
    val stillUrl: String?,
    val videoUrl: String?,
    val title: String?,
    val height: String = ""
)