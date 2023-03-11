package com.tinyDeveloper.na.ui.screens.main.profile

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyDeveloper.na.domain.models.request.BasicRequest
import com.tinyDeveloper.na.domain.models.request.users.UpdateProfileRequest
import com.tinyDeveloper.na.domain.models.response.BasicResponse
import com.tinyDeveloper.na.domain.models.response.users.get_profile.GetProfileResponse
import com.tinyDeveloper.na.domain.models.response.users.update_profile.UpdateProfileResponse
import com.tinyDeveloper.na.domain.use_cases.UseCases
import com.tinyDeveloper.na.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val useCases: UseCases): ViewModel() {
    private val _getResponse : MutableState<Resource<GetProfileResponse>?> = mutableStateOf(null)
    val getResponse: State<Resource<GetProfileResponse>?> = _getResponse

    private val _updateResponse: MutableState<Resource<UpdateProfileResponse>?> = mutableStateOf(null)
    val updateResponse: State<Resource<UpdateProfileResponse>?> = _updateResponse

    private val _image: MutableState<Bitmap?> = mutableStateOf(null)
    val image: State<Bitmap?> = _image

    fun updateImage(image: Bitmap?){
        _image.value = image
    }

    fun get(phone: String, password: String){
        _getResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            _getResponse.value = useCases.getProfile(
                BasicRequest(
                    phone = phone,
                    password = password
                )
            )
        }
    }

    fun update(phone: String, password: String, image: String) {
        _updateResponse.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            _updateResponse.value = useCases.updateProfileUseCase(
                UpdateProfileRequest(
                    phone = phone,
                    password = password,
                    image = image
                )
            )
        }
    }
}