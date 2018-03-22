package factory;

import java.util.Arrays;
import java.util.Date;

import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.PublicationType;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.Section;

public class PublicationFactory
{
	public static Publication createPublication()
	{
		Publication publication = new Publication();

		publication.setTitle("title");
		publication.setAbstractText("This is the abstract test");
		publication.setAbstractTextGerman("Das ist der Text der Kurzfassung auf deutsch.");
		publication.setKeywords(Arrays.asList("keyword1", "keyword2", "keyword3"));
		publication.setPublicationType(PublicationType.DIPLOM_UND_MASTERARBEITEN);

		Author author = createAuthor();
		Affiliation affiliation = createAffiliation();
		author.setAffiliations(Arrays.asList(affiliation));

		publication.setAuthors(Arrays.asList(author));
		publication.setAffiliations(Arrays.asList(affiliation));
		publication.setSource("Source");
		publication.setVolume("volume");
		publication.setIssue("Issue");
		publication.setPageFrom("1");
		publication.setPageTo("5");
		publication.setPublicationYear("2017");
		publication.setSections(Arrays.asList(createSection()));
		publication.setReferences(Arrays.asList(createReference()));

		publication.setDoi("10.1000/182");
		publication.setUrl("http://dx.doi.org/10.1000/182");

		publication.setPublicationYear("1990");
		publication.setPublicationMonth("9");

		return publication;
	}

	public static Publication createPublication(String id)
	{
		Publication publication = createPublication();
		publication.setId(id);

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

	public static ReferenceAuthor createReferenceAuthor()
	{
		ReferenceAuthor author = new ReferenceAuthor();
		author.setFirstNames(Arrays.asList("first"));
		author.setLastName("last");

		return author;
	}

	public static Affiliation createAffiliation()
	{
		Affiliation affiliation = new Affiliation();
		affiliation.setInstitution("TU Wien");
		affiliation.setDepartment("Information Systems Engineering");
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
		section.setLevel("1.1");

		return section;
	}

	public static Reference createReference()
	{
		Reference reference = new Reference();
		reference.setAuthors(Arrays.asList(createReferenceAuthor()));
		reference.setTitle("referencetitle");
		reference.setPageFrom("1");
		reference.setPageTo("2");
		reference.setPublicationDate(new Date());

		return reference;
	}
}
