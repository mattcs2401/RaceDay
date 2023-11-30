package com.mcssoft.raceday.data.repository.preferences.app

import androidx.datastore.core.Serializer
import com.mcssoft.raceday.data.repository.preferences.user.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppPrefsSerializer : Serializer<AppPreferences> {

    override val defaultValue: AppPreferences
        get() = AppPreferences()

    override suspend fun readFrom(input: InputStream): AppPreferences {
        return try {
            Json.decodeFromString(
                deserializer = AppPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = AppPreferences.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}