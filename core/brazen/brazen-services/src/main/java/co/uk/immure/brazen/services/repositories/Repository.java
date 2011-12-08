package co.uk.immure.brazen.services.repositories;

public interface Repository<T,ID> {
	
	public T get(ID id);
	public T save(T object);
	public void lock(ID id);
	public void unlock(ID id);
	public ID createUniqueId();

}
