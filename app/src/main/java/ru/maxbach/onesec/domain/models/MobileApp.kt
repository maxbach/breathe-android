package ru.maxbach.onesec.domain.models

enum class MobileApp(
  val id: MobileAppId,
  val appName: String,
  val packages: List<String>
) {
  TELEGRAM(MobileAppId("telegram"), "Telegram", listOf("")),
  YOUTUBE(MobileAppId("youtube"), "Youtube", listOf("")),
  INSTAGRAM(MobileAppId("instagram"), "Instagram", listOf("")),
  FACEBOOK(MobileAppId("facebook"), "Facebook", listOf("")),
}

@JvmInline
value class MobileAppId(val id: String)
