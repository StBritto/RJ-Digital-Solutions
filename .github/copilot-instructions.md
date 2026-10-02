# RJ Digital Solutions — Copilot / Superpowers Instructions

Use these instructions whenever you work in this repository.

## Working style
- Inspect the existing repository structure and relevant files before making changes.
- For non-trivial work, first form a short implementation plan, then execute it.
- Prefer small, reversible changes over large rewrites.
- Preserve existing working functionality unless the requested change explicitly replaces it.
- Fix root causes rather than applying cosmetic patches.

## Validation
- After editing, verify that the affected page or feature still works.
- Check both desktop and mobile layouts for UI changes.
- Look for broken links, missing assets, console errors, malformed HTML/CSS/JS, and regressions.
- Do not claim a fix is complete unless it has been checked against the current code.

## Design direction
- Keep RJ Digital Solutions looking premium, modern, professional, and clean.
- Maintain strong alignment, spacing, typography, and visual hierarchy.
- Avoid overlapping text, clutter, inconsistent button styles, and unnecessary visual noise.
- Reuse the existing brand language and assets where practical.
- Prefer polished, responsive layouts that work well on phones first as well as desktop.

## Safety and repository hygiene
- Never commit passwords, API keys, tokens, credentials, or other secrets.
- Do not delete or rename important production files without a clear reason.
- Avoid changing unrelated files.
- Preserve existing URLs and deployment behavior unless the task specifically requires a change.

## Debugging
- Reproduce or understand the issue before changing code.
- Trace the likely source of the problem instead of guessing.
- When multiple fixes are possible, prefer the simplest maintainable solution.
- Re-check the exact reported issue after the change.

## Implementation quality
- Keep code readable and appropriately commented.
- Avoid unnecessary dependencies for simple functionality.
- Use semantic HTML and accessible labels where practical.
- Keep CSS responsive and avoid fragile absolute positioning unless necessary.
- Keep JavaScript minimal and defensive.

## Before finishing
Confirm that:
1. The requested change is actually present.
2. Existing core functionality still works.
3. The result is responsive and visually consistent.
4. No secrets or temporary/debug files were added.
5. The final response clearly states what changed and any limitations that remain.
