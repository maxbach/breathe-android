package ru.maxbach.onesec.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import ru.maxbach.onesec.data.UserSettingsDto
import java.io.InputStream
import java.io.OutputStream

object UserSettingsSerializer : Serializer<UserSettingsDto> {
  override val defaultValue: UserSettingsDto = UserSettingsDto.newBuilder()
    .setBreatheDurationInSec(5)
    .setOpenBreatheDelayInSec(0)
    .clearAppsToBreathe()
    .build()

  override suspend fun readFrom(input: InputStream): UserSettingsDto {
    try {
      return UserSettingsDto.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(
    t: UserSettingsDto,
    output: OutputStream
  ) = t.writeTo(output)
}

val Context.settingsDataStore: DataStore<UserSettingsDto> by dataStore(
  fileName = "settings.pb",
  serializer = UserSettingsSerializer
)
