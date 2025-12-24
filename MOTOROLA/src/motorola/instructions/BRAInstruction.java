package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class BRAInstruction implements Instruction {
    private final String mnemonic = "BRA";
    @Override
    public void execute(CPU cpu, Memory memory) {
        int target = AddressingMode.relative8(cpu);
        cpu.setRegPC(target);
    }
    @Override public String getMnemonic() { return mnemonic; }
    @Override public int getSize() { return 2; }
}

