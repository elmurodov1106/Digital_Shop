package com.example.digital_shop.service.tv;

import com.example.digital_shop.domain.dto.TvDto;
import com.example.digital_shop.entity.product.TvEntity;
import com.example.digital_shop.entity.user.UserEntity;
import com.example.digital_shop.exception.DataNotFoundException;
import com.example.digital_shop.repository.UserRepository;
import com.example.digital_shop.repository.inventory.InventoryRepository;
import com.example.digital_shop.repository.tv.TvRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service

public class TvServiceImpl implements TvService {

    private final TvRepository tvRepository;
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public TvEntity add(TvDto tvDto, UUID userId, Integer amount, MultipartFile image) throws IOException {
        TvEntity tvEntity = modelMapper.map(tvDto,TvEntity.class);
        tvEntity.setUserId(userId);
        tvEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        return tvRepository.save(tvEntity);
    }


    @Override
    public List<TvEntity> getAllTv(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        List<TvEntity> content = tvRepository.findAll(pageable).getContent();
        if(content.isEmpty()){
            throw new DataNotFoundException("Tv not found");
        }
        return content;
    }

    @Override
    public List<TvEntity> search(int page, int size, String model) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        return tvRepository.searchTvEntitiesByModelContainingIgnoreCase(model, pageable);
    }



    @Override
    @Transactional
    public Boolean deleteById(UUID tvId, UUID userId) {
        TvEntity tvNotFound = tvRepository.findTvEntityById(tvId);
        if (tvNotFound == null) {
            return null;
        }
        if (tvNotFound.getUserId().equals(userId)){
            inventoryRepository.deleteByProductIdEquals(tvId);
            tvRepository.deleteById(tvId);
            return true;
        }
        return null;
    }
    @Override
    @Transactional
    public TvEntity update(TvDto tvDto, UUID tvId,UUID userId,Integer amount,MultipartFile image) throws IOException {
        TvEntity tvEntity = tvRepository.findTvEntityById(tvId);
        if (tvEntity==null){
            return null;
        }
        if (tvEntity
                .getUserId().equals(userId)){
            modelMapper.map(tvDto,tvEntity
            );
            tvEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            return tvRepository.save(tvEntity
            );
        }
        return null;
    }


    @Override
    public List<TvEntity> getSellerTv(int size,int page,UUID sellerId) {
        Pageable pageable = PageRequest.of(page, size);
        List<TvEntity> tvEntitiesByUserIdEquals = tvRepository.findTvEntitiesByUserIdEquals(pageable,sellerId);
        return tvEntitiesByUserIdEquals;
    }

    @Override
    public List<TvEntity> findTvEntityByOwnerId(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return tvRepository.findTvEntityByUserIdEquals(userId);
    }
}

