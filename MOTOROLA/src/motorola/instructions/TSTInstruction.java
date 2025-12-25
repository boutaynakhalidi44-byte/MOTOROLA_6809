package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class TSTInstruction implements Instruction {

    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;

    private final String mnemonic;
    private final int mode;

    public TSTInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        int operand;

        switch (mode) {
            case DIRECT:
                operand = memory.readByte(AddressingMode.direct(cpu));
                break;
            case EXTENDED:
                operand = memory.readByte(AddressingMode.extended(cpu));
                break;
            case INDEXED_X:
                operand = memory.readByte(AddressingMode.indexedX(cpu));
                break;
            default:
                throw new IllegalStateException("Mode invalide");
        }

        cpu.setFlag(CPU.CC_N, (operand & 0x80) != 0);
        cpu.setFlag(CPU.CC_Z, operand == 0);
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
                return 2;  // 0x0D + adresse
            case EXTENDED:
                return 3;  // 0x7D + 2 bytes adresse
            case INDEXED_X:
                return 2;  // 0x6D + offset
            default:
                return 0;
        }
    }
}
