package train;

import java.util.Arrays;

import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;
import org.grobid.trainer.SegmentationTrainer;

import config.Config;

/**
 * data for training: grobid-trainer/resources/dataset/<MODEL>/corpus/ </br>
 * data for evaluation: grobid-trainer/resources/dataset/<MODEL>/evaluation/ </br>
 * new generated models: grobid-home/models
 */
public class Training
{
	public static void main(String[] args) throws Exception
	{
		// createTrainingData();
		trainAndEvaluate();
	}

	private static void createTrainingData() throws Exception
	{
		// TODO Initialisierung evt. mit GrobidDemo run teilen
		GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(Config.pGrobidHome));
		GrobidProperties.getInstance(grobidHomeFinder);

		Engine engine = GrobidFactory.getInstance().createEngine();

		engine.batchCreateTraining(Config.trainingInput, Config.trainingOutput, 2);
	}

	private static void trainAndEvaluate() throws Exception
	{
		SegmentationTrainer.main(null);
	}

}
