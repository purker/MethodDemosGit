package utils;

import java.io.PrintStream;

public class FailureUtil
{
	public static void failureExit(Throwable ex, PrintStream errorWriter, String string, boolean exitProgram)
	{
		ex.printStackTrace(errorWriter);
		errorWriter.println(string);
		if(exitProgram)
		{
			exit("aborting program after failure");
		}
	}

	public static void exit(String string)
	{
		System.err.println("aborting program after failure");
		System.exit(-1);

	}
}
