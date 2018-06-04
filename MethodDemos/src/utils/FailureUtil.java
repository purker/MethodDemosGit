package utils;

import java.io.PrintStream;

public class FailureUtil
{
	public static void failureExit(Throwable ex, PrintStream errorWriter, String string, boolean exitProgram)
	{
		errorWriter.println(string);
		ex.printStackTrace(errorWriter);
		if(exitProgram)
		{
			exit("");
		}
	}

	public static void exit(String string)
	{
		System.err.println(string);
		System.err.println("aborting program after failure");
		System.exit(-1);

	}
}
