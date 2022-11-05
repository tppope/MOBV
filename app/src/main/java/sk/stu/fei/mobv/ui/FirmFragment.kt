package sk.stu.fei.mobv.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import sk.stu.fei.mobv.R
import sk.stu.fei.mobv.databinding.FragmentFirmBinding
import sk.stu.fei.mobv.ui.viewmodel.FirmViewModel
import sk.stu.fei.mobv.ui.viewmodel.factory.FirmViewModelFactory
import java.util.*

class FirmFragment : Fragment() {
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

    private var _binding: FragmentFirmBinding? = null
    private val binding get() = _binding!!

    private lateinit var waterAnimation: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        firmViewModel.getFirm(navigationArgs.firmId)
        binding.apply {
            firmViewModel = this@FirmFragment.firmViewModel
            thisFragment = this@FirmFragment
            lifecycleOwner = this@FirmFragment
        }

        waterAnimation = binding.animationView

        binding.addButton.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startAddWaterToGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }
        binding.removeButton.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRemoveWaterFromGlass()
                }
                MotionEvent.ACTION_UP -> {
                    stopAnimateWater()
                }
            }
            true
        }

    }

    fun pourOutGlass() {
        if (waterAnimation.progress != 0F) {
            waterAnimation.speed = -5F
            waterAnimation.resumeAnimation()
        }
    }

    private fun startAddWaterToGlass() {
        if (waterAnimation.progress != 1F) {
            waterAnimation.speed = 1F
            waterAnimation.resumeAnimation()
        }
    }

    private fun startRemoveWaterFromGlass() {
        if (waterAnimation.progress != 0F) {
            waterAnimation.speed = -1F
            waterAnimation.resumeAnimation()
        }
    }

    private fun stopAnimateWater() {
        waterAnimation.pauseAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun goToEditFirmScreen() {
        findNavController().navigate(
            FirmFragmentDirections.actionFirmFragmentToFirmFormFragment(
                navigationArgs.firmId
            )
        )
    }

    fun showOnMapIntent(latitude: String, longitude: String) {
        context?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:${latitude},${longitude}")
            )
        )

    }

    fun callPhoneNumberIntent(phoneNumber: String) {
        context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${phoneNumber}")))
    }

    fun showWebPageIntent(uriWeb: String) {
        if (uriWeb.startsWith("https://") || uriWeb.startsWith("http://")) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriWeb))
            context?.startActivity(intent)
        } else {
            Toast.makeText(context, "Invalid Url", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val GEO_PREFIX = "geo:%s,%s"
        val TEL_PREFIX = "tel:%s"
    }
}