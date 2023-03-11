package com.tinyDeveloper.na.ui.screens.main.jobs.job.submissions

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.AdvanceRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.files.AddFileRequest
import com.tinyDeveloper.na.domain.models.request.submissions.AddSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetAllSubmissionsRequest
import com.tinyDeveloper.na.domain.models.request.submissions.GetSubmissionRequest
import com.tinyDeveloper.na.domain.models.request.submissions.UpdateSubmissionRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.files.AddFileResponse
import com.tinyDeveloper.na.domain.models.response.files.File
import com.tinyDeveloper.na.domain.models.response.submissions.AddSubmissionResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetAllSubmissionsResponse
import com.tinyDeveloper.na.domain.models.response.submissions.GetSubmissionResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.AdvanceStateValues
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.StateValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubmissionsViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _getAllResponse: MutableState<Resource<GetAllSubmissionsResponse>?> = mutableStateOf(null)
    val getAllResponse: State<Resource<GetAllSubmissionsResponse>?> = _getAllResponse

    private val _getSubmissionResponse: MutableState<Resource<GetSubmissionResponse>?> = mutableStateOf(null)
    val getSubmissionResponse: State<Resource<GetSubmissionResponse>?> = _getSubmissionResponse

    private val _submissionRequest: MutableState<AddSubmissionRequest?> = mutableStateOf(null)
    val submissionRequest: State<AddSubmissionRequest?> = _submissionRequest

    private val _addSubmissionResponse: MutableState<Resource<AddSubmissionResponse>?> = mutableStateOf(null)
    val addSubmissionResponse: State<Resource<AddSubmissionResponse>?> = _addSubmissionResponse

    private val _basicResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val basicResponse: State<Resource<BasicResponse>?> = _basicResponse

    private val _addFileRequest: MutableState<AddFileRequest?> = mutableStateOf(null)
    val addFileRequest: State<AddFileRequest?> = _addFileRequest

    private val _addFileResponse: MutableState<Resource<AddFileResponse>?> = mutableStateOf(null)
    val addFileResponse: State<Resource<AddFileResponse>?> = _addFileResponse

    private val _files: MutableState<List<File>> = mutableStateOf(emptyList())
    val files: State<List<File>> = _files

    init {
        updateFields()
    }

    fun emptyGetAllResponse(){
        _getAllResponse.value = null
    }

    fun getAll(phone: String, password: String, id: String){
        _getAllResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _getAllResponse.value = useCases.getAllSubmissionsUseCase(
                getAllSubmissionsRequest = GetAllSubmissionsRequest(
                    phone = phone,
                    password = password,
                    jobId = id
                )
            )
        }
    }

    fun getSubmission(phone: String, password: String, id: String){
        _getSubmissionResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getSubmissionResponse.value = useCases.getSubmissionUseCase(
                getSubmissionRequest = GetSubmissionRequest(
                    id = id,
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun getUserSubmission(phone: String, password: String, jobId: String){
        _getSubmissionResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getSubmissionResponse.value = useCases.getUserSubmissionUseCase(
                advanceRequest = AdvanceRequest(
                    phone = phone,
                    password = password,
                    id = jobId
                )
            )
        }
    }

    fun add(phone: String, password: String, id: String, userId: String){
        _addSubmissionResponse.value = Resource.Loading()
        submissionRequest.value?.let {
            viewModelScope.launch(Dispatchers.IO){
                _addSubmissionResponse.value = useCases.addSubmissionUseCase(
                    addSubmissionsRequest = AddSubmissionRequest(
                        description = it.description,
                        jobId = id,
                        password = password,
                        phone = phone,
                        score = it.score,
                        status = it.status,
                        userId = userId
                    )
                )
            }
        }
    }

    fun update(phone: String, password: String, submissionId: String, jobId: String, userId: String){
        _basicResponse.value = Resource.Loading()
        submissionRequest.value?.let {
            viewModelScope.launch(Dispatchers.IO){
                _basicResponse.value = useCases.updateSubmissionUseCase(
                    updateSubmissionRequest = UpdateSubmissionRequest(
                        description = it.description,
                        id = submissionId,
                        password = password,
                        jobId = jobId,
                        phone = phone,
                        score = it.score,
                        status = it.status,
                        userId = userId
                    )
                )
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

    fun addFile(){
        addFileRequest.value?.let {
            _addFileResponse.value = Resource.Loading()
            viewModelScope.launch(Dispatchers.IO){
                delay(100)
                _addFileResponse.value = useCases.addFileUseCase(addFileRequest = it)
            }
        }
    }

    fun updateFiles(files: List<File>){
        _files.value = files
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

    fun updateFileFields(
        file: String = addFileRequest.value?.file?:"",
        jobId: String = addFileRequest.value?.jobId?:"-1",
        password: String = addFileRequest.value?.password?:"",
        phone: String = addFileRequest.value?.phone?:"",
        status: String = addFileRequest.value?.status?: StateValues.ACTIVE.value,
        submissionId: String = addFileRequest.value?.submissionId?:"-1"
    ){
        _addFileRequest.value = AddFileRequest(
            file = file,
            jobId = jobId,
            password = password,
            phone = phone,
            status = status,
            submissionId = submissionId
        )
    }

    fun updateFields(
        description: String = submissionRequest.value?.description?:"",
        jobId: String = submissionRequest.value?.jobId?:"",
        password: String = submissionRequest.value?.password?:"",
        phone: String = submissionRequest.value?.phone?:"",
        score: String = submissionRequest.value?.score?:"0",
        status: String = submissionRequest.value?.status?:AdvanceStateValues.WAITING.value,
        userId: String = submissionRequest.value?.userId?:""
    ){
        _submissionRequest.value = AddSubmissionRequest(
            description = description,
            jobId = jobId,
            password = password,
            phone = phone,
            score = score,
            status = status,
            userId = userId
        )
    }
}