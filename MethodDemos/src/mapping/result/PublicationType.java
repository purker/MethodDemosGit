package mapping.result;

import java.util.Arrays;
import java.util.List;

public enum PublicationType
{
	BEITRAEGE_AUS_TAGUNGSBAENDEN(
	        "Beitrag in Tagungsband",
	        "Beitrag in CD- oder Web-Tagungsband",
	        "Vortrag mit Tagungsband",
	        "Posterpräsentation mit Tagungsband",
	        "Vortrag mit CD- oder Web-Tagungsband",
	        "Posterpräsentation mit CD- oder Web-Tagungsband"),
	BUCHBEITRAEGE("Buchbeitrag"),
	DIPLOM_UND_MASTERARBEITEN("Diplom- oder Master-Arbeit"),
	ZEITSCHRIFTENARTIKEL("Zeitschriftenartikel");

	private List<String> names;

	private PublicationType(String... names)
	{
		this.names = Arrays.asList(names);
	}

	public PublicationType nameOf(String name)
	{
		return Arrays.stream(PublicationType.values()).filter(e -> e.names.contains(name)).findFirst().orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", name)));
	}

}
