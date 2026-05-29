# 🔮 XR Memory Orbs

> **Spatial personal memory infrastructure for Android XR and AI glasses.**  
> Attach meaningful moments, thoughts, and context to the places and objects that matter — then recall them through your phone or projected onto the world through AI glasses.

---

## What Is This?

Memory Orbs is a **local-first, privacy-native Android app** that lets users create personal memory objects — "orbs" — attached to places, moments, and context. Think of it as a second brain pinned to physical space, not a timeline feed.

The core difference from generic note apps, reminders, or AI vision assistants:

| Tool | What it answers |
|------|----------------|
| Google Gemini / AI camera | *What am I looking at?* |
| Notes / Reminders | *What did I write down?* |
| **Memory Orbs** | *What does this place or object mean to me? What happened here? What should surface now?* |

Orbs are user-created, intentional, and entirely private. No passive recording. No bystander identification. No cloud dependency in v1.

---

## Current Status

**Phase: MVP Validated — XR Projection Scaffolded**

| Capability | Status |
|---|---|
| Android app builds (`assembleDebug`) | ✅ Validated |
| Jetpack Compose UI renders | ✅ Validated |
| Room local persistence | ✅ Validated |
| Manual Memory Orb CRUD (create / list / detail / edit / delete) | ✅ Validated |
| Simulated context recall via `contextLabel` | ✅ Validated |
| `ProjectionActivity` (read-only, ADB-launchable) | ✅ Validated on tablet |
| Android XR projected-activity scaffold (build-safe) | ✅ Build-safe, phone/tablet fallback-safe |
| Real Android XR / AI-glasses projected runtime | ⚠️ Environment-blocked (see below) |
| GPS, camera, microphone, passive capture | 🚫 Not implemented (out of scope for MVP) |
| Cloud sync / multi-device | 🚫 Not implemented (deferred) |
| Play Store production release | 🚫 Not yet (pre-release) |

### XR Emulator Blocker

Real Android XR projected behavior is **not yet validated**. The local dev machine is Windows ARM64 / Snapdragon X Elite — the Android Emulator package and compatible AI-glasses AVD are unavailable on this architecture. XR emulator validation should continue on **Intel/AMD Windows, Linux x86_64, or macOS Apple Silicon** hardware.

This is an environment availability blocker, **not an architectural blocker**.

---

## Architecture

```
┌─────────────────────────────────────────┐
│           Phone App (source of truth)   │
│  MainActivity → OrbNavGraph             │
│  Screens: Create / List / Detail /      │
│           Edit / Delete / Recall        │
│  Room DB ← single storage authority    │
└─────────────────────┬───────────────────┘
                      │ reads one orb (read-only)
         ┌────────────▼───────────────┐
         │   ProjectionActivity       │
         │   (local/tablet fallback)  │
         │   Resolves by orbId or     │
         │   contextLabel lookup      │
         └────────────────────────────┘
                      │ future
         ┌────────────▼───────────────┐
         │  GlassesProjectionActivity │
         │  (XR scaffold — not yet    │
         │   validated on hardware)   │
         └────────────────────────────┘
```

### Ownership Rules

- **Phone app owns storage and all mutation.** Room is the single source of truth.
- **Projection is read-only.** It consumes one orb; it does not create, edit, delete, or persist data.
- **XR/glasses layer is a consumer of data,** not an owner of app navigation or storage.
- Same-module separate Activity is the current prototype strategy. Separate XR module is deferred until real XR dependencies require it.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Local persistence | Room (SQLite) |
| Navigation | Jetpack Navigation / `OrbNavGraph` |
| Build system | Gradle (Kotlin DSL) |
| Target SDK | 36 |
| Testing device | Physical Android tablet (Samsung, ADB) |
| IDE | Android Studio |
| XR scaffold | `ProjectionActivity` + `GlassesProjectionActivity` (same module) |

---

## MemoryOrb Data Model

Each orb is a contextual memory object — not just a text note.

| Field | Type | Purpose |
|---|---|---|
| `id` | UUID | Unique identifier |
| `title` | String | Short label |
| `bodyText` | String | Main memory content |
| `contextLabel` | String | Recall trigger (manual tag, location label) |
| `mediaUri` | String? | Optional local file URI reference (no copy/import in v1) |
| `mediaType` | Enum | `NONE / IMAGE / AUDIO / VIDEO` |
| `privacyState` | Enum | `PRIVATE` (v1 only; no sharing yet) |
| `createdAt` | Timestamp | Creation time |
| `modifiedAt` | Timestamp | Last edited time |

