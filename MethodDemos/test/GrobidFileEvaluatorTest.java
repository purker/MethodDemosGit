import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import evaluation.EvaluationMode;
import evaluation.GrobidXStreamFileEvaluator;
import evaluation.tools.DocumentSetResult;
import evaluation.tools.PublicationIterator;
import factory.PublicationFactory;
import mapping.result.Publication;
import pl.edu.icm.cermine.evaluation.exception.EvaluationException;

class GrobidFileEvaluatorTest
{
	private static GrobidXStreamFileEvaluator e = new GrobidXStreamFileEvaluator();
	private static EvaluationMode mode = EvaluationMode.SYSOUT_DETAILED;

	@Test
	void test100() throws EvaluationException, IOException
	{

		Publication originalPub = PublicationFactory.createPublication();
		Publication extractedPub = PublicationFactory.createPublication();

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		DocumentSetResult result = e.evaluate(mode, iter);

		assertEquals(new Double(1.), result.getAveragePrecision(), "average precsision");
		assertEquals(new Double(1.), result.getAverageRecall(), "average recall");
		assertEquals(new Double(1.), result.getAverageF1(), "average F1");
	}

	@Test
	void test0() throws EvaluationException, IOException
	{
		Publication originalPub = PublicationFactory.createPublication();
		Publication extractedPub = new Publication();

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		DocumentSetResult result = e.evaluate(mode, iter);

		assertEquals(new Double(Double.NaN), result.getAveragePrecision(), "average precsision");
		assertEquals(new Double(0.), result.getAverageRecall(), "average recall");
		assertEquals(new Double(0.), result.getAverageF1(), "average F1");
	}

	@Test
	void test() throws EvaluationException, IOException
	{
		Publication originalPub = PublicationFactory.createPublication();
		Publication extractedPub = PublicationFactory.createPublication();

		extractedPub.setPageTo(null);

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		DocumentSetResult result = e.evaluate(mode, iter);

		// assertEquals(new Double(0.), result.getAveragePrecision(), "average precsision");
		// assertEquals(new Double(0.), result.getAverageRecall(), "average recall");
		// assertEquals(new Double(0.), result.getAverageF1(), "average F1");
	}

}
