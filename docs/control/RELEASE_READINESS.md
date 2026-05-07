# Release Readiness

## Current completion estimates

- Local prototype: 90-95%
- Phone/tablet internal testing: 75-85%
- Phone-only Play Store MVP: 55-65%
- AI-glasses architecture readiness: 60-70%
- Hardware-validated glasses build: 20-30%

## Milestone status

- Android XR projected-activity scaffold is build-safe and phone/tablet fallback-safe. Local app UI renders and Room-backed persisted orb data survived after the XR scaffold. Real Android XR projected behavior is not yet validated and remains emulator/hardware-blocked.

## Remaining release tasks

- app icon
- naming
- privacy policy
- screenshots
- signed AAB
- Play Console internal testing
- permission audit
- exported Activity security review
- no-overclaim language

## Explicit non-claims

- not medical
- not therapy
- not passive recording
- not perfect memory
- not real glasses-validated yet

## Release boundary notes

- Release readiness is currently limited to build safety and local phone/tablet fallback safety.
- `GlassesProjectionActivity` is compatibility scaffolding only.
- Projected runtime behavior is not validated on XR emulator or hardware.
- Do not claim Android XR or AI-glasses validation from the current prototype state.
- Do not claim real glasses support is complete.
- Prototype completion is not publish readiness.
- The current app is suitable for internal prototype validation, not public overclaiming.
- `ProjectionActivity` export and debug launch behavior should be revisited before any publish path.
