import static org.junit.Assert.*;

import org.junit.Test;


public class AlienShotTest {

	@Test
	public void testMove() {
		BattleFieldElement element = new AlienShot(3,1);
		assertEquals("at the start x must be: 1", 1, element.getX());
		assertEquals("at the start x must be: 3", 3, element.getY());
		element.move(4,5);
		assertEquals("After move x must be: 1", 1, element.getX());
		assertEquals("After move x must be: 4", 4, element.getY());
	}

	@Test
	public void testGetXOffset() {
		AlienShot as = new AlienShot(2,1);
		assertEquals("X offset can be 0 in Empty", 0, as.getXOffset());
	}

	@Test
	public void testGetYOffset() {
		AlienShot as = new AlienShot(2,1);
		BattleField bf = null;
		try {
			bf = new BattleField("es-in.txt");
		} catch (IllegalElementException e) {
			e.printStackTrace();
		} catch (IllegalPositionException e) {
			e.printStackTrace();
		}
		bf.setRows(2);
		assertEquals("Verify is return is correct", 2-(2+1), as.getYOffset());
		
	}

	@Test
	public void testToString() {
		AlienShot as = new AlienShot(2,1);
		assertEquals("String returned must be: S", "S", as.toString());
	}

	@Test
	public void testAlienShot() {
		BattleFieldElement element = new AlienShot(3,1);
		assertEquals("The v must be: 3", 3, element.getY());
		assertEquals("The h must be: 1", 1, element.getX());
		AlienShot as = new AlienShot(2,1);
		assertEquals("The moved must be 0 after new declaration of AlienShot", 0, as.getMoved());
	}

	@Test
	public void testGetSetMoved() {
		AlienShot as = new AlienShot(2,1);
		as.setMoved(10);
		assertEquals("The moved must be 10", 10, as.getMoved());
	}

}
