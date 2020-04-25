package locusttask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.Locust;

import serviceobjects.ForcedAnswer;


public class ForcedYes extends AbstractTask  {
	
	private static final Logger logger = LoggerFactory.getLogger(ForcedYes.class);

	private int weight;


	ForcedAnswer forcedAnswer = new ForcedAnswer();


    public ForcedYes(Integer weight){
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return "Forced Yes";
    }


    @Override
    public void execute() {
        try {
            forcedAnswer.aForcedAnswerTypeIsRequested("yes");
            Locust.getInstance().recordSuccess("GET", getName(), forcedAnswer.getResponseTime(), 1);
        }catch (AssertionError | Exception error){
            Locust.getInstance().recordFailure("GET",getName(), forcedAnswer.getResponseTime(),"Yes has not been returned");
            logger.info("Something went wrong in the request");
        }
    
    }

	
	
}
