package sk.stu.fei.mobv.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import sk.stu.fei.mobv.MainApplication
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.databinding.FragmentFirmFormBinding
import sk.stu.fei.mobv.domain.Firm
import sk.stu.fei.mobv.ui.viewmodel.FirmViewModel
import sk.stu.fei.mobv.ui.viewmodel.factory.FirmViewModelFactory

class FirmFormFragment : Fragment() {
    private val navigationArgs: FirmFormFragmentArgs by navArgs()

    private val firmViewModel: FirmViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            FirmViewModelFactory((activity.application as MainApplication).repository)
        )[FirmViewModel::class.java]
    }

    private var _binding: FragmentFirmFormBinding? = null
    private val binding get() = _binding!!

    lateinit var firm: Firm
    private val firmTypesSelection: Map<String, Int> =
        mapOf(
            "bar" to 0,
            "pub" to 1,
            "nightclub" to 2
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillFirmTypes()
        val firmId = navigationArgs.firmId
        if (firmId > 0) {
            firmViewModel.getFirm(firmId).observe(this.viewLifecycleOwner) { firm ->
                this.firm = firm
                bindFirm()
            }

            binding.deleteButton.visibility = View.VISIBLE
            binding.deleteButton.setOnClickListener {
                showDeleteFirmConfirmationDialog()
            }
        } else {
            binding.saveButton.setOnClickListener {
                addFirm()
            }
        }
    }

    private fun fillFirmTypes() {
        val spinner = binding.firmType
        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.firm_types)
        )
    }

    private fun bindFirm() {
        binding.apply {
            firmNameEditText.setText(firm.name)
            ownerNameEditText.setText(firm.ownerName)
            firmTypesSelection[firm.type]?.let { firmType.setSelection(it) }
            latitudeEditText.setText(firm.latitude)
            longitudeEditText.setText(firm.longitude)
            phoneNumberEditText.setText(firm.phoneNumber)
            webUrlEditText.setText(firm.webPage)
            saveButton.setOnClickListener {
                updateFirm()
            }
        }
    }

    private fun showDeleteFirmConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question, firm.name, firm.ownerName))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ -> deleteFirm() }
            .show()
    }

    private fun deleteFirm() {
        firmViewModel.deleteFirm(firm)
        goToFirmListScreen()
    }

    private fun addFirm() {
        if (validateEntry()) {
            firmViewModel.addFirm(
                binding.run {
                    Firm(
                        name = firmNameEditText.text.toString(),
                        ownerName = ownerNameEditText.text.toString(),
                        type = firmType.selectedItem.toString(),
                        latitude = latitudeEditText.text.toString(),
                        longitude = longitudeEditText.text.toString(),
                        phoneNumber = phoneNumberEditText.text.toString(),
                        webPage = webUrlEditText.text.toString()
                    )
                }
            )
            showSuccessMessage(getString(R.string.succesfully_added))
            goToFirmListScreen()
        }
    }

    private fun updateFirm() {
        if (validateEntry()) {
            firmViewModel.editFirm(
                binding.run {
                    Firm(
                        id = navigationArgs.firmId,
                        name = firmNameEditText.text.toString(),
                        ownerName = ownerNameEditText.text.toString(),
                        type = firmType.selectedItem.toString(),
                        latitude = latitudeEditText.text.toString(),
                        longitude = longitudeEditText.text.toString(),
                        phoneNumber = phoneNumberEditText.text.toString(),
                        webPage = webUrlEditText.text.toString()
                    )
                }
            )
            showSuccessMessage(getString(R.string.succesfully_updated))
            goToFirmScreen()
        }
    }

    private fun goToFirmListScreen() {
        findNavController().navigate(R.id.action_firmFormFragment_to_firmListFragment)
    }

    private fun goToFirmScreen() {
        findNavController().navigate(
            FirmFormFragmentDirections.actionFirmFormFragmentToFirmFragment(
                binding.ownerNameEditText.text.toString().ifEmpty { "" },
                navigationArgs.firmId
            )
        )
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun validateEntry(): Boolean {
        val entryErrorResources: MutableList<Int> = firmViewModel.validateEntry(
            binding.latitudeEditText.text.toString(),
            binding.longitudeEditText.text.toString(),
        )

        if (entryErrorResources.isNotEmpty()) {

            Toast.makeText(
                requireContext(),
                makeEntryErrorMessage(entryErrorResources),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }

    private fun makeEntryErrorMessage(entryErrorResources: MutableList<Int>): String {
        var errorEntryMessage = getString(entryErrorResources.removeAt(0))

        entryErrorResources.forEach {
            errorEntryMessage += ", " + getString(it)
        }

        return getString(R.string.is_required, errorEntryMessage)
    }

}