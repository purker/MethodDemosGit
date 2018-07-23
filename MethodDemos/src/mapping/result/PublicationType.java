package mapping.result;

import java.util.Arrays;
import java.util.List;

public enum PublicationType implements Label
{
	BEITRAEGE_AUS_TAGUNGSBAENDEN(
	        "Beiträge in Tagungsbänden",
	        "Beitrag in Tagungsband",
	        "Beitrag in CD- oder Web-Tagungsband",
	        "Vortrag mit Tagungsband",
	        "Posterpräsentation mit Tagungsband",
	        "Vortrag mit CD- oder Web-Tagungsband",
	        "Posterpräsentation mit CD- oder Web-Tagungsband"),
	BUCHBEITRAEGE("Buchbeiträge", "Buchbeitrag"),
	DIPLOM_UND_MASTERARBEITEN("Diplom- oder Masterarbeiten", "Diplom- oder Master-Arbeit"),
	ZEITSCHRIFTENARTIKEL("Zeitschriftenartikel", "Zeitschriftenartikel");

	private String label; // pretty print for csv, ...
	private List<String> names; // for mapping of Publikationsdatenbank entries

	private PublicationType(String label, String... names)
	{
		this.label = label;
		this.names = Arrays.asList(names);
	}

	@Override
	public String getLabel()
	{
		return label;
	}

	public PublicationType nameOf(String name)
	{
		return Arrays.stream(PublicationType.values()).filter(e -> e.names.contains(name)).findFirst().orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", name)));
	}

}
