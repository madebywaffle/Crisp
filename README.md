Crisp [![Build Status](https://img.shields.io/github/actions/workflow/status/madebywaffle/Crisp/build.yml?branch=main)](https://github.com/madebywaffle/Crisp/actions)
===========

Fork of [Paper](https://github.com/PaperMC/Paper) made by Waffle.
Currently tracking Paper's published builds. Optimization patches are planned.

Downloads
------
Download the latest `crisp-*.jar` from the [releases page](https://github.com/madebywaffle/Crisp/releases).

Building
------
Requires JDK 25.

```bash
./gradlew applyAllPatches
./gradlew createPaperclipJar
```

The compiled jar will be in `crisp-server/build/libs`.
