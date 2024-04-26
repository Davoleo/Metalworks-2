package net.davoleo.mettle.api.metal;

import com.google.common.collect.Range;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Alloy {

    private final String name;

    private final MixtureRatio standardRatio;

    private final Map<String, Range<Integer>> validRanges;

    private Alloy(String name, MixtureRatio standardRatio)
    {
        this.name = name;
        this.standardRatio = standardRatio;
        this.validRanges = new HashMap<>(4, 1);
    }

    public String getName() {
        return name;
    }

    public MixtureRatio getStandardRatio() {
        return standardRatio;
    }

    public Set<String> getIngredientMetalNames() {
        return validRanges.keySet();
    }

    public boolean doesMixtureConform(MixtureRatio mixture) {
        for (String metal : mixture.getMetals())
        {
            if (!validRanges.containsKey(metal))
                return false;

            Range<Integer> validRange = validRanges.get(metal);
            return validRange.contains(mixture.getRatio(metal));
        }
        return false;
    }

    public static class Builder {

        String alloyName;

        String[] metalNames;
        int[] standards;
        int[] min;
        int[] max;

        int metalCount = 0;

        public Builder(String alloyName)
        {
            metalNames = new String[3];
            standards = new int[3];
            min = new int[3];
            max = new int[3];

            this.alloyName = alloyName;
        }

        /**
         * @return the same Builder to chain calls
         */
        public Builder addMetal(String metalName, int standard, int min, int max) {
            if (metalCount >= 3)
                throw new RuntimeException("No more than 3 metals can be added to an alloy [use recursive alloys to employ more metals in the same alloy]");
            this.metalNames[metalCount] = metalName;
            this.standards[metalCount] = standard;
            this.min[metalCount] = min;
            this.max[metalCount] = max;
            metalCount++;
            return this;
        }

        public Alloy build() {
            if (metalCount < 2)
                throw new IllegalStateException("Can't build Alloy with less than 2 ingredient metals!");

            MixtureRatio standard;
            if (metalCount > 2) {
                standard = new MixtureRatio(metalNames[0], standards[0], metalNames[1], standards[1], metalNames[2], standards[2]);
            }
            else {
                standard = new MixtureRatio(metalNames[0], standards[0], metalNames[1], standards[1]);
            }

            Alloy alloy = new Alloy(alloyName, standard);
            for (int i = 0; i < metalCount; i++)
                alloy.validRanges.put(metalNames[i], Range.closed(min[i], max[i]));

            return alloy;
        }
    }

}
