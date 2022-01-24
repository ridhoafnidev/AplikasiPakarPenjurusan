package com.kemenag_inhu.absensi.feature.home

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.kemenag_inhu.absensi.core_data.domain.Event
import com.kemenag_inhu.absensi.core_data.domain.ListEvents
import com.kemenag_inhu.absensi.core_data.domain.MenuStatus
import com.kemenag_inhu.absensi.core_domain.model.Menu
import com.kemenag_inhu.absensi.core_navigation.ModuleNavigator
import com.kemenag_inhu.absensi.core_resource.components.base.BaseFragment
import com.kemenag_inhu.absensi.core_util.dayTimeGreeting
import com.kemenag_inhu.absensi.feature.home.viewholder.ItemMenuViewHolder
import com.kemenag_inhu.absensi.feature.home.viewholder.ItemNewEventViewHolder
import com.kemenag_inhu.home.R
import com.kemenag_inhu.home.databinding.FragmentHomeBinding
import lt.neworld.spanner.Spanner
import lt.neworld.spanner.Spans.bold
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.kemenag_inhu.absensi.core_util.setSnapHelper
import com.kemenag_inhu.absensi.feature.home.viewholder.ItemCurrentEventViewHolder
import com.bumptech.glide.Glide
import com.kemenag_inhu.absensi.core_util.BaseDateTime
import com.kemenag_inhu.absensi.core_util.BaseDateTime.Companion.convertTo12H
import com.kemenag_inhu.absensi.core_util.BaseDateTime.Companion.currentDate
import com.kemenag_inhu.absensi.core_util.BaseDateTime.Companion.withIndFormat

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
            Menu(
                5,
                "Pengajuan Cuti",
                R.drawable.ic_paid_leave
            ),
            Menu(
                6,
                "Absensi",
                R.drawable.ic_attendance
            )
        )
    }

    private val homeViewModel: HomeViewModel by viewModel()

    private val recyclerViewMenus by lazy { binding.rvGridMenu }

    private val recyclerViewNewEvents by lazy { binding.rvNewEvent }

    private val recyclerViewCurrentEvents by lazy { binding.rvCurrentEvent }

    override fun initView() {
        getEvent()
        setupEvent()
        setupItems()
        setupMenus()
    }

    private fun getEvent() {
        homeViewModel.getEvent()
    }

    private fun setupEvent() {
        homeViewModel.newEvent.observe(viewLifecycleOwner) { listEvent ->
            if (listEvent != null) {
                setupNewEvents(listEvent)
                setupCurrentEvents(listEvent)
            }
        }
    }

    private fun setupItems() {
        with(binding){
            tvWelcome.text = Spanner().append("Halo ")
                .append("${dayTimeGreeting(requireContext())}, \n")
                .append(getString(R.string.welcome_name), bold())

            ivUser.load(R.drawable.photo_male){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun setupNewEvents(listEvents: ListEvents) {
        recyclerViewNewEvents.setup {
            withLayoutManager(LinearLayoutManager(context))
            withDataSource(dataSourceOf(listEvents))
            withItem<Event, ItemNewEventViewHolder>(R.layout.layout_item_new_event){
                onBind(::ItemNewEventViewHolder){_, item ->
                    Glide.with(requireActivity()).load(item.image).into(imageEvent)
                    titleEvent.text = item.name
                    timestampEvent.text = Spanner().append(item.startDate)
                        .append(" - ")
                        .append(convertTo12H(item.startTime))
                    placeEvent.text = Spanner().append(item.location)
                        .append(" - ")
                        .append(item.poweredBy)
                }
            }
        }
    }

    private fun setupCurrentEvents(listEvent: ListEvents) {

        setSnapHelper(recyclerViewCurrentEvents)
        recyclerViewCurrentEvents.setup {
            withLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
            withDataSource(dataSourceOf(listEvent.filter { it.startDate == currentDate() withIndFormat(BaseDateTime.DATE_FORMAT)  }))
            withItem<Event, ItemCurrentEventViewHolder>(R.layout.layout_item_current_event){
                onBind(::ItemCurrentEventViewHolder){_, item ->
                    Glide.with(requireActivity()).load(item.image).into(imageEvent)
                    titleEvent.text = item.name
                    dateEvent.text = item.startDate.take(2)
                    mounthEvent.text = item.startDate.trim().slice(3..6)
                    placeEvent.text = Spanner().append(item.location)
                        .append(" - ")
                        .append(item.poweredBy)
                }
            }
        }
    }

    private fun setupMenus() {
        recyclerViewMenus.setup {
            withLayoutManager(GridLayoutManager(context, 4))
            withDataSource(dataSourceOf(itemsMenu))
            withItem<Menu, ItemMenuViewHolder>(R.layout.layout_item_menu){
                onBind(::ItemMenuViewHolder){_, item ->
                    titleMenu.text = item.title
                    ivMenu.load(item.image)
                    ivMenu.setOnClickListener {
                        when(item.id){
                            MenuStatus.CreateEvent() -> {
                                navigateToCreateEventActivity()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initListener(){
    }
}