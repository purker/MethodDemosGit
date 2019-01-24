package train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.grobid.core.GrobidModels;
import org.grobid.core.IGrobidModel;
import org.grobid.core.engines.Duration;
import org.grobid.core.engines.DurationEnum;
import org.grobid.core.engines.Engine;
import org.grobid.core.jni.WapitiModel;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;
import org.grobid.trainer.AbstractTrainer;

import config.Config;
import demos.GrobidDemo;

/**
 * data for training: grobid-trainer/resources/dataset/<MODEL>/corpus/ </br>
 * data for evaluation: grobid-trainer/resources/dataset/<MODEL>/evaluation/ </br>
 * new generated models: grobid-home/models
 */
public class Training {
	private static File resources = new File(Config.pGrobidTrainer, "resources");
	private static File correctedTrainingData = new File(new File(Config.trainingOutput).getParent(), "done");
	private static List<AbstractTrainer> trainers = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		// File file = new File(Config.trainingOutput);
		// FileUtils.cleanDirectory(file);
		// FileUtils.copyDirectory(correctedTrainingData, file);
		// createTrainingData();

		// trainAndEvaluate();
		// deleteExistingTrainingData();

		// copyCorrectedTrainingData();
		// trainExistingModelAndEvaluate();
		// trainAndEvaluate();

		renameModels("original-model.wapiti", "model.wapiti", false);

