package com.learning.service.impl;

import com.learning.io.entity.UserEntity;
import com.learning.exceptions.UserServiceException;
import com.learning.io.repository.UserRepository;
import com.learning.service.UserService;
import com.learning.shared.dto.UserDto;
import com.learning.ui.model.response.ErrorMessages;
import com.learning.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Utils utils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new RuntimeException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        user.getAddresses().forEach(addressDto -> {
            addressDto.setUserDetails(user);
            addressDto.setAddressId(utils.generateAddressId(12));
            user.getAddresses().set(user.getAddresses().indexOf(addressDto),addressDto);
        });

        UserEntity userEntity = modelMapper.map(user,UserEntity.class);

        String secureId = utils.generateUserId(10);

        userEntity.setUserId(secureId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(storedUserDetails,UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with ID: " + userId + " not found"));

        UserDto returnValue = modelMapper.map(userEntity,UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(updatedUserDetails,UserDto.class);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<UserEntity> userPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = userPage.getContent();

        users.forEach(userEntity -> returnValue.add(modelMapper.map(userEntity,UserDto.class)));

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
