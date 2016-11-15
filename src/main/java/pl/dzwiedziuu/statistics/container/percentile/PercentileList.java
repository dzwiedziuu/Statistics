package pl.dzwiedziuu.statistics.container.percentile;

import pl.dzwiedziuu.statistics.container.utils.lists.SortedList;
import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class PercentileList
{
	private Set<Percentile> percentiles = new TreeSet<>();
	private SortedList sortedList = new SortedList();

	public PercentileList(double... percentiles)
	{
		for(double v : percentiles)
			addPercentile(v);
	}

	public void addPercentile(double percentileValue)
	{
		percentiles.add(new Percentile(sortedList, percentileValue));
	}

	public void add(double value, long weight)
	{
		SortedUnit unitToAdd = SortedUnit.create(value, weight);
		SortedUnit removedSortedUnit = sortedList.removeOldestValueIfNecessary();
		long valueToMoveBack = unitToAdd.getWeight() + (removedSortedUnit == null ? 0 : removedSortedUnit.getWeight());
		for(Percentile p : percentiles)
			p.moveCursorBack(valueToMoveBack);
		sortedList.addValue(unitToAdd);
		for(Percentile p : percentiles)
			p.update();
	}

	/*
		returns percentile value or if percentile is between two values, returns higher
	*/
	public Double getPercentileValue(Double d)
	{
		for(Percentile p : percentiles)
			if(p.getValue() == d)
				return p.getPercentile();
		throw new RuntimeException("Could not found percentile " + d);
	}

	@Override
	public String toString()
	{
		return "Percentiles:" + Arrays.toString(Arrays.asList(percentiles).stream().map(s -> "\n\t" + s.toString()).toArray()) + "\n" + sortedList.toStringSorted();
	}
}
