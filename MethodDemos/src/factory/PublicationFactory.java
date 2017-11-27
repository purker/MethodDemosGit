package factory;

import java.util.Arrays;
import java.util.Date;

import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;

public class PublicationFactory
{
	public static Publication createPublication()
	{
		Publication publication = new Publication();

		publication.setTitle("title");
		publication.setAuthors(Arrays.asList(createAuthor()));
		publication.setAffiliations(Arrays.asList(createAffiliation()));
		publication.setSections(Arrays.asList(createSection()));
		publication.setReferences(Arrays.asList(createReference()));

		publication.setPublicationYear("1990");
		publication.setPublicationMonth("9");

		return publication;
	}

	private static Author createAuthor()
	{
		Author author = new Author();
		author.setFirstNames(Arrays.asList("first"));
		author.setLastName("last");
		author.setEmail("blub@cob");
		// TODO author.setType("author");

		return author;
	}

	public static Affiliation createAffiliation()
	{
		Affiliation affiliation = new Affiliation();
		affiliation.setInstitution("TU Wien");
		affiliation.setCountry("Austria");
		affiliation.setCountryCode("AT");

		return affiliation;
	}

	public static Section createSection()
	{
		Section section = new Section();
		section.setId("id");
		section.setTitle("sectiontitle");
		section.setReferenceIds(Arrays.asList("referenceID"));
		section.setType("ref");

		return section;
	}

	public static Reference createReference()
	{
		Reference reference = new Reference();
		reference.setTitle("referencetitle");
		reference.setPageFrom("1");
		reference.setPageTo("2");
		reference.setPublicationDate(new Date());

		return reference;
	}
}
