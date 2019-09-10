package voronovo.koi2019.generation.condition;

public class Precondition {
    private final String variable;
    private final PreconditionType preconditionType;
    private final String value;

    public Precondition(String variable, PreconditionType preconditionType, String value) {
        this.variable = variable;
        this.preconditionType = preconditionType;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public PreconditionType getPreconditionType() {
        return preconditionType;
    }

    public String getVariable() {
        return variable;
    }
}