# MemoryOrbs Project Status

## Current validated app capabilities

- Android app builds successfully with `assembleDebug`.
- App installs and runs on a physical Samsung tablet.
- Jetpack Compose UI renders.
- Room local persistence works.
- Manual Memory Orb CRUD works: create, list, detail, edit, delete.
- Simulated context recall works by manual `contextLabel`.
- Read-only `ProjectionActivity` works.
- ADB can launch `ProjectionActivity` with `contextLabel`.
- `ProjectionActivity` reads one saved `MemoryOrb` from local Room data and displays it.
- Android XR projected-activity scaffold is build-safe and phone/tablet fallback-safe. Local app UI renders and Room-backed persisted orb data survived after the XR scaffold. Real Android XR projected behavior is not yet validated and remains emulator/hardware-blocked.

## What is proven

- The phone/tablet app is a working local-first Android prototype.
- The phone app owns storage and mutation.
- Room is the source of truth for saved orbs.
- Recall queries can surface saved orbs from local data.
- Projection can read and display one orb without owning storage.
- MainActivity still launches after the XR projected-activity scaffold was added.
- Existing Room-backed orb data remained visible after the XR scaffold update.

## What is not proven

- Real Google AI-glasses integration is not validated.
- Android XR projected-activity runtime behavior is not validated.
- Projection behavior on real glasses hardware is not validated.
- Multi-device sync, cloud sync, and remote storage are not validated.
- GPS, camera, microphone, passive capture, and ambient sensing are not implemented or validated.
- Production distribution, Play Store readiness, and signed release flows are not validated.

## Current architecture boundary

- `MainActivity` and the Compose phone flow are the validated phone-core shell.
- `OrbNavGraph` and CRUD/recall screens define the validated in-app experience.
- Room database is the only storage authority.
- `ProjectionActivity` is a separate read-only local/tablet fallback surface in the same app module.
- `GlassesProjectionActivity` is separate projected-activity compatibility scaffolding in the same app module.
- Projection reads one orb by `orbId` or deterministic `contextLabel` lookup.
- Projection does not create, edit, delete, or persist data.

## Current device/test environment

- Primary validated hardware: physical Samsung tablet.
- Local debugging and manual projection launching use ADB.
- Observed physical device ID during testing: `R9PT601Z40D`.
- Current prototype validation is local and developer-operated.

## Current build status

- `:app:assembleDebug` passes.
- Build passed after updating `compileSdk` from 35 to 36.
- One-off configuration cache test for `:app:assembleDebug --configuration-cache` passed.
- No permanent configuration cache flag has been enabled yet.

## Current known warnings

- `android.disallowKotlinSourceSets=false` is set and Gradle reports it as experimental.
- `ProjectionActivity` is currently `android:exported="true"` for debug/manual prototype launching and should be security-reviewed before publish.
- `GlassesProjectionActivity` currently exists as projected-activity compatibility scaffolding only.
- Compose emitted a deprecation warning for `Icons.Default.ArrowBack` in recall UI during an earlier build path. This is not a current blocker.

## Current non-goals

- No real XR/glasses validation claim yet.
- No cloud sync.
- No Airtable runtime integration.
- No passive recording.
- No medical, therapy, or "perfect memory" claims.

## Current next milestone

- Validate projected Android XR behavior on supported emulator or hardware.
- Preserve the validated phone-first, Room-first architecture while keeping the local `ProjectionActivity` fallback intact.
- Keep the XR scaffold narrow until projected runtime behavior is observed on supported XR runtime.

## Validation language guardrails

- `ProjectionActivity` is validated locally on a tablet, not on real glasses.
- Full Google AI-glasses integration is not yet validated.
- `GlassesProjectionActivity` is not real glasses validation.
