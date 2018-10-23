package train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.grobid.core.GrobidModel;
import org.grobid.core.GrobidModels;
import org.grobid.core.engines.Engine;
import org.grobid.core.jni.WapitiModel;
import org.grobid.core.mock.MockContext;
import org.grobid.trainer.AbstractTrainer;
import org.grobid.trainer.AffiliationAddressTrainer;
import org.grobid.trainer.CitationTrainer;
import org.grobid.trainer.DateTrainer;
import org.grobid.trainer.FigureTrainer;
import org.grobid.trainer.FulltextTrainer;
import org.grobid.trainer.HeaderTrainer;
import org.grobid.trainer.NameCitationTrainer;
import org.grobid.trainer.NameHeaderTrainer;
import org.grobid.trainer.ReferenceSegmenterTrainer;
import org.grobid.trainer.SegmentationTrainer;
import org.grobid.trainer.TableTrainer;

import config.Config;
import demos.GrobidDemo;

/**
 * data for training: grobid-trainer/resources/dataset/<MODEL>/corpus/ </br>
 * data for evaluation: grobid-trainer/resources/dataset/<MODEL>/evaluation/ </br>
 * new generated models: grobid-home/models
 */
public class Training
{
	private static File correctedTrainingData = new File(Config.trainingOutput, "done");
	private static List<AbstractTrainer> trainers = new ArrayList<>();

	public static void main(String[] args) throws Exception
	{

		// createTrainingData();
		// trainAndEvaluate();
		// copyCorrectedTrainingData();
		dumpModels();
	}

	private static void dumpModels()
	{
		GrobidDemo.init();
		addTrainers();

		for(AbstractTrainer trainer : trainers)
		{
			File model = new File(trainer.getModel().getModelPath());
			File outputFile = new File(Config.trainingDumpedModels, trainer.getModel().getModelName() + ".dumped.txt");

			System.out.println("trainer " + trainer.getClass().getSimpleName() + "\n\tdump model " + model + " ");
			WapitiModel.dump(model, outputFile);
			System.out.println("\tfinished");
		}
	}

	private static void addTrainers()
	{
		trainers.add(new AffiliationAddressTrainer());
		// models.add(new ChemicalEntityTrainer());
		trainers.add(new DateTrainer());
		trainers.add(new CitationTrainer());
		// models.add(new EbookTrainer());
		trainers.add(new FulltextTrainer());
		trainers.add(new HeaderTrainer());
		trainers.add(new NameCitationTrainer());
		trainers.add(new NameHeaderTrainer());
		// trainers.add(new PatentParserTrainer());
		trainers.add(new SegmentationTrainer());
		trainers.add(new ReferenceSegmenterTrainer());
		trainers.add(new FigureTrainer());
		trainers.add(new TableTrainer());
	}

	private static void createTrainingData() throws Exception
	{
		Engine engine = GrobidDemo.initEngine();

		engine.batchCreateTraining(Config.trainingInput, Config.trainingOutput, 2);
	}

	private static void trainAndEvaluate() throws Exception
	{
		GrobidDemo.init();

		// AbstractTrainer.runTraining(new AffiliationAddressTrainer());
		// System.out.println("AffiliationAddressTrainer");
		// // AbstractTrainer.runTraining(new ChemicalEntityTrainer());
		// AbstractTrainer.runTraining(new DateTrainer());
		// System.out.println("DateTrainer");
		// AbstractTrainer.runTraining(new CitationTrainer());
		// System.out.println("CitationTrainer");
		// // AbstractTrainer.runTraining(new EbookTrainer());
		// AbstractTrainer.runTraining(new FulltextTrainer());
		// System.out.println("FulltextTrainer");
		// AbstractTrainer.runTraining(new HeaderTrainer());
		// System.out.println("HeaderTrainer");
		// AbstractTrainer.runTraining(new NameCitationTrainer());
		// System.out.println("NameCitationTrainer");
		// AbstractTrainer.runTraining(new NameHeaderTrainer());
		// System.out.println("NameHeaderTrainer");
		// AbstractTrainer.runTraining(new PatentParserTrainer());
		// System.out.println("PatentParserTrainer");
		// AbstractTrainer.runTraining(new SegmentationTrainer());
		// System.out.println("SegmentationTrainer");
		// AbstractTrainer.runTraining(new ReferenceSegmenterTrainer());
		// System.out.println("ReferenceSegmenterTrainer");
		// AbstractTrainer.runTraining(new FigureTrainer());
		// System.out.println("FigureTrainer");
		// AbstractTrainer.runTraining(new TableTrainer());
		// System.out.println("TableTrainer");

		MockContext.destroyInitialContext();
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
