package net.davoleo.mettle.api.multiblock;

public interface IMultiblockController {

    Multiblock getMultiBlock();

    boolean isValid();

    void assemble();

    void disassemble();

}
