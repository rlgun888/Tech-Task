package com.ryangunn.techtask.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ryangunn.techtask.R
import com.ryangunn.techtask.databinding.FragmentLoginBinding
import com.ryangunn.techtask.repository.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment:Fragment() {
    var _binding:FragmentLoginBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel:LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.apply {
            loginButton.setOnClickListener {
                viewModel.login(usernameEditText.text.toString(),passwordEditText.text.toString())
            }
            collectResults()

        }

    }

    private fun FragmentLoginBinding.collectResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.invalidForm.collect { isInvalid ->
                        if (isInvalid) {
                            showErrorMessage(R.string.invalid_form)
                        }
                    }
                }

                launch {
                    viewModel.loginResult.collect { result ->
                        when (result) {
                            is Result.Error -> {
                                showErrorMessage(R.string.login_error)
                            }
                            is Result.Loading -> {
                                loginButton.isEnabled = !result.loading
                                if (result.loading) {
                                    progressCircle.visibility = View.VISIBLE
                                } else {
                                    progressCircle.visibility = View.GONE
                                }
                            }
                            is Result.Success -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

            }
        }
    }

    private fun showErrorMessage(messageStringID:Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.oh_oh)
            .setMessage(messageStringID)
            .setPositiveButton(R.string.okay
            ) { dialog, which ->
                viewModel.resetInvalidForm()
                dialog.dismiss()
            }
            .show()
    }
}

