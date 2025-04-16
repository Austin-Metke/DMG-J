# Game Boy Emulator

A Game Boy emulator written in Java. Supports loading ROMs, executing CPU instructions, handling memory management, GPU scanline rendering, sprite display, timer emulation, and interactive debugging.

## Features

✅ Partial 8-bit Z80 CPU emulation (`Z80.java`)
✅ Instruction decoding (`CBOpcodeInfo.java`, `OpcodeInfo.java`)
✅ ROM loading and memory mapping (`ROMLoader.java`, `MMU.java`)
✅ GPU with scanline and sprite rendering (sprite rendering currently broken) (`GPU.java`, `Sprite.java`)
✅ Joypad input handling (Broken) (`Joypad.java`)
✅ Debugger with UI (`DebuggerUI.java`, `DebuggerController.java`)

## Getting Started

1. Place your Game Boy ROM and BIOS file in the project directory.
2. Update the `Main.java` file to point to your ROM and BIOS file.
3. Run the project using your preferred Java IDE or with `javac` and `java`.
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```

## Requirements

- Java 8 or newer
- A valid Game Boy ROM file (`.gb`)

## TODO
- [~] Memory Viewer/Debugger (Crude)
- [ ] Functional UI
- [ ] Input Handling
- [ ] Sprite Rendering (Broken)
- [ ] Implement all CB opcodes
- [ ] Interrupt Handling (Broken)
- [ ] Audio emulation
- [ ] Save states
- [✔] BIOS support
- [ ] Load games without use of BIOS
- [ ] Link cable emulation