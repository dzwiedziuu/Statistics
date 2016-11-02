package pl.dzwiedziuu.statistics.container.utils.lists;

public class TechnicalSortedUnit extends SortedUnit
{
	public TechnicalSortedUnit(long id, double value)
	{
		super(id, value, 0L);
	}

	@Override
	public boolean isTechnical()
	{
		return true;
	}
}
