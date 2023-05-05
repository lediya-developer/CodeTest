package com.example.myapplication.model.data

/**
 * Store the response data in the model class
 * */
class Music : ArrayList<MusicItem>()
data class MusicItem(
    val bands: ArrayList<Band>?,
    val name: String?
)

data class Band(
    val name: String?,
    val recordLabel: String?
)

