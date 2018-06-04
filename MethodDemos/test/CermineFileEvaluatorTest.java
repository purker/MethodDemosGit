import evaluation.CermineXStreamFileEvaluator;
import evaluation.SystemEvaluator;

class CermineFileEvaluatorTest extends AbstractFileEvaluatorTest
{
	private static CermineXStreamFileEvaluator e = new CermineXStreamFileEvaluator(types, referenceTypes);

	@Override
	protected SystemEvaluator getEvalutator()
	{
		return e;
	}
}
