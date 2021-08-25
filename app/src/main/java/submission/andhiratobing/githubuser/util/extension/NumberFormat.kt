package submission.andhiratobing.githubuser.util.extension

import java.util.*

object NumberFormat {


    private val suffixes = TreeMap<Long, String>().apply {
        put(1_000L, "k")
        put(1_000_000L, "M")
        put(1_000_000_000L, "G")
    }

    private fun format(value: Long): String? {
        when {
            value == Long.MIN_VALUE -> {
                return format(Long.MIN_VALUE + 1)
            }
            value < 0 -> {
                return "-" + format(-value)
            }
            value < 1000 -> {
                return value.toString()
            }
            else -> {
                val e = this.suffixes.floorEntry(value)

                return e?.run<MutableMap.MutableEntry<Long, String>, String> {
                    val divideBy = e.key
                    val suffix = e.value
                    val truncated = value / (divideBy / 10)
                    val hasDecimal =
                        truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
                    if (hasDecimal) (truncated / 10.0).toString() + suffix
                    else (truncated / 10).toString() + suffix
                }
            }
        }

    }

    private fun Long.asFormattedDecimals() = format(this)

    fun Int.asFormattedDecimals() = (this.toLong()).asFormattedDecimals()
}