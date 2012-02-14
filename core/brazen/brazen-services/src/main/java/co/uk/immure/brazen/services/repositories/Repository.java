package co.uk.immure.brazen.services.repositories;

import java.util.Collection;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;

public interface Repository<T,ID> extends Processor {
	
	public T get(ID id);
	public T save(T object);
	public void lock(ID id);
	public void unlock(ID id);
	public ID createUniqueId();
	public Collection<T> getAll();

}
