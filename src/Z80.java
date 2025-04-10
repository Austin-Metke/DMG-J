public class Z80 {

    //Time clock
    int clock_m;
    int clock_t;
    Registers registers;
    MMU mmu;
    private final Instruction[] opcodeMap;
    private int halt;
    private int stop;

    public Z80() {
        this.registers = new Registers();
        this.opcodeMap = new Instruction[256];
        this.halt = 0;
        this.stop = 0;

        opcodeMap[0x00] = this::NOP;
        opcodeMap[0x01] = this::LD_BC_nn;
        opcodeMap[0x02] = this::LD_BC_A;

        opcodeMap[0xFA] = this::LDAmm;

    }

    public void reset() {
        this.clock_m = 0;
        this.clock_t = 0;
    }


    public void tick() {
        int opcode = mmu.readByte(registers.pc++)&0xFF;
        opcodeMap[opcode].execute();
        registers.pc &= 65535;
        this.clock_m += registers.m;
        this.clock_t += registers.t;
    }



    //Compare B to A
    public void CPr_b() {
        int i = registers.a;
        i -= registers.b;
        registers.f |= 0x40;
        if((i&255) == 0) {
            registers.f |= 0x80; // set subtraction flag
        }

        if(i < 0) {
            registers.f |=  0x10; // check for underflow
        }

        registers.m = 1; // 1 m time taken
        registers.t = 4;

    }

    public void LD_BC_A() {
        int addr =  registers.getBC()&0xFFFF; // mask to 16-bit address
        mmu.writeByte(addr, registers.a);
        registers.m = 2; // 2 m time taken
        registers.t = 8;

    }

    public void LD_BC_nn() {
        int value = mmu.readWord(registers.pc)&0xFFFF; // mask to 16 bits
        registers.pc += 2; // Advance pc by 2 (read word)
        registers.setBC(value);
        registers.m = 3;
        registers.t = 12;
    }





    //No operation (does nothing)
    public void NOP() {
        registers.m = 1; // 1 M time taken
        registers.t = 4;
    }

    public void PUSHBC() {
        registers.sp--;
        mmu.writeByte(registers.sp,  registers.b);
        registers.sp--;
        mmu.writeByte(registers.sp, registers.c);
        registers.m = 3;
        registers.t = 12;
    }





    public void POPHL() {
        registers.l = mmu.readByte(registers.sp); //low
        registers.sp++;
        registers.h = mmu.readByte(registers.sp);// high
        registers.sp++;
        registers.m = 3; // 3m time taken
        registers.t = 12;

    }


    /*
        *** Load/Store ***
     */
    public void LDrr_bb() { registers.b = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_bc() { registers.b = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_bd() { registers.b = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_be() { registers.b = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_bh() { registers.b = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_bl() { registers.b = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_ba() { registers.b = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_cb() { registers.c = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_cc() { registers.c = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_cd() { registers.c = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_ce() { registers.c = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_ch() { registers.c = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_cl() { registers.c = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_ca() { registers.c = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_db() { registers.d = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_dc() { registers.d = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_dd() { registers.d = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_de() { registers.d = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_dh() { registers.d = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_dl() { registers.d = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_da() { registers.d = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_eb() { registers.e = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_ec() { registers.e = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_ed() { registers.e = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_ee() { registers.e = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_eh() { registers.e = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_el() { registers.e = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_ea() { registers.e = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_hb() { registers.h = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_hc() { registers.h = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_hd() { registers.h = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_he() { registers.h = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_hh() { registers.h = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_hl() { registers.h = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_ha() { registers.h = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_lb() { registers.l = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_lc() { registers.l = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_ld() { registers.l = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_le() { registers.l = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_lh() { registers.l = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_ll() { registers.l = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_la() { registers.l = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrr_ab() { registers.a = registers.b; registers.m = 1; registers.t = 4; }
    public void LDrr_ac() { registers.a = registers.c; registers.m = 1; registers.t = 4; }
    public void LDrr_ad() { registers.a = registers.d; registers.m = 1; registers.t = 4; }
    public void LDrr_ae() { registers.a = registers.e; registers.m = 1; registers.t = 4; }
    public void LDrr_ah() { registers.a = registers.h; registers.m = 1; registers.t = 4; }
    public void LDrr_al() { registers.a = registers.l; registers.m = 1; registers.t = 4; }
    public void LDrr_aa() { registers.a = registers.a; registers.m = 1; registers.t = 4; }

    public void LDrHLm_b() { registers.b = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_c() { registers.c = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_d() { registers.d = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_e() { registers.e = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_h() { registers.h = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_l() { registers.l = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }
    public void LDrHLm_a() { registers.a = mmu.readByte((registers.h<<8)+registers.l); registers.m=2; registers.t=8; }

    public void LDHLmr_b() { mmu.writeByte((registers.getHL()), registers.b); registers.m = 2; registers.t = 8;}
    public void LDHLmr_c() { mmu.writeByte((registers.getHL()), registers.c); registers.m = 2; registers.t = 8;}
    public void LDHLmr_d() { mmu.writeByte((registers.getHL()), registers.d); registers.m = 2; registers.t = 8;}
    public void LDHLmr_e() { mmu.writeByte((registers.getHL()), registers.e); registers.m = 2; registers.t = 8;}
    public void LDHLmr_h() { mmu.writeByte(registers.getHL(), registers.h); registers.m = 2; registers.t = 8;}
    public void LDHLmr_l() { mmu.writeByte(registers.getHL(), registers.l); registers.m = 2; registers.t = 8;}
    public void LDHLmr_a() { mmu.writeByte(registers.getHL(), registers.a); registers.m = 2; registers.t = 8;}

    public void LDrn_b() { registers.b = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_c() { registers.c = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_d() { registers.d = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_e() { registers.e = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_h() { registers.h = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_l() { registers.l = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}
    public void LDrn_a() { registers.a = mmu.readByte(registers.pc); registers.pc++; registers.m = 2; registers.t = 8;}

    public void LDHLmn() { mmu.writeByte((registers.h<<8)+registers.l, mmu.readByte(registers.pc)); registers.pc++; registers.m = 3; registers.t = 12;}

    public void LDBCmA() { mmu.writeByte(registers.getBC(), registers.a); registers.m = 2; registers.t = 8; }

    public void LDDEmA() { mmu.writeByte(registers.getDE(), registers.a); registers.m = 2; registers.t = 8; }

    public void LDmmA() { mmu.writeByte(mmu.readWord(registers.pc), registers.a); registers.pc +=2; registers.m = 4; registers.t=16; }

    public void LDABCm() { registers.a = mmu.readByte(registers.getBC()); registers.m = 2; registers.t = 8; }
    public void LDADEm() { registers.a = mmu.readByte(registers.getDE()); registers.m = 2; registers.t = 8; }

    public void LDAmm() { registers.a = mmu.readByte(registers.pc); registers.pc+=2; registers.m = 4; registers.t = 16;}

    public void LDBCnn() { registers.c = mmu.readByte(registers.pc); registers.b = mmu.readByte(registers.pc+1); registers.pc+=2; registers.m = 3; registers.t = 12; }
    public void LDDEnn() { registers.e = mmu.readByte(registers.pc); registers.d = mmu.readByte(registers.pc+1); registers.pc+=2; registers.m = 3; registers.t = 12; }
    public void LDHLnn() { registers.l = mmu.readByte(registers.pc); registers.h = mmu.readByte(registers.pc+1); registers.pc +=2; registers.m = 3; registers.t = 12; }
    public void LDSPnn() { registers.sp = mmu.readWord(registers.pc); registers.pc +=2; registers.m = 3; registers.t = 12; }

    public void LDHLmm() { int i = mmu.readWord(registers.pc); registers.pc +=2; registers.l = mmu.readByte(i); registers.h = mmu.readByte(i+1); registers.m = 5; registers.t = 20; }
    public void LDmmHL() { int i = mmu.readWord(registers.pc); registers.pc +=2; mmu.writeWord(i, registers.getHL()); registers.m = 5; registers.t = 20; }

    public void LDHLIA() { mmu.writeByte(registers.getHL(), registers.a); registers.l = (registers.l + 1) & 0xFF; if ((registers.l & 0xFF) == 0) { registers.h = (registers.h + 1) & 0xFF;} registers.m = 2; registers.t = 8; }
    public void LDAHLI() { registers.a = mmu.readByte(registers.getHL()); registers.l = (registers.l+1)&0xFF; if ((registers.l&0xFF) == 0) { registers.h = (registers.h+1)&0xFF;} registers.m = 2; registers.t = 8; }

    public void LDHLDA() {
        mmu.writeByte((registers.getHL()), registers.a);
        registers.l = (registers.l-1)&0xFF;
        if((registers.l&0xFF)==255) {
            registers.h = (registers.h-1)&0xFF;
        }

        registers.m = 2;
        registers.t = 8;
    }

    public void LDAHLD() {
        registers.a = mmu.readByte(registers.getHL());
        registers.l = (registers.l-1)&0xFF;
        if(registers.l==255) {
            registers.h = (registers.h-1)&0xFF;
        }

        registers.m = 2;
        registers.t = 8;
    }

    public void LDAIOn() { registers.a = mmu.readByte(0xFF00+mmu.readByte(registers.pc)); registers.pc++; registers.m = 3; registers.t = 12;}
    public void LDAIOnA() { mmu.writeByte(0xFF00+mmu.readByte(registers.pc), registers.a); registers.pc++; registers.m=3; registers.t=12; }
    public void LDAIOC() { registers.a = mmu.readByte(0xFF00+ registers.c); registers.m = 2; registers.t = 8; }
    public void LDAIOCA() { mmu.writeByte(0xFF00+registers.c, registers.a); registers.m = 2; registers.t = 8; }

    public void LDHLSPn() {
        int offset = (byte) mmu.readByte(registers.pc); // Sign-extend automatically
        registers.pc++;
        int result = (registers.sp + offset) & 0xFFFF;
        registers.h = (result >> 8) & 0xFF;
        registers.l = result & 0xFF;
        registers.m = 3;
        registers.t = 12;

    }

    public void SWAPr_b() {int tr = registers.b;registers.b = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_c() {int tr = registers.c;registers.c = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_d() {int tr = registers.d;registers.d = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_e() {int tr = registers.e;registers.e = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_h() {int tr = registers.h;registers.h = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_l() {int tr = registers.l;registers.l = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}
    public void SWAPr_a() {int tr = registers.a;registers.a = mmu.readByte(registers.getHL());mmu.writeByte(registers.getHL(), tr);registers.m = 4;registers.t = 16;}

    /* **Data Processing** */

    public void ADDr_b() { int result = registers.a + registers.b; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_c() { int result = registers.a + registers.c; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_d() { int result = registers.a + registers.d; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_e() { int result = registers.a + registers.e; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_h() { int result = registers.a + registers.h; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_l() { int result = registers.a + registers.l; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }
    public void ADDr_a() { int result = registers.a + registers.a; fz(result & 0xFF); if (result > 255) { registers.f |= 0x10; } registers.a = result & 0xFF; registers.m = 1; registers.t = 4; }

    public void ADDHL(){ int result = registers.a + mmu.readByte(registers.getHL()); fz(registers.a); if(result>0xFF) {registers.f|=0x10;} registers.a = result&0xFF; registers.m=2; registers.t=8; }
    public void ADDn() { int result = registers.a + mmu.readByte(registers.pc); registers.pc++; fz(registers.a); if(result > 0xFF) {registers.f|=0x10;} registers.a = result&0xFF; registers.m = 2; registers.t = 8; }
    public void ADDHLBC() { int hl = registers.getHL(); hl+=registers.getBC(); if(hl>0xFFFF) { registers.f |= 0x10;} else {registers.f&=0xEF;} registers.h = (hl>>8)&0xFF; registers.l=hl&255; registers.m = 3; registers.t = 12;}


    //Helper Function
    public void fz(int i, int as) {
        registers.f = 0;
        if((i&255) == 0) {
            registers.f |= 128;
        }
        registers.f |= ((as != 0) ? 0x40 : 0);
    }

    public void fz(int i) {
        fz(i, 0);
    }

    private static class Registers {
        //8 bit registers set
        private int a;
        private int b;
        private int c;
       private int d;
        private int e;
        private int h;
        private int l;
        private int m;
        private int t;
        int f;
        //16 bit registers
        private int pc;
        private int sp;

        Registers() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.h = 0;
            this.l = 0;
            this.m = 0;
            this.t = 0;
            this.f = 0;
            this.pc = 0;
            this.sp = 0;
        }


        public int getBC() {
            return ((b & 0xFF) << 8) | (c & 0xFF);
        }

        public void setBC(int val) {
            b = (val >> 8) & 0xFF;
            c = val & 0xFF;
        }
        public int getDE() {
            return ((d & 0xFF) << 8) | (e & 0xFF);
        }

        public void setDE(int val) {
            d = (val >> 8) & 0xFF;
            e = val & 0xFF;
        }

        public int getHL() {
            return ((h & 0xFF) << 8) | (l & 0xFF);
        }

        public void setHL(int val) {
            h = (val >> 8) & 0xFF;
            l = val & 0xFF;
        }


    }

    private static class MMU {

        public int readByte(int addr) {
            return 0;
        }

        public int readWord(int addr) {
            return 0;
        }

        public void writeByte(int addr, int val) {


        }

        public void writeWord(int addr, int val) {

        }

    }

    @FunctionalInterface
    interface Instruction {
        void execute();
    }

}
