package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.User;
import com.thungcam.chacalang.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> getAllUserAddresses(User user);

    UserAddress getUserAddressByIdAndUser(Long id, User user);

    void createUserAddress(User user, UserAddress address, Boolean isDefault);

    void updateUserAddress(User user, UserAddress address, Boolean isDefault);

    void deleteAddress(User user, Long addressId);

    List<UserAddress> getAddressesByUserId(User user);

    List<UserAddress> getDefaultAddressesByUserId(User user);

    List<UserAddress> getAddressByUserId(User user);

    UserAddress getAddressById(Long id);
}
