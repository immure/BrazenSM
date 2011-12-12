package co.uk.immure.brazen.services.repositories.mem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.immure.brazen.services.repositories.Repository;


public abstract class AbstractTransientRepository<T, ID> implements Repository<T, ID> {

	protected Map<ID, T> repository = new HashMap<ID, T>();
	
	@Override
	public T get(ID id) {
		return repository.get(id);
	}
	
	public T save(T object) {
		repository.put(getId(object), object);
		return object;
	};

	@Override
	public void lock(ID id) {}
	
	public void unlock(ID id) {};
	
	public abstract ID getId(T object);
	
	@Override
	public Collection<T> getAll() {
		return repository.values();
	}

}
