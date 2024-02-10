package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //class 전체에 해당
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional // saveItem에만 우선권 , 즉 오버라이딩 함
  public void saveItem(Item item){
    itemRepository.save(item);
  }

  public List<Item> findItem(){
    return itemRepository.findAll();
  }

  public Item findOne(Long itemId){
    return itemRepository.findOne(itemId);
  }


}
