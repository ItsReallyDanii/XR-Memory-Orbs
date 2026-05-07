# MemoryOrbs Agent Spec

## Recommended agent configs by task type

### Small bug fix

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: low
- Use for manifest edits, one-line fixes, and contained build-error repairs

### Gradle recovery

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: medium
- Use when sync/build breaks, version catalogs drift, or plugin arrangement is unclear

### Feature implementation

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: medium
- Use for contained app features that extend the validated architecture without refactoring core flows

### Architecture audit

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: high
- Use for boundary-setting, handoff prep, and change-risk evaluation

### XR integration audit

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: high
- Use when official-source review is needed before XR-specific implementation

### Release/publishing prep

- Agent company: Codex
- Model: `gpt-5.4`
- Reasoning: medium
- Use for packaging, manifest/security review, policy readiness, and internal testing prep

## Default model and reasoning guidance

- Use `Codex / gpt-5.4 / low` for manifest or one-line fixes.
- Use `Codex / gpt-5.4 / medium` for contained app features.
- Use `Codex / gpt-5.4 / high` for architecture or XR integration audits.

## Operating rules

- Always inspect before editing.
- Always list exact files to touch before edits.
- Never modify Gradle unless the build error requires it.
- Never add dependencies without naming the source error or feature need.
- Never rewrite validated CRUD, recall, or projection flows unless explicitly approved.
- Build once after changes.
- After implementation, report only `BUILD PASSED` or the first remaining error.

## Architecture safety rules

- Phone app remains the source of truth.
- Room remains the source of truth.
- Projection remains read-only unless a future plan explicitly changes that boundary.
- XR work should attach through narrow read-only seams first.
