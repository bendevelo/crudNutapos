package com.s.testnutaposdua.ui

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.s.testnutaposdua.R
import com.s.testnutaposdua.databinding.FragmentInputUangBinding
import com.s.testnutaposdua.db.trxDatabase
import com.s.testnutaposdua.db.trxRepository
import com.s.testnutaposdua.model.Transaksi
import com.s.testnutaposdua.util.BitmapConverter
import com.s.testnutaposdua.viewmodel.ViewModelFactory
import com.s.testnutaposdua.viewmodel.trxViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class InputUangFragment : Fragment() {

    lateinit var binding: FragmentInputUangBinding
    lateinit var viewModel: trxViewModel

    var struk = "none"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputUangBinding.inflate(inflater, container, false)
        val view = binding.root

        val type = arguments?.getString("inputText")

        if (type == "edit") {

            binding.ETasal.setText(arguments?.getString("asal"))
            binding.ETmasuk.setText(arguments?.getString("kasir"))
            binding.ETjumlah.setText(arguments?.getString("jumlah"))
            binding.ETketerangan.setText(arguments?.getString("keterangan"))
            binding.ETJenis.setText(arguments?.getString("jenis"))
            struk = arguments?.getString("struk").toString()
        }




        val repository =
            trxRepository(trxDatabase.getDatabaseInstance(requireContext()).trxDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[trxViewModel::class.java]

        binding.save.setOnClickListener({

            if (binding.ETmasuk.text!!.isNotEmpty() && binding.ETasal.text!!.isNotEmpty() && binding.ETJenis.text!!.isNotEmpty() && binding.ETjumlah.text!!.isNotEmpty() && binding.ETketerangan.text!!.isNotEmpty()) {

                val type = arguments?.getString("inputText")

                if (type == "edit") {
                    val trx = Transaksi(
                        tanggal = LocalDate.now().toString(),
                        jam = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                        kasir = binding.ETmasuk.text.toString(),
                        jumlah = binding.ETjumlah.text.toString().toInt(),
                        sumber = binding.ETasal.text.toString(),
                        struk = struk,
                        keterangan = binding.ETketerangan.text.toString(),
                        jenis = binding.ETJenis.text.toString(),
                        flag = 1,
                        id = arguments?.getString("id")?.toLong()
                    )
                    viewModel.updateTrx(trx)
                    Toast.makeText(context, "Data Berhasil disimpan", Toast.LENGTH_LONG).show()

                    val fragment1: DaftarUangMasukFragment = DaftarUangMasukFragment.newInstance()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment1, "input_uang1")
                        .commit()

                } else {

                    val trx = Transaksi(
                        tanggal = LocalDate.now().toString(),
                        jam = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                        kasir = binding.ETmasuk.text.toString(),
                        jumlah = binding.ETjumlah.text.toString().toInt(),
                        sumber = binding.ETasal.text.toString(),
                        struk = struk,
                        keterangan = binding.ETketerangan.text.toString(),
                        jenis = binding.ETJenis.text.toString(),
                        flag = 1,
                        id = null
                    )
                    viewModel.insertTrx(trx)
                    Toast.makeText(context, "Data Berhasil disimpan", Toast.LENGTH_LONG).show()

                    val fragment1: DaftarUangMasukFragment = DaftarUangMasukFragment.newInstance()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_container, fragment1, "input_uang1")
                        .commit()
                }


            } else {

                Toast.makeText(context, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }


        })

        binding.lebihtau.setOnClickListener({

            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.lebihtau_layout, null)
            dialog.setContentView(view)
            dialog.show()
        })


        binding.ETJenis.setOnClickListener({

            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.jenis_layout)

            val option1 = dialog.findViewById(R.id.pendapatan) as TextView
            val option2 = dialog.findViewById(R.id.nonpendapat) as TextView

            option1.setOnClickListener({

                binding.ETJenis.setText("Pendapatan Lain")
                dialog.dismiss()
            })

            option2.setOnClickListener({

                binding.ETJenis.setText("Non Pendapatan")
                dialog.dismiss()
            })

            dialog.show()

        })

        binding.TVfoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.ubah.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.hapus.setOnClickListener({
            binding.TVfoto.isVisible = true
            binding.linfoto.isGone = true

        })

        binding.back.setOnClickListener({

            activity?.supportFragmentManager!!.popBackStack()
        })

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            binding.TVfoto.isGone = true
            binding.linfoto.isVisible = true
            binding.ImgFoto.setImageURI(uri)
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                requireContext().contentResolver, Uri.parse(
                    uri.toString()
                )
            )

            struk = BitmapConverter.fromBitmap(bitmap)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Kembali", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {

        fun newInstance() = InputUangFragment()


    }
}