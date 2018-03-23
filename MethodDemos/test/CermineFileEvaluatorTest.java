import evaluation.CermineXStreamFileEvaluator;
import evaluation.SystemEvaluator;

class CermineFileEvaluatorTest extends AbstractFileEvaluatorTest
{
	private static CermineXStreamFileEvaluator e = new CermineXStreamFileEvaluator(types);

	@Override
	protected SystemEvaluator getEvalutator()
	{
		return e;
	}
}
