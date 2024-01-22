package com.mcssoft.raceday.data.repository.preferences.user

import android.util.Log
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPrefsSerializer : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            Json.decodeFromString(
                deserializer = UserPreferences.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (ex: SerializationException) {
            ex.message?.let {
                Log.d("TAG", "UserPrefsSerializer::readFrom $it")
            }
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = UserPreferences.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        }
    }
}
