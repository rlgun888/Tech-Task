package com.ryangunn.techtask.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ryangunn.techtask.R
import com.ryangunn.techtask.databinding.FragmentDriverDetailBinding


class DriverDetailFragment : Fragment(), OnMapReadyCallback {
    var _binding: FragmentDriverDetailBinding? = null
    val binding get() = _binding!!

    val args: DriverDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.drivers_details)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        displayDriverDetail()
    }

    private fun displayDriverDetail() {
        binding.apply {
            binding.driverNameTextView.text =
                String.format(
                    getString(R.string.driver_name), args.driver.getFullName()
                )

            binding.driverPhoneNumberTextView.text =
                String.format(
                    getString(R.string.driver_phone_number), args.driver.phoneNumber
                )

            binding.driverPlateTextView.text =
                String.format(
                    getString(R.string.driver_plate), args.driver.driverDetails.plateNumber
                )

            binding.driverTrailerTypeTextView.text =
                String.format(
                    getString(R.string.driver_trailer_type), args.driver.driverDetails.trailerType
                )
            binding.driverTrailerInfoTextView.text =
                String.format(
                    getString(R.string.driver_trailer_info),
                    args.driver.driverDetails.trailerLength.toString(),
                    args.driver.driverDetails.trailerWidth.toString(),
                    args.driver.driverDetails.trailerHeight.toString()
                )
        }
    }

    override fun onMapReady(map: GoogleMap) {
        val driverLocation = LatLng(
            args.driver.driverDetails.currentLocation.latitude,
            args.driver.driverDetails.currentLocation.latitude
        )
        map.addMarker(
            MarkerOptions()
                .position(driverLocation)
                .title(getString(R.string.driver_location))
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(driverLocation))
    }


}