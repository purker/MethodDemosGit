package mapping;

import mapping.result.AbstractPublication;

public abstract class Worker<T extends AbstractPublication<?, ?, ?, ?, ?>>
{
	protected abstract void doWork(T publication);
}
