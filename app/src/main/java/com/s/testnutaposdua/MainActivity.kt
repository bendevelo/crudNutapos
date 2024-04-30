package com.s.testnutaposdua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.s.testnutaposdua.databinding.ActivityMainBinding
import com.s.testnutaposdua.ui.DaftarUangMasukFragment
import com.s.testnutaposdua.ui.InputUangFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create fragment instance
        val fragment : DaftarUangMasukFragment = DaftarUangMasukFragment.newInstance()
        val fragment1 : InputUangFragment = InputUangFragment.newInstance()


        // check is important to prevent activity from attaching the fragment if already its attached
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment, "input_uang")
                .commit()
        }


    }
    }
