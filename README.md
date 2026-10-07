# Obsidian (obi)

Open source standalone Minecraft Java client with its own loader, patcher, api and mod tooling.
Targets Minecraft 1.21.1, Java 21. Runs on PC, laptops and PojavLauncher.

## Modules
- `obi-loader`: boots the game through our own class loader
- `obi-api`: events + hooks for mods (todo)
- `obi-patcher`: our own bytecode patcher (todo)
- `obi-tooler`: dev tooling for mod makers, like Loom (todo)
- `obsidian-client`: the client built on obi (todo)

## Try the loader
1. `gradle :obi-loader:jar`
2. Copy `obi-loader/build/libs/obi-loader-0.1.0.jar` to
   `<.minecraft>/libraries/net/obi/obi-loader/0.1.0/obi-loader-0.1.0.jar`
3. Copy `obi-loader/launcher/obi-1.21.1.json` to
   `<.minecraft>/versions/obi-1.21.1/obi-1.21.1.json`
4. Install vanilla 1.21.1 once, then launch the `obi-1.21.1` version.
5. Log should show `[obi] obi loader booting`.

Minecraft is not included. Players download it from Mojang.

## License
MIT
