import org.junit.runner.RunWith;

import evaluation.SystemEvaluator;
import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractTest
{
	protected abstract SystemEvaluator getEvalutator();

}
