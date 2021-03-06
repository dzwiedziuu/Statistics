package pl.aniedzwiedz.statistics.container.percentile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.aniedzwiedz.statistics.container.utils.lists.SortedList;
import pl.aniedzwiedz.statistics.container.utils.lists.SortedUnit;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class PercentileList
{
	private static final Logger logger = LoggerFactory.getLogger(PercentileList.class);

	private Set<Percentile> percentiles = new TreeSet<>();
	private SortedList sortedList;

	public PercentileList(double... percentiles)
	{
		this(SortedList.DEFAULT_LIMIT, percentiles);
	}

	public PercentileList(int listSize, double... percentiles)
	{
		sortedList = new SortedList(listSize);
		for(double v : percentiles)
			addPercentile(v);
	}

	public void addPercentile(double percentileValue)
	{
		if(percentileValue == 1.0)
			return;
		if(percentileValue < 0 || percentileValue >= 1.0)
			throw new RuntimeException("Percentile can be only higher or equal to 0 and lower or equal to than 1!");
		percentiles.add(new Percentile(sortedList, percentileValue));
	}

	public void add(double value, long weight)
	{
		SortedUnit unitToAdd = SortedUnit.create(value, weight);
		if(logger.isTraceEnabled())
			logger.trace("Adding unit [{}]", unitToAdd);
		SortedUnit removedSortedUnit = sortedList.removeOldestValueIfNecessary();
		for(Percentile p : percentiles)
		{
			if(removedSortedUnit != null)
			{
				p.switchPointerIfRemoved(removedSortedUnit);
				if(removedSortedUnit.getValue() < p.getPointerValue())
					p.increaseTotalWeight(-removedSortedUnit.getWeight());
			}
			//			if(unitToAdd.getValue() < p.getPointerValue()) {
			p.moveCursorBack(unitToAdd.getWeight() + (removedSortedUnit == null ? 0 : removedSortedUnit.getWeight()));
			//            }
		}
		sortedList.addValue(unitToAdd);
		for(Percentile p : percentiles)
		{
			if(unitToAdd.getValue() < p.getPointerValue())
				p.increaseTotalWeight(unitToAdd.getWeight());
			p.update();
		}
	}

	/*
		returns percentile value or if percentile is between two values, returns higher
	*/
	public Double getPercentileValue(Double d)
	{
		if(sortedList.isEmpty())
			return null;
		if(d == 1.0)
			return sortedList.getLast().getValue();
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

	public int size()
	{
		return sortedList.size();
	}
}
