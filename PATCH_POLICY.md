# Patch Management Policy

## Dependency Update Frequency
Dependencies are reviewed and updated on a **weekly** basis via automated Dependabot pull requests.

## Responsibilities
The engineering team is responsible for reviewing and approving all automated dependency pull requests within 3 business days.

## Review Process
All dependency updates go through automated testing (CI pipeline) and require passing status checks before merge. Auto-merge is enabled for validated updates.

## Container Image Lifecycle Policy
- Base images are rebuilt **daily** via a scheduled GitHub Actions pipeline (`ghcr-publish.yml`).
- Maximum image lifetime: **24 hours**. Images older than this are automatically replaced by the nightly build.

## Attack Surface Reduction Policy
All container images use minimal base images (`node:18-alpine`) to reduce attack surface. No full OS-based images (e.g., ubuntu, debian) are used in production Dockerfiles.