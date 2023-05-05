package com.example.myapplication.communication

import com.example.myapplication.model.data.Music
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Interface class to list out the API end points used in this application
 * Responses of each API call is mapped into a global {@link Result<T>} object.
 *
 * Refer {@see ResultCall} class for Global response handling
 */
interface ApiEndPointService {
    @Headers("Accept: application/json")
    @GET("api/v1/festivals")
    suspend fun getList(): Response<Music>
}