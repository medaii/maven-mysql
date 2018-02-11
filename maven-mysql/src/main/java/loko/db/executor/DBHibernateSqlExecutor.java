package loko.db.executor;


import org.hibernate.SessionFactory;

/**
 * Rozhrani pro praci s DB pomoci Hibernate a nebo pred�n� SessionFactory pro
 * komunikaci s DB pomoci Hibernate
 * 
 * @author Erik Markovi�
 *
 */

public interface DBHibernateSqlExecutor {

	/**
	 * Vraci instanci SessionFactory
	 */
	SessionFactory getSessionFactory();

	/**
	 * Vrac� po�adovan� ��dek jako objekt
	 * 
	 * @param id ��dku v DB
	 * @param t�ida entity
	 * @return vrac� objekt
	 */
	<T> T getObject(int id, Class<?> trida);

	/**
	 * P�id�n� nov�ho ��dku do tabulky
	 * 
	 * @param object -  entita, kter� bude ulo�en� do tabulky v DB
	 * @return id - vracen� id nov� vytvo�en�ho z�znamu v DB
	 */
	<T> int insertObject(T object);

	/**
	 * Aktualizace hodnot v ��dku v DB
	 * 
	 * @param object -  entita, dle n� se m� zm�nit z�znam v DB
	 * @return - 
	 */
	<T> void updateObject(T object);

	/**
	 * 
	 * Vymaz�n� entity z DB
	 * 
	 * @param id- id ��dku v DB
	 * @param objectClass  -  v DB
	 */
	<T> void deleteObject(int id, Class<T> objectClass);

}