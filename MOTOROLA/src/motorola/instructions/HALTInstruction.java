package motorola.instructions;

import motorola.cpu.CPU;
import motorola.memory.Memory;

/**
 * Instruction HALT - Arrête le CPU
 * Opcode: 0x3F
 * Modes: Aucun (instruction sans opérande)
 */
public class HALTInstruction implements Instruction {

    private final String mnemonic;

    public HALTInstruction(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public void execute(CPU cpu, Memory memory) {
        System.out.println(">>> HALT - CPU arrêté");
        cpu.setHalted(true);
    }

    @Override
    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public int getSize() {
        return 1;  // Instruction sans opérande
    }
}
