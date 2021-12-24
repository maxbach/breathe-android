package ru.maxbach.onesec.domain.models

enum class MobileApp(
  val id: MobileAppId,
  val packages: List<String>
) {
  TELEGRAM(MobileAppId("telegram"), listOf("")),
  YOUTUBE(MobileAppId("telegram"), listOf("")),
  INSTAGRAM(MobileAppId("telegram"), listOf("")),
  FACEBOOK(MobileAppId("telegram"), listOf("")),
}

@JvmInline
value class MobileAppId(val id: String)
