# RJ Digital Solutions — AI Agent Workflow

This repository uses a disciplined AI-assisted development workflow. Any coding agent, Copilot session, or automated contributor should follow this process before making changes.

## 1. Understand the request
- Restate the goal in concrete technical terms.
- Inspect the relevant existing files before editing anything.
- Identify what must stay unchanged.
- Prefer the smallest safe change that satisfies the request.

## 2. Plan before editing
For non-trivial work, create a short implementation plan covering:
1. Files to inspect or modify.
2. Expected user-visible result.
3. Risks or regressions to avoid.
4. How the result will be verified.

Do not begin large refactors without a clear reason.

## 3. Preserve the current product
RJ Digital Solutions should remain:
- Premium, modern, clean, and professional.
- Mobile-friendly and responsive.
- Easy to understand for non-technical business customers.
- Consistent in spacing, typography, alignment, and visual hierarchy.

Do not remove working features, links, sections, contact methods, or assets unless explicitly requested.

## 4. Implementation rules
- Reuse existing patterns and styles before introducing new ones.
- Keep code simple and maintainable.
- Avoid unnecessary dependencies.
- Never expose secrets, API keys, tokens, passwords, or private credentials.
- Do not make unrelated changes while solving a focused request.
- Keep accessibility and mobile behavior in mind for every UI change.

## 5. Debugging workflow
When something is broken:
1. Reproduce or inspect the failure.
2. Identify the root cause.
3. Fix the root cause instead of masking the symptom.
4. Re-check nearby functionality that could be affected.
5. Explain what changed and why.

Do not repeatedly guess at fixes without inspecting the relevant code.

## 6. Verification checklist
Before marking work complete, verify where applicable:
- The page loads without obvious errors.
- Navigation and links still work.
- Desktop layout is intact.
- Mobile layout is usable.
- Text does not overlap.
- Images and assets load correctly.
- Buttons and contact actions behave as expected.
- No unrelated functionality was removed.

If full verification is not possible, clearly state what was and was not verified.

## 7. Review pass
Before finalizing:
- Re-read the user's request.
- Review the diff for accidental edits.
- Remove dead code or temporary debugging output introduced during the task.
- Confirm naming, formatting, and comments are clear.

## 8. Completion report
For each completed task, report:
- What changed.
- Which files changed.
- What was verified.
- Any remaining limitation or follow-up item.

## 9. Change philosophy
Prefer:
**plan → inspect → implement → test → review → report**

Avoid:
**guess → edit widely → claim success without verification**

This workflow complements `.github/copilot-instructions.md` and should be treated as repository-wide guidance for AI-assisted development.
