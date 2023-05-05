package com.example.myapplication.model.data

/**
 * Store the response data in the model class
 * */


data class RecordLabel(
    val bands: ArrayList<BandList>?,
    val name: String
)

data class BandList(
    val festivals: ArrayList<Festival>?,
    val bandName:String
)

data class Festival(
    val name: String?
)