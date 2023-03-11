package com.tinyDeveloper.na.ui.screens.main.useers

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.users.AdvanceUserRequest
import com.tinyDeveloper.na.domain.models.request.users.BasicUserRequest
import com.tinyDeveloper.na.domain.models.request.users.User
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.users.get_all.GetAllUsersResponse
import com.tinyDeveloper.na.domain.models.response.users.get_user.GetUserResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.ui.screens.main.useers.user.UserRoleList
import com.tinyDeveloper.na.ui.screens.main.useers.user.UserRoleState
import com.tinyDeveloper.na.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _allUsersResponse: MutableState<Resource<GetAllUsersResponse>?> = mutableStateOf(null)
    val allUsersResponse: State<Resource<GetAllUsersResponse>?> = _allUsersResponse

    private val _usersResponse: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val usersResponse: State<Resource<BasicResponse>?> = _usersResponse

    private val _getUserResponse: MutableState<Resource<GetUserResponse>?> = mutableStateOf(null)
    val getUserResponse: State<Resource<GetUserResponse>?> = _getUserResponse

    private val _roles: MutableState<UserRoleList> = mutableStateOf(UserRoleList())
    val roles: State<UserRoleList> = _roles

    private val _image: MutableState<Bitmap?> = mutableStateOf(null)
    val image: State<Bitmap?> = _image

    private val _userPhone: MutableState<String> = mutableStateOf("")
    val userPhone: State<String> = _userPhone

    fun setUserPhone(phone:String){
        _userPhone.value = phone
    }

    fun updateImage(image: Bitmap?){
        _image.value = image
    }

    fun emptyUserResponse(){
        _usersResponse.value = null
    }

    private val _user: MutableState<User> = mutableStateOf(User())
    val user: State<User> = _user

    fun getAll(phone: String, password: String){
        _allUsersResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _allUsersResponse.value = useCases.getAllUsersUseCase(
                BasicRequest(phone = phone, password = password)
            )
        }
    }

    fun delete(phone: String, password: String, user: String){
        _usersResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _usersResponse.value = useCases.deleteUserUseCase(
                BasicUserRequest(
                    adminPhone = phone,
                    adminPassword = password,
                    phone = user
                )
            )
        }
    }

    fun get(phone: String, password: String, user: String){
        _getUserResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _getUserResponse.value = useCases.getUserUseCase(
                BasicUserRequest(
                    adminPhone = phone,
                    adminPassword = password,
                    phone = user
                )
            )
        }
    }

    fun update(phone: String, password: String, imageChange: Boolean){
        _usersResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _usersResponse.value = useCases.updateUserUseCase(
                advanceUserRequest = AdvanceUserRequest(
                    adminPhone          = phone,
                    adminPassword       = password,
                    baseId              = user.value.baseId,
                    firstName           = user.value.firstName,
                    lastName            = user.value.lastName,
                    fatherName          = user.value.fatherName,
                    birthDay            = user.value.birthDay,
                    image               = if(imageChange) user.value.image else "-1",
                    nationalCode        = user.value.nationalCode,
                    phone               = user.value.phone,
                    password            = user.value.password,
                    status              = user.value.status,
                    addUser             = if (roles.value.addUser.checked) 1 else 0,
                    deleteUser          = if (roles.value.deleteUser.checked) 1 else 0,
                    updateUser          = if (roles.value.updateUser.checked) 1 else 0,
                    updateUsers         = if (roles.value.updateUsers.checked) 1 else 0,
                    getUser             = if (roles.value.getUser.checked) 1 else 0,
                    getUsers            = if (roles.value.getUsers.checked) 1 else 0,
                    getNotifications    = if (roles.value.getNotifications.checked) 1 else 0,
                    addNotification     = if (roles.value.addNotification.checked) 1 else 0,
                    deleteNotification  = if (roles.value.deleteNotification.checked) 1 else 0,
                    updateNotification  = if (roles.value.updateNotification.checked) 1 else 0,
                    addJob              = if (roles.value.addJob.checked) 1 else 0,
                    deleteJob           = if (roles.value.deleteJob.checked) 1 else 0,
                    updateJob           = if (roles.value.updateJob.checked) 1 else 0,
                    getJob              = if (roles.value.getJob.checked) 1 else 0,
                    getFile             = if (roles.value.getFile.checked) 1 else 0,
                    addFile             = if (roles.value.addFile.checked) 1 else 0,
                    deleteFile          = if (roles.value.deleteFile.checked) 1 else 0,
                    addBase             = if (roles.value.addBase.checked) 1 else 0,
                    deleteBase          = if (roles.value.deleteBase.checked) 1 else 0,
                    getBases            = if (roles.value.getBases.checked) 1 else 0,
                    updateBase          = if (roles.value.updateBase.checked) 1 else 0,
                    addSubmission       = if (roles.value.addSubmission.checked) 1 else 0,
                    deleteSubmission    = if (roles.value.deleteSubmission.checked) 1 else 0,
                    updateSubmission    = if (roles.value.updateSubmission.checked) 1 else 0,
                    getSubmissions      = if (roles.value.getSubmissions.checked) 1 else 0,
                    getBaseInfo         = if (roles.value.getBaseInfo.checked) 1 else 0,
                    editBaseInfo        = if (roles.value.editBaseInfo.checked) 1 else 0,
                )
            )
        }
    }

    fun updateUser(
        baseId: String = user.value.baseId,
        firstName:String = user.value.firstName,
        lastName: String = user.value.lastName,
        fatherName: String = user.value.fatherName,
        birthDay: String = user.value.birthDay,
        nationalCode: String = user.value.nationalCode,
        image: String = user.value.image,
        phone: String = user.value.phone,
        password: String = user.value.password,
        status: String = user.value.status,
        empty: Boolean = false
    ) {
        if(!empty){
            _user.value = User(
                birthDay = birthDay,
                firstName = firstName,
                lastName = lastName,
                fatherName = fatherName,
                baseId = baseId,
                nationalCode = nationalCode,
                image = image,
                phone = phone,
                password = password,
                status = status
            )
        }else {
            _user.value = User()
        }
    }

    fun updateRoles(
        addUser: Boolean = roles.value.addUser.checked,
        deleteUser: Boolean = roles.value.deleteUser.checked,
        updateUser: Boolean = roles.value.updateUser.checked,
        updateUsers: Boolean = roles.value.updateUsers.checked,
        getUser: Boolean = roles.value.getUser.checked,
        getUsers: Boolean = roles.value.getUsers.checked,
        getNotifications: Boolean = roles.value.getNotifications.checked,
        addNotification: Boolean = roles.value.addNotification.checked,
        deleteNotification: Boolean = roles.value.deleteNotification.checked,
        updateNotification: Boolean = roles.value.updateNotification.checked,
        addJob: Boolean = roles.value.addJob.checked,
        deleteJob: Boolean = roles.value.deleteJob.checked,
        updateJob: Boolean = roles.value.updateJob.checked,
        getJob: Boolean = roles.value.getJob.checked,
        getFile: Boolean = roles.value.getFile.checked,
        addFile: Boolean = roles.value.addFile.checked,
        deleteFile: Boolean = roles.value.deleteFile.checked,
        addBase: Boolean = roles.value.addBase.checked,
        deleteBase: Boolean = roles.value.deleteBase.checked,
        getBases: Boolean = roles.value.getBases.checked,
        updateBase: Boolean = roles.value.updateBase.checked,
        addSubmission: Boolean = roles.value.addSubmission.checked,
        deleteSubmission: Boolean = roles.value.deleteSubmission.checked,
        updateSubmission: Boolean = roles.value.updateSubmission.checked,
        getSubmissions: Boolean = roles.value.getSubmissions.checked,
        getBaseInfo: Boolean = roles.value.getBaseInfo.checked,
        editBaseInfo: Boolean = roles.value.editBaseInfo.checked,
        empty: Boolean = false
    ){
        if (!empty){
            _roles.value = UserRoleList(
                addUser = UserRoleState(checked = addUser, title = roles.value.addUser.title),
                deleteUser = UserRoleState(checked = deleteUser, title = roles.value.deleteUser.title),
                updateUser = UserRoleState(checked = updateUser, title = roles.value.updateUser.title),
                updateUsers = UserRoleState(checked = updateUsers, title = roles.value.updateUsers.title),
                getUser = UserRoleState(checked = getUser, title = roles.value.getUser.title),
                getUsers = UserRoleState(checked = getUsers, title = roles.value.getUsers.title),
                getNotifications = UserRoleState(checked = getNotifications, title = roles.value.getNotifications.title),
                addNotification = UserRoleState(checked = addNotification, title = roles.value.addNotification.title),
                deleteNotification = UserRoleState(checked = deleteNotification, title = roles.value.deleteNotification.title),
                updateNotification = UserRoleState(checked = updateNotification, title = roles.value.updateNotification.title),
                addJob = UserRoleState(checked = addJob, title = roles.value.addJob.title),
                deleteJob = UserRoleState(checked = deleteJob, title = roles.value.deleteJob.title),
                updateJob = UserRoleState(checked = updateJob, title = roles.value.updateJob.title),
                getJob = UserRoleState(checked = getJob, title = roles.value.getJob.title),
                getFile = UserRoleState(checked = getFile, title = roles.value.getFile.title),
                addFile = UserRoleState(checked = addFile, title = roles.value.addFile.title),
                deleteFile = UserRoleState(checked = deleteFile, title = roles.value.deleteFile.title),
                addBase = UserRoleState(checked = addBase, title = roles.value.addBase.title),
                deleteBase = UserRoleState(checked = deleteBase, title = roles.value.deleteBase.title),
                getBases = UserRoleState(checked = getBases, title = roles.value.getBases.title),
                updateBase = UserRoleState(checked = updateBase, title = roles.value.updateBase.title),
                addSubmission = UserRoleState(checked = addSubmission, title = roles.value.addSubmission.title),
                deleteSubmission = UserRoleState(checked = deleteSubmission, title = roles.value.deleteSubmission.title),
                updateSubmission = UserRoleState(checked = updateSubmission, title = roles.value.updateSubmission.title),
                getSubmissions = UserRoleState(checked = getSubmissions, title = roles.value.getSubmissions.title),
                getBaseInfo = UserRoleState(checked = getBaseInfo, title = roles.value.getBaseInfo.title),
                editBaseInfo = UserRoleState(checked = editBaseInfo, title = roles.value.editBaseInfo.title),
            )
        }else {
            _roles.value = UserRoleList()
        }
    }

    fun add(phone: String, password: String){
        _usersResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _usersResponse.value = useCases.addUserUserCase(
                advanceUserRequest = AdvanceUserRequest(
                adminPhone          = phone,
                adminPassword       = password,
                baseId              = user.value.baseId,
                firstName           = user.value.firstName,
                lastName            = user.value.lastName,
                fatherName          = user.value.fatherName,
                birthDay            = user.value.birthDay,
                image               = user.value.image,
                nationalCode        = user.value.nationalCode,
                phone               = user.value.phone,
                password            = user.value.password,
                status              = user.value.status,
                addUser             = if (roles.value.addUser.checked) 1 else 0,
                deleteUser          = if (roles.value.deleteUser.checked) 1 else 0,
                updateUser          = if (roles.value.updateUser.checked) 1 else 0,
                updateUsers         = if (roles.value.updateUsers.checked) 1 else 0,
                getUser             = if (roles.value.getUser.checked) 1 else 0,
                getUsers            = if (roles.value.getUsers.checked) 1 else 0,
                getNotifications    = if (roles.value.getNotifications.checked) 1 else 0,
                addNotification     = if (roles.value.addNotification.checked) 1 else 0,
                deleteNotification  = if (roles.value.deleteNotification.checked) 1 else 0,
                updateNotification  = if (roles.value.updateNotification.checked) 1 else 0,
                addJob              = if (roles.value.addJob.checked) 1 else 0,
                deleteJob           = if (roles.value.deleteJob.checked) 1 else 0,
                updateJob           = if (roles.value.updateJob.checked) 1 else 0,
                getJob              = if (roles.value.getJob.checked) 1 else 0,
                getFile             = if (roles.value.getFile.checked) 1 else 0,
                addFile             = if (roles.value.addFile.checked) 1 else 0,
                deleteFile          = if (roles.value.deleteFile.checked) 1 else 0,
                addBase             = if (roles.value.addBase.checked) 1 else 0,
                deleteBase          = if (roles.value.deleteBase.checked) 1 else 0,
                getBases            = if (roles.value.getBases.checked) 1 else 0,
                updateBase          = if (roles.value.updateBase.checked) 1 else 0,
                addSubmission       = if (roles.value.addSubmission.checked) 1 else 0,
                deleteSubmission    = if (roles.value.deleteSubmission.checked) 1 else 0,
                updateSubmission    = if (roles.value.updateSubmission.checked) 1 else 0,
                getSubmissions      = if (roles.value.getSubmissions.checked) 1 else 0,
                getBaseInfo         = if (roles.value.getBaseInfo.checked) 1 else 0,
                editBaseInfo        = if (roles.value.editBaseInfo.checked) 1 else 0,
                )
            )
        }
    }
}