		// dumpModels();
	}

	/**
	 * deletes all corpus/[tei|raw] directories
	 */
	private static void deleteExistingTrainingData() throws IOException {
		for(IGrobidModel model : GrobidModels.values()) {
			File corpusFile = GrobidProperties.getCorpusPath(resources, model);
			File teiCorpusPath = new File(corpusFile, "tei");
			File rawCorpusPath = new File(corpusFile, "raw");

			if(teiCorpusPath.exists())
			{
				FileUtils.cleanDirectory(teiCorpusPath);
				if(rawCorpusPath.exists())
				{
					FileUtils.cleanDirectory(rawCorpusPath);
				}
			}
			else
				if(corpusFile.exists())
				{
					FileUtils.cleanDirectory(corpusFile);
				}
		}

	}

	private static void dumpModels() {
		GrobidDemo.init();
		addTrainers();

		for(AbstractTrainer trainer : trainers) {
			File model = new File(trainer.getModel().getModelPath());
			File outputFile = new File(Config.trainingDumpedModels, trainer.getModel().getModelName() + ".dumped.txt");

			System.out.println("trainer " + trainer.getClass().getSimpleName() + "\n\tdump model " + model + " ");
			WapitiModel.dump(model, outputFile);
			System.out.println("\tfinished");
		}
	}

	private static void addTrainers() {
		// DONE trainers.add(new SegmentationTrainer());

		// DONE HEADER
		// DONE trainers.add(new HeaderTrainer());
		// DONE trainers.add(new AffiliationAddressTrainer());
		// DONE trainers.add(new NameHeaderTrainer());
		// DONE trainers.add(new DateTrainer());
		// reference?
		// DONE BODY
		// DONE trainers.add(new FulltextTrainer());
		// DONE trainers.add(new FigureTrainer());
		// DONE trainers.add(new TableTrainer()); // create new model, not with existing one
		// DONE REFERENCES
		// DONE trainers.add(new ReferenceSegmenterTrainer());
		// DONE trainers.add(new CitationTrainer());
		// DONE trainers.add(new NameCitationTrainer());

		// models.add(new EbookTrainer());
		// models.add(new ChemicalEntityTrainer());
		// trainers.add(new PatentParserTrainer());
	}

	private static void createTrainingData() throws Exception {
		Engine engine = GrobidDemo.initEngine();

		Duration.addStart(DurationEnum.ALL);

		engine.batchCreateTraining(Config.trainingInput, Config.trainingOutput, Config.trainingError, 2);

		Duration.addEnd(DurationEnum.ALL);
	}

	private static void trainAndEvaluate() throws Exception {
		GrobidDemo.init();
		addTrainers();

		for(AbstractTrainer trainer : trainers) {
			AbstractTrainer.runTraining(trainer);
		}

		MockContext.destroyInitialContext();
	}

	private static void trainExistingModelAndEvaluate() throws Exception {
		GrobidDemo.init();
		addTrainers();

		for(AbstractTrainer trainer : trainers) {
			AbstractTrainer.runTrainingExistingModel(trainer);
			System.out.println(trainer.getModel().getModelName());
		}

		MockContext.destroyInitialContext();
	}

	/**
	 * Copies files from correctedTrainingData to grobid-trainer/resources/dataset/<MODEL>/corpus/[tei|raw]/
	 */
	private static void copyCorrectedTrainingData() throws IOException {
		Collection<File> list = Arrays.asList(correctedTrainingData.listFiles());

		for(File file : list) {
			String fileName = file.getName();

			String[] parts = fileName.split(".training.");

			String id = parts[0];
			String modelType = parts[1];

			File copyTo;
			if(modelType.endsWith(".xml"))
			{
				// grobid-trainer/resources/dataset/<MODEL>/corpus/tei/TUW-137078.training.<MODEL>.tei.xml
				IGrobidModel model = getModelFor(modelType);

				File teiCorpusPath;
				if (model.isCorpusSplitted()) {
					teiCorpusPath = new File(GrobidProperties.getCorpusPath(resources, model), "tei");
				} else {
					teiCorpusPath = GrobidProperties.getCorpusPath(resources, model);
				}

				copyTo = new File(teiCorpusPath, fileName);
				FileUtils.copyFile(file, copyTo);
				System.out.println(copyTo.getAbsolutePath());
			}
			else if (modelType.endsWith(".rawtxt"))
				{
					// do nothing
					// TUW-137078.training.segmentation.rawtxt
				}
			else {
					// grobid-trainer/resources/dataset/<MODEL>/corpus/raw/TUW-137078.training.segmentation
					IGrobidModel model = getModelFor(modelType);

					File rawCorpusPath = new File(GrobidProperties.getCorpusPath(resources, model), "raw");

					copyTo = new File(rawCorpusPath, fileName);
					FileUtils.copyFile(file, copyTo);
					System.out.println(copyTo.getAbsolutePath());
				}
		}
	}

	/**
	 * Get model from string of training filename
	 * 
	 * @param modelType
	 * @return
	 */
	private static IGrobidModel getModelFor(String modelType) {
		if(modelType.endsWith(".tei.xml")) {
			modelType = modelType.replace(".tei.xml", "");
		} else {
			// date model only ends with ".xml"
			modelType = modelType.replace(".xml", "");
		}

		Map<String, String> mapping = new HashMap<>();
		mapping.put("header.authors", "name/header");
		mapping.put("header.date", "date");
		mapping.put("header.affiliation", "affiliation-address");
		mapping.put("references", "citation");
		mapping.put("references.authors", "name/citation");
		mapping.put("references.referenceSegmenter", "reference-segmenter");
		mapping.put("referenceSegmenter", "reference-segmenter");

		String newModelName = mapping.get(modelType);
		if(newModelName == null) {
			return GrobidModels.modelFor(modelType);
		} else {
			return GrobidModels.modelFor(newModelName);
		}
	}

	/**
	 * @param beforeString
	 * @param renameTo
	 * @param move
	 *            true=move, false=copy
	 * @throws Exception
	 */
	public static void renameModels(String beforeString, String renameTo, boolean move) throws Exception
	{
		GrobidDemo.init();

		File directory = new File(GrobidProperties.get_GROBID_HOME_PATH(), GrobidProperties.FOLDER_NAME_MODELS);
		Collection<File> files = FileUtils.listFiles(directory, new IOFileFilter()
		{

			@Override
			public boolean accept(File dir, String name)
			{
				if(dir.getName().equals("table"))
				{
					return true;
				}
				else
					// if(dir.getName().equals("all")) // folder in patent dir
					// {
					// return false;
					// }
					// else
					return name.equals(beforeString);
			}

			@Override
			public boolean accept(File file)
			{
				return accept(file.getParentFile(), file.getName());
			}
		}, TrueFileFilter.TRUE);
		// File[] files = directory.listFiles(new FilenameFilter()
		// {
		// @Override
		// public boolean accept(File dir, String name)
		// {
		// return name.equals(beforeString);
		// }
		// });
		for(File file : files)
		{
			File newFile = new File(file.getParent(), renameTo);
			System.out.println(newFile);
			if(move)
				file.renameTo(newFile);
			else
				FileUtils.copyFile(file, newFile);
		}
		MockContext.destroyInitialContext();
	}
}
