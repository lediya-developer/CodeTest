package com.example.myapplication.model.respository

import com.example.myapplication.communication.RestClient
import com.example.myapplication.model.data.*
import com.example.myapplication.utility.Event
import com.example.myapplication.utility.ResultImp
import com.example.myapplication.utility.ResultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicRepository {
    val recordList : StateFlow<List<RecordLabel>> get() = _recordLabelList
    private  var _recordLabelList= MutableStateFlow(listOf<RecordLabel>())
    private val _getResult = MutableStateFlow(Event(ResultImp(ResultType.PENDING)))
    val getResult: StateFlow<Event<ResultImp>>
        get() = _getResult

    /**
     * The method used to get music data from backend, request perform and set the result
     **/
    suspend fun getMusicDataFromServer() {
        _getResult.emit(Event(ResultImp(ResultType.PENDING)))
        try {
            val response = RestClient.getInstance("").getList()
            if (response.body() != null) {
                response.body()?.let {
                    validateMusicData(it)
                }
            }
            else if(response.code()==429){
                _getResult.emit(Event(ResultImp(ResultType.TOO_MANY_REQUEST)))
            }
            else {
                _getResult.emit(Event(ResultImp(ResultType.FAILURE)))
            }
        }
        catch (e: Exception) {
            _getResult.emit(Event(ResultImp(ResultType.FAILURE)))
        }
    }

    /** insert country data to local database*/
    suspend fun validateMusicData(musicList: Music) {
        val recordLabelList = mutableListOf<RecordLabel>()
        val recordLabelEmptyList = mutableListOf<RecordLabel>()
        musicList.toList().onEach { band->
            band.bands?.forEach {
                val fest = arrayListOf<Festival>()
                val bands = arrayListOf<BandList>()
                val festName= Festival(band.name)
                fest.add(festName)
                val recordName = it.recordLabel
                val bandName = it.name?.let { it1 -> BandList(fest, it1) }
                if (bandName != null) {
                    bands.add(bandName)
                    if(recordName==null || recordName.trim() == ""){
                        val filterRecordData = recordLabelEmptyList.filter { recordLabel ->recordLabel.name.contains(" . ") &&recordLabel.bands!=null  }
                        if(filterRecordData!=null&& filterRecordData.isNotEmpty()){
                            filterRecordData[0].bands?.let { it1 -> bands.addAll(it1) }
                            recordLabelEmptyList.remove(filterRecordData[0])
                        }
                        val recordLabel = RecordLabel(bands," . ")
                        recordLabelEmptyList.add(recordLabel)
                    }else {
                        val filterRecordData = recordLabelList.filter { recordLabel ->recordLabel.name.contains(recordName) &&recordLabel.bands!=null  }
                        if(filterRecordData!=null&& filterRecordData.isNotEmpty()){
                            filterRecordData[0].bands?.let { it1 -> bands.addAll(it1) }
                            recordLabelList.remove(filterRecordData[0])
                        }
                        val recordLabel = RecordLabel(bands, recordName)
                        recordLabelList.add(recordLabel)
                    }
                }
            }
        }
        recordLabelEmptyList.onEach { it.bands?.sortWith(compareBy { it1 -> it1.bandName }) }
        recordLabelEmptyList.onEach { bandList->bandList.bands?.onEach { it.festivals?.sortWith(
            compareBy { it1 -> it1.name  }) } }
        recordLabelEmptyList.onEach { it.bands?.onEach { it1->it1.festivals?.forEach { it2 ->it2.name } } }
        recordLabelList.sortWith(compareBy { it.name })
        recordLabelList.onEach { it.bands?.sortWith(compareBy { it1 -> it1.bandName }) }
        recordLabelList.onEach { bandList->bandList.bands?.onEach { it.festivals?.sortWith(
            compareBy { it1 -> it1.name  }) } }
        val list = recordLabelEmptyList + recordLabelList
        _recordLabelList.emit(list)
        _getResult.emit(Event(ResultImp(ResultType.SUCCESS)))
    }

}