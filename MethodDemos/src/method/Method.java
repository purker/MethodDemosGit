package method;

import java.io.File;

import config.Config;

public enum Method
{
	CERMINE("cermine", Config.cermineOutputDir),
	GROBID("grobid", Config.grobidOutputDir),
	PARSCIT("parscit", Config.parsCitOutputDir),
	PDFX("pdfx", Config.pdfxOutputDir),
	ALL("all", null);

	private static String toReplace = "<method>";
	private String name;
	private File resultDirectory;

	private Method(String name, File resultDirectory)
	{
		this.name = name;
		this.resultDirectory = resultDirectory;
	}

	public String getName()
	{
		return name;
	}

	public File getResultDirectory()
	{
		return resultDirectory;
	}

	public String replace(String file)
	{
		return file.replace(toReplace, name);
	}

}
