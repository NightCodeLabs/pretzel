package locusttask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import serviceobjects.ForcedAnswer;


public class ForcedNo extends AbstractTask  {
	
	private static final Logger logger = LoggerFactory.getLogger(ForcedNo.class);

	private int weight;

    ForcedAnswer forcedAnswer = new ForcedAnswer();


    public ForcedNo(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "Forced No";
    }


    @Override
    public void execute() {
        try {
            forcedAnswer.aForcedAnswerTypeIsRequested("no");
            Locust.getInstance().recordSuccess("GET", getName(), forcedAnswer.getResponseTime(), 1);
        }catch (AssertionError | Exception error){
            Locust.getInstance().recordFailure("GET",getName(), forcedAnswer.getResponseTime(),"No has not been returned");
            logger.info("Something went wrong in the request");
        }
    
    }

	
	
}
