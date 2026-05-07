# GT Demo Project

Mini corporate-style automation framework for a 3-day BA/TA training.

## Stack
- Java 21, Maven
- Web/Mobile: Cucumber + TestNG + Selenium/Appium
- API: Karate + JUnit 5
- Reports: Cucumber HTML/JSON + TestNG default reports

## Locked Demo Targets
- Web business: ParaBank
- Web technical lab: The Internet
- Advanced DOM lab: SelectorsHub
- Mobile native: TheApp (Android + iOS structure)
- Mobile hybrid: Android ApiDemos (context switch teaching only)
- API: Restful-Booker

## Runnable Scope
- Web: login, form fill, dropdown, iframe, explicit wait, shadow DOM, nested shadow DOM
- API: ping, POST booking, GET booking, reusable auth-token flow (callable Karate feature)
- Mobile: Android native + Android hybrid flows implemented with step definitions and screen objects

## Run Commands
- Web + API default: `mvn test`
- Web only (terminal or VS Code Maven test action): `mvn surefire:test`
- API only: `mvn failsafe:integration-test@api-tests failsafe:verify@api-tests`
- Mobile profile: `mvn -Pmobile surefire:test@mobile-tests`

## Codespaces Training Environment
- Participants should use the default headless web config in Codespaces.
- Trainers can show a visible browser through noVNC with `scripts/start-novnc.sh`.
- Full setup notes: `docs/codespaces-training-setup.md`

## Mobile Setup Notes
- Start Appium server and emulator/simulator first.
- Configure app binaries in `src/test/resources/config/framework.properties`.
- Default mobile profile is `theapp`; `@apidemos` scenarios auto-switch to ApiDemos profile in hooks.

## Training Assets
- Core executable features:
  - `src/test/resources/features/web`
  - `src/test/resources/features/mobile`
  - `src/test/resources/features/api`
- BDD classroom examples:
  - `src/test/resources/features/training/bdd`
- Anti-pattern examples (DO NOT USE):
  - `src/test/resources/features/training/anti-patterns`

## Documentation
- Architecture: `docs/architecture-overview.md`
- Class map: `docs/class-purpose-guide.md`
- Scenario catalog: `docs/training-scenarios-list.md`
- BDD guidance: `docs/bdd-examples.md`
- Anti-pattern guide: `docs/bad-examples-guide.md`
- VS Code/Codespaces setup: `docs/vscode-karate-cucumber-setup.md`
- Codespaces training setup: `docs/codespaces-training-setup.md`
- Demo flow: `docs/trainer-demo-order.md`
- Limitations: `docs/known-limitations.md`
- Target rationale: `docs/target-selection-summary.md`
