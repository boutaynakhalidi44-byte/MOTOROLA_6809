package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class LDInstruction implements Instruction {

    public static final int IMM8 = 0;
    public static final int DIRECT = 1;
    public static final int EXTENDED = 2;
    public static final int INDEXED_X = 3;
    public static final int EXTENDED_INDIRECT = 4;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final boolean useA;
    private final int mode;

    public LDInstruction(String mnemonic, boolean useA, int mode) {
        this.mnemonic = mnemonic;
        this.useA = useA;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {

        int value;
        
        // Vérifier si le mode indirect est activé
        boolean isIndirect = cpu.isExtendedIndirectMode();
        cpu.setExtendedIndirectMode(false); // Réinitialiser pour l'instruction suivante

        switch (mode) {
            case IMM8:
                value = AddressingMode.immediate8(cpu);
                System.out.printf("DEBUG LDInstruction IMM8: value=0x%02X, useA=%s\n", value, useA);
                break;
            case DIRECT:
                value = memory.readByte(AddressingMode.direct(cpu));
                break;
            case EXTENDED:
                if (isIndirect) {
                    value = memory.readByte(AddressingMode.extendedIndirect(cpu, memory));
                } else {
                    value = memory.readByte(AddressingMode.extended(cpu));
                }
                break;
            case EXTENDED_INDIRECT:
                value = memory.readByte(AddressingMode.extendedIndirect(cpu, memory));
                break;
            case INDEXED_X:
                value = memory.readByte(AddressingMode.indexedX(cpu));
                break;
            case INDEXED:
                int addr = AddressingMode.indexedGeneric(cpu);
                System.out.printf("DEBUG LDInstruction INDEXED: address=0x%04X\n", addr);
                value = memory.readByte(addr);
                System.out.printf("DEBUG LDInstruction INDEXED: value read from memory=0x%02X\n", value);
                break;
            default:
                throw new IllegalStateException();
        }

        value &= 0xFF;

        if (useA) {
            cpu.setAccA(value);
            System.out.printf("DEBUG: After setAccA(0x%02X), A=0x%02X\n", value, cpu.getAccA());
        } else {
            cpu.setAccB(value);
            System.out.printf("DEBUG: After setAccB(0x%02X), B=0x%02X\n", value, cpu.getAccB());
        }

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
            case IMM8:
                return 2;      // 1 opcode + 1 operand
            case DIRECT:
                return 2;    // 1 opcode + 1 address
            case EXTENDED:
                return 3;  // 1 opcode + 2 address
            case EXTENDED_INDIRECT:
                return 3;  // 1 opcode + 2 address (indirect)
            case INDEXED_X:
                return 2; // 1 opcode + 1 postbyte
            case INDEXED:
                return 2; // 1 opcode + 1 postbyte
            default:
                return 1;
        }
    }
}
