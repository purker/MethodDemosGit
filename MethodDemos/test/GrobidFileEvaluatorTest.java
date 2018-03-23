import evaluation.GrobidXStreamFileEvaluator;
import evaluation.SystemEvaluator;

class GrobidFileEvaluatorTest extends AbstractFileEvaluatorTest
{
	private static GrobidXStreamFileEvaluator e = new GrobidXStreamFileEvaluator(types);

	@Override
	protected SystemEvaluator getEvalutator()
	{
		return e;
	}
}
