package com.example.myapplication.viewmodel

import com.example.myapplication.communication.ApiEndPointService
import com.example.myapplication.model.respository.MusicRepository
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import com.example.myapplication.communication.RestClient
import com.example.myapplication.model.data.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {
    private lateinit var repository: MusicRepository
    private lateinit var viewModel: ListViewModel
    private lateinit var testApis: ApiEndPointService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var music: Music


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testApis = RestClient.getInstance(mockWebServer.url("/").toString())
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = ListViewModel()
        repository = MusicRepository()
        val bands = arrayListOf<Band>(Band("Winter Primates", "A"),
            Band("Recording", "Z"))
        val band2 = arrayListOf<Band>(
            Band("Book Primates", "B"),
            Band("Carol Primates", "D"))
        music = Music()
        music.add(MusicItem(bands,"X.mas"))
        music.add(MusicItem(band2,"test1"))

    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun tooManyRequest(){
        try {
            runBlocking {
                val response: Response<Music> =
                    RestClient.getInstance("").getList()
                assertEquals(response.code()==429,true)
            }
        }
        catch (exception: IOException) {
        }


    }
    @Test
    fun success() = runTest {
        val musicDummyData =  mockk<Music>(relaxed = true)
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(musicDummyData))
        mockWebServer.enqueue(expectedResponse)
        val actualResponse = mockk<Music>(relaxed = true)
        assertThat(actualResponse).isEqualTo(musicDummyData)
    }
    /**
     * Validate bandList not empty
     **/
    @Test
    fun bandListDataNotEmpty() {
        try {
            runBlocking {
                val response: Response<Music> =
                    RestClient.getInstance("").getList()
                val responseData: Music? = response.body()
                assertEquals(
                    responseData?.get(0)?.bands!=null&& responseData[0].bands!!.size>0
                            && responseData[0].bands!!.isNotEmpty(),
                    true)
            }
        }
        catch (exception: IOException) {
        }
    }
    /**
     * Validate festival not empty
     **/
    @Test
    fun festivalDataNotEmpty() {
        try {
            runBlocking {
                val response: Response<Music> =
                    RestClient.getInstance("").getList()
                val responseData: Music? = response.body()
                assertEquals(
                    responseData?.get(0)!=null,
                    true)
            }
        }
        catch (exception: IOException) {
        }
    }
    @Test
    fun sortRecordLabel(){
        try {
            runBlocking {
                repository.validateMusicData(music)
                viewModel.itemsList.value = repository.recordList.value
                assertEquals(viewModel.itemsList.value[0].name,"A")
                assertEquals(viewModel.itemsList.value[1].name,"B")
                assertEquals(viewModel.itemsList.value[2].name,"D")
                assertEquals(viewModel.itemsList.value[3].name,"Z")

            }
        }
        catch (exception: IOException) {
        }
    }
    @Test
    fun sortBandListData(){

        runBlocking {
            repository.validateMusicData(music)
            viewModel.itemsList.value = repository.recordList.value
            // recordlabel A, band winter primates
            assertEquals(viewModel.itemsList.value[0].bands?.get(0)?.bandName,"Winter Primates")
            // recordlabel B, band Book Primates
            assertEquals(viewModel.itemsList.value[1].bands?.get(0)?.bandName,"Book Primates")
            // recordlabel D, band Carol Primates
            assertEquals(viewModel.itemsList.value[2].bands?.get(0)?.bandName,"Carol Primates")
            // recordlabel Z, band Recording
            assertEquals(viewModel.itemsList.value[3].bands?.get(0)?.bandName,"Recording")


        }
    }

    @Test
    fun sortFestivalData(){
        runBlocking {
            repository.validateMusicData(music)
            viewModel.itemsList.value = repository.recordList.value
            // recordlabel A, band winter primates,Festival Xmas
            assertEquals(viewModel.itemsList.value[0].bands?.get(0)?.festivals?.get(0)?.name,"X.mas")
            // recordlabel B, band Book Primates, festival Xmas
            assertEquals(viewModel.itemsList.value[1].bands?.get(0)?.festivals?.get(0)?.name,"test1")
            // recordlabel D, band Carol Primates,festival test1
            assertEquals(viewModel.itemsList.value[2].bands?.get(0)?.festivals?.get(0)?.name,"test1")
            // recordlabel Z, band Recording,Festival test1
            assertEquals(viewModel.itemsList.value[3].bands?.get(0)?.festivals?.get(0)?.name,"X.mas")


        }
    }
}