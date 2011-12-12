package co.uk.immure.brazen.services.repositories;

import java.util.Collection;
import java.util.List;

public interface Repository<T,ID> {
	
	public T get(ID id);
	public T save(T object);
	public void lock(ID id);
	public void unlock(ID id);
	public ID createUniqueId();
	public Collection<T> getAll();

}
