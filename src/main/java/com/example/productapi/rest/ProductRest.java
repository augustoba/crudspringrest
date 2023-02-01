
package com.example.productapi.rest;

import com.example.productapi.entity.Product;
import com.example.productapi.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductRest {
    
    @Autowired
    private ProductRepository productRepo;
    
    @GetMapping
    public ResponseEntity <List<Product>> getProduct(){
      List<Product> products= productRepo.findAll();
        return ResponseEntity.ok(products);
    }
    
    @RequestMapping(value = "{productId}")
    public ResponseEntity <Product> getProductById(@PathVariable("productId") Long productId){
        Optional<Product> optionalProduct =productRepo.findById(productId);
        
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        }
        else{   
            return ResponseEntity.noContent().build();
            
        }
        
    }
    
    @PostMapping
    public ResponseEntity<Product> createProduct( @RequestBody Product product) throws Exception{
        
        if (product.getName().isEmpty()|| product.getName()==null) {
            throw new Exception("el nombre no puede estar vacio");
            
        }
        
       Product newProduct= productRepo.save(product);
       return ResponseEntity.ok(newProduct);
    }
    
    @DeleteMapping (value = "{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long productId){
        productRepo.deleteById(productId);
       return ResponseEntity.ok(null);
    }
    
    
    
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Optional<Product> optionalProduct =productRepo.findById(product.getId());
        
        if (optionalProduct.isPresent()) {
            Product updateProduct=optionalProduct.get();
             updateProduct.setName(product.getName());
             productRepo.save(updateProduct);
             return ResponseEntity.ok(updateProduct);
        }
        else{   
            return ResponseEntity.notFound().build();
            
        }
    }
    
 //@GetMapping   //getmapping sirve para enviar a la url ya definida arriba
 @RequestMapping(value="hello",method = RequestMethod.GET) //sirve para definir una nueva url a la que va a llevar se necesita
 // value que seria la url esta seria localhost8080/hello y el metodo get.
public String hello(){
    return"hello world";
}
}
