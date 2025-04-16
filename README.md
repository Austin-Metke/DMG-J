# DMG-J

A Game Boy emulator written in Java. Supports loading ROMs, executing CPU instructions, handling memory management, GPU scanline rendering, sprite rendering (broken at the moment), and (partially) interactive debugging.

## Features

 Partial 8-bit Z80 CPU emulation (`Z80.java`)
 
 Instruction decoding (`CBOpcodeInfo.java`, `OpcodeInfo.java`)
 
 ROM loading and memory mapping (`ROMLoader.java`, `MMU.java`)
 
 GPU with scanline and sprite rendering (sprite rendering currently broken) (`GPU.java`, `Sprite.java`)
 
 Joypad input handling (Broken) (`Joypad.java`)


 Debugger with UI (`DebuggerUI.java`, `DebuggerController.java`)

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

## In Progress
- [ ] Load games without use of BIOS
- [ ] Input Handling
- [ ] Implement all opcodes
- [ ] Sprite Rendering (Broken)
- [ ] Interrupt Handling (Broken)

## TODO
- [ ] Memory Viewer/Debugger (Crude)
- [ ] Functional UI
- [ ] Audio emulation
- [ ] Save states
- [x] BIOS support
- [ ] Link cable emulation
