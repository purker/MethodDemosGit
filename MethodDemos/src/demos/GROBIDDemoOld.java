package demos;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;

/**
 * http://grobid.readthedocs.io/en/latest/Grobid-java-library/
 *
 */
public class GROBIDDemoOld
{

	public static void main(String[] args)
	{
		// String pdfPath = "D:/TU/Masterarbeit/Papers/Methoden/ParsCit an Open-source CRF reference string parsing package.pdf";
		String resultPath = "D:/output/Grobid";
		String directoryPath = "D:/TU/Masterarbeit/Papers/Methoden/";
		boolean consolidateCitations = false;
		boolean consolidateHeader = false;
		try
		{
			String homeDir = System.getProperty("user.home");
			System.out.println(homeDir);
			String directory = "C:/Users/Angela/git/";
			String pGrobidHome = directory + "grobid/grobid-home";
			String pGrobidProperties = directory + "grobid/grobid-home/config/grobid.properties";

			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);
			GrobidProperties.getInstance();

			System.out.println(">>>>>>>> GROBID_HOME=" + GrobidProperties.get_GROBID_HOME_PATH());

			Engine engine = GrobidFactory.getInstance().createEngine();

			// BiblioItem resHeader = new BiblioItem();
			// String tei = engine.processHeader(pdfPath, false, resHeader);
			// System.out.println(resHeader);

			int tei = engine.batchProcessFulltext(directoryPath, resultPath, consolidateHeader, consolidateCitations);
			System.out.println(tei);
		}
		catch(Exception e)
		{
			// If an exception is generated, print a stack trace
			e.printStackTrace();
		}
		finally
		{
			try
			{
				MockContext.destroyInitialContext();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}

}
