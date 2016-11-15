package pl.aniedzwiedz.statistics.container.utils.lists;

public class SortedUnit implements Comparable<SortedUnit>
{
	private static long idSeq = 0L;

	private long id;
	SortedUnit prev;
	SortedUnit next;
	private double value;
	private long weight;

	public static SortedUnit create(double value, long weight)
	{
		return create(createNextId(), value, weight);
	}

	public static SortedUnit create(long id, double value, long weight)
	{
		return new SortedUnit(id, value, weight);
	}

	protected synchronized static long createNextId()
	{
		return ++idSeq;
	}

	SortedUnit(long id, double value, long weight)
	{
		super();
		this.value = value;
		this.weight = weight;
		this.id = id;
	}

	public double getValue()
	{
		return value;
	}

	public long getWeight()
	{
		return weight;
	}

	void setAsNext(SortedUnit sortedListNode)
	{
		sortedListNode.prev = this;
		sortedListNode.next = this.next;
		this.next = sortedListNode;
	}

	void remove()
	{
		if(this.prev != null)
			this.prev.next = this.next;
		if(this.next != null)
			this.next.prev = this.prev;
	}

	public SortedUnit getNext()
	{
		return next;
	}

	public SortedUnit getPrev()
	{
		return prev;
	}

	@Override
	public int compareTo(SortedUnit o)
	{
		return Double.compare(value, o.value);
	}

	@Override
	public String toString()
	{
		return "SortedUnit [id=" + id + ", value=" + value + ", weight=" + weight + ", prev=" + (prev == null ? null : prev.id) + ", next=" + (next == null ? null : next.id) + "]";
	}

	public Long getId()
	{
		return id;
	}

	public boolean isTechnical()
	{
		return false;
	}
}
