package sk.stu.fei.mobv

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import sk.stu.fei.mobv.databinding.FragmentFirmBinding
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val OWNER_NAME = "owner_name"
private const val FIRM_ID = "firm_id"
private const val FIRM_NAME = "firm_name"
private const val PHONE_NUMBER = "phone_number"
private const val WEB_URL = "web_url"
private const val LATITUDE = "latitude"
private const val LONGITUDE = "longitude"

class FirmFragment : Fragment() {
    private var _binding: FragmentFirmBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private lateinit var waterAnimation: LottieAnimationView
    private lateinit var firmName: String
    private lateinit var uriMap: Uri
    private lateinit var uriPhone: Uri
    private lateinit var uriWeb: String
    private var firmId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firmName = it.getString(FIRM_NAME).toString()
            firmId = it.getLong(FIRM_ID)
            uriMap = Uri.parse(
                String.format(
                    Locale.ENGLISH,
                    GEO_PREFIX,
                    it.getString(LATITUDE),
                    it.getString(LONGITUDE)
                )
            )
            uriPhone = Uri.parse(
                String.format(
                    Locale.ENGLISH,
                    TEL_PREFIX,
                    it.getString(PHONE_NUMBER),
                )
            )
            uriWeb = it.getString(WEB_URL).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirmBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        waterAnimation = binding.animationView

        binding.firmName.text = firmName

        binding.deleteButton.setOnClickListener {
            val action = FirmFragmentDirections.actionFirmFragmentToFirmListFragment(
                firmId = this.firmId
            )
            view.findNavController().navigate(action)
        }

        binding.showOnMapButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, uriMap)
            context?.startActivity(intent)
        }

        binding.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, uriPhone)
            context?.startActivity(intent)
        }

        binding.showInBrowserButton.setOnClickListener {
            if (uriWeb.startsWith("https://") || uriWeb.startsWith("http://")) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriWeb))
                context?.startActivity(intent)
            }else{
                Toast.makeText(context, "Invalid Url", Toast.LENGTH_SHORT).show()
            }
        }

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
        binding.pourOutButton.setOnClickListener { pourOutGlass() }

    }

    private fun pourOutGlass() {
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

    companion object {
        val GEO_PREFIX = "geo:%s,%s"
        val TEL_PREFIX = "tel:%s"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FirmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(
            firmId: Long,
            ownerName: String,
            firmName: String,
            phoneNumber: String,
            webUrl: String,
            latitude: String,
            longitude: String
        ) =
            FirmFragment().apply {
                arguments = Bundle().apply {
                    putString(OWNER_NAME, ownerName)
                    putLong(FIRM_ID, firmId)
                    putString(FIRM_NAME, firmName)
                    putString(PHONE_NUMBER, phoneNumber)
                    putString(WEB_URL, webUrl)
                    putString(LATITUDE, latitude)
                    putString(LONGITUDE, longitude)
                }
            }
    }
}