package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

/**
 * LDU - Load U register (16-bit)
 * Loads a 16-bit value into the U register
 */
public class LDUInstruction implements Instruction {

    public static final int IMM16 = 0;
    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;
    public static final int EXTENDED_INDIRECT = 4;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final int mode;

    public LDUInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        int value;
        
        // Vérifier si le mode indirect est activé
        boolean isIndirect = cpu.isExtendedIndirectMode();
        cpu.setExtendedIndirectMode(false); // Réinitialiser pour l'instruction suivante

        switch (mode) {
            case IMM16:
                value = AddressingMode.immediate16(cpu);
                break;
            case DIRECT:
                int dirAddr = AddressingMode.direct(cpu);
                value = memory.readWord(dirAddr);
                break;
            case EXTENDED:
                if (isIndirect) {
                    value = memory.readWord(AddressingMode.extendedIndirect(cpu, memory));
                } else {
                    int extAddr = AddressingMode.extended(cpu);
                    value = memory.readWord(extAddr);
                }
                break;
            case INDEXED_X:
                int idxAddr = AddressingMode.indexedX(cpu);
                value = memory.readWord(idxAddr);
                break;
            case INDEXED:
                int idxAddr2 = AddressingMode.indexedGeneric(cpu);
                value = memory.readWord(idxAddr2);
                break;
            default:
                throw new IllegalStateException("Mode invalide");
        }

        // Load U register
        cpu.setRegU(value & 0xFFFF);

        // Update flags based on loaded value
        cpu.setFlag(CPU.CC_N, (value & 0x8000) != 0);
        cpu.setFlag(CPU.CC_Z, value == 0);
        cpu.setFlag(CPU.CC_V, false); // Always clear overflow
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
            case EXTENDED_INDIRECT:
                return 4;  // 0x04 0xFE 0x00 0x09
            default:
                return 0;
        }
    }
}
