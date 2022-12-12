package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class MainController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public MainController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    // Данный метод предназначен для отображении товаров без прохождения аутентификации и авторизации
    @GetMapping("")
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("from") String from, @RequestParam("to") String to, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model) {
        if(from.isEmpty()){
            from = "0";
        }
        if(to.isEmpty()){
            to = "99999999";
        }
        // Если диапазон цен от и до не пустой
        if (!from.isEmpty() & !to.isEmpty()) {
            // Если сортировка по цене выбрана
            if (!price.isEmpty()) {
                // Если в качестве сортировки выбрана сортировка по возрастанию
                if (price.equals("sorted_by_ascending_price")) {
                    // Если категория товара не пустая
                    //if (!category.isEmpty()) {
                        // Если категория равна мебели
                        if (category.toString().equals("PC")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                            // Если категория равна бытовой технике
                        } else if (category.toString().equals("Other")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        }
                        // Если категория не выбрана
                    //} else {
                        //model.addAttribute("search_product", productRepository.findByTitleOrderByPrice(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    //}
                    // Если в качестве сортировки выбрана сортировка по убыванию
                } else if (price.equals("sorted_by_descending_price")) {
                    //if (!category.isEmpty()) {
                        if (category.toString().equals("PC")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 1));
                        } else if (category.toString().equals("Other")) {
                            model.addAttribute("search_product", productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to), 2));
                        }
                    //} else {
                        //model.addAttribute("search_product", productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
                    //}
                }
            } else {
                model.addAttribute("search_product", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search.toLowerCase(), Float.parseFloat(from), Float.parseFloat(to)));
            }
        } else {
            model.addAttribute("search_product", productRepository.findByTitleContainingIgnoreCase(search));
        }
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_from", from);
        model.addAttribute("value_price_to", to);
        model.addAttribute("products", productService.getAllProduct());

        return "/product/product";
    }

    @PostMapping("/search1")
    public String productSearch1(@RequestParam("search1") String search1, @RequestParam("from1") String from1, @RequestParam("to1") String to1, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model) {
        if(from1.isEmpty()){
            from1 = "0";
        }
        if(to1.isEmpty()){
            to1 = "99999999";
        }
        // Если диапазон цен от и до не пустой
        if (!from1.isEmpty() & !to1.isEmpty()) {
            // Если сортировка по цене выбрана
            if (!price.isEmpty()) {
                // Если в качестве сортировки выбрана сортировка по возрастанию
                if (price.equals("sorted_by_ascending_price")) {
                    // Если категория товара не пустая
                    if (!category.isEmpty()) {
                        // Если категория равна мебели
                        if (category.equals("PC")) {
                            model.addAttribute("search_product1", productRepository.findByTitleAndCategoryOrderByPrice(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1), 1));
                            // Если категория равна бытовой технике
                        } else if (category.equals("Other")) {
                            model.addAttribute("search_product1", productRepository.findByTitleAndCategoryOrderByPrice(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1), 2));

                        }
                        // Если категория не выбрана
                    } else {
                        model.addAttribute("search_product1", productRepository.findByTitleOrderByPrice(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1)));
                    }
                    // Если в качестве сортировки выбрана сортировка по убыванию
                } else if (price.equals("sorted_by_descending_price")) {
                    if (!category.isEmpty()) {
                        if (category.equals("PC")) {
                            model.addAttribute("search_product1", productRepository.findByTitleAndCategoryOrderByPriceDesc(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1), 1));
                        } else if (category.equals("Other")) {
                            model.addAttribute("search_product1", productRepository.findByTitleAndCategoryOrderByPriceDesc(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1), 2));

                        }
                    } else {
                        model.addAttribute("search_product1", productRepository.findByTitleOrderByPriceDesc(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1)));
                    }
                }
            } else {
                model.addAttribute("search_product1", productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThan(search1.toLowerCase(), Float.parseFloat(from1), Float.parseFloat(to1)));
            }
        } else {
            model.addAttribute("search_product1", productRepository.findByTitleContainingIgnoreCase(search1));
        }
        model.addAttribute("value_search1", search1);
        model.addAttribute("value_price_from1", from1);
        model.addAttribute("value_price_to1", to1);
        model.addAttribute("products", productService.getAllProduct());

        return "/user/index";
    }

}

