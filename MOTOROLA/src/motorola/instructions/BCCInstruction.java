package motorola.instructions;

import motorola.addressing.AddressingMode;
import motorola.cpu.CPU;
import motorola.memory.Memory;

public class BCCInstruction implements Instruction {
    private final String mnemonic = "BCC";
    @Override
    public void execute(CPU cpu, Memory memory) {
        int target = AddressingMode.relative8(cpu);
        if (!cpu.isFlagSet(CPU.CC_C)) cpu.setRegPC(target);
    }
    @Override public String getMnemonic() { return mnemonic; }
    @Override public int getSize() { return 2; }
}
