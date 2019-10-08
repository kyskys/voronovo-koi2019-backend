package voronovo.koi2019.generation.test.pow;

import lombok.Data;
import voronovo.koi2019.generation.test.DefaultTestBuilder;
import voronovo.koi2019.generation.test.api.TestBuilderPart;

@Data
public class PowInPowTestBuilder extends DefaultTestBuilder implements TestBuilderPart {
    private String pattern = "([+-]?)(\\d+)\\^([+-]?)(\\d+)([\\/*]?)([+-]?)(\\d+)\\^([+-]?)(\\d+)";
    private String sample = "([var1]^[var2])^[var3]";

}
