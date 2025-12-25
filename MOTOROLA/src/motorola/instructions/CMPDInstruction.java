package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

/**
 * CMPD - Compare 16-bit value with D (A:B accumulator pair)
 * Sets flags based on D - operand (without storing result)
 */
public class CMPDInstruction implements Instruction {

    public static final int IMM16 = 0;
    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final int mode;

    public CMPDInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        int d = cpu.getAccD();
        int operand;

        switch (mode) {
            case IMM16:
                operand = AddressingMode.immediate16(cpu);
                break;
            case DIRECT:
                int dirAddr = AddressingMode.direct(cpu);
                operand = memory.readWord(dirAddr);
                break;
            case EXTENDED:
                int extAddr = AddressingMode.extended(cpu);
                operand = memory.readWord(extAddr);
                break;
            case INDEXED_X:
                int idxAddr = AddressingMode.indexedX(cpu);
                operand = memory.readWord(idxAddr);
                break;
            case INDEXED:
                int idxAddr2 = AddressingMode.indexedGeneric(cpu);
                operand = memory.readWord(idxAddr2);
                break;
            default:
                throw new IllegalStateException("Mode invalide");
        }

        int result = d - operand;
        int finalValue = result & 0xFFFF;

        // Update flags (D is NOT modified)
        cpu.setFlag(CPU.CC_N, (finalValue & 0x8000) != 0);
        cpu.setFlag(CPU.CC_Z, finalValue == 0);
        cpu.setFlag(CPU.CC_V, ((d ^ operand) & (d ^ result) & 0x8000) != 0);
        cpu.setFlag(CPU.CC_C, (result & 0x10000) != 0);
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public int getSize() {
        switch(mode) {
            case IMM16:
                return 3;
            case DIRECT:
                return 2;
            case EXTENDED:
                return 3;
            case INDEXED_X:
                return 2;
            default:
                return 1;
        }
    }
}
