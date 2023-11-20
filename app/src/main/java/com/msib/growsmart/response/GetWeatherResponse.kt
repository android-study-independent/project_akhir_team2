package com.msib.growsmart.response

import com.google.gson.annotations.SerializedName

data class GetWeatherResponse(

	@field:SerializedName("currentWeather")
	val currentWeather: CurrentWeather,

	@field:SerializedName("hourlyWeather")
	val hourlyWeather: List<HourlyWeatherItem>,

	@field:SerializedName("weeklyWeather")
	val weeklyWeather: List<WeeklyWeatherItem>
)

data class CurrentWeather(

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("rainVolume")
	val rainVolume: Int,

	@field:SerializedName("temperature")
	val temperature: Any,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("windSpeed")
	val windSpeed: Any,

	@field:SerializedName("rainChance")
	val rainChance: String
)

data class WeeklyWeatherItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("temperature")
	val temperature: Any
)

data class HourlyWeatherItem(

	@field:SerializedName("pop")
	val pop: Any,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("temperature")
	val temperature: Any,

	@field:SerializedName("time")
	val time: String
)
