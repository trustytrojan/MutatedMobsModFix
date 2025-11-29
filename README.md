# MutatedMobsModFix
Since it seems [rolik1997](https://www.curseforge.com/members/rolik1997) has abandoned the [Mutated Mobs Mod](https://www.curseforge.com/minecraft/mc-mods/mutated-mobs-mod), and since I really like it for the variety of monsters it adds to Minecraft, I decided to decompile it to fix a lot of bugs.

## Most annoying bug I found
On the same day I discovered the real culprit is [this code](https://github.com/trustytrojan/MutatedMobsModFix/blob/0520f762ddd6472da6a7df87b6a165f99d766aab/src/main/java/mmm/common/entities/EntityLoader.java#L144-L151) for registering entities into Forge. The cause for all of the typecasting crashes is the fact that `EntityLoader` uses a monotonically increasing `id` integer when registering entities, AND allows you to disable the registering of any entity class. And if you disable just one entity on the server but not the client, the remaining to-be-registered entities will all have mismatched IDs from server to client, causing NBT data meant for one entity class to arrive at another.

**Lesson: do not change any of the `EnableXXXXMob` configuration values!!!** It is not necessary when you can set spawnrates to zero. And if you really don't want to register one of those mobs, **make sure the change is done both server- and client-side.**

## Archives in the repo
- [mutatedmobs-1.0.8.jar](/mutatedmobs-1.0.8.jar): The final release of the mod from CurseForge.
- [mutatedmobs-1.0.8-deobf.7z](/mutatedmobs-1.0.8-deobf.7z): Deobfuscated contents using [SimplyProgrammer/Minecraft-Deobfuscator3000](https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000)
  - Provided as a Git submodule; unfortunately it requires Windows to run
  - The code from the deobfuscator did not build on its own; it required manual changes using [Python scripts](scripts/) to change many lines for a successful build.

It shouldn't be necessary to extract from [mutatedmobs-1.0.8-deobf.7z](/mutatedmobs-1.0.8-deobf.7z) again as all the necessary fixes to build have been applied to the sources under [src/main/java/mmm](/src/main/java/mmm).

## Credit & Copyright Notice
All credit for the original mod (code, assets) goes to [rolik1997](https://www.curseforge.com/members/rolik1997).

If the original author of the mod ([rolik1997](https://www.curseforge.com/members/rolik1997)) does not want the code and/or assets hosted on this repository, please leave an issue or email me at [t@trustytrojan.dev](mailto:t@trustytrojan.dev).
