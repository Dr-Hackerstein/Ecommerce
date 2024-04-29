package com.kevin.springboot.SpringEcommerce.controller;

import com.kevin.springboot.SpringEcommerce.model.Order;
import com.kevin.springboot.SpringEcommerce.model.OrderDetails;
import com.kevin.springboot.SpringEcommerce.model.Product;
import com.kevin.springboot.SpringEcommerce.model.User;
import com.kevin.springboot.SpringEcommerce.service.IOrderDetailsService;
import com.kevin.springboot.SpringEcommerce.service.IOrderService;
import com.kevin.springboot.SpringEcommerce.service.IUserService;
import com.kevin.springboot.SpringEcommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailsService orderDetailsService;

    // para almacenar los detalles de la orden
    List<OrderDetails> details = new ArrayList<OrderDetails>();

    // datos de la orden
    Order order = new Order();


    @GetMapping("")
    public String home( Model model) {

        model.addAttribute("products",productService.findAll());
        return "users/home";
    }

    @GetMapping("producthome/{id}")
    public String productHome(@PathVariable Integer id, Model model) {
        log.info("Id producto enviado como parámetro {}", id);
        Product product = new Product();
        Optional<Product> productOptional = productService.get(id);
        product = productOptional.get();

        model.addAttribute("product", product);

        return "users/producthome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model) {
        OrderDetails orderDetails = new OrderDetails();
        Product product = new Product();
        double totalSum = 0;

        Optional<Product> optionalProduct = productService.get(id);
        log.info("Producto añadido: {}", optionalProduct.get());
        log.info("Cantidad: {}", quantity);
        product = optionalProduct.get();

        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(product.getPrice());
        orderDetails.setName(product.getName());
        orderDetails.setTotal(product.getPrice() * quantity);
        orderDetails.setProduct(product);

        //validar que le producto no se añada 2 veces
        Integer idProduct=product.getId();
        boolean ingresado=details.stream().anyMatch(p -> p.getProduct().getId()==idProduct);

        if (!ingresado) {
            details.add(orderDetails);
        }

        totalSum = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

        order.setTotal(totalSum);
        model.addAttribute("cart", details);
        model.addAttribute("order", order);

        return "users/cart";
    }

    // quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id, Model model) {

        // lista nueva de prodcutos
        List<OrderDetails> newOrders = new ArrayList<OrderDetails>();

        for (OrderDetails orderDetails : details) {
            if (orderDetails.getProduct().getId() != id) {
                newOrders.add(orderDetails);
            }
        }

        // poner la nueva lista con los productos restantes
        details = newOrders;

        double sumaTotal = 0;
        sumaTotal = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

        order.setTotal(sumaTotal);
        model.addAttribute("cart", details);
        model.addAttribute("order", order);

        return "users/cart";
    }

    @GetMapping("/getCart")
    public String getCart(Model model, HttpSession session) {

        model.addAttribute("cart", details);
        model.addAttribute("order", order);

        //sesion
        model.addAttribute("session", session.getAttribute("iduser"));
        return "/users/cart";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {

        User user =userService.findById(1).get();

        model.addAttribute("cart", details);
        model.addAttribute("order", order);
        model.addAttribute("user", user);

        return "users/resumenorden";
    }


    // guardar la orden
    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session ) {
        Date createdDate = new Date();
        order.setDateCreated(createdDate);
        order.setNumber(orderService.generarNumeroOrden());

        //user
        User user =userService.findById(1).get();

        order.setUser(user);
        orderService.save(order);

        //guardar detalles
        for (OrderDetails dt:details) {
            dt.setOrder(order);
            orderDetailsService.save(dt);
        }

        ///limpiar lista y orden
        order = new Order();
        details.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model) {
        log.info("Nombre del producto: {}", nombre);
        List<Product> products= productService.findAll().stream().filter( p -> p.getName().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "users/home";
    }

}
