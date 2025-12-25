package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class JSRInstruction implements Instruction {

    public static final int INDEXED_X = 3;
    public static final int EXTENDED = 2;
    public static final int INDEXED = 8;  // Generic indexed mode that decodes register from postbyte

    private final String mnemonic;
    private final int mode;

    public JSRInstruction(String mnemonic, int mode) {
        this.mnemonic = mnemonic;
        this.mode = mode;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        // Calculer l'adresse de retour (PC après l'instruction)
        int returnAddr = cpu.getRegPC() + getSize();
        
        // Empiler l'adresse de retour (little-endian)
        cpu.pushWord(returnAddr);
        
        // Calculer l'adresse cible
        int target;
        switch (mode) {
            case EXTENDED:
                target = AddressingMode.extended(cpu);
                break;
            case INDEXED_X:
                target = AddressingMode.indexedX(cpu);
                break;
            case INDEXED:
                target = AddressingMode.indexedGeneric(cpu);
                break;
            default:
                target = cpu.getRegPC();
        }
        
        cpu.setRegPC(target);
    }

    @Override
    public String getMnemonic() { 
        return mnemonic; 
    }

    @Override
    public int getSize() {
        return (mode == EXTENDED) ? 3 : 2;
    }
}
