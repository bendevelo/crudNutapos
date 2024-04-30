package com.s.testnutaposdua.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.testnutaposdua.R
import com.s.testnutaposdua.adapter.AdapterTrx
import com.s.testnutaposdua.databinding.FragmentDaftarUangMasukBinding
import com.s.testnutaposdua.databinding.FragmentInputUangBinding
import com.s.testnutaposdua.db.trxDatabase
import com.s.testnutaposdua.db.trxRepository
import com.s.testnutaposdua.viewmodel.ViewModelFactory
import com.s.testnutaposdua.viewmodel.trxViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DaftarUangMasukFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentDaftarUangMasukBinding
    lateinit var viewModel: trxViewModel

    private val trxAdapter: AdapterTrx by lazy {
        val repository =
            trxRepository(trxDatabase.getDatabaseInstance(requireContext()).trxDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[trxViewModel::class.java]
        AdapterTrx(requireActivity(), viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDaftarUangMasukBinding.inflate(inflater, container, false)
        val view = binding.root


        val repository =
            trxRepository(trxDatabase.getDatabaseInstance(requireContext()).trxDao())
        val viewModelFactory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[trxViewModel::class.java]

        binding.RvTrx.apply {
            adapter = trxAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }


        val fragment1: InputUangFragment = InputUangFragment.newInstance()
        binding.inputUang.setOnClickListener({

            requireActivity().supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_container, fragment1, "input_uang1")
                .commit()

        })

        return view
    }

    override fun onResume() {
        super.onResume()

        loadUsers()
    }

    private fun loadUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val trx = viewModel.getAlltrx()

            lifecycleScope.launch(Dispatchers.Main) {
                trxAdapter.setData(trx)
            }

        }
    }

    companion object {
        fun newInstance() = DaftarUangMasukFragment()
    }
}