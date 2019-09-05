package voronovo.koi2019.condition;

import java.util.EnumSet;

public enum PreconditionType {
    EQ("==") {
        @Override
        public int generateValue(String value) {
            return Integer.parseInt(value);
        }
    },
    BETWEEN("between") {
        @Override
        public int generateValue(String value) {
            String[] split = value.split(";");
            int min = Integer.parseInt(split[0]);
            int max = Integer.parseInt(split[1]);
            return (int) Math.round(Math.random() * (max - min) + min);
        }
    };

    private final String identifier;

    PreconditionType(String identifier) {
        this.identifier = identifier;
    }

    public static PreconditionType byIdentifier(String identifier) {
        return EnumSet.allOf(PreconditionType.class).stream().filter(value -> value.identifier.equals(identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid condition identifier"));
    }

    public abstract int generateValue(String value);
}
