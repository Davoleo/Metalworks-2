package net.davoleo.mettle.api.metal;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

public class MixtureRatio {

    private final Pair<String, Integer> metal1;
    private final Pair<String, Integer> metal2;
    @Nullable
    private final Pair<String, Integer> metal3;

    public MixtureRatio(String metal1, int ratio1, String metal2, int ratio2)
    {
        this(metal1, ratio1, metal2, ratio2, null, 0);
    }

    public MixtureRatio(String metal1, int ratio1, String metal2, int ratio2, String metal3, int ratio3)
    {
        this.metal1 = Pair.of(metal1, ratio1);
        this.metal2 = Pair.of(metal2, ratio2);
        this.metal3 = metal3 == null ? null : Pair.of(metal3, ratio3);

        float total = ratio1 + ratio2 + ratio3;
        assert total == 100;
    }

    public int getRatio(String metalName) {
        if (metalName.equals(metal1.getLeft()))
            return metal1.getRight();
        else if (metalName.equals(metal2.getLeft()))
            return metal2.getRight();
        else if (metal3 != null && metalName.equals(metal3.getLeft()))
            return metal3.getRight();
        else
            return 0;
    }

    public String[] getMetals() {
        if (metal3 != null)
            return new String[] {this.metal1.getLeft(), this.metal2.getLeft(), this.metal3.getLeft()};
        else
            return new String[] {this.metal1.getLeft(), this.metal2.getLeft()};
    }

}
