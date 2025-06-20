package com.example.fitchef.ui.login.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.fitchef.ui.main.MainActivity
import com.example.fitchef.R
import com.example.fitchef.common.showSnackBar
import com.example.fitchef.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding:FragmentSignUpBinding?=null
    private val binding get()=_binding!!

    private val viewModel by viewModels<SignUpFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            signUpButton.setOnClickListener{
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val confirmPassword = confirmPasswordEditText.text.toString()
                val nickname = nicknameEditText.text.toString()
                val phoneNumber = phoneNumberEditText.text.toString()
                when {
                    email.isEmpty() -> requireView().showSnackBar("E-posta alanı boş olamaz.")
                    password.isEmpty() -> requireView().showSnackBar("Şifre alanı boş olamaz.")
                    confirmPassword.isEmpty() -> requireView().showSnackBar("Şifre Tekrar alanı boş olamaz.")
                    nickname.isEmpty() -> requireView().showSnackBar("Kullanıcı Adı alanı boş olamaz.")
                    else -> {
                        viewModel.signUp(
                            eMail = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            nickname = nickname,
                            phoneNumber = phoneNumber
                        )
                    }
                }
            }
        }

        initObservers()

    }

    private fun initObservers() {
        viewModel.signUpState.observe(viewLifecycleOwner) {
            if (it.isSignUp) {
                Intent(context, MainActivity::class.java).apply {
                    startActivity(this)
                    requireActivity().finish()
                }
            }

            if (it.errorMessage != null) {
                requireView().showSnackBar(getString(R.string.wrong_email_password))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}