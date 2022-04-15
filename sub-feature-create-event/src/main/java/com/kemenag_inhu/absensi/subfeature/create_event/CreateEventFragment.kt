package com.kemenag_inhu.absensi.subfeature.create_event

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.form
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import com.akexorcist.snaptimepicker.TimeRange
import com.akexorcist.snaptimepicker.TimeValue
import com.bumptech.glide.Glide
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_util.*
import com.kemenag_inhu.absensi.core_util.utility.BaseDateTime
import com.kemenag_inhu.absensi.core_util.utility.BaseDateTime.Companion.withIndFormat
import com.kemenag_inhu.absensi.core_util.utility.BaseDateTime.Companion.withIndoFormat
import com.kemenag_inhu.absensi.core_util.utility.dismissKeyboard
import com.kemenag_inhu.absensi.subfeature.create_event.R as R_create_event
import com.kemenag_inhu.absensi.subfeature.create_event.databinding.FragmentCreateEventBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class CreateEventFragment : Fragment(), ModuleNavigator {

    private var _binding: FragmentCreateEventBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CreateEventViewModel>()

    private val edtEventName by lazy {
        binding.edtEventName
    }
    private val edtEventLocation by lazy {
        binding.edtEventLocation
    }
    private val edtEventDescription by lazy {
        binding.edtEventDescription
    }
    private val edtEventOrganizer by lazy {
        binding.edtEventOrganizer
    }
    private val edtDateStart by lazy {
        binding.edtDateFrom
    }
    private val edtDateEnd by lazy {
        binding.edtDateTo
    }
    private val edtTimeStart by lazy {
        binding.tvDateFromClock
    }
    private val edtTimeEnd by lazy {
        binding.tvDateToClock
    }
    private val btnNext by lazy {
        binding.btnNext
    }

    private val textHintEmptyEventName by lazy {
        getString(R_create_event.string.require_event_name)
    }
    private val textHintEmptyEventLocation by lazy {
        getString(R_create_event.string.require_event_location)
    }
    private val textHintEmptyEventDateStart by lazy {
        getString(R_create_event.string.require_event_start_date)
    }
    private val textHintEmptyEventTimeStart by lazy {
        getString(R_create_event.string.require_start_time)
    }
    private val textHintEmptyEventDateEnd by lazy {
        getString(R_create_event.string.require_event_end_date)
    }
    private val textHintEmptyEventTimeEnd by lazy {
        getString(R_create_event.string.require_event_end_time)
    }
    private val textHintEmptyEventDescription by lazy {
        getString(R_create_event.string.require_event_description)
    }
    private val textHintEmptyEventOrganizer by lazy {
        getString(R_create_event.string.require_event_organizer)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    var imageUri: Uri? = null
    var imagePath: String? = null

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        setupInput()
        setupListener()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ permission ->
        if (permission){
            navigateToGallery()
        }
        else{
            Toast.makeText(requireContext(), getString(R_create_event.string.no_permission), Toast.LENGTH_SHORT).show()
        }
    }

    private val resultPickImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        imageUri = result.data?.data

        if (imageUri != null) {
            imagePath = getRealPathFromURI(requireContext(), imageUri)
            Glide.with(requireActivity()).load(imagePath).into(binding.ivEvent)
        }

    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun navigateToGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        resultPickImage.launch(intent)
    }

    private fun setupInput() {
        form {
            useRealTimeValidation(disableSubmit = true)
            inputLayout(R_create_event.id.il_event_name){
                isNotEmpty().description(textHintEmptyEventName)
            }
            inputLayout(R_create_event.id.il_event_location){
                isNotEmpty().description(textHintEmptyEventLocation)
            }
            inputLayout(R_create_event.id.il_date_from){
                isNotEmpty().description(textHintEmptyEventDateStart)
            }
            inputLayout(R_create_event.id.il_date_from_clock){
                isNotEmpty().description(textHintEmptyEventTimeStart)
            }
            inputLayout(R_create_event.id.il_date_to_clock){
                isNotEmpty().description(textHintEmptyEventTimeEnd)
            }
            inputLayout(R_create_event.id.il_event_description){
                isNotEmpty().description(textHintEmptyEventDateEnd)
            }
            inputLayout(R_create_event.id.il_event_organizer){
                isNotEmpty().description(textHintEmptyEventDescription)
            }
            inputLayout(R_create_event.id.il_event_organizer){
                isNotEmpty().description(textHintEmptyEventOrganizer)
            }
            submitWith(R_create_event.id.btn_next){
                dismissKeyboard()
                if (imagePath != null){

                    showProgress()

                    val eventName = "${edtEventName.text}"
                    val eventLocation = "${edtEventLocation.text}"
                    val eventDateStart = "${edtDateStart.text}"
                    val eventDateEnd = "${edtDateEnd.text}"
                    val eventTimeStart = "${edtTimeStart.text}"
                    val eventTimeEnd = "${edtTimeEnd.text}"
                    val eventDescription = "${edtEventDescription.text}"
                    val eventOrganizer = "${edtEventOrganizer.text}"
                    val image = imagePath
                    image?.let { img ->
                        viewModel.next(eventName, eventLocation, eventDateStart, eventDateEnd, eventTimeStart,
                            eventTimeEnd, eventDescription, eventOrganizer, img)
                    }
                }
                else{
                    Toast.makeText(context, getString(R_create_event.string.pick_image), Toast.LENGTH_SHORT).show()
                }

            }
        }

        btnNext.bindLifecycle(viewLifecycleOwner)

    }

    private fun setupObserver() {
        viewModel.startTime.observe(viewLifecycleOwner){ localTime ->
            val time =  localTime withIndoFormat BaseDateTime.TIME_FORMAT
            edtTimeStart.text = time.toEditable()
        }

        viewModel.endTime.observe(viewLifecycleOwner){ localTime ->
            val time =  localTime withIndoFormat BaseDateTime.TIME_FORMAT
            edtTimeEnd.text = time.toEditable()
        }

        viewModel.startDate.observe(viewLifecycleOwner){ localDate ->
            val time =  localDate withIndFormat(BaseDateTime.DATE_FORMAT)
            edtDateStart.text = time.toEditable()
        }

        viewModel.endDate.observe(viewLifecycleOwner){ localDate ->
            val time =  localDate withIndFormat(BaseDateTime.DATE_FORMAT)
            edtDateEnd.text = time.toEditable()
        }

        viewModel.isSaved.observe(viewLifecycleOwner){ isSaved ->
            if (isSaved){
                Handler(Looper.getMainLooper()).postDelayed({
                    hideProgress(true)
                    navigateToHomeActivity(true)
                }, 1000L)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {

        binding.cardView.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                navigateToGallery()
            } else {
                if (isCameraPermissionGranted()){
                    navigateToGallery()
                } else{
                    requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

        edtTimeStart.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    showTimePicker(viewModel.startTime.value!!, viewModel.endTime.value!!, R_create_event.string.event_time_from){ time ->
                        viewModel.setStartTime(time)
                    }
                    true
                }
                else -> false
            }
        }

        edtTimeEnd.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    showTimePicker(viewModel.startTime.value!!, viewModel.minStartTime,R_create_event.string.event_time_to){ time ->
                        viewModel.setEndTime(time)
                    }
                    true
                }
                else -> false
            }
        }

        edtDateStart.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    showDatePicker(viewModel.minStartDate, viewModel.startDate.value!!, getString(R_create_event.string.event_date_from)){ date ->
                        viewModel.setStartDate(date)
                    }
                    true
                }
                else -> false
            }
        }

        edtDateEnd.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action) {
                MotionEvent.ACTION_UP -> {
                    showDatePicker(viewModel.minStartDate, viewModel.startDate.value!!, getString(R_create_event.string.event_date_to)){ date ->
                        viewModel.setEndDate(date)
                    }
                    true
                }
                else -> false
            }
        }
    }

    @SuppressLint("NewApi")
    private fun showDatePicker(
        minDate: LocalDate?,
        initialDate: LocalDate,
        title: CharSequence,
        onSelected: (LocalDate) -> Unit
    ) {
        DatePickerDialog(
            requireContext(),
            null,
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        ).apply {
            minDate?.let {
                datePicker.minDate = it.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            }
            setTitle(title)
            setButton(
                DialogInterface.BUTTON_POSITIVE,
                getText(R_create_event.string.dialog_button_positive)
            ) { dialog, _ ->
                with((dialog as DatePickerDialog).datePicker) {
                    onSelected(LocalDate.of(year, month + 1, dayOfMonth))
                }
            }
            setButton(
                DialogInterface.BUTTON_NEGATIVE,
                getText(R_create_event.string.dialog_button_negative),
                null as Message?
            )
        }.show()
    }

    @SuppressLint("NewApi")
    fun showTimePicker(selectedTime: LocalTime, minTime: LocalTime, title: Int, onSelected: (LocalTime) -> Unit) {

        val minValue = TimeValue(minTime.hour,minTime.minute)
        val selectedValue = TimeValue(selectedTime.hour, selectedTime.minute)

        val maxValue = TimeValue(24, 59)
        SnapTimePickerDialog.Builder().apply {
            setTitle(title)
            setPrefix(R.string.time_suffix)
            setSuffix(R.string.time_prefix)
            setThemeColor(R.color.colorTextAccent)
            setTitleColor(R.color.colorDivider)
            setPreselectedTime(selectedValue)
            setSelectableTimeRange(TimeRange(minValue, maxValue))
        }.build().apply {
            setListener { hour, minute ->
                onSelected(LocalTime.of(hour, minute))
            }
        }.show(childFragmentManager, "time_picker")
    }

    private fun showProgress() = with(binding) {
        listOf(
            btnNext, ilEventName, ilEventLocation, ilDateFrom, ilDateTo, ilDateToClock,
            ilDateFromClock, ilEventDescription, ilEventOrganizer
        ).forEach { it.isEnabled = false }
        btnNext.showProgress()
    }

    private fun hideProgress(isEnable: Boolean = false) = with(binding){
        btnNext.postDelayed(
            {
                listOf(
                    btnNext, ilEventName, ilEventLocation, ilDateFrom, ilDateTo, ilDateToClock,
                    ilDateFromClock, ilEventDescription, ilEventOrganizer
                ).forEach { it.isEnabled = true }
            },1000L
        )

        btnNext.hideProgress(getString(R_create_event.string.next)){
            isEnable && with(binding) {
                "${edtEventName.text}".isNotBlank() && "${edtEventLocation.text}".isNotBlank()
                && "${edtDateFrom.text}".isNotBlank() && "${edtDateTo.text}".isNotBlank()
                && "${edtTimeStart.text}".isNotBlank() && "${edtTimeEnd.text}".isNotBlank()
                && "${edtEventDescription.text}".isNotBlank() && "${edtEventOrganizer.text}".isNotBlank()
            }
        }
    }

}