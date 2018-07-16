import evaluation.CermineXStreamFileEvaluator;
import evaluation.SystemEvaluator;

class CermineFileEvaluatorTest extends AbstractFileEvaluatorTest
{
	private static CermineXStreamFileEvaluator e = new CermineXStreamFileEvaluator();

	@Override
	protected SystemEvaluator getEvalutator()
	{
		return e;
	}
}
