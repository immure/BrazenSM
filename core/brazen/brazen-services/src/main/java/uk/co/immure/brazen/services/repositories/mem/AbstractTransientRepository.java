package uk.co.immure.brazen.services.repositories.mem;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;

<<<<<<< HEAD:core/brazen/brazen-services/src/main/java/co/uk/immure/brazen/services/repositories/mem/AbstractTransientRepository.java
import co.uk.immure.brazen.services.repositories.Repository;
=======
import uk.co.immure.brazen.services.repositories.Repository;

>>>>>>> 791429bf15e224780e44d5a8e5666248eee3da0f:core/brazen/brazen-services/src/main/java/uk/co/immure/brazen/services/repositories/mem/AbstractTransientRepository.java


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
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		
	}

}
