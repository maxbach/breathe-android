package ru.maxbach.onesec.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import ru.maxbach.onesec.data.UserSettingsDto
import ru.maxbach.onesec.domain.models.UserSettings
import java.io.InputStream
import java.io.OutputStream

object UserSettingsSerializer : Serializer<UserSettingsDto> {

  override val defaultValue: UserSettingsDto = UserSettingsDto.newBuilder()
    .setBreatheDurationInSec(UserSettings.BREATHE_DURATION_IN_SEC_DEFAULT)
    .setOpenBreatheDelayInSec(UserSettings.BREATHE_OPEN_DELAY_IN_SEC_DEFAULT)
    .addAllAppsToBreathe(UserSettings.CHOSEN_APPS_DEFAULT.map { it.id })
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
