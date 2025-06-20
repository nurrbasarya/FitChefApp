package com.example.fitchef.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitchef.common.Resource
import com.example.fitchef.data.repos.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInFragmentViewModel @Inject constructor(
    private val usersRepo: UserRepository,
) :ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState : LiveData<SignInState>
        get()=_signInState

    fun signIn(eMail: String,password: String) {
        viewModelScope.launch {
            when (val response = usersRepo.signIn(eMail, password)) {
                is Resource.Success -> {
                    _signInState.value = SignInState(
                        isLoading = false,
                        isSignIn = true
                    )
                }

                is Resource.Fail -> {
                    _signInState.value = SignInState(isLoading = false, failMessage = response.message)
                }

                is Resource.Error -> {
                    _signInState.value = SignInState(isLoading = false, errorMessage = response.throwable.message)
                }
            }
        }
    }
}
data class SignInState(
    val isLoading:Boolean = false,
    val isSignIn: Boolean = false,
    val failMessage: String? = null,
    val errorMessage : String? = null
)