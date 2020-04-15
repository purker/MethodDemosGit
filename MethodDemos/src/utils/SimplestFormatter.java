package utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimplestFormatter extends Formatter
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
	 */
	@Override
	public String format(LogRecord record)
	{
		StringBuilder sb = new StringBuilder();
		// sb.append(record.getLevel()).append(':');
		sb.append(record.getMessage());
		sb.append("\r\n");
		return sb.toString();
	}
}