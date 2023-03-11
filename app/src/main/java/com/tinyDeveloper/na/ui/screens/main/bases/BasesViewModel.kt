package com.tinyDeveloper.na.ui.screens.main.bases

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.bases.add.AddBaseRequest
import com.tinyDeveloper.na.domain.models.request.bases.update.UpdateBaseRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.domain.models.response.bases.get_all.GetAllBasesResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.StateValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasesViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _getAllResponse: MutableState<Resource<GetAllBasesResponse>?> = mutableStateOf(null)
    val getAllBasesResponse: State<Resource<GetAllBasesResponse>?> = _getAllResponse

    private val _basicRequest: MutableState<BasicRequest?> = mutableStateOf(null)
    val basicRequest: State<BasicRequest?> = _basicRequest

    private val _advanceRequest: MutableState<AdvanceRequest?> = mutableStateOf(null)
    val advanceRequest: State<AdvanceRequest?> = _advanceRequest

    private val _getResponse: MutableState<Resource<GetBaseResponse>?> = mutableStateOf(null)
    val getResponse: State<Resource<GetBaseResponse>?> = _getResponse

    private val _basicResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val basicResponse: State<Resource<BasicResponse>?> = _basicResponse

    private val _addRequest: MutableState<AddBaseRequest?> = mutableStateOf(null)
    val addRequest: State<AddBaseRequest?> = _addRequest

    private val _baseId: MutableState<String> = mutableStateOf("")
    val baseId: State<String> = _baseId

    fun emptyBasicResponse(){
        _basicResponse.value = null
    }

    fun setBaseId(id: String){
        _baseId.value = id
    }

    fun updateBasicRequest(phone: String, password: String){
        _basicRequest.value = BasicRequest(phone = phone, password = password)
    }

    fun updateAdvanceRequest(phone: String, password: String, id: String){
        _advanceRequest.value = AdvanceRequest(
            phone = phone,
            password = password,
            id = id
        )
    }

    fun updateAddRequest(
        phone: String = addRequest.value?.phone?: "",
        password: String = addRequest.value?.password?: "",
        baseName: String = addRequest.value?.baseName?: "",
        centerId: String = addRequest.value?.centerId?: "100000",
        address: String = addRequest.value?.address?: "",
        commanderName: String = addRequest.value?.commanderName?: "",
        commanderPhone: String = addRequest.value?.commanderPhone?: "",
        status: String = addRequest.value?.status?: StateValues.ACTIVE.value

    ){
        _addRequest.value = AddBaseRequest(
            address = address,
            phone = phone,
            password = password,
            baseName = baseName,
            centerId = centerId,
            commanderPhone = commanderPhone,
            commanderName = commanderName,
            status = status
        )
    }

    fun getAll(phone: String, password: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getAllResponse.value = useCases.getAllBasesUseCase(
                basicRequest = BasicRequest(phone = phone, password = password)
            )
        }
    }

    fun add(phone: String, password: String){
        _basicResponse.value = Resource.Loading()
        addRequest.value?.let {
            viewModelScope.launch(Dispatchers.IO){
                _basicResponse.value = useCases.addBaseUseCase(
                    addBaseRequest = it.copy(phone = phone, password = password)
                )
            }
        }
    }

    fun update(phone: String, password: String, id: String){
        _basicResponse.value = Resource.Loading()
        addRequest.value?.let {
            viewModelScope.launch(Dispatchers.IO){
                _basicResponse.value = useCases.updateBaseUseCase(
                    updateBaseRequest = UpdateBaseRequest(
                        address = it.address,
                        baseName = it.baseName,
                        centerId = it.centerId,
                        commanderName = it.commanderName,
                        commanderPhone = it.commanderPhone,
                        id = id,
                        phone = phone,
                        password = password,
                        status = it.status
                    )
                )
            }
        }
    }

    fun delete(phone: String, password: String, id: String){
        _basicResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _basicResponse.value = useCases.deleteBaseUseCase(
                advanceRequest = AdvanceRequest(
                    id = id,
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun get(phone: String, password: String, id: String){
        _getResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getResponse.value = useCases.getBaseUseCase(
                advanceRequest = AdvanceRequest(
                    id = id,
                    phone = phone,
                    password = password
                )
            )
        }
    }
}