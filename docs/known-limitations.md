# Known Limitations

## Environment limits
- Java 21 is required (`mvn -v` must show Java 21).
- Web and API examples require internet access (public demo targets).
- Selenium may log CDP version warnings on new Chrome builds; this does not block core web flows in this repo.
- Codespaces uses `.devcontainer/` to install browser and noVNC dependencies. Existing Codespaces
  created before this setup should be rebuilt.

## Mobile limits
- Mobile tests are not run by default; they require `-Pmobile`.
- Appium server, emulator/simulator, and app binaries must be prepared locally.
- `apps/` folder does not ship binaries by design; trainers supply local artifacts.
- ApiDemos hybrid flow is Android-only by design.
- iOS locator structure is included, but classroom execution is typically Android-first.

## Demo-target limits
- Restful-Booker is a shared public service; data can reset or change.
- SelectorsHub and The Internet are public demo pages; small DOM changes can require locator maintenance.

## Scope limits (intentional)
- This is a training mini-framework, not a production enterprise framework.
- Device farm/grid, heavy abstraction layers, and advanced infra are intentionally out of scope.
