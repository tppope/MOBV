package sk.stu.fei.mobv.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.ui.adapter.FirmItemAdapter
import sk.stu.fei.mobv.data.SettingsDataStore
import sk.stu.fei.mobv.databinding.FragmentFirmListBinding
import sk.stu.fei.mobv.ui.adapter.FirmEventListener
import sk.stu.fei.mobv.ui.viewmodel.FirmViewModel
import sk.stu.fei.mobv.ui.viewmodel.factory.FirmViewModelFactory

private const val FIRM_ID = "firm_id"

class FirmListFragment : Fragment() {

    private var _binding: FragmentFirmListBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsDataStore: SettingsDataStore


    private val firmViewModel: FirmViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            FirmViewModelFactory(activity.application)
        )[FirmViewModel::class.java]
    }

    private var sortModeFirmList: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setMenuBar()

        refreshDataOnStart()

        bind()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setMenuBar() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.firm_list_menu, menu)

                val layoutButton = menu.findItem(R.id.action_switch_layout)
                layoutButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_sort)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_switch_layout -> {
                        sortModeFirmList = when (sortModeFirmList) {
                            "asc" -> "desc"
                            "desc" -> "asc"
                            else -> "asc"
                        }
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun bind() {
        binding.apply {
            firmViewModel = this@FirmListFragment.firmViewModel
            thisFragment = this@FirmListFragment
            firmListView.adapter = FirmItemAdapter(
                FirmEventListener {ownerName: String, firmId: Long ->
                findNavController()
                    .navigate(FirmListFragmentDirections.actionFirmListFragmentToFirmFragment(
                        firmId = firmId,
                        ownerName = ownerName
                    ))
            })
            refreshLayout.setOnRefreshListener {
                this@FirmListFragment.firmViewModel.refreshDataFromRepository()
                refreshLayout.isRefreshing = false
            }
        }
    }

    fun goToAddFirmScreen() {
        findNavController().navigate(R.id.action_firmListFragment_to_firmFormFragment)
    }

    private fun refreshDataOnStart() {
        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.refreshDataPreferences.asLiveData()
            .observe(viewLifecycleOwner) { isFirmsRefreshed ->
                if (!isFirmsRefreshed) {
                    firmViewModel.refreshDataFromRepository()
                }
            }
    }



}