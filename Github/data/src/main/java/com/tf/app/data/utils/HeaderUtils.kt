package com.tf.app.data.utils

import okhttp3.Headers


/**
 * Parse the Link headers link.
 * @return Map of links in Link header.
 */
fun Headers.parseLinkHeader():Map<String,String>{
    val links = get("Link")
    val map = mutableMapOf<String,String>()
    links?.split(",")?.forEach {
        val link = it.replace("<","")
            .replace(">","")
            .replace("\"","")
            .replace("rel=","")
            .replace(" ","")
        val linkInfo = link.split(";")
        map[linkInfo[1]] = linkInfo[0]
    }
    return map.toMap()
}

fun Headers.getLimit() : Int {
    val limit = get("X-RateLimit-Limit")
    return limit?.toInt() ?: Int.MAX_VALUE
}

fun Headers.getLimitRemaining() : Int {
    val limit = get("X-RateLimit-Remaining")
    return limit?.toInt() ?: 0
}

fun Headers.getLimitUsed() : Int {
    val limit = get("X-RateLimit-Used")
    return limit?.toInt() ?: 0
}

fun Headers.getLimitReset() : Int {
    val limit = get("X-RateLimit-Reset")
    return limit?.toInt() ?: Int.MAX_VALUE

}