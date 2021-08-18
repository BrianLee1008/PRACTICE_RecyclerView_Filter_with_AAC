package com.example.practice_recyclerview_filter_with_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList



class MainViewModel : ViewModel(){

	val countryListWithEmoji = ArrayList<String>()

	private var _countryLiveData = MutableLiveData<Any>(getListOfCountryList())
	val countryLiveData : LiveData<Any>
	get() = _countryLiveData


	private fun getListOfCountryList(){
		val isoCountryList = Locale.getISOCountries()


		for(countryCode in isoCountryList){
			val locale = Locale("",countryCode)
			val countryName = locale.displayCountry // 나라 이름

			val flagOffset = 0x1F1E6
			val asciiOffset = 0x41
			val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
			val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
			val flag =
				(String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))) // 이모지

			countryListWithEmoji.add("$countryName $flag")

		}
	}
}