package com.example.subfeature.nilai_siswa

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_data.domain.NilaiSiswa
import com.example.core_data.domain.Siswa
import com.example.subfeature.nilai_siswa.databinding.ItemNilaiSiswaBinding
import timber.log.Timber

class NilaiSiswaAdapter(
    private val nilaiSiswaList: List<NilaiSiswa>,
    private val siswaList: List<Siswa>,
    private val nilaiSiswaInterface: NilaiSiswaInterface
) : RecyclerView.Adapter<NilaiSiswaAdapter.NilaiSiswaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NilaiSiswaViewHolder {
        val binding = ItemNilaiSiswaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NilaiSiswaViewHolder(binding)
    }

    override fun getItemCount() = nilaiSiswaList.size + 1

    override fun onBindViewHolder(holder: NilaiSiswaViewHolder, position: Int) {
        with(holder) {
            binding.tvNama.setBackgroundResource(R.drawable.background_black_outline)
            binding.tvRataIpa.setBackgroundResource(R.drawable.background_black_outline)
            binding.tvRataIps.setBackgroundResource(R.drawable.background_black_outline)
            binding.tvRataAkhir.setBackgroundResource(R.drawable.background_black_outline)
            binding.tvAksi.setBackgroundResource(R.drawable.background_black_outline)

            if (position == 0) {
                binding.tvNama.text = "Nama"
                binding.tvRataIpa.text = "Rata-Rata Ipa"
                binding.tvRataIps.text = "Rata-Rata Ips"
                binding.tvRataAkhir.text = "Rata-Rata Akhir"
                binding.tvAksi.text = "Aksi"
            } else {
                val dataNilaiSiswa = nilaiSiswaList[position - 1]
                binding.tvNama.text = siswaList.find { it.idUser == dataNilaiSiswa.user_id }?.nama
                binding.tvRataIpa.text = dataNilaiSiswa.rata_raport_ipa.toString()
                binding.tvRataIps.text = dataNilaiSiswa.rata_raport_ips.toString()
                binding.tvRataAkhir.text = dataNilaiSiswa.rata_akhir.toString()
                binding.tvAksi.text = "Hapus"

                binding.tvAksi.setOnClickListener {
                    nilaiSiswaInterface.onDeleteNilaiSiswa(dataNilaiSiswa.user_id.toString())
                }
            }
        }
    }

    inner class NilaiSiswaViewHolder(val binding: ItemNilaiSiswaBinding) :
        RecyclerView.ViewHolder(binding.root)

}