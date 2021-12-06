package spec.repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import spec.entity.Item;

public class ItemRepository {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("itemUnit");
	private EntityManager entityManager = factory.createEntityManager();

	public List<Item> selectAllItems() {
		String query = "select i from Item i order by i.value";
		return entityManager.createQuery(query, Item.class).getResultList();
	}

	public int insertItem(Item item) {
		item.setId(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
		entityManager.getTransaction().begin();
		entityManager.persist(item);
		entityManager.getTransaction().commit();
		return 1;
	}

	public int updateItem(String id, Item item) {
		entityManager.getTransaction().begin();
		Item i = new Item();
		i.setId(id);
		i.setValue(item.getValue());
		entityManager.merge(i);
		entityManager.getTransaction().commit();
		return 1;
	}

	public int deleteItem(String id) {
		entityManager.getTransaction().begin();
		entityManager.remove(entityManager.find(Item.class, id));
		entityManager.getTransaction().commit();
		return 1;
	}

}
