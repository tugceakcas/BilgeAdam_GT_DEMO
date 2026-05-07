# VS Code / Codespaces Setup (Karate + Cucumber)

Use this setup to avoid language-mode conflicts for `.feature` files.

## Recommended Extensions

- Extension Pack for Java (`vscjava.vscode-java-pack`)
- Maven for Java (`vscjava.vscode-maven`)
- Test Runner for Java (`vscjava.vscode-java-test`)
- Cucumber (Official) (`CucumberOpen.cucumber-official`)
- Karate (`karatelabs.karate`)

Do not install: `Karate Runner (Kirk Slota)`.

## Required Workspace Behavior

- API features under `src/test/resources/features/api/**` must be `karate`.
- Web/mobile/training features must be `cucumber`.

This mapping is already enforced in `.vscode/settings.json`.

## Critical Cleanup (User/Remote Settings)

Open both:

- `Preferences: Open User Settings (JSON)`
- `Preferences: Open Remote Settings (JSON)`

Remove conflicting keys if present:

- `"files.associations": { "*.feature": "karate" }`
- `"files.associations": { "*.feature": "cucumber" }`
- Any `karateRunner.*` setting

Only workspace-level mapping should remain active.

## Reset Order

1. `Developer: Reload Window`
2. `Java: Clean Java Language Server Workspace`
3. If needed, disable/enable Cucumber and Karate extensions, then reload
4. If still broken, rebuild Codespace container and re-check settings

## How to Run

- Web: prefer class-level run from `WebCucumberTest.java` using Java Test Runner; the default workspace config runs it headless.
- API: run from `ApiKarateTest.java` using Java Test Runner (or Karate run action)

`Problems` warnings tagged as `cucumber.undefined-step` in API feature files are lint diagnostics. They are not proof that API tests were executed by Cucumber.

## Visible Browser Demo

Codespaces uses the default `web-headless` config for participant stability.

For trainer-only browser demos, start noVNC first:

```bash
scripts/start-novnc.sh
```

Then open the forwarded port `6080` and run `WebCucumberTest.java` with the `web-novnc` Java test config.

See `docs/codespaces-training-setup.md` for the full flow.
