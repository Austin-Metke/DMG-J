import javax.swing.*;
import java.io.IOException;

public class GameBoy {
    private Z80 cpu;
    private MMU mmu;
    private GPU gpu;
    private JFrame jFrame;
    private int frameCycles = 70224;

    public GameBoy(Z80 cpu, MMU mmu, GPU gpu) {
        this.cpu = cpu;
        this.mmu = mmu;
        this.gpu = gpu;

        // Link MMU and GPU to CPU
        this.mmu.setGpu(gpu);
        this.mmu.setCpu(cpu);
        this.gpu.setCpu(cpu);
        this.cpu.setMmu(mmu);

    }

    public void reset(String romPath, String biosPath) throws IOException {
        gpu.reset();
        cpu.reset();

        mmu.loadROM(ROMLoader.loadROM(romPath));
        mmu.loadBIOS(ROMLoader.loadROM(biosPath));
    }

    public void frame() {
        int targetTicks = cpu.clock_t + 70224;

        while (cpu.clock_t < targetTicks) {
            int opcode = mmu.readByte(cpu.registers.pc++);
            cpu.opcodeMap[opcode].execute();   // Fetch and execute opcode
            cpu.clock_t += cpu.registers.t;
            cpu.clock_m += cpu.registers.m;
            gpu.tick();                         // Tick the GPU to progress video rendering
        }
    }


    public void run() {
        // This method would need a GUI + timing loop to animate frame-by-frame
        while (true) {
            frame();

            try {
                Thread.sleep(16); // Roughly 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gpu.present(); // Redraw to screen if needed
        }
    }
}
