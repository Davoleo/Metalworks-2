package net.davoleo.mettle.api.metal;

public record ToolStats(
        int harvestLevel,
        float efficiency,
        float damage,

        double attackSpeed,
        double reachDistance
)
{
}
