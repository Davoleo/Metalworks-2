package net.davoleo.mettle.data;

public interface IReplacement {

    String pathName();

    String name();

    static IReplacement joint(String name) {
        return new Joint(name);
    }

    static IReplacement split(String pathname, String name) {
        return new Split(name, pathname);
    }

    record Joint(String name) implements IReplacement {
        @Override
        public String pathName() {
            return name;
        }
    }

    record Split(
            String name,
            String pathName
    ) implements IReplacement
    { }


}
