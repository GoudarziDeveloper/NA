package com.tinyDeveloper.na.ui.screens.main.jobs

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.request.jobs.AddJobRequest
import com.tinyDeveloper.na.domain.models.request.jobs.UpdateJobRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.domain.models.response.jobs.add.AddJobResponse
import com.tinyDeveloper.na.domain.models.response.jobs.get_all.GetAllJobsResponse
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.domain.models.response.jobs.get_job.GetJobResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.StateValues
import com.tinyDeveloper.na.utils.TimeValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _getAllResponse: MutableState<Resource<GetAllJobsResponse>?> = mutableStateOf(null)
    val getAllResponse: State<Resource<GetAllJobsResponse>?> = _getAllResponse

    private val _getResponse: MutableState<Resource<GetJobResponse>?> = mutableStateOf(null)
    val getResponse: State<Resource<GetJobResponse>?> = _getResponse

    private val _files: MutableState<List<File>> = mutableStateOf(emptyList())
    val files: State<List<File>> = _files

    private val _addFileRequest: MutableState<AddFileRequest?> = mutableStateOf(null)
    val addFileRequest: State<AddFileRequest?> = _addFileRequest

    private val _addFileResponse: MutableState<Resource<AddFileResponse>?> = mutableStateOf(null)
    val addFileResponse: State<Resource<AddFileResponse>?> = _addFileResponse

    private val _basicResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val basicResponse: State<Resource<BasicResponse>?> = _basicResponse

    private val _jobId: MutableState<String> = mutableStateOf("-1")
    val jobId: State<String> = _jobId

    fun setJobId(id: String){
        _jobId.value = id
    }

    fun emptyAddResponse(){
        _addResponse.value = null
    }
    fun emptyFileResponse(){
        _addFileResponse.value = null
    }

    fun emptyBasicResponse(){
        _basicResponse.value = null
    }

    fun emptyGetResponse(){
        _getResponse.value = null
    }

    fun emptyFiles(){
        _files.value = emptyList()
    }

    fun addToFiles(file: File){
        val list: MutableList<File> = files.value.toMutableList()
        list += file
        _files.value = list
    }

    fun deleteInFiles(fileId: String){
        var file: File? = null
        _files.value.forEach {
            if (it.id == fileId)
                file = it
        }
        val list: MutableList<File> = files.value.toMutableList()
        if (file != null)
            list -= file!!
        _files.value = list
    }

    fun updateFiles(files: List<File>){
        _files.value = files
    }

    private val _addRequest: MutableState<AddJobRequest> = mutableStateOf(
        AddJobRequest()
    )
    val addRequest: State<AddJobRequest?> = _addRequest

    private val _addResponse: MutableState<Resource<AddJobResponse>?> = mutableStateOf(null)
    val addResponse: State<Resource<AddJobResponse>?> = _addResponse

    fun addFile(){
        addFileRequest.value?.let {
            _addFileResponse.value = Resource.Loading()
            viewModelScope.launch(Dispatchers.IO){
                delay(100)
                _addFileResponse.value = useCases.addFileUseCase(addFileRequest = it)
            }
        }
    }

    fun deleteFile(id: String, phone: String ,password: String){
        _basicResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _basicResponse.value = useCases.deleteFileUseCase(
                advanceRequest = AdvanceRequest(
                    id = id,
                    phone = phone,
                    password = password,
                )
            )
        }
    }

    fun updateFileFields(
        file: String = addFileRequest.value?.file?:"",
        jobId: String = addFileRequest.value?.jobId?:"-1",
        password: String = addFileRequest.value?.password?:"",
        phone: String = addFileRequest.value?.phone?:"",
        status: String = addFileRequest.value?.status?:StateValues.ACTIVE.value,
        submissionId: String = addFileRequest.value?.submissionId?:"-1",
        empty: Boolean = false
    ){
        if (!empty){
            _addFileRequest.value = AddFileRequest(
                file = file,
                jobId = jobId,
                password = password,
                phone = phone,
                status = status,
                submissionId = submissionId
            )
        }else {
            _addFileRequest.value = null
        }
    }

    fun updateFields(
        centerId: String = addRequest.value?.centerId?:"5",
        description: String = addRequest.value?.description?:"",
        maxShowTime: String = addRequest.value?.maxShowTime?:TimeValues.ONE_DEY.value,
        password: String = addRequest.value?.password?:"",
        phone: String = addRequest.value?.phone?:"",
        status: String = addRequest.value?.status?: StateValues.ACTIVE.value,
        title: String = addRequest.value?.title?:"",
        empty: Boolean = false
    ){
        if (!empty){
            _addRequest.value = AddJobRequest(
                centerId = centerId,
                description = description,
                maxShowTime = maxShowTime,
                password = password,
                phone = phone,
                status = status,
                title = title
            )
        }else {
            _addRequest.value = AddJobRequest()
        }
    }

    fun delete(phone: String, password: String, id: String){
        _basicResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _basicResponse.value = useCases.deleteJobUseCase(
                AdvanceRequest(
                    id = id,
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun update(id: String){
        _basicResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _basicResponse.value = useCases.updateJobUseCase(
                updateJobRequest = UpdateJobRequest(
                    phone = addRequest.value?.phone?:"",
                    password = addRequest.value?.password?:"",
                    centerId = addRequest.value?.centerId?:"",
                    title = addRequest.value?.title?:"",
                    description = addRequest.value?.description?:"",
                    id = id,
                    maxShowTime = addRequest.value?.maxShowTime?:"",
                    status = addRequest.value?.status?:""
                )
            )
        }
    }

    fun add(){
        _addResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _addResponse.value = addRequest.value?.let { useCases.addJobUseCase(addJobRequest = it) }
        }
    }

    fun getAll(phone: String, password: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getAllResponse.value = useCases.getAllJobsUseCase(
                BasicRequest(
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun getAllUserJobs(phone: String, password: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getAllResponse.value = useCases.getAllUserJobsUseCase(
                BasicRequest(
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun getAllActiveJobs(phone: String, password: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getAllResponse.value = useCases.getAllActiveJobsUseCase(
                BasicRequest(
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun getAllNotWorkedJobs(phone: String, password: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getAllResponse.value = useCases.getAllNotWorkedJobsUseCase(
                BasicRequest(
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun get(phone: String, password: String, id: String){
        _getResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getResponse.value = useCases.getJobUseCase(
                AdvanceRequest(
                    phone = phone,
                    password = password,
                    id = id
                )
            )
        }
    }

    fun findStatus(id: String): String?{
        getAllResponse.value?.data?.jobs?.forEach {
            if (it.id == id)
                return it.status
        }
        return null
    }
}