package mapping;

import mapping.result.Publication;
import utils.FailureUtil;

public abstract class Worker
{
	public abstract void doWork(Publication publication);

	public void doWorkCatchException(Publication publication)
	{
		try
		{
			doWork(publication);

		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.err, this.getClass().getName(), true);
		}
	}
}
