package motorola.instructions;

import motorola.cpu.CPU;
import motorola.memory.Memory;
import motorola.addressing.AddressingMode;

public class BVCInstruction implements Instruction {
    private final String mnemonic = "BVC";
    @Override
    public void execute(CPU cpu, Memory memory) {
        int target = AddressingMode.relative8(cpu);
        if (!cpu.isFlagSet(CPU.CC_V)) cpu.setRegPC(target);
    }
    @Override public String getMnemonic() { return mnemonic; }
    @Override public int getSize() { return 2; }
}

