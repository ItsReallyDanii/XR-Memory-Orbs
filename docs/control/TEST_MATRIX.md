# Test Matrix

| Test | Command/Action | Expected Result | Current Status | Notes |
|---|---|---|---|---|
| Build test | `.\gradlew.bat :app:assembleDebug` | Debug build succeeds | Validated | Current build path passes |
| Install test | Install debug APK to physical tablet | App installs successfully | Validated | Physical Samsung tablet |
| Launch MainActivity | Launch app from launcher | Main phone UI opens | Validated | Compose shell renders |
| Create orb | Add a new Memory Orb in app | Orb is saved | Validated | Manual CRUD validated |
| List orb | Return to list screen | Saved orb appears in list | Validated | Local Room-backed list |
| Detail orb | Tap orb in list | Detail screen opens with orb fields | Validated | Existing phone flow |
| Edit orb | Edit existing orb and save | Updated values persist | Validated | Existing phone flow |
| Delete orb | Delete existing orb | Orb is removed from list and storage | Validated | Existing phone flow |
| App restart persistence | Close and relaunch app | Saved orbs remain | Validated | Local Room persistence works |
| Reinstall behavior | Reinstall app, relaunch | Local DB may be cleared | Partially validated | Treat reinstall as potential data wipe |
| Simulated context recall | Open Recall, enter `contextLabel`, tap Recall | Matching orbs display or empty state shows | Validated | Manual label-driven recall |
| Post-XR-scaffold app launch | Install updated debug build and launch `MainActivity` | App launches and existing UI still renders | Validated | Verified after `compileSdk 36` and projected scaffold |
| Post-XR-scaffold persisted orb presence | Launch app after XR scaffold update | Previous Room-backed orb still appears | Validated | Persisted local orb survived scaffold update |
| ProjectionActivity by contextLabel | Launch `ProjectionActivity` with `--es contextLabel <label>` | First matching orb displays | Validated | Read-only local projection path |
| ProjectionActivity by orbId | Launch `ProjectionActivity` with `--ei orbId <id>` | Exact orb displays | Validated | Deterministic direct lookup |
| GlassesProjectionActivity resolution | Resolve `GlassesProjectionActivity` via package manager or ADB | Activity resolves without runtime crash evidence | Validated | Compatibility scaffolding only, not real XR validation |
| Empty state tests | Launch projection with missing orb or bad context label | Clear empty state displays | Validated | Read-only failure path |
| Tablet limitations | Run full prototype on tablet | Works locally on tablet only | Validated | Not a glasses validation |
| Real XR/glasses limitations | Attempt to claim XR/glasses support | Must not claim validated XR behavior | Not validated | Real Android XR projected behavior remains emulator/hardware-blocked |
| XR emulator environment setup | Install Android Emulator package, phone host AVD, and AI-glasses AVD | Official AI-glasses emulator environment exists locally | Blocked | Environment-blocked on Windows ARM64 / Snapdragon X Elite |
