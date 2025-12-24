package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class DECInstruction implements Instruction {

    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;

    private final String mnemonic;
    private final int mode;

    public DECInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {

        int addr;
        switch (mode) {
            case DIRECT:
                addr = AddressingMode.direct(cpu);
                break;
            case EXTENDED:
                addr = AddressingMode.extended(cpu);
                break;
            case INDEXED_X:
                addr = AddressingMode.indexedX(cpu);
                break;
            default:
                throw new IllegalStateException();
        }

        int value = memory.readByte(addr);
        int result = (value - 1) & 0xFF;
        memory.writeByte(addr, result);

        cpu.setFlag(CPU.CC_N, (result & 0x80) != 0);
        cpu.setFlag(CPU.CC_Z, result == 0);
        cpu.setFlag(CPU.CC_V, value == 0x80);
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public int getSize() {
        switch(mode) {
            case DIRECT:
                return 2;  // 0x0A + adresse
            case EXTENDED:
                return 3;  // 0x7A + 2 bytes adresse
            case INDEXED_X:
                return 2;  // 0x6A + offset
            default:
                return 0;
        }
    }
}
