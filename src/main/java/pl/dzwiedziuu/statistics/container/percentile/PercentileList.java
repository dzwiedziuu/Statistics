package pl.dzwiedziuu.statistics.container.percentile;

import pl.dzwiedziuu.statistics.container.utils.lists.SortedList;
import pl.dzwiedziuu.statistics.container.utils.lists.SortedUnit;

import java.util.Arrays;

public class PercentileList
{
	private Percentile[] percentiles;
	private SortedList sortedList = new SortedList();

	public PercentileList(Percentile... percentiles)
	{
		this.percentiles = percentiles;
	}

	public PercentileList(double... percentiles)
	{
		Percentile[] pList = new Percentile[percentiles.length];
		for (int i = 0; i < percentiles.length; i++)
			pList[i] = new Percentile(percentiles[i]);
		this.percentiles = pList;
		initPercentiles();
	}

	private void initPercentiles()
	{
		for (Percentile p : percentiles)
			p.floorSortedUnit = sortedList.getFirst();
	}

	public void add(double value, long weight)
	{
		long totalWeight = sortedList.getTotalWeight();
		SortedUnit removedSortedUnit = sortedList.removeOldestValueIfNecessary();
		SortedUnit addedSortedUnit = sortedList.addValue(value, weight);
		for (int i = percentiles.length - 1; i >= 0; i--) // if higher percentile is not updated, then lower is not too
			if (!percentiles[i].update(removedSortedUnit, addedSortedUnit, totalWeight))
				break;
	}

	public Double getPercentileValue(Double d)
	{
		for (Percentile p : percentiles)
			if (p.getValue() == d)
				return p.getPercentile();
		throw new RuntimeException("Could not found percentile " + d);
	}

	@Override
	public String toString()
	{
		return sortedList.toStringSorted() + "\nPercentiles:" + Arrays.toString(Arrays.asList(percentiles).stream().map(s -> "\n\t" + s.toString()).toArray());
	}
}
