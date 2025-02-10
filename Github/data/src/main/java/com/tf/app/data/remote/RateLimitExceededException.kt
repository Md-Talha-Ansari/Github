package com.tf.app.data.remote

class RateLimitExceededException(val limit: Int, val remaining: Int, val used: Int,val timeToReset: Int): Exception() {

    override val message: String
        get() {
            return "Rate limit exceeded. Limit: $limit, Remaining: $remaining, Used: $used, Reset: $timeToReset"
        }


}