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
	val rainVolume: Double,

	@field:SerializedName("indexUV")
	val indexUV: Double,

	@field:SerializedName("temperature")
	val temperature: Double,

	@field:SerializedName("humidity")
	val humidity: Int,

	@field:SerializedName("windspeed")
	val windspeed: Double,

	@field:SerializedName("suggest")
	val suggest: String,

	@field:SerializedName("rainChance")
	val rainChance: String
)

data class WeeklyWeatherItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("uv_index")
	val uvIndex: Double,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("temperature")
	val temperature: Double,

	@field:SerializedName("wind_speed")
	val windSpeed: Double
)

data class HourlyWeatherItem(

	@field:SerializedName("pop")
	val pop: Double,

	@field:SerializedName("uv_index")
	val uvIndex: Double,

	@field:SerializedName("weatherIcon")
	val weatherIcon: String,

	@field:SerializedName("weatherDescription")
	val weatherDescription: String,

	@field:SerializedName("temperature")
	val temperature: Double,

	@field:SerializedName("wind_speed")
	val windSpeed: Double,

	@field:SerializedName("time")
	val time: String
)
