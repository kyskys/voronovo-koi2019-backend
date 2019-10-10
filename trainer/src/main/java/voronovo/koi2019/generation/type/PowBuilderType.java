package voronovo.koi2019.generation.type;

import voronovo.koi2019.generation.test.pow.*;
import voronovo.koi2019.generation.util.TestBuilderUtil;

import java.util.EnumSet;

import static voronovo.koi2019.generation.util.ConstantsHolder.SEPARATOR;

public enum PowBuilderType {
    SAME_DEGREE("SAME_DEGREE") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(SameDegreePowTestBuilder.class, value);
        }
    },
    SAME_BASE("SAME_BASE") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(SameBasePowTestBuilder.class, value);
        }
    },
    POW_IN_POW("POW_IN_POW") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(PowInPowTestBuilder.class, value);
        }
    },
    NEGATIVE_DEGREE("NEGATIVE_DEGREE") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(NegativeDegreePowTestBuilder.class, value);
}
    },
    SAME_DEGREE_REVERT("SAME_DEGREE_REVERT") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(SameDegreeRevertPowTestBuilder.class, value);
        }
    },
    SAME_BASE_REVERT("SAME_BASE_REVERT") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(SameBaseRevertPowTestBuilder.class, value);
        }
    },
    POW_IN_POW_REVERT("POW_IN_POW_REVERT") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(PowInPowRevertTestBuilder.class, value);
        }
    },
    NEGATIVE_DEGREE_REVERT("NEGATIVE_DEGREE_REVERT") {
        @Override
        public AbstractPowBuilder getPowBuilder(String value) {
            return getBuilder(NegativeDegreeRevertPowTestBuilder.class, value);
        }
    };

    protected AbstractPowBuilder getBuilder(Class<? extends AbstractPowBuilder> clazz, String parameters) {
        return TestBuilderUtil.getPowBuilder(clazz, parameters.split(SEPARATOR)[1], parameters.split(SEPARATOR)[2]);
    }

    private final String identifier;

    PowBuilderType(String identifier) {
        this.identifier = identifier;
    }

    public static AbstractPowBuilder find(String builder) {
        return EnumSet.allOf(PowBuilderType.class).stream().filter(value -> builder.split(" ")[1].equals(value.identifier)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid pow builder identifier for " + builder))
                .getPowBuilder(builder.trim());
    }

    public abstract AbstractPowBuilder getPowBuilder(String value);
}
