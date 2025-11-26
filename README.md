# MutatedMobsModFix
Since it seems [rolik1997](https://www.curseforge.com/members/rolik1997) has abandoned the [Mutated Mobs Mod](https://www.curseforge.com/minecraft/mc-mods/mutated-mobs-mod), and since I really like it for the variety of monsters it adds to Minecraft, I decided to decompile and deobfuscate it to fix the many crashes it causes due to simple errors in type-casting.

## Archives in the repo
- [mutatedmobs-1.0.8.jar](/mutatedmobs-1.0.8.jar): The final release of the mod from CurseForge.
- [mutatedmobs-1.0.8-deobf.7z](/mutatedmobs-1.0.8-deobf.7z): Deobfuscated contents using [SimplyProgrammer/Minecraft-Deobfuscator3000](https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000)
  - Provided as a Git submodule; unfortunately it requires Windows to run
  - The code from the deobfuscator did not build on its own; it required manual changes using [Python scripts](scripts/) to change many lines for a successful build.

It shouldn't be necessary to extract from [mutatedmobs-1.0.8-deobf.7z](/mutatedmobs-1.0.8-deobf.7z) again as all the necessary fixes to build have been applied to the sources under [src/main/java/mmm](/src/main/java/mmm).

## Credit & Copyright Notice
All credit for the original mod (code, assets) goes to [rolik1997](https://www.curseforge.com/members/rolik1997).

If the original author of the mod ([rolik1997](https://www.curseforge.com/members/rolik1997)) does not want the code and/or assets hosted on this repository, please leave an issue or email me at [t@trustytrojan.dev](mailto:t@trustytrojan.dev).
