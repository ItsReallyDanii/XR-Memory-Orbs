# XR Next Steps

## Current projection prototype status

- Current projection prototype is local-only and read-only.
- `ProjectionActivity` can be launched manually and display one saved orb from Room data.
- This is validated on a tablet, not on real glasses hardware.
- Android XR projected-activity scaffold is build-safe and phone/tablet fallback-safe. Local app UI renders and Room-backed persisted orb data survived after the XR scaffold. Real Android XR projected behavior is not yet validated and remains emulator/hardware-blocked.

## Why the current architecture fits projected activity thinking

- Phone app owns persistence and mutation.
- Projection is already separated into a dedicated Activity.
- Projection consumes one orb and does not own app navigation or storage.
- This matches the intended pattern of a projected/read-only surface attached to a phone-first app.

## What must be audited against official Google docs before adding XR dependencies

- Correct Android XR or projected-activity dependency path
- Supported Activity/component model for projection on target devices
- Manifest requirements
- Lifecycle and display-surface constraints
- Launch semantics and intent contracts
- Security/export rules for prototype vs publishable builds

## Next 1-2 prompt plan

### Prompt 1

- Official docs and dependency audit only
- Confirm the current Android XR / projected activity path from official Google sources
- Identify exact dependencies, manifest changes, and minimum supported patterns
- No code changes unless explicitly approved after the audit

### Prompt 2

- Validate the current projected-activity scaffold on official XR emulator or supported hardware
- Keep the current `ProjectionActivity` fallback
- Add only the minimum XR-specific follow-up needed if projected runtime behavior fails or requires documented adjustments

## Guardrails

- Do not implement a full XR SDK path yet without official-source confirmation.
- Keep ADB and local `ProjectionActivity` fallback working.
- Preserve the phone-first source-of-truth architecture.
- Do not overclaim glasses readiness before real hardware validation.
- Treat `GlassesProjectionActivity` as compatibility scaffolding, not proof of XR behavior.
