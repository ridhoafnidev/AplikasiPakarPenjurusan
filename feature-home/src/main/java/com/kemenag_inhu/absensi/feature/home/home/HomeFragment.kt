package com.kemenag_inhu.absensi.feature.home.home

import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import com.kemenag_inhu.absensi.core_domain.model.Menu
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_util.dayTimeGreeting
import com.kemenag_inhu.absensi.feature.home.HomeViewModel
import com.kemenag_inhu.absensi.feature.home.viewholder.ItemHistoryAttendanceViewHolder
import com.kemenag_inhu.home.R
import com.kemenag_inhu.home.databinding.FragmentHomeBinding
import lt.neworld.spanner.Spanner
import lt.neworld.spanner.Spans.bold
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), ModuleNavigator {

    private val itemsMenu by lazy {
        listOf(
            Menu(
                1,
                "Buat Event",
                R.drawable.ic_create_event
            ),
            Menu(
                2,
                "Event Saya",
                R.drawable.ic_my_event
            ),
            Menu(
                3,
                "Daftar Karyawan",
                R.drawable.ic_employe
            ),
            Menu(
                4,
                "Reservasi Meeting",
                R.drawable.ic_reservation
            ),
        )
    }

    private val homeViewModel: HomeViewModel by viewModel()

    private val recyclerViewHistoryAttendance by lazy { binding.rvHistoryAttendance }

    override fun initView() {
        getEvent()
        setupEvent()
        setupItems()
    }

    private fun getEvent() {
        homeViewModel.getEvent()
    }

    private fun setupEvent() {
        homeViewModel.newEvent.observe(viewLifecycleOwner) { listEvent ->
            if (listEvent != null) {
                setupNewEvents(listEvent)
            }
        }
    }

    private fun setupItems() {
        with(binding){
            tvWelcome.text = Spanner().append("Assalamualaikum \n")
                .append("${dayTimeGreeting(requireContext())}, \n")
                .append(getString(R.string.welcome_name), bold())

            ivUser.load(R.drawable.photo_male){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun setupNewEvents(listEvents: ListEvents) {
        recyclerViewHistoryAttendance.setup {
            withLayoutManager(LinearLayoutManager(context))
            withDataSource(dataSourceOf(listEvents))
            withItem<Event, ItemHistoryAttendanceViewHolder>(R.layout.layout_item_new_event){
                onBind(::ItemHistoryAttendanceViewHolder){ _, item ->
                    dateAttendance.text = item.startDate.take(2)
                    dayAttendance.text = item.startDate.trim().slice(3..6)
                }
            }
        }
    }

    override fun initListener(){
        binding.btnClockIn.setOnClickListener {
            navigateToCreateEventActivity()
        }
    }
}