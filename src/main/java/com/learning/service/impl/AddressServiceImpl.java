package com.learning.service.impl;

import com.learning.io.entity.AddressEntity;
import com.learning.io.entity.UserEntity;
import com.learning.io.repository.AddressRepository;
import com.learning.io.repository.UserRepository;
import com.learning.service.AddressService;
import com.learning.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = null;
        if (userRepository.findByUserId(userId).isPresent()) {
            userEntity = userRepository.findByUserId(userId).get();
        } else {
            return returnValue;
        }

        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        addresses.forEach(addressEntity -> returnValue.add(modelMapper.map(addressEntity,AddressDto.class)));

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        AddressDto returnValue = null;
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if (addressEntity != null) {
            returnValue = modelMapper.map(addressEntity, AddressDto.class);
        }

        return returnValue;
    }
}
