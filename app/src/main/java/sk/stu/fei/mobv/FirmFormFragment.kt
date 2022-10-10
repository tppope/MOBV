package sk.stu.fei.mobv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import sk.stu.fei.mobv.databinding.FragmentFirmFormBinding

class FirmFormFragment : Fragment() {

    private var _binding: FragmentFirmFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirmFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.confirmButton.setOnClickListener{
            val ownerName = binding.ownerNameEditText.text.toString()
            val firmName = binding.firmNameEditText.text.toString()
            val latitude = binding.latitudeEditText.text.toString()
            val longitude = binding.longitudeEditText.text.toString()
            val phoneNumber = binding.phoneNumberEditText.text.toString()
            val action = FirmFormFragmentDirections.actionFirmFormFragmentToFirmFragment(
                ownerName = ownerName,
                firmName = firmName,
                latitude = latitude,
                longitude = longitude,
                phoneNumber = phoneNumber
            )

            view.findNavController().navigate(action)
        }
    }

}