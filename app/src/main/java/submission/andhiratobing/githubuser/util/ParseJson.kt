package submission.andhiratobing.githubuser.util

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

object ParseJson {
    fun getJSONFromAssets(context: Context): String? {
        val json: String?
        val charset: Charset = Charsets.UTF_8
        try {
            val jsonFile = context.assets.open("githubuser.json")
            val size = jsonFile.available()
            val buffer = ByteArray(size)
            jsonFile.read(buffer)
            jsonFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}