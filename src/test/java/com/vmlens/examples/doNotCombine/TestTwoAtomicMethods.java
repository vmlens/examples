package com.vmlens.examples.doNotCombine;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ConcurrentHashMap;


import org.junit.After;
import org.junit.Test;

import com.vmlens.annotation.Interleave;

public class TestTwoAtomicMethods {
	private final ConcurrentHashMap<Integer,Integer>  map = new  ConcurrentHashMap<Integer,Integer>();
	@Interleave
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
		Thread first  = new Thread( () -> { update();   }  );
		Thread second = new Thread( () -> { update();   }  );
		first.start();
		second.start();
		first.join();
		second.join();
		
	}	
	@After
	public void checkResult() {
		assertEquals( 2 , map.get(1).intValue() );
	}	
}
