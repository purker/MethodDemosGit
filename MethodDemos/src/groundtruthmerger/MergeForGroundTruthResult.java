package groundtruthmerger;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapping.result.Publication;
import utils.XStreamUtil;

/**
 * @author Angela
 *
 *         For creation of groundtruth
 */
public class MergeForGroundTruthResult
{

	public static <K> void main(String[] args)
	{
		Map<String, List<File>> groundtruth = new HashMap<>();

		List<String> ids = Arrays.asList("TUW-137078", "TUW-138011", "TUW-138544", "TUW-138547", "TUW-139781", "TUW-140047", "TUW-140048", "TUW-140229", "TUW-140253", "TUW-140867", "TUW-140983", "TUW-141024", "TUW-141065", "TUW-141121", "TUW-141140", "TUW-168222", "TUW-169511", "TUW-172697", "TUW-177140", "TUW-179146", "TUW-180162", "TUW-181199", "TUW-185321", "TUW-185441", "TUW-186227", "TUW-189842", "TUW-191977", "TUW-192724", "TUW-194660", "TUW-197422", "TUW-198400", "TUW-198401", "TUW-198405", "TUW-198408", "TUW-201066", "TUW-201160", "TUW-201167", "TUW-202822", "TUW-202824", "TUW-203409", "TUW-203924", "TUW-204709", "TUW-205557", "TUW-213513", "TUW-216744", "TUW-217690", "TUW-217971", "TUW-223906", "TUW-226000", "TUW-233317", "TUW-233657", "TUW-236063", "TUW-236120", "TUW-237297", "TUW-240858", "TUW-247301", "TUW-247741", "TUW-252847", "TUW-255712", "TUW-256654", "TUW-257870", "TUW-141618", "TUW-176087", "TUW-223973", "TUW-231707", "TUW-245336", "TUW-257397", "TUW-139761", "TUW-139769", "TUW-139785", "TUW-139794", "TUW-140533", "TUW-140895", "TUW-141758", "TUW-168482", "TUW-170329", "TUW-175428", "TUW-194085", "TUW-200748", "TUW-202034", "TUW-221215", "TUW-226016", "TUW-138447", "TUW-139299", "TUW-139994", "TUW-140308", "TUW-141336", "TUW-174216", "TUW-182414", "TUW-182899", "TUW-191715", "TUW-194561", "TUW-197852", "TUW-200950", "TUW-200959", "TUW-204724", "TUW-205933", "TUW-228620", "TUW-245799", "TUW-251544");

		for(String id : ids)
		{
			groundtruth.put(id, getAllResultFiles(id));
		}

		Publication mergedPublication = new Publication();
		int x = 0;
		for(List<File> files : groundtruth.values())
		{

			for(File file : files)
			{
				if(x == 0)
				{
					try
					{
						Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
						merge(mergedPublication, publication);
					}
					catch(Exception e)
					{
						System.err.println("error in file " + file);
						e.printStackTrace();
						return;
					}
					x++;
				}
			}
		}

	}

	private static void merge(Publication publication, Publication publicationToMerge)
	{
		if(publication == null)
		{
			publication = publicationToMerge;
		}
		else
		{
			try
			{
				for(PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(Publication.class).getPropertyDescriptors())
				{

					// propertyEditor.getReadMethod() exposes the getter
					// btw, this may be null if you have a write-only property
					Method method = propertyDescriptor.getReadMethod();
					if(method != null && !"class".equals(propertyDescriptor.getName()))
					{
						System.out.println(method);
						Object o = method.invoke(publication);
						Object o2 = method.invoke(publicationToMerge);
						System.out.println(o);
						System.out.println(o2);
					}
				}
			}
			catch(Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static List<File> getAllResultFiles(String id)
	{
		String path = "D:/output/methods/";

		List<File> files = new ArrayList<>();
		// files.add(new File("D:/output/GroundTruthNoSubDir/" + id + "-publicationdb.xml"));
		files.add(new File(path + "grobid", "grobid-" + id + "-xstream.xml"));
		files.add(new File(path + "cermine", "cermine-" + id + "-xstream.xml"));
		files.add(new File(path + "parscit", "parscit-" + id + "-xstream.xml"));
		files.add(new File(path + "pdfx", "pdfx-" + id + "-xstream.xml"));

		for(File file : files)
		{
			if(!file.exists()) System.out.println(file);
		}

		return files;
	}
}
