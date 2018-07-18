package method;

import java.io.File;

import config.Config;

public enum Method
{
	CERMINE("cermine", "Cermine", Config.cermineOutputDir),
	GROBID("grobid", "Grobid", Config.grobidOutputDir),
	PARSCIT("parscit", "ParsCit", Config.parsCitOutputDir),
	PDFX("pdfx", "PDFX", Config.pdfxOutputDir),
	ALL("all", "All", null);

	private static String toReplace = "<method>";
	private String name;
	private String printName;
	private File resultDirectory;

	private Method(String name, String printName, File resultDirectory)
	{
		this.name = name;
		this.printName = printName;
		this.resultDirectory = resultDirectory;
	}

	public String getName()
	{
		return name;
	}

	public String getPrintName()
	{
		return printName;
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
