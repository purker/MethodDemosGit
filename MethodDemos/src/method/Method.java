package method;

import java.io.File;

import config.Config;

public enum Method
{
	CERMINE("cermine", Config.cermineOutputDir),
	GROBID("grobid", Config.grobidOutputDir),
	PARSCIT("parscit", Config.parsCitOutputDir),
	PDFX("pdfx", Config.pdfxOutputDir);

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
}
