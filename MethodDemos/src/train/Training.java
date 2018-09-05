package train;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.grobid.core.GrobidModel;
import org.grobid.core.GrobidModels;
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
	private static File correctedTrainingData = new File(Config.trainingOutput, "done");

	public static void main(String[] args) throws Exception
	{
		// createTrainingData();
		trainAndEvaluate();
		copyCorrectedTrainingData();
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
		GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(Config.pGrobidHome));
		GrobidProperties.getInstance(grobidHomeFinder);

		SegmentationTrainer.main(null);
	}

	private static void copyCorrectedTrainingData() throws IOException
	{
		Collection<File> list = Arrays.asList(correctedTrainingData.listFiles());

		for(File file : list)
		{
			String fileName = file.getName();

			String[] parts = fileName.split(".training.");

			String id = parts[0];
			String modelType = parts[1];

			File copyTo;
			if(modelType.endsWith(".tei.xml"))
			{
				// grobid-trainer/resources/dataset/<MODEL>/corpus/tei/TUW-137078.training.segmentation.tei.xml
				GrobidModel model = GrobidModels.modelFor(modelType.replace(".tei.xml", ""));
				if(modelType.startsWith("references.referenceSegmenter")) model = GrobidModels.REFERENCE_SEGMENTER;

				File grobidHome = new File(Config.pGrobidTrainer, "resources/dataset/" + model.getFolderName() + "/corpus");
				File teiCorpusPath = new File(grobidHome, "tei");

				copyTo = new File(teiCorpusPath, fileName);
				FileUtils.copyFile(file, copyTo);
				System.out.println(copyTo.getAbsolutePath());
			}
			else
				if(modelType.endsWith(".rawtxt"))
				{
					// do nothing
					// TUW-137078.training.segmentation.rawtxt
				}
				else
				{
					// grobid-trainer/resources/dataset/<MODEL>/corpus/raw/TUW-137078.training.segmentation

					GrobidModel model = GrobidModels.modelFor(modelType);
					if(modelType.startsWith("references.referenceSegmenter")) model = GrobidModels.REFERENCE_SEGMENTER;
					File grobidHome = new File(Config.pGrobidTrainer, "resources/dataset/" + model.getFolderName() + "/corpus");
					File rawCorpusPath = new File(grobidHome, "raw");

					copyTo = new File(rawCorpusPath, fileName);
					FileUtils.copyFile(file, copyTo);
					System.out.println(copyTo.getAbsolutePath());
				}
		}
	}

}
