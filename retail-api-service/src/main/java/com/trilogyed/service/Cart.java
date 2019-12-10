package com.trilogyed.service;

import com.trilogyed.exception.InsufficientInventoryException;
import com.trilogyed.exception.QuantityValueException;
import com.trilogyed.model.*;
import com.trilogyed.util.feign.*;
import com.trilogyed.viewmodel.CustomerViewModel;
import com.trilogyed.viewmodel.InvoiceItemViewModel;
import com.trilogyed.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Cart {

    private CustomerClient customerClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;

    @Autowired
    public Cart(CustomerClient customerClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }

    public Invoice addInvoice(Invoice invoice) {
        return invoiceClient.addInvoice(invoice);
    }

    public Invoice findInvoiceById(int id) {
        return invoiceClient.getInvoiceById(id);
    }

    public List<Invoice> findAllInvoices() {
        return invoiceClient.getAllInvoices();
    }

    public List<Invoice> findInvoiceByCustomerId(int id) {
        return invoiceClient.getInvoiceByCustomerId(id);
    }

    public List<Product> findAllProducts() {
        return productClient.getAllProducts();
    }

    public Product findProductById(int id) {
        return productClient.getProductById(id);
    }

    public List<Product> getProductsWithInventory() {
        List<Product> products = productClient.getAllProducts();

        return products.stream().filter(product -> product.getInventory() > 0).collect(Collectors.toList());
    }

    public List<InvoiceItem> findInvoiceItemsByInvoiceId(Integer id) {
        return invoiceClient.getInvoiceItemsByInvoiceId(id.toString());
    }

    public List<Product> getProductsByInvoiceId(Integer id) {
        List<InvoiceItem> items = findInvoiceItemsByInvoiceId(id);
        List<Product> products = new ArrayList<>();

        for(InvoiceItem item: items) {
            products.add(productClient.getProductById(item.getProductId()));
        }
        return products;
    }

    public LevelUp findLevelUpByCustomerId(int id) {
        return levelUpClient.retrieveLevelUpByCustomerId(id);
    }

    public LevelUp assignPoints(Double cost, Invoice invoice) throws Exception {
        LevelUp levelUp = levelUpClient.retrieveLevelUpByCustomerId(invoice.getCustomerId());

        if (levelUp == null) {
            levelUp = levelUpClient.addLevelUp(new LevelUp(invoice.getCustomerId(), 0, invoice.getPurchaseDate()));
        }

        Integer points = 0;

        for (int i = 0; i < cost; i += 50) {
            if(i%50 == 0 && i != 0) {
                points += 10;
            }
        }
        levelUp.setPoints(levelUp.getPoints() + points);
        levelUpClient.updateLevelUp(levelUp);

        return levelUp;
    }

    public InvoiceViewModel makeAnOrder(Order order) throws Exception {
        Customer customer = customerClient.getCustomerByEmail(order.getEmail());
        CustomerViewModel theCustomer = buildCustomerViewModel(customer);
        Double total = 0.0;

        Set<Integer> productId = order.getProductAndQuantity().keySet();
        Map<Integer, Integer> productQuantity = order.getProductAndQuantity();
        List<InvoiceItemViewModel> items = new ArrayList<>();

        LevelUp levelUp = new LevelUp();
        Invoice invoice = new Invoice(customer.getId(), order.getPurchaseDate());
        invoice = invoiceClient.addInvoice(invoice);
        InvoiceViewModel purchase = buildInvoiceViewModel(invoice, theCustomer);

        for (Integer id: productId){
            Product product = productClient.getProductById(id);
            InvoiceItem invoiceItem = new InvoiceItem(invoice.getId(), product.getId(), productQuantity.get(id), product.getListPrice());
            invoiceItem = invoiceClient.addInvoiceItem(invoiceItem);

            if (product.getInventory() < productQuantity.get(id)) {
                String insufficient = "Sorry but we only have " + product.getInventory()
                        + " in stock, so we cannot provide you with " + invoiceItem.getQuantity() + " "
                        + product.getName() + "'s.";
                throw new InsufficientInventoryException(insufficient);
            }

            if (invoiceItem.getQuantity() > 0) {
                total += (order.getProductAndQuantity().get(id) * product.getListPrice().doubleValue());
                product.setInventory(product.getInventory() - order.getProductAndQuantity().get(id));
                productClient.updateProduct(product);

                items.add(buildInvoiceItemViewModel(invoiceItem, product));
            }else {
                throw new QuantityValueException("Quantity must be greater than zero.");
            }
        }

        levelUp = assignPoints(total, invoice);
        purchase.setTotal(total);
        purchase.setLevelUp(levelUp);
        purchase.setItems(items);

        return purchase;
    }

    private InvoiceItemViewModel buildInvoiceItemViewModel(InvoiceItem invoiceItem, Product product) {
        InvoiceItemViewModel viewModel = new InvoiceItemViewModel();

        viewModel.setProductDescription(product.getDescription());
        viewModel.setProductName(product.getName());
        viewModel.setProductPrice(product.getListPrice());

        viewModel.setQuantityToPurchase(invoiceItem.getQuantity());

        return viewModel;
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice, CustomerViewModel customer) {
        InvoiceViewModel viewModel = new InvoiceViewModel();

        viewModel.setCustomer(customer);
        viewModel.setPurchaseDate(invoice.getPurchaseDate());

        return viewModel;
    }

    private CustomerViewModel buildCustomerViewModel(Customer customer) {
        CustomerViewModel viewModel = new com.trilogyed.viewmodel.CustomerViewModel();

        String name = customer.getFirstName() + " " + customer.getLastName();
        String address = customer.getStreet() + " " + customer.getCity() + " " + customer.getZip();
        viewModel.setName(name);
        viewModel.setAddress(address);
        viewModel.setEmail(customer.getEmail());
        viewModel.setPhone(customer.getPhone());

        return viewModel;
    }
}
