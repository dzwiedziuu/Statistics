package pl.dzwiedziuu.statistics.container.utils.lists;

public class TechnicalSortedUnit extends SortedUnit
{
	public TechnicalSortedUnit(double value)
	{
		super(createNextId(), value, Long.MAX_VALUE);
	}
}
