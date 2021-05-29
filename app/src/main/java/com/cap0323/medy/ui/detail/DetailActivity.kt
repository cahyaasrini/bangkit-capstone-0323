package com.cap0323.medy.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cap0323.medy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

        companion object {
            const val idMedicine = "extra_id_medicine"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}