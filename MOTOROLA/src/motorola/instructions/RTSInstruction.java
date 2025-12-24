package motorola.instructions;

import motorola.cpu.CPU;
import motorola.memory.Memory;

public class RTSInstruction implements Instruction {

    private final String mnemonic = "RTS";

    @Override
    public void execute(CPU cpu, Memory memory) {
        // RTS dépile l'adresse de retour (little-endian)
        int returnAddr = cpu.popWord();
        cpu.setRegPC(returnAddr);
    }

    @Override
    public String getMnemonic() { return mnemonic; }

    @Override
    public int getSize() { return 1; }
}
