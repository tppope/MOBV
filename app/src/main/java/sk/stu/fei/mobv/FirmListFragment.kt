package sk.stu.fei.mobv

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sk.stu.fei.mobv.adapter.FirmItemAdapter
import sk.stu.fei.mobv.data.Datasource
import sk.stu.fei.mobv.data.FirmDatasource
import sk.stu.fei.mobv.databinding.FragmentFirmFormBinding
import sk.stu.fei.mobv.databinding.FragmentFirmListBinding
import sk.stu.fei.mobv.model.Firm


class FirmListFragment : Fragment() {

    private var _binding: FragmentFirmListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.firmListView
        var firmList: List<Firm> = FirmDatasource().loadFirms(requireContext())
        recyclerView.adapter = FirmItemAdapter(requireContext(), firmList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}