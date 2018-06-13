package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import config.Config;

public class FormatingUtil
{
	public static String formatDouble(Double value)
	{
		if(value.isNaN())
		{
			return "NAN";
		}
		return Config.decimalFormatter.format(value);
	}

	public static String roundAndFormat(Double value)
	{
		return formatDouble(round(value));
	}

	public static String roundAndFormatX100(Double value)
	{
		return roundAndFormat(value * 100);
	}

	public static Double round(Double value)
	{
		// if(places < 0) throw new IllegalArgumentException();
		if(value.isNaN()) return value;

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(Config.decimalPlaces, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static Double x100AndRound(double value)
	{
		return round(value * 100);
	}
}
