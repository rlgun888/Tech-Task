package com.ryangunn.techtask.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ryangunn.techtask.R
import com.ryangunn.techtask.databinding.FragmentDriverBinding
import com.ryangunn.techtask.driver.model.Driver
import com.ryangunn.techtask.repository.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DriversFragment : Fragment() {
    var _binding: FragmentDriverBinding? = null
    val binding get() = _binding!!

    lateinit var viewModel: DriversViewModel
    lateinit var adapter: DriverAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.drivers)
        viewModel = ViewModelProvider(this).get(DriversViewModel::class.java)
        binding.apply {
            viewModel.getDrivers()
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.drivers.collect { result ->
                            when (result) {
                                is Result.Error -> {
                                    showErrorMessage()
                                }
                                is Result.Success -> {
                                    displayDrivers(result.data)
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    fun displayDrivers(drivers: List<Driver>) {
        adapter = DriverAdapter(drivers)
        val layoutManger = LinearLayoutManager(requireContext())
        binding.driverRecyclerView.adapter = adapter
        binding.driverRecyclerView.layoutManager = layoutManger
        adapter.onDriverClick = { driver ->
            findNavController().navigate(
                DriversFragmentDirections.actionDriversFragmentToDriverDetailFragment(
                    driver
                )
            )
        }
    }

    private fun showErrorMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.oh_oh)
            .setMessage(R.string.drivers_error)
            .setPositiveButton(
                R.string.okay
            ) { dialog, which ->
                dialog.dismiss()
                viewModel.getDrivers()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}