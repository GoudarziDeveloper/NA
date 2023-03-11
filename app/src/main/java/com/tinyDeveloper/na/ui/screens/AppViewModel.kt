package com.tinyDeveloper.na.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.response.common.Base
import com.tinyDeveloper.na.domain.models.request.users.CheckRequest
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfo
import com.tinyDeveloper.na.domain.models.response.base_info.BaseInfoResponse
import com.tinyDeveloper.na.domain.models.response.common.Role
import com.tinyDeveloper.na.domain.models.response.users.check_code.CheckResponse
import com.tinyDeveloper.na.domain.models.response.users.role.ChatInfo
import com.tinyDeveloper.na.domain.models.response.users.role.RoleResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Constants.VERIFICATION_TIME
import com.tinyDeveloper.na.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val useCase: UseCases): ViewModel() {
    private val _shouldStartSMSRetrieval: MutableState<Boolean> = mutableStateOf(false)
    val shouldStartSMSRetrieval: State<Boolean> = _shouldStartSMSRetrieval

    private val _phoneValue: MutableState<String> = mutableStateOf("09")
    val phoneValue: State<String> = _phoneValue

    private val _passwordValue: MutableState<String> = mutableStateOf("")
    val passwordValue: State<String> = _passwordValue

    private val _verificationCode: MutableState<String> = mutableStateOf("")
    val verificationCode: State<String> = _verificationCode

    private val _verificationTime: MutableState<Int> = mutableStateOf(VERIFICATION_TIME)
    val verificationTime: State<Int> = _verificationTime

    private val _loginValue: MutableState<Resource<BasicResponse>?> = mutableStateOf(null)
    val loginValue: State<Resource<BasicResponse>?> = _loginValue

    private val _checkCodeValue: MutableState<Resource<CheckResponse>?> = mutableStateOf(null)
    val checkCodeValue: State<Resource<CheckResponse>?> = _checkCodeValue

    private val _userRoleValue: MutableState<Resource<RoleResponse>?> = mutableStateOf(null)
    val userRoleValue: State<Resource<RoleResponse>?> = _userRoleValue

    private val _bases: MutableState<List<Base>?> = mutableStateOf(emptyList())
    val bases: State<List<Base>?> = _bases

    private val _roles: MutableState<Role?> = mutableStateOf(null)
    val roles: State<Role?> = _roles

    private val _chatInfo: MutableState<ChatInfo?> = mutableStateOf(null)
    val chatInfo: State<ChatInfo?> = _chatInfo

    private var _baseInfoValue: MutableState<Resource<BaseInfoResponse>?> = mutableStateOf(null)
    val baseInfoValue: State<Resource<BaseInfoResponse>?> = _baseInfoValue

    private val _profileImage: MutableState<String?> = mutableStateOf(null)
    val profileImage: State<String?> = _profileImage

    private val _infoValue: MutableStateFlow<BasicRequest?> = MutableStateFlow(null)
    val infoValue: StateFlow<BasicRequest?> = _infoValue

    private val _showSnow: MutableState<Boolean> = mutableStateOf(false)
    val showSnow: State<Boolean> = _showSnow

    fun setSnow(showSnow: Boolean){
        _showSnow.value = showSnow
    }

    fun emptyInfo(){
        _infoValue.value = null
        _phoneValue.value = ""
        _passwordValue.value = ""
        setInfo()
    }

    fun onPasswordChange(password: String){
        if(password.count() < 20)
            _passwordValue.value = password
    }

    fun setShouldStartSMSRetrievalChange(state: Boolean) {
        _shouldStartSMSRetrieval.value = state
    }

    fun onPhoneChange(phone: String){
        if(!phone[phone.count()-1].isLetter()){
            if(phone != ">"){
                if(phone.count() < 12)
                    _phoneValue.value = phone
            } else if (phone == ">"){
                if(phoneValue.value.isNotEmpty())
                    _phoneValue.value = phoneValue.value.dropLast(1)
            }
        }
    }

    fun setVerificationCode(code: String){
        _verificationCode.value = code
    }

    fun onVerificationCodeChange(code: String){
        if(!code[code.count()-1].isLetter()){
            if(code != ">"){
                if(code.count() < 7)
                    _verificationCode.value = code
            } else{
                if(verificationCode.value.isNotEmpty())
                    _verificationCode.value = verificationCode.value.dropLast(1)
            }
        }
    }

    fun onVerificationTimeChanged(time: Int){
        _verificationTime.value = time
    }

    fun loginRequest(){
        _loginValue.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _loginValue.value =  useCase.loginUsersUseCase(
                basicRequest = BasicRequest(phoneValue.value, passwordValue.value)
            )
        }
    }

    fun logout(){
        _loginValue.value = null
    }

    fun checkCode(){
        _checkCodeValue.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _checkCodeValue.value = useCase.checkCodeUseCase(
                checkRequest = CheckRequest(phone = phoneValue.value, code = verificationCode.value)
            )
            _roles.value = checkCodeValue.value?.data?.roles
            _bases.value = checkCodeValue.value?.data?.bases
            _chatInfo.value = checkCodeValue.value?.data?.chatInfo
        }
    }

    fun getBaseInfo(){
        _baseInfoValue.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _baseInfoValue.value = useCase.getBaseInfoUseCase()
        }
    }

    fun setBaseInfo(baseInfo: BaseInfo){
        _baseInfoValue.value = null
        _baseInfoValue.value =
            Resource.Success(data = BaseInfoResponse(
                baseInfo = baseInfo,
                message = baseInfoValue.value?.data?.message?:"",
                status = baseInfoValue.value?.data?.status?:false
            )
        )
    }

    fun setInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.setInfoUseCase(
                BasicRequest(
                    phoneValue.value,
                    passwordValue.value
                )
            )
        }
    }

    fun getInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getInfoUseCase().collect {info ->
                _infoValue.value = info
                _phoneValue.value = info.phone
                _passwordValue.value = info.password
            }
        }
    }

    fun changeProfileImage(image: String?){
        _profileImage.value = image
    }

    fun getUserRole(){
        if(infoValue.value != null){
            _userRoleValue.value = Resource.Loading()
            viewModelScope.launch(Dispatchers.IO) {
                _userRoleValue.value = useCase.getUserRoleUseCase(
                    BasicRequest(infoValue.value!!.phone, infoValue.value!!.password)
                )
                _roles.value = userRoleValue.value?.data?.roles
                _bases.value = userRoleValue.value?.data?.bases
                _chatInfo.value = userRoleValue.value?.data?.chatInfo
                changeProfileImage(userRoleValue.value?.data?.chatInfo?.image)
            }
        }
    }
}