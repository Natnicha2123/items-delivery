package th.ku.itemsdelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import th.ku.itemsdelivery.model.DateTimeAdapter;
import th.ku.itemsdelivery.model.OrderRequest;
import th.ku.itemsdelivery.service.ItemService;
import th.ku.itemsdelivery.service.OrderRequestService;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private OrderRequestService orderRequestService;
    private ItemService itemService;

    public HomeController(OrderRequestService orderRequestService,ItemService itemService){
        this.itemService=itemService;
        this.orderRequestService = orderRequestService;
    }

    @GetMapping
    public String getHomePage(Model model){
        ArrayList<OrderRequest> currentOrderslist=new ArrayList<>();
        DateTimeAdapter dateTimeAdapter =new DateTimeAdapter();
        List<OrderRequest> pendingOrderList = orderRequestService.getOrderRequestStatusAll("PENDING");
        HashMap<Integer, Boolean> checkOrder = orderRequestService.pendingCheckOrderRequestItemAll();
        for(OrderRequest orderRequest : pendingOrderList){
            if(checkOrder.get(orderRequest.getId()))
                orderRequest.setStatus("PENDING - READY");
            else
                orderRequest.setStatus("PENDING - NOT READY");
        }

        currentOrderslist.addAll(pendingOrderList);
        currentOrderslist.addAll(orderRequestService.getOrderRequestStatusAll("PROGRESS"));
        model.addAttribute("allOrders",currentOrderslist);
        model.addAttribute("dateTimeAdapter",dateTimeAdapter);
        return "home";
    }

    @GetMapping("/info/{id}")
    public String getInfoPage(@PathVariable int id,Model model){

        model.addAttribute("Order",orderRequestService.getOrderRequest(id));
        model.addAttribute("allItem",itemService.getListItemOrder(id));
        model.addAttribute("itemService",itemService);
        model.addAttribute("dateTimeAdapter",new DateTimeAdapter());
        return "info_order";
    }

//    @PostMapping
//    public String showStatus(Model model) {
//        List<OrderRequest> ordersPendinglist = new ArrayList<OrderRequest>();
//        for(OrderRequest order : orderRequestService.getOrders())
//            if(orderRequestService.checkStatus(order) = "PENDING")
//                ordersPendinglist.add(order);
//        model.addAttribute("allOrders", ordersPendinglist);
//        return "redirect:home";
//    }

//    @PostMapping
//    public String registerCustomer(@ModelAttribute OrderRequest orderRequest, Model model) {
//        model.addAttribute("order", orderRequest);
//        return "redirect:home";
//    }
}
