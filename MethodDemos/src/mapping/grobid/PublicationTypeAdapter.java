package mapping.grobid;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import mapping.result.PublicationType;

public class PublicationTypeAdapter extends XmlAdapter<String, PublicationType>
{
	/*
	 * 	https://tei-c.org/release/doc/tei-p5-doc/de/html/ref-title.html
a
    (unselbständig) der Titel gehört zu einer unselbständigen Publikation, wie einem Artikel, Gedicht oder einem anderen Werk, das als Teil einer umfangreicheren Einheit publiziert wurde.
m
    (Monografie) der Titel bezieht sich auf Monografien wie z.B. ein Bücher oder andere selbständige Publikationen, also auch auf einzelne Bände in einem mehrbändigen Werk.
j
    (Zeitschrift) der Titel bezieht sich auf jede Art fortlaufender oder periodischer Veröffentlichungen wie z. B. Zeitschriften, Magazine oder Zeitungen.
s
    (Reihe) der Titel bezeichnet eine Reihe von ansonsten selbständig publizierten Veröffentlichungen, wie z. B. eine Buchreihe.
u
    (unveröffentlicht) der Titel bezieht sich auf unveröffentliches Material (incl. universitäre Qualifikationsarbeiten, soweit sie nicht von einem Verlag veröffentlicht worden sind).
	 */
	
	private static Map<String, PublicationType> stringToType = new HashMap<String, PublicationType>();
	
	{
		stringToType.put("a", PublicationType.BEITRAEGE_AUS_TAGUNGSBAENDEN);
		stringToType.put("m", PublicationType.BUCHBEITRAEGE);
		stringToType.put("j", PublicationType.ZEITSCHRIFTENARTIKEL);
		stringToType.put("s", PublicationType.BUCHBEITRAEGE);
		stringToType.put("u", PublicationType.DIPLOM_UND_MASTERARBEITEN);
	}

	/*
	 * Java => XML Given the unmappable Java object, return the desired XML representation.
	 */
	@Override
	public String marshal(PublicationType publicationType) throws Exception
	{
		// not implemented
		return null;
	}

	/*
	 * XML => Java Given an XML string, use it to build an instance of the unmappable class.
	 */
	@Override
	public PublicationType unmarshal(String string) throws Exception
	{
		if(string == null) {
			return null;
		} else {
			PublicationType publicationType = stringToType.get(string);
			return publicationType;
		}
	}

}