package net.davoleo.mettle.api.metal;

import net.davoleo.mettle.api.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class Alloy implements Registry.Nameable {

    public record Ingredient(IMetal metal, int ratio) {
    }

    private final Ingredient[] metals;
    private final IMetal alloy;

    public Alloy(IMetal alloy, Ingredient... ingredients) {
        this.alloy = alloy;

        if (ingredients.length < 2 || ingredients.length > 3) {
            throw new IllegalArgumentException("Alloys only support 2 or 3 ingredients");
        }

        this.metals = ingredients;
    }

    public static Builder create(IMetal output) {
        return new Builder(output);
    }

    @Nullable
    public Ingredient getIngredient(String name) {
        for (var ingredient : metals) {
            if (name.equals(ingredient.metal.name())) {
                return ingredient;
            }
        }

        return null;
    }

    public Ingredient[] getIngredients() {
        return metals;
    }

    public int getIngredientCount() {
        return metals.length;
    }

    public IMetal getResult() {
        return alloy;
    }

    @Override
    public String name() {
        return alloy.name();
    }

    public static class Builder {

        private final IMetal output;

        private final Ingredient[] ingredients;

        private int metalCount;

        public Builder(IMetal output) {
            this.output = output;
            this.metalCount = 0;
            ingredients = new Ingredient[3];
        }

        /**
         * @return the same Builder to chain calls
         */
        public Builder addMetal(@NotNull IMetal metal, int ratio) {
            Objects.requireNonNull(metal, "metal in alloy should not be null");

            if (ratio < 1 || ratio > 63) {
                throw new IllegalArgumentException("ingredient ratio in the alloy should be between 1 and 63");
            }

            if (metalCount >= 3)
                throw new IllegalStateException(String.format("No more than 3 metals can be added to an alloy, current alloy %s already has 3 ingredients", output.name()));

            ingredients[metalCount] = new Ingredient(metal, ratio);
            metalCount++;
            return this;
        }

        public Alloy build() {
            if (metalCount < 2)
                throw new IllegalStateException("Can't build Alloy with less than 2 ingredient metals!");

            var fittedArray = Arrays.copyOf(ingredients, metalCount);
            int ratioSum = Arrays.stream(fittedArray).mapToInt(Ingredient::ratio).sum();
            if (ratioSum > 64) {
                throw new IllegalStateException("Can't build alloy with a sum-ratio of %d".formatted(ratioSum));
            }

            return new Alloy(output, fittedArray);
        }
    }

}
