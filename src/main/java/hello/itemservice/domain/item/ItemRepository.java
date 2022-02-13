package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence =0L;

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}


//(실무에서) 동시에 여러 쓰레드가 접근할 때는 HashMap 쓰면 안된다. ConcurrentHashMap 을 써야한다.
//sequence도 동시에 접근하면 값이 꼬일 수 있기 때문에 auto로 값을 주는 것을 사용해야한다.
//return new ArrayList<>(store.values());  -> 한번 감싸서 반환하면 store 에 영향을 안 주게 되므로 감싼 것이다. 안 감싸도 괜찮긴하다.