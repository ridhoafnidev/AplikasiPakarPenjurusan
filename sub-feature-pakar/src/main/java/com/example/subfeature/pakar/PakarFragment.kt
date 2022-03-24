package com.example.subfeature.pakar

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.example.core_data.domain.Answer
import com.example.core_data.domain.OnBoardingData
import com.example.core_data.domain.QuestionForPost
import com.example.core_data.domain.Questions
import com.example.core_resource.components.base.BaseFragment
import com.example.subfeature.pakar.adapter.OnBoardingItemAdapter
import com.example.subfeature.pakar.adapter.ViewPagerAdapter
import com.example.subfeature.pakar.databinding.FragmentPakarBinding
import com.example.subfeature.pakar.fragments.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.PakarSecondViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PakarFragment : BaseFragment<FragmentPakarBinding>(FragmentPakarBinding::inflate) {

    private val firstPakarViewModel: PakarFirstViewModel by sharedViewModel()
    private val secondPakarViewModel: PakarSecondViewModel by sharedViewModel()

//    private val itemsAdapter by lazy {
//        OnBoardingItemAdapter(
//            listOf(
//                OnBoardingData(
//                    "Pilih salah satu dari 2 jurusan berikut:",
//                    listOf(Questions( "J01", "IPA"), Questions("J02", "IPS"))
//                ),
//                OnBoardingData(
//                    "Pilih 2 mata pelajaran yang kamu inginkan",
//                    listOf(Questions("B01", "Matematika"), Questions("B02", "Fisika"), Questions("B03","Biologi"), Questions("B04", "Kimia"),Questions("B05", "Bahasa Asing"), Questions("B06", "Bahasa dan Sastra Inggris"))
//                ),
//                OnBoardingData(
//                    "Pilih 2 mata pelajaran yang kamu inginkan",
//                    listOf(Questions("B09", "Sejarah"), Questions("B07", "Geografi"), Questions("B08", "Ekonomi"), Questions("B10", "Sosiologi"))
//                ),
//                OnBoardingData(
//                    "Pilih 5 mata pelajaran yang kamu senangi",
//                    listOf(Questions("M01", "Matematika"), Questions("M04", "Biologi"), Questions("M03", "Kimia"), Questions("M02", "Fisika"),
//                        Questions("M07", "Sejarah"), Questions("M05", "Geografi"), Questions("M06", "Ekonomi"), Questions("M08", "Sosiologi"))
//                ),
//                OnBoardingData(
//                    "Pilih pekerjaan yang Mamu senangi pada bidang apa?",
//                    listOf(Questions("C01", "Kesehatan"), Questions("C02", "Ekonomi"), Questions("C03", "Pendidikan"), Questions("C04", "Aparatur Sipil Negara"), Questions("C05", "Media"))
//                ),
//                OnBoardingData(
//                    "Keterangan minat pekerjaan",
//                    listOf(Questions("P01", "Kesehatan(Dokter, Perawat, Apoteker, Ahli Gizi, Bidang Ahli lab, Klinik dan Medis, Radiografer, Penyuluh Kesehatan, Ahli Epidemiologi, dll)"), Questions("P02", "Ekonomi(Analisi Kredit, Konsultan Ekonomi, Petugas Kredit, Pengusaha Pangan, Petugas Bank, Analisis Kuantitatif, dll)"), Questions("P03", "Pendidikan(Guru, Dosen, Kepala Sekolah, Penulis Buku, Pustakawan, Daycare Staf, Pelatih Olahraga)"), Questions("P04", "Aparatur Sipil Negara(TNI, Polisi, Jaksa, Hukum, dll)"), Questions("P05", "Media(Wartawan, Jurnalis, Reporter, dll ) "))
//                ),
//                OnBoardingData(
//                    "Pilih 2 fakultas yang kamu inginkan jika sudah lulus SMA",
//                    listOf(
//                        Questions("N01", "Fakultas ekonomi dan bisnis"), Questions("N02", "Fakultas farmasi"), Questions("N03", "Fakultas hukum"), Questions("N04", "Fakultas ilmu keperawatan"), Questions("N05", "Fakultas ilmu komputer"),
//                        Questions("N06", "Fakultas kedokteran"), Questions("N07", "Fakultas kedokteran gigi"), Questions("N08", "Fakultas kesehatan masyarakat"), Questions("N09", "Fakultas teknik"), Questions("N10", "Fakultas ilmu pengetahuan budaya"),
//                        Questions("N11", "Fakultas matematika dan ilmu pengetahuan alam"), Questions("N12", "Fakultas sosial dan politik"), Questions("N13", "Fakultas pendidikan fokasi"), Questions("N14", "Fakultas psikologi")),
//                    ),
//                OnBoardingData(
//                    "Pilih salah satu bidang yang kamu cita-citakan",
//                    listOf(
//                        Questions("C01", "Kesehatan"), Questions("C02", "Ekonomi"), Questions("C03", "Pendidikan"), Questions("C04", "Aparatur Sipil Negara"), Questions("C05", "Media"))
//                    ),
//                OnBoardingData(
//                    "Halo Bapa/Ibu pilihlah salah satu penjurusan yang diinginkan untuk anak Bapa/Ibu pada SMA Negeri 1 Lareh Sago Halaban",
//                    listOf(
//                        Questions("D01", "Matematika dan Ilmu Alam"), Questions("D02", "Peminatan Ilmu-ilmu Sosial"))
//                    )
//                ),
//            )
//    }

    private val itemViewPager2 by lazy {
        binding.viewPager
    }

    private val indicatorContainer by lazy {
        binding.indicatorContainer
    }

    private val btnNext by lazy {
        binding.next
    }

    private val btnPrev by lazy {
        binding.preview
    }

    private val itemAdapter by lazy {
        ViewPagerAdapter(requireActivity().supportFragmentManager)
    }

    override fun initView(){
        setupIndicators()
        setupCurrentIndicator(0)
        itemViewPager2.adapter = itemAdapter
        itemViewPager2.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setupCurrentIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
//        itemViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                setupCurrentIndicator(position)
//            }
//        })
    }

    private fun setupIndicators() {
        val indicator = arrayOfNulls<ImageView>(itemAdapter.count)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0, 8,0)
        for(i in indicator.indices){
            indicator[i] = ImageView(requireContext())
            indicator[i]?.let { imageView ->
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
                imageView.layoutParams = layoutParams
                indicatorContainer.addView(imageView)
            }
        }
    }

    private fun setupCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount

        if (position == childCount - 1) btnNext.text = "SELESAI"
        else {
            btnNext.text = "NEXT"
            btnNext.visibility = View.VISIBLE
        }

        if (position == 0) btnPrev.visibility = View.INVISIBLE
        else btnPrev.visibility = View.VISIBLE

        for (i in 0 until childCount){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active_background
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }

    }

    override fun initListener() {
        with(binding){
            val data = arrayListOf<Answer>()
            next.setOnClickListener {
                if (itemViewPager2.currentItem + 1 < itemAdapter.count){
                    itemViewPager2.currentItem += 1
                }
                else {
                    if (next.text.equals("SELESAI")){
                        data.addAll(firstPakarViewModel.getFinalData())
                        data.addAll(secondPakarViewModel.getFinalData())
                        val dataForPost = QuestionForPost(idSiswa = 1L, answers = data)
                        println("data.123 ${dataForPost}")
                    }
                }
            }
            preview.setOnClickListener {
                if (itemViewPager2.currentItem -1 < itemAdapter.count){
                    itemViewPager2.currentItem -= 1
                }
            }
        }
    }

}