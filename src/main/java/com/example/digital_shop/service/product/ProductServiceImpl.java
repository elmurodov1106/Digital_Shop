package com.example.digital_shop.service.product;

import com.example.digital_shop.domain.dto.InventoryCreateDto;
import com.example.digital_shop.domain.dto.ProductCreatDto;
import com.example.digital_shop.domain.dto.ProductUpdateDto;
import com.example.digital_shop.entity.inventory.InventoryEntity;
import com.example.digital_shop.entity.product.ProductEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.UserRepository;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ProductEntity add(ProductCreatDto product, UUID userId, Integer amount, MultipartFile image){
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        productEntity.setUserId(userId);
        try {
            productEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProductEntity savedProduct = productRepository.save(productEntity);
        InventoryCreateDto inventoryCreateDto = new InventoryCreateDto();
        inventoryCreateDto.setProductId(savedProduct.getId());
        inventoryCreateDto.setProductCount(amount);
        InventoryEntity inventoryEntity = modelMapper.map(inventoryCreateDto, InventoryEntity.class);
        inventoryRepository.save(inventoryEntity);
        return savedProduct;
    }

    @Override
    public List<ProductEntity> findProductEntityByOwnerId(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return productRepository.findProductEntityByUserIdEquals(userId);
    }

    @Override
    public List<ProductEntity> getAllProducts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductEntity> content = productRepository.findAll(pageable).getContent();
        return content;
    }

    @Override
    public List<ProductEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.searchProductEntitiesByModelContainingIgnoreCase(model, pageable);
    }

    @Override
    @Transactional
    public Boolean deleteById(UUID productId, UUID userId) {
        ProductEntity productNotFound = productRepository.findProductEntityById(productId);
        if(productNotFound==null){
            System.out.println(true);
            return null;
        }
        System.out.println(productNotFound.getUserId());
        if (productNotFound.getUserId().equals(userId)) {
            System.out.println(true);
            inventoryRepository.deleteByProductIdEquals(productId);
            productRepository.deleteById(productId);
            return true;
        }
        return null;
    }

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getById(UUID productId) {
        return productRepository.findProductEntityById(productId);
    }

    @Override
    public List<ProductEntity> getSellerProduct(UUID sellerId,int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        List<ProductEntity> productEntitiesByUserIdEquals = productRepository.findProductEntitiesByUserIdAndProductTypeEqualsIgnoreCase(sellerId,"product" ,pageable);
        if(productEntitiesByUserIdEquals.isEmpty()){
            return null;
        }
        return productEntitiesByUserIdEquals;
    }

    @Override
    @Transactional
    public ProductEntity update(ProductUpdateDto update, UUID productId, Integer amount, UUID userId, MultipartFile image) throws IOException {
        ProductEntity productEntity = productRepository.findProductEntityById(productId);
        System.out.println("update"+update.toString());
        if(productEntity==null){
            return null;
        }
        if (productEntity.getUserId().equals(userId)) {
            if(!update.getName().equals("")){
                productEntity.setName(update.getName());
            }
            if(!update.getModel().equals("")){
                productEntity.setModel(update.getModel());
            }
            if(update.getCost()!=null){
                productEntity.setCost(update.getCost());
            }
            if(update.getAmount()!=null&& update.getAmount()>=1){
                productEntity.setAmount(update.getAmount());
            }
            if(!update.getProductType().equals("")){
                productEntity.setProductType(update.getProductType());
            }
            String s = Base64.getEncoder().encodeToString(image.getBytes());
            if (!s.equals(" ") && !s.equals("")) {
                productEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                return productRepository.save(productEntity);
            }
            productEntity.setImage(productEntity.getImage());
            return productRepository.save(productEntity);
        }
        return null;
    }


}
