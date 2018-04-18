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
			System.err.println("aborting program after failure");
			System.exit(-1);
		}
	}
}
