package mapping;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.PathTracker;
import com.thoughtworks.xstream.io.path.PathTrackingWriter;

import config.Config;
import mapping.result.Id;

public class IdConverter implements Converter
{
	@Override
	public boolean canConvert(Class type)
	{
		return type.equals(Id.class);
	}

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		String currentPath = getCurrentPath(writer);

		String prefix;
		if(currentPath.equals("/Publication/id"))
		{
			prefix = Config.publicationPrefix;
		}
		else
		{
			prefix = Config.referencePrefix;
		}
		Id id = (Id)value;
		// writer.startNode("id");
		writer.setValue(prefix + id.getId().toString());
		// writer.endNode();
	}

	private String getCurrentPath(HierarchicalStreamWriter writer)
	{
		Field field = ReflectionUtils.findField(PathTrackingWriter.class, "pathTracker");
		field.setAccessible(true);
		PathTracker pathTracker = (PathTracker)ReflectionUtils.getField(field, writer);
		String currentPath = pathTracker.getPath().toString();

		return currentPath;
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		Id id = null;
		try
		{
			// reader.moveDown();
			Integer idNumber = new Integer(reader.getValue().replace(Config.publicationPrefix, "").replaceAll(Config.referencePrefix, ""));
			id = new Id(idNumber);
			// reader.moveUp();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return id;
	}

}