package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import config.Config;

public class FormatingUtil
{
	public static String formatBigDecimal(BigDecimal value)
	{
		if(value == null) {
			return "not defined";
		}
		if (new BigDecimal(-1).compareTo(value) == 0) {
			return "NAN";
		}
		return Config.decimalFormatter.format(value);
	}

	public static String roundAndFormat(BigDecimal value)
	{
		return formatBigDecimal(round(value));
	}

	public static String roundAndFormatX100(BigDecimal value)
	{
		return roundAndFormat(value.multiply(new BigDecimal(100)));
	}

	public static BigDecimal round(BigDecimal value)
	{
		// if(places < 0) throw new IllegalArgumentException();
		if(new BigDecimal(-1).compareTo(value) == 0) {
			return value;
		}

		value = value.setScale(Config.decimalPlaces, RoundingMode.HALF_UP);
		return value;
	}

	public static BigDecimal x100AndRound(BigDecimal value)
	{
		return round(value.multiply(new BigDecimal(100)));
	}
}
