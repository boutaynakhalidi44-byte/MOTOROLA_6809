package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class BITInstruction implements Instruction {

    public static final int IMM8 = 0;
    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final int register; // 0 = A, 1 = B
    private final int mode;

    public BITInstruction(String mnemonic, int register, int mode) {
        this.mnemonic = mnemonic;
        this.register = register;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        int acc = (register == 0) ? cpu.getAccA() : cpu.getAccB();
        int operand;

        switch (mode) {
            case IMM8:
                operand = AddressingMode.immediate8(cpu);
                break;
            case DIRECT:
                operand = memory.readByte(AddressingMode.direct(cpu));
                break;
            case EXTENDED:
                operand = memory.readByte(AddressingMode.extended(cpu));
                break;
            case INDEXED_X:
                operand = memory.readByte(AddressingMode.indexedX(cpu));
                break;
            case INDEXED:
                operand = memory.readByte(AddressingMode.indexedGeneric(cpu));
                break;
            default:
                throw new IllegalStateException("Mode invalide");
        }

        int result = (acc & operand) & 0xFF;

        cpu.setFlag(CPU.CC_N, (result & 0x80) != 0);
        cpu.setFlag(CPU.CC_Z, result == 0);
        cpu.setFlag(CPU.CC_V, false); // BIT clears V
    }

    @Override
    public String getMnemonic() { 
        return mnemonic; 
    }

    @Override
    public int getSize() { 
        return 1; 
    }
}