**Media handling:** V1 stores local URI references only. The app does not copy, import, or manage a full media library. This keeps MVP small, local-only, and aligned with Android storage permissions.

---

## Privacy Model

Memory Orbs is intentionally **not a surveillance product**.

**Allowed in MVP:**
- User-created orbs with explicit user action
- Local-only storage (no account required)
- Text, optional local media URI references
- Manual context label for recall
- Delete / edit at any time

**Explicitly out of scope for MVP:**
- Passive / always-on recording
- Background camera or microphone capture
- Bystander identification
- Cloud sync or remote storage
- Account system
- Medical, therapy, or "perfect memory" product claims

---

## Platform Strategy

```
Phase 1 (now)   → Android phone app — local, Room-first, CRUD + recall
Phase 2 (now)   → ProjectionActivity — tablet/phone fallback read surface
Phase 3 (next)  → Android XR projected Activity — AI-glasses display layer
Phase 4 (later) → Play Store XR form-factor release (distribution rules TBD)
Phase 5 (later) → Optional: visionOS prototype, Smart-home Memory Mesh spinoff
```

> **Snap Spectacles** is parked. Snap Lens Studio code does not transfer to Android XR. Android-first is the main target.

---

## Repository Structure

```
XR-Memory-Orbs/
├── app/                        # Main Android app module
│   └── src/
│       └── main/
│           ├── MainActivity.kt
│           ├── OrbNavGraph.kt
│           ├── screens/        # Compose CRUD + recall screens
│           ├── data/           # Room DB, MemoryOrb entity, DAO, repo
│           ├── ProjectionActivity.kt
│           └── GlassesProjectionActivity.kt
├── docs/
│   └── control/
│       ├── PROJECT_STATUS.md          # Validated capabilities + current state
│       ├── ARCHITECTURE_BOUNDARY.md   # Ownership and change-control rules
│       ├── AGENT_SPEC.md              # Rules for coding agents
│       ├── COMMON_FAILURES.md         # Known issues + fixes
│       ├── XR_NEXT_STEPS.md           # Guarded XR progression plan
│       ├── TEST_MATRIX.md             # Test coverage matrix
│       └── RELEASE_READINESS.md       # Store readiness tracker
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## Getting Started

### Prerequisites

- Android Studio (latest stable)
- Android SDK, platform-tools, ADB installed
- Physical Android device with USB debugging enabled
- **Note:** Android Emulator and Android XR Emulator are unavailable on Windows ARM64 / Snapdragon X Elite. Use a physical device.

### Build & Run

```bash
# From project root
./gradlew assembleDebug

# Install to connected physical device
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch ProjectionActivity manually for testing
adb shell am start -n com.example.memoryorbs/.ProjectionActivity \
  --es contextLabel "home"
```

### Known Build Warnings

- `android.disallowKotlinSourceSets=false` is set (experimental Gradle flag)
- `ProjectionActivity` is currently `android:exported="true"` for prototype testing — must be reviewed before production publish
- `Icons.Default.ArrowBack` deprecation warning in recall UI (non-blocking)

---

## Roadmap

### Milestones

| # | Milestone | Status |
|---|---|---|
| 1 | MVP Definition Locked | ✅ Done |
| 2 | Android Core App Scaffold | ✅ Done |
| 3 | Memory Graph Prototype (CRUD + simulated recall) | ✅ Done |
| 4 | Android XR Projection Prototype (build-safe scaffold) | ✅ Done |
| 5 | Privacy / Store Readiness | 🔄 In progress |
| — | XR Emulator / Hardware Validation | ⚠️ Environment-blocked |
| — | Play Store Production Release | 🔲 Future |

### Parked (Not in MVP Scope)

- **Smart-home Memory Mesh** — future monetization/service branch. Same schema concept (objects as containers for persistent meaning) applied to rooms, devices, and home automations. Parked until Memory Orbs core is validated.
- **Snap Spectacles prototype lane** — deferred; not the main platform path.
- **visionOS** — optional Apple hedge, future only.

---

## Control Docs

All architectural decisions, agent rules, test matrices, and release gates live in [`docs/control/`](./docs/control/).

These docs are the **source of truth** for what is validated vs claimed, what the agent may and may not change, and what the next guarded steps are.

---

## Project Tracker

Full PM backlog, sprint items, priorities, and acceptance criteria are tracked in [Airtable](https://airtable.com/apptdMBGalkKRrjPs/tbldLZUGD2mQdKJvn).

---

## License

Private / work-in-progress. License TBD.
