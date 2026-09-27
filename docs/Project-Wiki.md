# PolyMO Project Wiki

> A physical digital pet you keep alive — ESP32-S3 firmware and an Android app that does all the thinking on-device.
>
> This page is the documentation index for the repository. The linked documents remain the source of truth; this page explains where to start and how the documents relate.

## Start here

- [Project README](../README.md) — product overview, architecture, hardware, build instructions, models, generated assets, and licensing.
- [Product and UX design](../DESIGN.md) — the authoritative product direction, simulation rules, state model, flows, visual identity, and design-sync decisions.
- [Contributor and build instructions](../CLAUDE.md) — build commands, test expectations, generated-file rules, hardware verification guidance, and repository conventions.

## Product documentation

### Product concept and behavior

Read [DESIGN.md](../DESIGN.md) for the complete product specification. Important sections include:

- **§1 Product direction** — the pet's life model, screen-time relationship, simulation scores, sickness, death, quiet hours, calls, storage, play, and roadmap.
- **§2 State model** — phone state, pet-observable state, protocol boundaries, and the projection between them.
- **§3 Tone of voice** — how the pet speaks and why brevity is a hard constraint.
- **§4 Visual identity** — naming, stage presentation, color, typography, and pet-specific display constraints.
- **§5 Information architecture and flows** — app surfaces, first run, failure states, settings, and off/on behavior.
- **§7 Claude Design round trip** — design-system authority, synchronization, and what is intentionally different between design mocks and the implementation.

### Architecture

The architecture is summarized in [README.md](../README.md#how-it-works):

- The **ESP32-S3 pet** owns the display, microphone, speaker, touch, simulation state, persistence, and clock.
- The **Android app** provides BLE, Whisper speech recognition, llama.cpp inference, Piper text-to-speech, and screen-time input.
- The phone is compute; it is not a participant in the conversation.
- The shared wire protocol must change on both sides together: [`pet_proto.h`](../pet-esp32/main/pet_proto.h) and [`PetProtocol.kt`](../android/app/src/main/java/com/digitalpet/ble/PetProtocol.kt).

## Repository map

| Area | Documentation | What it covers |
|---|---|---|
| Project | [README.md](../README.md) | Public overview and complete build/setup orientation |
| Product/UX | [DESIGN.md](../DESIGN.md) | Product decisions, state model, UX, visual identity, and flows |
| Development | [CLAUDE.md](../CLAUDE.md) | Working conventions, tests, builds, generators, and hardware verification |
| Firmware | [`pet-esp32/`](../pet-esp32/) | ESP-IDF display, input, audio, BLE, persistence, and simulation |
| Android | [`android/`](../android/) | BLE, local AI models, conversation engine, screen time, and UI |
| Shared protocol | [`shared/`](../shared/) | Cross-platform wire definitions and shared contracts |
| Design system | [design-system/README.md](../design-system/README.md) | Vendored design tokens, components, strings, faces, personas, and sync workflow |
| Hardware | [hardware/README.md](../hardware/README.md) | Printable enclosures, board fit, battery measurements, and physical controls |
| Tools | [`tools/`](../tools/) | Design synchronization and face/persona generation tools |
| Licenses | [LICENSE](../LICENSE), [licences/GPL-3.0.txt](../licences/GPL-3.0.txt), [NOTICE](../NOTICE) | Source, binary, and dependency licensing information |

## Design system and generated assets

The [design-system README](../design-system/README.md) documents the vendored half of the Claude Design round trip:

- `tokens/` contains colors, spacing, shape, and typography tokens.
- `components.txt` and `strings.txt` define the component roster and ranked user-facing copy.
- `faces/*.json` defines what the pet looks like.
- `personas/*.json` defines what the pet says, including prompts and unprompted lines.
- Generated firmware faces, Android face sets, notification artwork, persona code, and design cards must not be hand-edited.

After changing a source face set or persona, run:

```bash
tools/gen-faces.py
tools/gen-personas.py
```

For design synchronization, start with `tools/design-sync.sh` and follow the workflow described in [design-system/README.md](../design-system/README.md#keeping-it-current) and [DESIGN.md §7](../DESIGN.md).

## Build and test quick reference

### Firmware

```bash
. ~/esp/esp-idf/export.sh
cd pet-esp32
idf.py build
idf.py -p $(ls /dev/cu.usbmodem* | head -1) flash
```

Verify behavior on hardware after flashing. A display may require a power cycle before it appears.

### Android

Use **JDK 21**, not the system JDK 25:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
  ./gradlew assembleDebug --offline
```

Run the device-independent unit tests with:

```bash
./gradlew testDebugUnitTest --offline
```

The test suite includes source-level synchronization checks for contrast, literals, tokens, components, faces, permissions, generated content, and design inputs. See [README.md](../README.md#building) and [CLAUDE.md](../CLAUDE.md) for the full rules.

## Models and privacy

The app does not ship the AI models. Import these locally through the Android file picker:

| Capability | Format |
|---|---|
| Thinking | `.gguf` |
| Hearing | Whisper `.bin` |
| Voice | Piper `.onnx` |

PolyMO has no account or server and declares no `INTERNET` permission. See [README.md](../README.md#models) and [README.md#how-it-works](../README.md#how-it-works).

## Hardware and enclosures

The supported board is the Waveshare ESP32-S3 Touch AMOLED 1.8, with a 368×448 AMOLED display, touch, microphone, speaker, BLE, and LiPo power. The [hardware README](../hardware/README.md) documents the printable `PolyMO_BMO.stl` and `PolyMO_C0F-E.stl` enclosures, battery measurements, accessible buttons, and known omissions such as print settings and source CAD.

## Licensing

- Repository source is primarily Apache-2.0.
- A released APK linking espeak-ng is GPL-3.0 because of the combined native work.
- [NOTICE](../NOTICE) records dependency versions and terms.
- See [README.md#licence](../README.md#licence) for the release checklist. This documentation is informational, not legal advice.

## Documentation rules

1. Link to the source document instead of copying a decision into a second source of truth.
2. Cite `DESIGN.md` section numbers when explaining why product behavior exists.
3. Treat generated face and persona files as outputs; edit their JSON sources and regenerate.
4. Keep firmware and Android protocol changes synchronized.
5. Record hardware measurements, not only build success.
6. Keep this index updated when a new authoritative document is added.

## Referable links

For stable references in issues, pull requests, and code comments, use these repository URLs:

- `https://github.com/WIddeeeeee/polymo/blob/main/README.md`
- `https://github.com/WIddeeeeee/polymo/blob/main/DESIGN.md`
- `https://github.com/WIddeeeeee/polymo/blob/main/CLAUDE.md`
- `https://github.com/WIddeeeeee/polymo/blob/main/design-system/README.md`
- `https://github.com/WIddeeeeee/polymo/blob/main/hardware/README.md`
- `https://github.com/WIddeeeeee/polymo/blob/main/docs/Project-Wiki.md`

GitHub automatically adds line anchors to these URLs when a specific passage is needed, for example `#L1-L20`.
