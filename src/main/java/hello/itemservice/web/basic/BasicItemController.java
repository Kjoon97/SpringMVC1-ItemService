package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor  // final이 붙은 변수를 포함한 생성자(DI)(원래는 생성자+@Autowired인데 생성자 하나일 때-> @Autowired생략 가능, @RequiredArgsConstructor로 생성자 대체 가능)
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //바디 값 @RequestParam으로 받기.(GET 쿼리 파라미터, POST Form 방식을 모두 지원한다
    //  @PostMapping("/add")    // '/add'가 요청될 때 Post 방식일때는 이게 호출됨.('등록하기' 버튼 누르면 이게 호출됨 -Form -Post 방식이므로)
    public String addItemV1(@RequestParam String itemName, @RequestParam int price, @RequestParam Integer quantity, Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

    //바디 값 @ModelAttribute 로 받기.
   // @PostMapping("/add")    // '/add'가 요청될 때 Post 방식일때는 이게 호출됨.('등록하기' 버튼 누르면 이게 호출됨 -Form -Post 방식이므로)
    public String addItemV2(@ModelAttribute("item") Item item, Model model){  // 2. Model model 생략 가능.

        itemRepository.save(item);
        //model.addAttribute("item",item);  //1. @ModelAttribute("item")에서 "item"이란 name을 지정해줬기 때문에 이 코드도 생략 가능하다.
        return "basic/item";
    }

    //바디 값 @ModelAttribute 로 받기 & name 생략할 경우
  //  @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){  //클래스 명 Item의 첫 글자만 소문자로 바꾼 item을 name으로 설정한다.
                                                                      //클래스 명이 HelloData이면 name은 helloData로 설정된다. model.addAttribute("helloData",item);
        itemRepository.save(item);

        return "basic/item";
    }

    //addItemV3()에서 더 나아가 @ModelAttribute 생략 가능
    @PostMapping("/add")
    public String addItemV4(Item item){  //파라미터에 string, int 같은 일반형식이면 @RquestParam이 동작하지만 그외 객체인 경우는 @modelAttribute가 자동으로 동작한다.

        itemRepository.save(item);

        return "basic/item";   //클래스 명 Item이므로 -> 앞글자 소문자 변환한 item으로 model이름이 설정되어 넘어간다. ex) model.addAttribute("item",item);
    }

    //테스트용 데이터 추가
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}
