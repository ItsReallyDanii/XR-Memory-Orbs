# Common Failures

## A. Gradle sync too slow / frozen

- Cause: Gradle config, catalog, or plugin changes trigger dependency re-resolution.
- Response: do not keep prompting the agent repeatedly; inspect the first red error and repair that first.

## B. Version catalog mismatch

- Symptom: missing `libs` aliases for Compose, Room, or Navigation.
- Fix pattern: align `app/build.gradle.kts` with `gradle/libs.versions.toml`.

## C. Duplicate Kotlin plugin

- Symptoms:
  - plugin already on classpath with unknown version
  - `Cannot add extension with name kotlin`
- Fix pattern:
  - do not apply Kotlin Android twice
  - preserve the working Kotlin plugin arrangement

## D. kotlinOptions unresolved

- Cause: Kotlin plugin removed from the visible module plugin block.
- Fix used:
  - remove invalid `kotlinOptions` block if the plugin DSL no longer exposes it

## E. KSP/Kotlin mismatch

- Symptom:
  - KSP version too old for current Kotlin
- Fix pattern:
  - align KSP version with Kotlin version

## F. Built-in Kotlin sourceSets failure

- Symptom:
  - `Using kotlin.sourceSets DSL to add Kotlin sources is not allowed with built-in Kotlin`
- Fix used:
  - `android.disallowKotlinSourceSets=false` in `gradle.properties`

## G. ADB not recognized

- Fix:
  - use full path:
  - `& "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"`

## H. Multiple ADB devices

- Symptom:
  - `more than one device/emulator`
- Fix:
  - use `adb -s <deviceId>`
  - current physical device ID observed: `R9PT601Z40D`

## I. ProjectionActivity not exported

- Symptom:
  - `SecurityException` not exported from uid
- Fix:
  - `android:exported="true"` for `ProjectionActivity` during local prototype testing
- Warning:
  - before publishing, reassess whether it should remain exported

## J. ProjectionActivity empty state

- Symptom:
  - `No memory orbs found for this projection request`
- Causes:
  - DB wiped after reinstall
  - `contextLabel` mismatch
  - wrong ADB extra type
- Fix:
  - create a fresh orb with `contextLabel` `tablet`
  - use `--es contextLabel tablet`
