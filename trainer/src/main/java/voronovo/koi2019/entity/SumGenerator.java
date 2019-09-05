package voronovo.koi2019.entity;

import lombok.Data;
import voronovo.koi2019.api.IGenerator;
import voronovo.koi2019.api.IQuestion;
import voronovo.koi2019.api.Randomiser;
import voronovo.koi2019.type.Sum;

@Data
public class SumGenerator implements IGenerator<SumGenerator>, IQuestion<Integer> {

    private Integer first;
    private Integer second;
    private Integer answer;
    private Randomiser randomiser;



    @Override
    public void generate() {
        first = randomiser.random();
        second = randomiser.random();
    }

    @Override
    public void evaluate() {
        answer = first + second;
    }

    @Override
    public String toStringFormat() {
        return first + " + " + second;
    }

    @Override
    public Integer getAnswer() {
        return answer;
    }
}
