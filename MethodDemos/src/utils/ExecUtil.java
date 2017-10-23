package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class ExecUtil
{
	public static String exec(List<String> command) throws IOException
	{
		return execInWorkingDir(null, command);
	}

	public static String execInWorkingDir(File workingDirectory, List<String> command) throws IOException
	{
		Process p = null;

		// System.out.println("cd " + workingDirectory);
		// System.out.println(command.stream().collect(Collectors.joining(" ")));

		ProcessBuilder pb = new ProcessBuilder(command);
		if(workingDirectory != null) pb.directory(workingDirectory);
		p = pb.start();

		return IOUtils.toString(p.getErrorStream(), StandardCharsets.UTF_8);
	}

	private void worksbutnotwitherroroutput(File workingDirectory, String command, String... addArguments)
	{
		// CommandLine commandLine = CommandLine.parse(command);
		// commandLine.addArguments(addArguments);
		// System.out.println(commandLine);
		//
		// try (BufferedOutputStream outputStream = new BufferedOutputStream())
		// {
		// PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
		// DefaultExecutor exec = new DefaultExecutor();
		// exec.setStreamHandler(streamHandler);
		// if(workingDirectory != null)
		// {
		// exec.setWorkingDirectory(workingDirectory);
		// }
		// exec.execute(commandLine);
		//
		// return outputStream.toString();
		//
		// }
	}

	private static void oldandfuntioning() throws Exception
	{
		// String output = ExecUtil.execInWorkingDir("citeExtract.pl", new File("C:/Users/Angela/git/ParsCit/bin/"));
		// System.out.println(output);

		// ProcessBuilder pb = new ProcessBuilder("cmd.exe /c start C:/Users/Angela/git/ParsCit/bin/citeExtract.pl");
		// pb.directory(new File("C:/Users/Angela/git/ParsCit/bin/"));

		// Map<String, String> env = pb.environment();
		// for(String key : env.keySet())
		// System.out.println(key + ": " + env.get(key));

		ProcessBuilder pb = new ProcessBuilder("cmd.exe /c C:/Users/Angela/git/ParsCit/bin/citeExtract.pl C:/Users/Angela/git/ParsCit/demodata/sample1.txt");
		// ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "citeExtract.pl", "D:/output/GroundTruth/BeiträgeausTagungsbänden/TUW-137078.pdf");
		// Map<String, String> envs = pb.environment();
		// System.out.println(envs.get("Path"));
		// envs.put("Path", "C:/Users/Angela/git/ParsCit/bin/");
		// pb.redirectErrorStream();
		pb.directory(new File("C:/Users/Angela/git/ParsCit/bin"));
		System.out.println(pb.directory());

		Process process = pb.start();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		System.out.println(IOUtils.toString(reader));
		StringBuilder builder = new StringBuilder();
		String line = null;
		while((line = reader.readLine()) != null)
		{
			System.out.println(line);
		}
		String result = builder.toString();
		System.out.println("1" + result);

		BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		System.out.println(IOUtils.toString(reader2));
		StringBuilder builder2 = new StringBuilder();
		String line2 = null;
		while((line2 = reader2.readLine()) != null)
		{
			System.out.println(line2);
		}
		String result2 = builder2.toString();
		System.out.println("2" + result2);
	}

	public static void main(String[] args) throws Exception
	{
		oldandfuntioning();
	}

}
