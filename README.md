# ERP Android Runtime

## Purpose
erp-android-runtime is a thin Android runtime (shell) for ERP modules.
It contains no business logic and only provides execution and routing.

## Architecture
- Runtime is the only Android application.
- Sales / Audit / Inventory are independent modules.
- Each module lives in its own repository.
- Modules can be sold, replaced, or removed independently.

## Dependency Rules
- Runtime -> Module : ALLOWED
- Module -> Runtime : FORBIDDEN
- Module -> Module : FORBIDDEN

Runtime is the single integration point.

## Connection Rule
Runtime never imports module classes directly.
Runtime launches modules only via Intent Action strings.

Example:
- com.lsam.erp.sales.ENTRY
- com.lsam.erp.audit.ENTRY
- com.lsam.erp.inventory.ENTRY

Modules declare their entry point in their own AndroidManifest.xml.

## Fallback Behavior
If a module is not installed or disabled:
- Runtime MUST redirect to a Placeholder screen.
- Runtime MUST NOT crash.

This guarantees safe operation even when modules are missing.

## Feature Flags
Runtime controls module availability via feature flags.
Modules do not know whether they are enabled or disabled.

This allows:
- staged rollout
- partial installation
- license-based enablement

## Philosophy
Runtime acts like an OS or kernel.
Modules act like applications.
Human governance and replaceability are first-class concepts.
