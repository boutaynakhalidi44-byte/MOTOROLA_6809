package motorola.instructions;

import motorola.cpu.CPU;
import motorola.memory.Memory;

public class ABXInstruction implements Instruction {

    @Override
    public void execute(CPU cpu, Memory memory) {
        cpu.setRegX((cpu.getRegX() + cpu.getAccB()) & 0xFFFF);
    }

    @Override
    public String getMnemonic() {
        return "ABX";
    }

    @Override
    public int getSize() {
        return 1;
    }
}
