# Codespaces Training Setup

This project includes a dev container so participants can use the same Java, Maven, browser, and VS Code setup without installing tools on corporate laptops.

## Participant Mode

Use this mode for normal classroom execution.

1. Open the repository in Codespaces.
2. Wait for the dev container setup to finish.
3. Run web tests from `WebCucumberTest.java` using the default Java Test Runner config.

The default config is headless. Participants do not need noVNC for normal exercises.

## Trainer Browser Demo Mode

Use this mode only when the trainer wants to show a visible browser from Codespaces.

1. Start noVNC:

   ```bash
   scripts/start-novnc.sh
   ```

2. Open the forwarded port `6080`.
3. In VS Code, select the `web-novnc` Java test config.
4. Run `WebCucumberTest.java`.

The test browser will run on the virtual display and be visible through noVNC.

## Environment Check

Run this when a Codespace looks suspicious:

```bash
scripts/codespaces-doctor.sh
```

The quick check runs automatically after container creation:

```bash
scripts/codespaces-doctor.sh --quick
```

## Rebuild Rule

After changes under `.devcontainer/`, use:

```text
Codespaces: Rebuild Container
```

This is required because browser and noVNC dependencies are installed at container build time.
