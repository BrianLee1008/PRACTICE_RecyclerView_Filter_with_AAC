package com.example.practice_recyclerview_filter_with_mvvm


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practice_recyclerview_filter_with_mvvm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

	private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
	override fun onCreate(savedInstanceState: Bundle?) {
			super.onCreate(savedInstanceState)
			setContentView(binding.root)

		binding.detailCountryText.text =intent.extras!!.getString("passSelectedCountry")!!
	}
}