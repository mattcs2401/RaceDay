package com.mcssoft.raceday.utility

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mcssoft.raceday.FileMetaData
import java.io.InputStream
import java.io.OutputStream

class RaceDaySerializer() : Serializer<FileMetaData> {
    override val defaultValue: FileMetaData
        get() = FileMetaData.getDefaultInstance()

    override fun readFrom(input: InputStream): FileMetaData {
        try {
            return FileMetaData.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto buffer: ", ex)
        }
    }

    override fun writeTo(t: FileMetaData, output: OutputStream) {
        t.writeTo(output)
    }
}