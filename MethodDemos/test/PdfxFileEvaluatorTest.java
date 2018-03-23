import evaluation.PdfxXStreamFileEvaluator;
import evaluation.SystemEvaluator;

class PdfxFileEvaluatorTest extends AbstractFileEvaluatorTest
{
	private static PdfxXStreamFileEvaluator e = new PdfxXStreamFileEvaluator(types);

	@Override
	protected SystemEvaluator getEvalutator()
	{
		return e;
	}
}
