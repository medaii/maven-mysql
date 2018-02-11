package loko.db.executor.impl;


import org.hibernate.SessionFactory;

public interface IFDBHibernateSqlExecutor {

	/**
	 * Vraci instanci SessionFactory
	 */
	SessionFactory getSessionFactory();

	/**
	 * Vrací poøadovaný øádek jako objekt
	 * 
	 * @param id øádku v DB
	 * @param tøida entity
	 * @return vrací objekt
	 */
	<T> T getObject(int id, Class<?> trida);

	/**
	 * Pøidání nového øádku do tabulky
	 * 
	 * @param object -  entita, která bude uložená do tabulky v DB
	 * @return id - vracení id novì vytvoøeného záznamu v DB
	 */
	<T> int insertObject(T object);

	/**
	 * Aktualizace hodnot v øádku v DB
	 * 
	 * @param object -  entita, dle ní se má zmìnit záznam v DB
	 * @return - 
	 */
	//TODO zmìnit na void
	<T> int updateObject(T object);

	/**
	 * 
	 * Vymazání entity z DB
	 * 
	 * @param id- id øádku v DB
	 * @param objectClass  -  v DB
	 */
	<T> void deleteObject(int id, Class<T> objectClass);


}