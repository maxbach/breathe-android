package ru.maxbach.onesec.domain.usecase

class IsAppSystemUseCase {
  private val systemAppPackageNames = listOf(
    "com.android.systemui",
    "com.google.android.inputmethod.latin"
  )

  operator fun invoke(packageName: CharSequence): Boolean = packageName in systemAppPackageNames
}
