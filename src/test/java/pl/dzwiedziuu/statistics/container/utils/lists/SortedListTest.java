package pl.dzwiedziuu.statistics.container.utils.lists;

import org.junit.Test;

public class SortedListTest
{
	@Test
	public void test()
	{
		SortedList sortedList = new SortedList();

		System.out.println(sortedList.toStringSorted());
		sortedList.addValue(3.0, 1L);
		System.out.println(sortedList.toStringSorted());
		sortedList.addValue(4.0, 1L);
		sortedList.addValue(2.0, 1L);
		sortedList.addValue(2.5, 1L);
		sortedList.addValue(3.1, 1L);
		sortedList.addValue(2.5, 1L);
		// System.out.println(sortedList);
		sortedList.addValue(3.5, 1L);
		// System.out.println(sortedList);
		System.out.println(sortedList.toStringSorted());
	}
}
