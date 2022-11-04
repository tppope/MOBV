package sk.stu.fei.mobv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import sk.stu.fei.mobv.databinding.FragmentFirmFormBinding
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.ui.viewmodel.FirmViewModel
import sk.stu.fei.mobv.ui.viewmodel.factory.FirmViewModelFactory

class FirmFormFragment : Fragment() {
    private val navigationArgs: FirmFragmentArgs by navArgs()

    private val firmViewModel: FirmViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            FirmViewModelFactory(activity.application)
        )[FirmViewModel::class.java]
    }

    private var _binding: FragmentFirmFormBinding? = null
    private val binding get() = _binding!!

    lateinit var firm: Firm

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirmFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val firmId = navigationArgs.firmId
        if (id > 0) {
            firmViewModel.getFirm(firmId).observe(this.viewLifecycleOwner) { firm ->
                this.firm = firm
                bindFirm()
            }

            binding.deleteButton.visibility = View.VISIBLE
            binding.deleteButton.setOnClickListener {
                deleteFirm()
            }
        } else {
            binding.saveButton.setOnClickListener {
                addFirm()
            }
        }
    }

    private fun bindFirm() {
        binding.apply {
            firmNameEditText.setText(firm.name)
            ownerNameEditText.setText(firm.ownerName)
            latitudeEditText.setText(firm.latitude)
            longitudeEditText.setText(firm.longitude)
            phoneNumberEditText.setText(firm.phoneNumber)
            webUrlEditText.setText(firm.webPage)
            saveButton.setOnClickListener {
                updateFirm()
            }
        }
    }

    private fun deleteFirm() {
        firmViewModel.deleteFirm(firm)
    }

    private fun addFirm() {
        firmViewModel.addFirm(
            Firm(

            )
        )
    }

    private fun updateFirm() {
        firmViewModel.editFirm(
            Firm(
                id = navigationArgs.firmId,
            )
        )
    }

}