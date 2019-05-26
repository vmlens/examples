package com.vmlens.examples.concurrentHashMap;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

import com.vmlens.annotation.Interleave;

public class TestUpdateWrong {
	private final ConcurrentHashMap<Integer,Integer>  map = new  ConcurrentHashMap<Integer,Integer>();
	@Interleave(group=TestUpdateWrong.class,threadCount=2)
	public void update()  {
			Integer result = map.get(1);		
			if( result == null )  {
				map.put(1, 1);
			}
			else	{
				map.put(1, result + 1 );
			}	
	}
	@Test
	public void testUpdate() throws InterruptedException	{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute( () -> { update();   }  );
		executor.execute( () -> { update();   }  );
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
	}	
	@After
	public void checkResult() {
		assertEquals( 2 , map.get(1).intValue() );
	}	
}
