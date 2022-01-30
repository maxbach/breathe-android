package ru.maxbach.onesec.domain.models

enum class MobileApp(
  val id: MobileAppId,
  val appName: String,
  val packages: List<String>
) {
  TELEGRAM(MobileAppId("telegram"), "Telegram", listOf("org.telegram.messenger")),
  YOUTUBE(MobileAppId("youtube"), "Youtube", listOf("com.google.android.youtube")),
  INSTAGRAM(MobileAppId("instagram"), "Instagram", listOf("com.instagram.android")),
  FACEBOOK(MobileAppId("facebook"), "Facebook", listOf("com.facebook.katana")),
}

@JvmInline
value class MobileAppId(val id: String)
