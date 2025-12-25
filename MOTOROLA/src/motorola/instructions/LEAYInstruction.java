package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

/**
 * LEAY - Load Effective Address Y
 * Loads the effective address (not the value at that address) into Y
 */
public class LEAYInstruction implements Instruction {

    public static final int INDEXED_X = 0;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final int mode;

    public LEAYInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        int address;

        switch (mode) {
            case INDEXED_X:
                address = AddressingMode.indexedX(cpu);
                break;
            case INDEXED:
                address = AddressingMode.indexedGeneric(cpu);
                break;
            default:
                throw new IllegalStateException("Mode invalide");
        }

        // Load effective address into Y (not the value at that address)
        cpu.setRegY(address & 0xFFFF);

        // Update flags based on loaded address
        cpu.setFlag(CPU.CC_N, (address & 0x8000) != 0);
        cpu.setFlag(CPU.CC_Z, address == 0);
        cpu.setFlag(CPU.CC_V, false);
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public int getSize() {
        switch(mode) {
            case INDEXED_X:
                return 2; // 1 opcode + 1 postbyte
            case INDEXED:
                return 2; // 1 opcode + 1 postbyte
            default:
                return 1;
        }
    }
}
