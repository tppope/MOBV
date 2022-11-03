package sk.stu.fei.mobv

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.adapter.FirmItemAdapter
import sk.stu.fei.mobv.data.FirmDatasource
import sk.stu.fei.mobv.databinding.FragmentFirmListBinding
import sk.stu.fei.mobv.model.Firm

private const val FIRM_ID = "firm_id"

class FirmListFragment : Fragment() {

    private var _binding: FragmentFirmListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var firmItemAdapter: FirmItemAdapter
    private var sortModeFirmList: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (firmList == null) {
            firmList = FirmDatasource().loadFirms(context).toMutableList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            removeFromFirmList(it.getLong(FIRM_ID))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.firm_list_menu, menu)

                val layoutButton = menu.findItem(R.id.action_switch_layout)
               layoutButton.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_sort)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_switch_layout -> {
                        sortModeFirmList = when(sortModeFirmList){
                            "asc" -> "desc"
                            "desc" -> "asc"
                            else -> "asc"
                        }
                        sortFirmList()
                        return true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        recyclerView = binding.firmListView
        firmItemAdapter = FirmItemAdapter(requireContext(), firmList!!)
        recyclerView.adapter = firmItemAdapter

        binding.addFirm.setOnClickListener {
            view.findNavController()
                .navigate(FirmListFragmentDirections.actionFirmListFragmentToFirmFormFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removeFromFirmList(id: Long) {
        firmList!!.removeAll{
            it.id == id
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sortFirmList() {
        when(sortModeFirmList) {
            "asc" -> firmList!!.sortBy { it.tags.firmName }
            else -> firmList!!.sortByDescending { it.tags.firmName }
        }
        firmItemAdapter.notifyDataSetChanged()
    }

    companion object {
        private var firmList: MutableList<Firm>? = null
    }

}