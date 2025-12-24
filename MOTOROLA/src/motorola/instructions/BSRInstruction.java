package motorola.instructions;

import motorola.cpu.CPU;
import motorola.memory.Memory;
import motorola.addressing.AddressingMode;

public class BSRInstruction implements Instruction {
    private final String mnemonic = "BSR";
    @Override
    public void execute(CPU cpu, Memory memory) {
        int target = AddressingMode.relative8(cpu);
        // Empiler l'adresse de retour (PC après l'instruction)
        int returnAddr = cpu.getRegPC();
        cpu.pushWord(returnAddr);
        cpu.setRegPC(target);
    }
    @Override public String getMnemonic() { return mnemonic; }
    @Override public int getSize() { return 2; }
}
