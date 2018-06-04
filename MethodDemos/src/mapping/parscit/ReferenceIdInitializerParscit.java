package mapping.parscit;

import java.util.Iterator;

import mapping.Worker;
import mapping.result.Id;
import mapping.result.Publication;
import mapping.result.Reference;
import utils.FailureUtil;

/**
 * Changes b0, b1, ... to ref1, ref2, ...
 *
 */
public class ReferenceIdInitializerParscit extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			int x = 1;
			for(Iterator<Reference> iterator = publication.getReferences().iterator(); iterator.hasNext();)
			{
				iterator.next().setId(new Id(x++));

			}
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.err, this.getClass().getName(), true);
		}
	}
}
