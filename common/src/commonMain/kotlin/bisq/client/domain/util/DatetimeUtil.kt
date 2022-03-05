package bisq.client.domain.util

import kotlinx.datetime.*

class DatetimeUtil {
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

    fun prettyDatetime(date: LocalDateTime?): String {
        val sb = StringBuilder()
        date?.run {
            val time = if (this.hour > 12) {
                (this.hour - 12).toString() + ":" + this.minute.toString().padStart(2, '0') + " pm"
            } else {
                if (this.hour != 0) this.hour.toString() + ":" + this.minute.toString().padStart(2, '0') + " am"
                else "12:" + this.minute.toString().padStart(2, '0') + " am"
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
