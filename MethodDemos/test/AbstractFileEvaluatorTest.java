import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import evaluation.tools.AbstractCollectionResult;
import evaluation.tools.CollectionEnum;
import evaluation.tools.EvalInformationType;
import evaluation.tools.EvaluationResult;
import evaluation.tools.PublicationIterator;
import factory.PublicationFactory;
import mapping.result.Publication;
import utils.FileCollectionUtil;
import utils.XStreamUtil;

public abstract class AbstractFileEvaluatorTest extends AbstractTest
{
	private static boolean DEFAULT_PRINT_RESULTS = false;

	protected static List<EvalInformationType> types = EvalInformationType.getTypesForPublications();
	protected static List<EvalInformationType> referenceTypes = EvalInformationType.getTypesForReferences();

	@Test
	void testDocumentResult100() throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("100001");
		Publication extractedPub = PublicationFactory.createPublication("100001");

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		AbstractCollectionResult<?> result = getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);

		assertThat("average precsision", new BigDecimal(100), Matchers.comparesEqualTo(result.getDocumentResult().getAveragePrecision()));
		assertThat("average recall", new BigDecimal(100), Matchers.comparesEqualTo(result.getDocumentResult().getAverageRecall()));
		assertThat("average F1", new BigDecimal(100), Matchers.comparesEqualTo(result.getDocumentResult().getAverageF1()));
	}

	@Test
	void testDocumentResult0() throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("100001");
		Publication extractedPub = new Publication();

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		AbstractCollectionResult<?> result = getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);

		EvaluationResult evaluationResult = result.getDocumentResult();
		assertThat("average precsision", new BigDecimal(Double.NaN), Matchers.comparesEqualTo(evaluationResult.getAveragePrecision()));
		assertThat("average recall", new BigDecimal(0), Matchers.comparesEqualTo(evaluationResult.getAverageRecall()));
		assertThat("average F1", new BigDecimal(0), Matchers.comparesEqualTo(evaluationResult.getAverageF1()));
	}

	@ParameterizedTest
	@MethodSource(value = "evalInformationTypeValuesPublication")
	//@ValueSource(strings = {"PAGES"}) //also works
	void testEvalutionType100Publication(EvalInformationType evalInfoType) throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("100001");
		Publication extractedPub = PublicationFactory.createPublication("100001");

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);
		AbstractCollectionResult<?> result = getEvalutator().getCollectionResultByCollectionEnum(CollectionEnum.PUBLICATION);

		EvaluationResult evaluationResult = result.getPerType().getResultForKey(evalInfoType);
		assertNotNull(evaluationResult);
		assertThat(evalInfoType + " average precsision", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAveragePrecision()));
		assertThat(evalInfoType + " average recall", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAverageRecall()));
		assertThat(evalInfoType + " average F1", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAverageF1()));
	}
	
	@ParameterizedTest
	@MethodSource(value = "evalInformationTypeValuesReference")
	void testEvalutionType100Reference(EvalInformationType evalInfoType) throws IOException
	{
		Publication originalPub = PublicationFactory.createPublication("100001");
		Publication extractedPub = PublicationFactory.createPublication("100001");

		PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
		getEvalutator().evaluate(iter, DEFAULT_PRINT_RESULTS);
		AbstractCollectionResult<?> result = getEvalutator().getCollectionResultByCollectionEnum(CollectionEnum.REFERENCE);

		EvaluationResult evaluationResult = result.getPerType().getResultForKey(evalInfoType);
		assertNotNull(evaluationResult);
		assertThat(evalInfoType + " average precsision", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAveragePrecision()));
		assertThat(evalInfoType + " average recall", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAverageRecall()));
		assertThat(evalInfoType + " average F1", new BigDecimal(100), Matchers.comparesEqualTo(evaluationResult.getAverageF1()));
	}

	@SuppressWarnings("unused")
	private static Stream<EvalInformationType> evalInformationTypeValuesPublication()
	{
		return EvalInformationType.getTypesForPublications().stream();
	}

	@SuppressWarnings("unused")
	private static Stream<EvalInformationType> evalInformationTypeValuesReference()
	{
		return EvalInformationType.getTypesForReferences().stream();
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
	// Publication originalPub = PublicationFactory.createPublication("100001");
	// Publication extractedPub = PublicationFactory.createPublication("100001");
	//
	// extractedPub.setPageTo(null);
	//
	// PublicationIterator iter = new PublicationIterator(originalPub, extractedPub);
	// DocumentSetResult result = e.evaluate(modes, iter);
	//
	// assertThat("average precsision", new BigDecimal(0), Matchers.comparesEqualTo(result.getDocumentResult().getAveragePrecision()));
	// assertThat("average recall", new BigDecimal(0), Matchers.comparesEqualTo(result.getDocumentResult().getAverageRecall()));
	// assertThat("average F1", new BigDecimal(0), Matchers.comparesEqualTo(result.getDocumentResult().getAverageF1()));
	// }
}
