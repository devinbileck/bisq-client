package bisq.client.domain.util

import kotlinx.datetime.*

class DatetimeUtil
{
    fun now(): LocalDateTime {
        val currentMoment: Instant = Clock.System.now()
        return currentMoment.toLocalDateTime(TimeZone.UTC)
    }

    fun toLocalDate(date: Double): LocalDateTime {
        return Instant.fromEpochMilliseconds(date.toLong()).toLocalDateTime(TimeZone.UTC)
    }

    fun toEpochMilliseconds(date: LocalDateTime): Double {
        return date.toInstant(TimeZone.UTC).toEpochMilliseconds().toDouble()
    }

    @ExperimentalStdlibApi
    fun prettyDatetime(date: LocalDateTime?): String {
        val sb = StringBuilder()
        date?.run {
            val time = if (this.hour > 12) {
                (this.hour - 12).toString() + ":" + String.format("%02d", this.minute) + " pm"
            } else {
                if (this.hour != 0) this.hour.toString() + ":" + String.format("%02d", this.minute) + " am"
                else "12:" + String.format("%02d", this.minute) + " am"
            }
            val today = now()
            val tomorrow = Clock.System.now()
                .plus(1, DateTimeUnit.DAY, TimeZone.UTC)
                .toLocalDateTime(TimeZone.UTC)
            val yesterday = Clock.System.now()
                .minus(1, DateTimeUnit.DAY, TimeZone.UTC)
                .toLocalDateTime(TimeZone.UTC)
            when (this.date) {
                yesterday.date -> sb.append("Yesterday at $time")
                today.date -> sb.append("Today at $time")
                tomorrow.date -> sb.append("Tomorrow at $time")
                else -> sb.append(
                    this.date.month.name.lowercase().replaceFirstChar { it.uppercase() }
                            + " ${this.date.dayOfMonth} at $time")
            }
        }?: sb.append("Unknown")
        return sb.toString()
    }

}
