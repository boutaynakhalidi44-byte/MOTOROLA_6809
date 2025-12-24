package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class STInstruction implements Instruction {

    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final boolean useA;
    private final int mode;

    public STInstruction(String mnemonic, boolean useA, int mode) {
        this.mnemonic = mnemonic;
        this.useA = useA;
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
            case INDEXED:
                addr = AddressingMode.indexedGeneric(cpu);
                break;
            default:
                throw new IllegalStateException();
        }

        int value = useA ? cpu.getAccA() : cpu.getAccB();
        value &= 0xFF;

        memory.writeByte(addr, value);

        cpu.setFlag(CPU.CC_N, (value & 0x80) != 0);
        cpu.setFlag(CPU.CC_Z, value == 0);
        cpu.setFlag(CPU.CC_V, false);
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public int getSize() {
        switch(mode) {
            case DIRECT:
                return 2;    // 1 opcode + 1 address
            case EXTENDED:
                return 3;    // 1 opcode + 2 address
            case INDEXED_X:
                return 2;    // 1 opcode + 1 postbyte
            default:
                return 1;
        }
    }
}
