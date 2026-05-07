# MemoryOrbs Architecture Boundary

## Core ownership rules

- Phone app owns storage and CRUD.
- Room database is the source of truth.
- `ProjectionActivity` is read-only.
- Projection layer must not own storage.
- Projection layer must not duplicate CRUD.
- XR/glasses layer should request and display one `MemoryOrb`.

## Validated phone-core boundary

These are validated phone-core surfaces and should not be changed unless explicitly approved:

- `MainActivity`
- `OrbNavGraph`
- CRUD screens
- Recall screen
- Existing Room model, database, and repository pattern unless a targeted error requires change

## Projection boundary

- Projection exists to display one orb from existing local data.
- It may resolve by `orbId`.
- It may resolve by `contextLabel` using a deterministic rule.
- It must not become a second app shell.
- It must not become a second navigation graph.
- It must not mutate stored orb data.

## Strategy boundary

- Same-module separate Activity is the current prototype strategy.
- Separate XR module is deferred until real XR dependencies require it.
- Real XR or glasses dependencies should be added only after official-doc audit confirms the correct path.

## Change control rules

- Prefer adding narrow read-only seams over broad refactors.
- Preserve the phone-first architecture.
- Keep projection as a consumer of data, not an owner of data.
- If XR requirements later force a different boundary, document why before moving architecture.
