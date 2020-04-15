import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;

import evaluation.tools.AbstractCollectionResult;
import evaluation.tools.EvalInformationType;
import evaluation.tools.EvaluationResult;
import evaluation.tools.PublicationIterator;
import factory.PublicationFactory;
import junitparams.JUnitParamsRunner;
import mapping.result.Publication;
import utils.FileCollectionUtil;
import utils.XStreamUtil;

@RunWith(JUnitParamsRunner.class)
public abstract class AbstractFileEvaluatorTest extends AbstractTest
{
	private static boolean DEFAULT_PRINT_RESULTS = false;

	protected static List<EvalInformationType> types = EvalInformationType.getTypesForPublications();
	protected static List<EvalInformationType> referenceTypes = EvalInformationType.getTypesForReferences();

	@Test
	void testDocumentResult100() throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("1");
		Publication extractedPub = PublicationFactory.createPublication("1");

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		AbstractCollectionResult<?> result = getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);

		assertEquals(new Double(100.), result.getDocumentResult().getAveragePrecision(), "average precsision");
		assertEquals(new Double(100.), result.getDocumentResult().getAverageRecall(), "average recall");
		assertEquals(new Double(100.), result.getDocumentResult().getAverageF1(), "average F1");
	}

	@Test
	void testDocumentResult0() throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("1");
		Publication extractedPub = new Publication();

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		AbstractCollectionResult<?> result = getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);

		EvaluationResult evaluationResult = result.getDocumentResult();
		assertEquals(new Double(Double.NaN), evaluationResult.getAveragePrecision(), "average precsision");
		assertEquals(new Double(0.), evaluationResult.getAverageRecall(), "average recall");
		assertEquals(new Double(0.), evaluationResult.getAverageF1(), "average F1");
	}
	//
	// @Test
	// @Parameters({"TITLE"})

	@ParameterizedTest
	@MethodSource(value = "evalInformationTypeValues")
	// @ValueSource(strings = {"TITLE"}) also works
	void testTitle100(EvalInformationType evalInfoType) throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("1");
		Publication extractedPub = PublicationFactory.createPublication("1");

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		AbstractCollectionResult<?> result = getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);

		// EvalInformationType evalInfoType = EvalInformationType.TITLE;
		EvaluationResult evaluationResult = result.getPerType().getResultForKey(evalInfoType);
		assertNotNull(evaluationResult);
		assertEquals(new Double(100.), evaluationResult.getAveragePrecision(), evalInfoType + " average precsision");
		assertEquals(new Double(100.), evaluationResult.getAverageRecall(), evalInfoType + " average recall");
		assertEquals(new Double(100.), evaluationResult.getAverageF1(), evalInfoType + " average F1");
	}

	private static Stream<EvalInformationType> evalInformationTypeValues()
	{
		return Stream.of(EvalInformationType.values());
	}

	@Test
	void testPublicationSetOnReferences() throws IOException
	{
		File file = FileCollectionUtil.getCermineResultFiles().get(0);
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		System.out.println(publication.getReferences().get(0).getPublication());
		assertNotNull("publiation not null", publication.getReferences().get(0).getPublication());

	}
	// @Test
	// void test() throws EvaluationException, IOException
	// {
	// Publication originalPub = PublicationFactory.createPublication("1");
	// Publication extractedPub = PublicationFactory.createPublication("1");
	//
	// extractedPub.setPageTo(null);
	//
	// PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
	// DocumentSetResult result = e.evaluate(modes, iter);
	//
	// assertEquals(new Double(0.), result.getDocumentResult().getAveragePrecision(), "average precsision");
	// assertEquals(new Double(0.), result.getDocumentResult().getAverageRecall(), "average recall");
	// assertEquals(new Double(0.), result.getDocumentResult().getAverageF1(), "average F1");
	// }
}
