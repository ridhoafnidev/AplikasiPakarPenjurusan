package com.example.subfeature.nilai_penjurusan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_data.domain.*
import com.example.subfeature.nilai_penjurusan.databinding.ItemNilaiPenjurusanBinding

class NilaiPenjurusanAdapter(
    private val nilaiSiswaList: List<NilaiSiswa>,
    private val siswaList: List<Siswa>,
    private val hasilAngketList: LastResults
) : RecyclerView.Adapter<NilaiPenjurusanAdapter.NilaiPenjurusanViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NilaiPenjurusanViewHolder {
        val binding = ItemNilaiPenjurusanBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NilaiPenjurusanViewHolder(binding)
    }

    override fun getItemCount() = nilaiSiswaList.size + 1

    override fun onBindViewHolder(holder: NilaiPenjurusanViewHolder, position: Int) {
        with(holder) {
            binding.tvNama.setBackgroundResource(R.drawable.background_black_outline)
            binding.tvRataAkhir.setBackgroundResource(R.drawable.background_black_outline)

            if (position == 0) {
                binding.tvNama.setBackgroundResource(R.drawable.background_outline)
                binding.tvRataAkhir.setBackgroundResource(R.drawable.background_outline)
                binding.tvNama.setTextColor(binding.root.context.resources.getColor(R.color.white))
                binding.tvRataAkhir.setTextColor(binding.root.context.resources.getColor(R.color.white))
                binding.tvNama.text = "Nama"
                binding.tvRataAkhir.text = "Hasil Penjurusan"
            } else {
                val dataNilaiSiswa = nilaiSiswaList[position - 1]
                var dataNilaiHasilAngket = LastResult()
                dataNilaiHasilAngket = hasilAngketList[position - 1]

                binding.tvNama.text = siswaList.find { it.idUser == dataNilaiSiswa.user_id }?.nama

                //region condition get last result magor

                binding.tvRataAkhir.text = when {
                    dataNilaiHasilAngket.isIpa() && dataNilaiSiswa.isIpa() -> IPA
                    dataNilaiHasilAngket.isIps() && dataNilaiSiswa.isIps() -> IPS
                    dataNilaiHasilAngket.isIpc() && dataNilaiSiswa.isIps() -> IPS
                    dataNilaiHasilAngket.isIps() && dataNilaiSiswa.isIpa() -> dataNilaiSiswa.isIpaOrIps()
                    dataNilaiHasilAngket.isIpa() && dataNilaiSiswa.isIps() -> dataNilaiSiswa.isIpaOrIps()
                    else -> ""
                }

                //endregion

            }
        }
    }

    inner class NilaiPenjurusanViewHolder(val binding: ItemNilaiPenjurusanBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        const val IPA = "IPA"
        const val IPS = "IPS"
    }

}