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
	val rainVolume: Any,

	@field:SerializedName("indexUV")
	val indexUV: Int,

	@field:SerializedName("temperature")
	val temperature: Double,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("windspeed")
	val windspeed: Any,

	@field:SerializedName("suggest")
	val suggest: String,

	@field:SerializedName("rainChance")
	val rainChance: String
)

data class WeeklyWeatherItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("uv_index")
	val uvIndex: Any,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("temperature")
	val temperature: Any,

	@field:SerializedName("wind_speed")
	val windSpeed: Any
)

data class HourlyWeatherItem(

	@field:SerializedName("pop")
	val pop: Any,

	@field:SerializedName("uv_index")
	val uvIndex: Any,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("temperature")
	val temperature: Double,

	@field:SerializedName("wind_speed")
	val windSpeed: Any,

	@field:SerializedName("time")
	val time: String
)
