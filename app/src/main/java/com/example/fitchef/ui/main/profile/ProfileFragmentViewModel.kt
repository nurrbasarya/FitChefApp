package com.example.fitchef.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitchef.common.Resource
import com.example.fitchef.data.model.UserModel
import com.example.fitchef.data.repos.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val usersRepo: UserRepository
) : ViewModel() {

    private var _profilState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState>
        get() = _profilState

    fun getUserInfo() {
        viewModelScope.launch {
            when (val response = usersRepo.getUserInfo()) {
                is Resource.Success ->  {
                    _profilState.value = ProfileState(
                        isLoading = false,
                        userData =  response.data
                    )
                }

                is Resource.Fail -> {
                    _profilState.value = ProfileState(isLoading = false, failMessage = response.message)
                }

                is Resource.Error -> {
                    _profilState.value = ProfileState(isLoading = false, errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun signOut() = usersRepo.signOut()

}

data class ProfileState(
    val isLoading: Boolean = false,
    val userData: UserModel? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null
)
