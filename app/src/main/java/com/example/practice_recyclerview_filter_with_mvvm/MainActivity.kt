package com.example.practice_recyclerview_filter_with_mvvm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice_recyclerview_filter_with_mvvm.databinding.ActivityMainBinding

/*
* 서치바 셋팅.
* 국가리스트 데이터 dataBinding
* Filer 기능(필터링 안된 리스트, 필터링 중인 리스트, 필터링 된 리스트 개념)*/

class MainActivity : AppCompatActivity() {
	private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
	private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
	private lateinit var countryAdapter: CountryAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		setSearchViewBar()
		setRecyclerView()
		getCountryList()
		setOnQueryTextListener()

	}


	private fun setSearchViewBar(){
		val searchIcon = binding.countrySearch.findViewById<ImageView>(R.id.search_mag_icon)
		searchIcon.setColorFilter(Color.WHITE)

		val cancelIcon = binding.countrySearch.findViewById<ImageView>(R.id.search_close_btn)
		cancelIcon.setColorFilter(Color.WHITE)

		val textView = binding.countrySearch.findViewById<TextView>(R.id.search_src_text)
		textView.setTextColor(Color.WHITE)
	}

	private fun setRecyclerView(){
		binding.recyclerView.let {
			it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
			it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
			it.setHasFixedSize(true)
		}
	}

	// xo viewModel의 LiveData로 Data분리
	private fun getCountryList(){
		countryAdapter = CountryAdapter(viewModel.countryListWithEmoji)
		viewModel.countryLiveData.observe(
			this,{
				binding.recyclerView.adapter = countryAdapter
			}
		)
	}

	/* xo
	    6.setOnQueryTextListener를 추가.
	    onQueryTextChange 메소드는 서치뷰에 기입하고 수정할 떄마다 호출되고 새 결과로 RecyclerView를 업데이트.*/

	private fun setOnQueryTextListener(){
		binding.countrySearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
			androidx.appcompat.widget.SearchView.OnQueryTextListener{
			override fun onQueryTextSubmit(query: String?): Boolean {
				return false
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				countryAdapter.filter.filter(newText)
				return false
			}

		})

	}
}