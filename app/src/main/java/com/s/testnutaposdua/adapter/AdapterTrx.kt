package com.s.testnutaposdua.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.s.testnutaposdua.R
import com.s.testnutaposdua.databinding.ListTrxBinding

import com.s.testnutaposdua.model.Transaksi
import com.s.testnutaposdua.ui.DaftarUangMasukFragment
import com.s.testnutaposdua.ui.InputUangFragment
import com.s.testnutaposdua.util.BitmapConverter
import com.s.testnutaposdua.viewmodel.trxViewModel

class AdapterTrx(val ctx : FragmentActivity,val vm :trxViewModel) : RecyclerView.Adapter<AdapterTrx.CustomHolder>() {

    private val trx = mutableListOf<Transaksi>()
    private lateinit var mContext: Context
    var tanggall ="none"
    var totalAll =0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val binding = ListTrxBinding.inflate(inflater, parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(trx[position])

    }

    override fun getItemCount(): Int = trx.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Transaksi>) {
        trx.clear()
        trx.addAll(list)
        notifyDataSetChanged()
    }

    inner class CustomHolder(private val binding: ListTrxBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trx: Transaksi) {
            binding.apply {
                trx.also {

                   if (it.flag==2){
                       binding.tanggal.isVisible =true
                       binding.total.isVisible = true
                       binding.constraintLayout.isGone = true

                   }

                    binding.hapus.setOnClickListener({v->

                       vm.deleteTrx(Transaksi(it.id,it.tanggal,it.jam,it.kasir,it.keterangan,it.jenis,it.sumber,it.jumlah,it.struk,it.flag))
                        val fragment1 : DaftarUangMasukFragment = DaftarUangMasukFragment.newInstance()
                        ctx.supportFragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container, fragment1, "input_uang1")
                            .commit()
                    })

                    binding.lihatfoto.setOnClickListener({v->

                        val dialog = Dialog(ctx)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(true)
                        dialog.setContentView(R.layout.foto_layout)

                        val image = dialog.findViewById(R.id.image) as ImageView

                        if(it.struk=="none"){
                            Toast.makeText(ctx,"no photo",Toast.LENGTH_SHORT).show()
                        }else{
                            image.setImageBitmap(BitmapConverter.toBitmap(it.struk))
                        }


                        dialog.show()
                    })

                    binding.edit.setOnClickListener({ v->

                        val bundle = Bundle()
                        bundle.putString("inputText", "edit")
                        bundle.putString("id", it.id.toString())
                        bundle.putString("asal", it.sumber)
                        bundle.putString("kasir", it.kasir)
                        bundle.putString("keterangan", it.keterangan)
                        bundle.putString("jenis", it.jenis)
                        bundle.putString("jumlah", it.jumlah.toString())
                        bundle.putString("struk", it.struk)


                        val fragment1 : InputUangFragment = InputUangFragment.newInstance()
                        fragment1.arguments = bundle
                        ctx.supportFragmentManager
                            .beginTransaction()
                            .addToBackStack(null)
                            .add(R.id.fragment_container, fragment1, "input_uang1")
                            .commit()

                    })

                    binding.tanggal.text = it.tanggal
                    binding.jam.text = it.jam
                    binding.total.text = it.jumlah.toString()
                    binding.modal.text = it.jumlah.toString()
                    binding.keterangan.text = "Dari "+it.sumber+" ke "+it.kasir


                }
            }
        }
    }

}