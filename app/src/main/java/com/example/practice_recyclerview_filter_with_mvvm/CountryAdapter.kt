package com.example.practice_recyclerview_filter_with_mvvm


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_recyclerview_filter_with_mvvm.databinding.ItemCountryBinding
import java.util.*
import kotlin.collections.ArrayList

class CountryAdapter(private var countryList: ArrayList<String>) :
	RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

	private lateinit var mContext: Context

	private var countryFilterList = ArrayList<String>()

	// Filter 작업을 할 때 counterFilterList의 갱신작업이 필요하기 때문에 countryList만 사용하지 않고
	// FilterList를 하나 더 만들어줘 countryList랑 연동해 놓고 사용한다.
	init {
		countryFilterList = countryList
	}


	inner class CountryViewHolder(val binding: ItemCountryBinding) :
		RecyclerView.ViewHolder(binding.root) { //RecyclerView.ViewHolder에 binding.root를 입력값으로 준다.

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = ItemCountryBinding.inflate(inflater, parent, false)

		mContext = parent.context
		val sch = CountryViewHolder(binding)

		return sch
	}

	//co 왜 countryList를 사용하면 국가 목록은 뜨는데 Filtering이 안되는지 연구 필요
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val countryHolder = holder as CountryViewHolder // holder에 CountryViewHolder를 캐스팅.
		countryHolder.binding.selectCountryText.text = countryFilterList[position]

		//아이템 세부설정
		countryHolder.binding.selectCountryText.setTextColor(Color.WHITE)
		countryHolder.binding.selectCountryContainer.setBackgroundColor(Color.TRANSPARENT)

		//아이템뷰 클릭하면 디테일 Activity로 가기.
		countryHolder.itemView.setOnClickListener() {
			val intent = Intent(mContext, DetailActivity::class.java)
			intent.putExtra("passSelectedCountry", countryFilterList[position])
			mContext.startActivity(intent)
			Log.d("Selected:", countryFilterList[position])

		}
	}

	// 전체 아이템 가져오기
	override fun getItemCount(): Int {
		return countryFilterList.size
	}

	/* xo 원래 Filter가 없으면 countryList ArrayList 하나만 사용해도 됬지만
		   Filtering을 하기전 원래 값의 목록 리스트
		   Filtering을 한 뒤 새로운 목록 갱신이 필요하기 때문에 countryFilterList ArrayList를 하나 더 생성해서 사용하는 것*/
	override fun getFilter(): Filter {
		return object : Filter() {

			override fun performFiltering(constraint: CharSequence?): FilterResults {
				val charSearch = constraint.toString()

				// 서치바가 비어있다면 countryFilterList에 countryList : ArrayList 를 넣고 (안넣으면 서치바에 글자 지울 때 갱신 안됨)
				if (charSearch.isEmpty()) {
					countryFilterList = countryList
				} else {
					val resultList = ArrayList<String>()

					//countryList 목록을 반복하는데 row에 글자가 들어간다면 그 값을 resultList:ArrayList에 추가 시킨다.
					for (row in countryList) {
						if (row.lowercase(Locale.ROOT)
								.contains(charSearch.lowercase(Locale.ROOT))
						) {
							resultList.add(row)
						}
					}
					// 비어있지 않다면 resultList : ArrayList 를 넣는다.
					countryFilterList = resultList
				}
				val filterResults = FilterResults()
				filterResults.values = countryFilterList

				// publicResults에 반환하기 위한 filterResults에 countryFilterList를 넣어 반환한다.
				return filterResults
			}

			// xo publishResults는 이러한 결과를 가져와 countryFilterList 배열에 전달하고 RecyclerView를 업데이트.

			@Suppress("UNCHECKED_CAST")
			override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
				countryFilterList = results?.values as ArrayList<String>
				notifyDataSetChanged()
			}


		}
	}
}