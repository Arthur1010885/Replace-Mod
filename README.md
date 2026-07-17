<p align="center">
  <img src="assets/banner-animated.gif" alt="AutoReplace animated banner" width="800"/>
</p>

<h1 align="center">AutoReplace</h1>

<p align="center">
  <b>Select · Break · Fill</b> — server-side mass editing for Minecraft (Fabric)
</p>

<p align="center">
  <a href="#features">Features</a> ·
  <a href="#installation">Install</a> ·
  <a href="#how-to-use">Usage</a> ·
  <a href="#supported-versions">Versions</a> ·
  <a href="#building-from-source">Build</a> ·
  <a href="#faq">FAQ</a>
</p>

<p align="center">
  <img alt="Minecraft" src="https://img.shields.io/badge/Minecraft-1.21.1%20to%2026.2-62b47a?style=for-the-badge"/>
  <img alt="Fabric" src="https://img.shields.io/badge/Loader-Fabric-dbd0b4?style=for-the-badge"/>
  <img alt="License" src="https://img.shields.io/badge/License-CC0%201.0-4f8cff?style=for-the-badge"/>
</p>
<h1 align="center">AutoReplace</h1>

<p align="center">
  <b>Select · Break · Fill</b> — server-side mass editing for Minecraft (Fabric)
</p>

<p align="center">
  <a href="#features">Features</a> ·
  <a href="#installation">Install</a> ·
  <a href="#how-to-use">Usage</a> ·
  <a href="#supported-versions">Versions</a> ·
  <a href="#building-from-source">Build</a> ·
  <a href="#faq">FAQ</a>
</p>

<p align="center">
  <img alt="Minecraft" src="https://img.shields.io/badge/Minecraft-1.21.1%20to%2026.2-62b47a?style=for-the-badge"/>
  <img alt="Fabric" src="https://img.shields.io/badge/Loader-Fabric-dbd0b4?style=for-the-badge"/>
  <img alt="License" src="https://img.shields.io/badge/License-CC0%201.0-4f8cff?style=for-the-badge"/>
</p>

---

## Why AutoReplace?

Client-only break tools often **look** like they work — then the server restores the blocks.

AutoReplace runs mass break/fill on the **logical server** (integrated singleplayer server or dedicated path), so edits **persist**.

| Before | After |
|--------|--------|
| Blocks reappear | Blocks stay gone |
| Fill is visual only | Fill is real in the world |
| Singleplayer desync | Singleplayer just works |

---

## Features

- **Area selection** — right-click two corners (Litematica-style workflow)
- **Mass break** — mine one block inside the selection; server clears matching blocks
- **Mass fill** — fill from hand item or world pick
- **Z menu** — toggles, tooltips, fill preview, status footer
- **Server-authoritative** — no plugin needed in singleplayer / Fabric servers
- **Optional Paper plugin** — multiplayer on Paper/Spigot
- **Safety** — 4096 block cap, reach checks, skip unbreakables

---

## Demo flow

```text
[Z] open menu
   │
   ▼
Selection mode ON → right-click Pos1 → right-click Pos2
   │
   ├─ Auto-break ON → mine 1 block → area clears (server)
   │
   └─ Pick block (hand/world) → Fill selection → area filled (server)
```

| Key | Action |
|-----|--------|
| `Z` | Open / close menu (rebindable in Controls) |

---

## Installation

### Singleplayer

1. Install **Fabric Loader** for your Minecraft version  
2. Put **Fabric API** in `mods/`  
3. Put the matching **AutoReplace** jar in `mods/`  
4. Launch  

No plugin required.

### Multiplayer

| Server type | What to install |
|-------------|-----------------|
| **Fabric server** | AutoReplace + Fabric API on server **and** clients |
| **Paper / Spigot** | `AutoReplacePlugin` in `plugins/` + AutoReplace mod on clients |

---

## How to use

### Select
1. Press `Z`
2. Enable **Selection mode**
3. Right-click corner A, then corner B

### Mass break
1. Enable **Auto-break**
2. Mine **one** block inside the selection
3. Server removes the rest

### Mass fill
1. Hold a block → **Use block in hand**  
   or **Pick from world** and right-click a block
2. Press **Fill selection**  
   (or enable **Fill when picking**)

---

## Menu options

| Option | Description |
|--------|-------------|
| Auto-break | Mass break when mining one block in selection |
| Selection mode | Right-click two corners |
| Only same block | Break only the mined block type |
| Drop items | Survival drops (creative never drops) |
| Use block in hand | Fill material = held item |
| Pick from world | Fill material = clicked block |
| Fill when picking | Auto-fill after choosing material |
| Replace blocks | Fill over existing blocks, not only air |
| Fill selection | Run mass fill now |
| Clear selection | Reset Pos1 / Pos2 |

---

## Supported versions

| Minecraft | Status |
|-----------|--------|
| 26.2 / 26.1.x | Full |
| 1.21.11 | Dedicated port |
| 1.21.8 / 1.21.5 / 1.21.4 / 1.21.1 | Multi-version builds |

Always use **Fabric API for the same game version**.

---

## Building from source

### 26.2 (main)

```bash
cd autoreplace-mod
./gradlew build
# build/libs/autoreplace-1.2.0.jar
```

### 1.21.11

```bash
cd autoreplace-1.21.11
./gradlew build
```

### Paper plugin (optional)

```bash
cd autoreplace-plugin
./gradlew build
# build/libs/AutoReplacePlugin-1.2.0.jar
```

---

## Project structure

```text
autoreplace-mod/        # Fabric mod (26.x base)
  src/main/             # payloads + server handlers
  src/client/           # menu, keys, selection, client net
  assets/               # README banner
autoreplace-1.21.11/    # 1.21.11 port
autoreplace-plugin/     # Paper multiplayer helper
```

---

## Networking

| Channel | Direction | Purpose |
|---------|-----------|---------|
| `autoreplace:mass_break` | Client → Server | Request mass break |
| `autoreplace:mass_break_result` | Server → Client | Break feedback |
| `autoreplace:mass_fill` | Client → Server | Request mass fill |
| `autoreplace:mass_fill_result` | Server → Client | Fill feedback |

In singleplayer these go to the integrated server via Fabric networking.

---

## Safety limits

- Max **4096** blocks per mass action
- Player must be near the selection
- Adventure / spectator cannot mass edit
- Unbreakable blocks are skipped
- Survival fill consumes inventory; creative does not

---

## FAQ

**Blocks used to come back after breaking**  
That was client-only editing. Current versions break/fill on the server.

**Do I need the Paper plugin in singleplayer?**  
No.

**Launcher shows “Unknown”**  
Often catalog delay (especially CurseForge review) or a local jar. In-game, check for the AutoReplace load message.

**Selection outline missing on some versions**  
Some 1.21.x ports limit outline rendering due to API changes. Selection + break/fill still work.

---

## Links

- Modrinth: *(add your project URL)*
- CurseForge: *(add your project URL)*
- Issues: use GitHub Issues on this repository

---

## License

This project is dedicated to the public domain under **[CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/)**.

You may use, modify, and redistribute freely.

---

<p align="center">
  <sub>Built with Fabric · Inspired by selection tools like Litematica / WorldEdit · Focused on a simple break & fill loop</sub>
</p>
