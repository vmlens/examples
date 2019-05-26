package com.vmlens.examples.concurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import org.junit.Test;

public class TestUpdateRecursive {
	private final ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();
	public TestUpdateRecursive() {
		map.put(1, 1);
		map.put(2, 2);
	}
	public void update12() {
		map.compute(1,  (key,value) ->   {  	
			   map.compute(2, ( k , v ) ->  {  return 2; }  );   
		       return 2;	   
		});
	}	
	public void update21() {
		map.compute(2,  (key,value) ->   {  	
			   map.compute(1, ( k , v ) ->  {  return 2; }  );   
		       return 2;	   
		});
	}
	@Test
	public void testUpdate() throws InterruptedException	{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute( () -> { update12();   }  );	
		executor.execute( () -> { update21();   }  );
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.MINUTES);
	}	
	
}
