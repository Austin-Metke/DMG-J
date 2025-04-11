
public class Main {
    public static void main(String[] args) {

    Z80 cpu = new Z80();

    cpu.reset();

    while(true) {
        cpu.tick();
    }


    }
}