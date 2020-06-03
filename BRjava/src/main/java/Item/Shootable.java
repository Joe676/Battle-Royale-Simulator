package Item;

/**
 * Interface for items that can shoot
 * @author Jozef Bossowski
 *
 */
public interface Shootable {
	/**
	 * Manages what happens when the weapon shoots
	 */
	public void shoot();
	/**
	 * Manages what happens when a weapon is reloaded
	 */
	public void reload();
}
