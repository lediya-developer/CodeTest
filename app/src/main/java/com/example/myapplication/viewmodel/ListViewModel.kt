package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.data.Music
import com.example.myapplication.model.data.RecordLabel
import com.example.myapplication.model.respository.MusicRepository
import com.example.myapplication.utility.ResultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ListViewModel : ViewModel() {
    private var repository: MusicRepository = MusicRepository()
    private var _errorCode = MutableStateFlow(value = -1)
    val errorCode: StateFlow<Int> get() = _errorCode
    val itemsList = MutableStateFlow(listOf<RecordLabel>())
    val items: StateFlow<List<RecordLabel>> get() = itemsList
    private val itemIdsList = MutableStateFlow(listOf<Int>())
    val itemIds: StateFlow<List<Int>> get() = itemIdsList
    private val childIdsList = MutableStateFlow(listOf<Int>())
    val childItemIds: StateFlow<List<Int>> get() = childIdsList
    var List= MutableStateFlow(arrayListOf<Music>())

    init {
        getData()
        setData()
    }

    fun getData() {
        viewModelScope.launch {
                repository.getMusicDataFromServer()

        }
    }
    private fun setData(){
        viewModelScope.launch {
            repository.getResult.collect{   value ->
                value.getContentIfNotHandled()?.let { result ->
                    _errorCode.value = when (result.resultType) {
                        ResultType.PENDING -> {
                            0
                        }
                        ResultType.SUCCESS -> {
                            itemsList.value = repository.recordList.value
                            //repository.validateMusicData()
                            1
                        }
                        ResultType.FAILURE -> {
                            2
                        }
                        ResultType.TOO_MANY_REQUEST ->{
                            3
                        }
                    }
                }
            }
        }

    }

    fun onItemClicked(itemId: Int) {
        itemIdsList.value = itemIdsList.value.toMutableList().also { list ->
            if (list.contains(itemId)) {
                list.remove(itemId)
            } else {
                list.add(itemId)
            }
        }

    }

    fun onChildItemClicked(childItemId:Int){
        childIdsList.value = childIdsList.value.toMutableList().also { list ->
            if (list.contains(childItemId)) {
                list.remove(childItemId)
            } else {
                list.add(childItemId)
            }
        }
    }
}