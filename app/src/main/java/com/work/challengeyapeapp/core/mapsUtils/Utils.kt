package com.work.challengeyapeapp.core.mapsUtils

class Utils {

    fun getMark(gentile: String): MarkPoint {
        val gentileEnum = Gentiles.valueOf(gentile)
        val (latitude, altitude) = gentileEnum.coordinates
        return MarkPoint(latitude, altitude)
    }

    class MarkPoint(latitude: Double, altitude: Double) {
        var latitudeMark = latitude
        var altitudeMark = altitude
    }
}
