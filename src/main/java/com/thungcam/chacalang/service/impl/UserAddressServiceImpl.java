package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;
import com.thungcam.chacalang.repository.UserAddressRepository;
import com.thungcam.chacalang.repository.UserRepository;
import com.thungcam.chacalang.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository userAddressRepository;

    private final UserRepository userRepository;


    @Override
    public List<UserAddress> getAllUserAddresses(User user) {
        return userAddressRepository.findAllByUserOrderByIsDefaultDesc(user);
    }

    @Override
    public UserAddress getUserAddressByIdAndUser(Long id, User user) {
        return userAddressRepository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
    }

    @Override
    @Transactional
    public void createUserAddress(User user, UserAddress address, Boolean isDefault) {
        if (isDefault) {
            unsetAllDefaultForUser(user);
        }
        address.setCity("Thành Phố Hà Nội");
        address.setUser(user);
        address.setIsDefault(isDefault);
        address.setId(null); // đảm bảo là địa chỉ mới
        address.setCreatedAt(LocalDateTime.now());
        userAddressRepository.save(address);
    }

    @Override
    @Transactional
    public void updateUserAddress(User user, UserAddress address, Boolean isDefault) {
        UserAddress existing = getUserAddressByIdAndUser(address.getId(), user);

        if (isDefault && !Boolean.TRUE.equals(existing.getIsDefault())) {
            unsetAllDefaultForUser(user);
        }

        existing.setFullName(address.getFullName());
        existing.setPhone(address.getPhone());
        existing.setAddress(address.getAddress());
        existing.setWard(address.getWard());
        existing.setDistrict(address.getDistrict());
        existing.setCity("Thành Phố Hà Nội");
        existing.setIsDefault(isDefault);
        existing.setUpdatedAt(LocalDateTime.now());

        userAddressRepository.save(existing);
    }

    private void unsetAllDefaultForUser(User user) {
        userAddressRepository.findAllByUserOrderByIsDefaultDescUpdatedAtDesc(user).stream()
                .filter(a -> Boolean.TRUE.equals(a.getIsDefault()))
                .forEach(a -> {
                    a.setIsDefault(Boolean.FALSE);
                    userAddressRepository.save(a);
                });
    }

    @Override
    public void deleteAddress(User user, Long addressId) {
        UserAddress address = getUserAddressByIdAndUser(addressId, user);
        userAddressRepository.delete(address);
    }

    @Override
    public List<UserAddress> getAddressesByUserId(User user) {
        return userAddressRepository.findAllByUser(user);
    }

    @Override
    public List<UserAddress> getDefaultAddressesByUserId(User user) {
        return userAddressRepository.findAllByUserAndIsDefault(user, true);
    }

    @Override
    public List<UserAddress> getAddressByUserId(User user) {
        return userAddressRepository.findAllByUser(user);
    }

    @Override
    public UserAddress getAddressById(Long id) {
        return userAddressRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
    }
}
