package net.davoleo.mettle.api.metal;

public record MetalProperties(int meltingTemperature,
                              float ductility,
                              float density,
                              float tensileStrength,
                              float hardness) {
}
