package mapping;

import java.util.ListIterator;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import config.Config;
import mapping.result.Section;

public class SectionAdapter extends XmlAdapter<Section, Section>
{

	/*
	 * Java => XML Given the unmappable Java object, return the desired XML representation.
	 */
	@Override
	public Section marshal(Section section) throws Exception
	{
		for(ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
		{
			String referenceId = iterator.next();

			iterator.set(Config.referencePrefix + referenceId);
		}
		return section;
	}

	/*
	 * XML => Java Given an XML string, use it to build an instance of the unmappable class.
	 */
	@Override
	public Section unmarshal(Section section) throws Exception
	{
		for(ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
		{
			String referenceId = iterator.next();

			iterator.set(referenceId.replace(Config.referencePrefix, ""));
		}
		return section;
	}

}