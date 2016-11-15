package pl.dzwiedziuu.statistics.container.utils.lists;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SortedList
{
	private class TechnicalSortedUnit extends SortedUnit
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

	private static final long START_NODE_ID = 0;
	public static final int DEFAULT_LIMIT = 10000;

	private TreeMap<Double, LinkedList<SortedUnit>> map = new TreeMap<>();
	private LinkedList<SortedUnit> list = new LinkedList<>();
	private long totalWeight;
    private int limit;

    public SortedList()
    {
        this(DEFAULT_LIMIT);
    }

	public SortedList(int limit)
	{
        this.limit = limit;
		addValue(new TechnicalSortedUnit(START_NODE_ID, -Double.MAX_VALUE));
		list.clear();
		totalWeight = 0L;
	}

	public SortedUnit getFirst()
	{
		return map.firstEntry().getValue().getFirst();
	}

	public SortedUnit getLast()
	{
		return map.lastEntry().getValue().getLast();
	}
	public long getTotalWeight()
	{
		return totalWeight;
	}

	public void addValue(double value, long weight)
	{
		addValue(SortedUnit.create(value, weight));
	}

	private void putToMap(SortedUnit sortedUnit)
	{
		LinkedList<SortedUnit> sul = map.get(sortedUnit.getValue());
		if(sul == null)
		{
			sul = new LinkedList<>();
			map.put(sortedUnit.getValue(), sul);
		}
		sul.add(sortedUnit);
	}

	private SortedUnit getLowerOrEqual(SortedUnit sortedUnit)
	{
		Entry<Double, LinkedList<SortedUnit>> entry = map.floorEntry(sortedUnit.getValue());
		return entry != null ? entry.getValue().getLast() : null;
	}

	private SortedUnit getGreater(SortedUnit sortedUnit)
	{
		Entry<Double, LinkedList<SortedUnit>> entry = map.higherEntry(sortedUnit.getValue());
		return entry != null ? entry.getValue().getFirst() : null;
	}

	public void addValue(SortedUnit current)
	{
		// removeOldestValueIfNecessary();
		totalWeight += current.getWeight();
		SortedUnit prev = getLowerOrEqual(current);
		putToMap(current);
		SortedUnit next = getGreater(current);
		current.prev = prev;
		if(prev != null)
		{
			current.next = prev.next;
			prev.next = current;
		}
		if(next != null)
		{
			next.prev = current;
			current.next = next;
		}
		list.addLast(current);
	}

	public SortedUnit removeOldestValueIfNecessary()
	{
		if(list.size() < limit)
			return null;
		SortedUnit sortedUnit = list.removeFirst();
		List<SortedUnit> list = map.get(sortedUnit.getValue());
		list.remove(sortedUnit);
		if(list.isEmpty())
			map.remove(sortedUnit.getValue());
		totalWeight -= sortedUnit.getWeight();
		sortedUnit.remove();
		return sortedUnit;
	}

	@Override
	public String toString()
	{
		return "LIST:" + Arrays.toString(list.stream().map(s -> "\n\t" + s.toString()).toArray());
	}

	public String toStringSorted()
	{
		return "LIST:" + Arrays.toString(list.stream().sorted().map(s -> "\n\t" + s.toString()).toArray());
	}
}
