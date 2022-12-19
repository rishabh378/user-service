package com.learning.ui.controller;

import com.learning.service.AddressService;
import com.learning.service.UserService;
import com.learning.shared.dto.AddressDto;
import com.learning.shared.dto.UserDto;
import com.learning.ui.model.request.UserDetailsModelRequest;
import com.learning.ui.model.response.AddressesRest;
import com.learning.ui.model.response.OperationStatusModel;
import com.learning.ui.model.response.RequestOperationStatus;
import com.learning.ui.model.response.UserRest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users") // http:localhost:8082/users
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AddressService addressesService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = service.getUsers(page,limit);

        users.forEach(userDto -> returnValue.add(modelMapper.map(userDto, UserRest.class)));

        return returnValue;
    }

    @GetMapping("/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserDto userDto= service.getUserByUserId(id);
        return modelMapper.map(userDto,UserRest.class);
    }

    @GetMapping("/{id}/addresses")
    public List<AddressesRest> getUserAddress(@PathVariable String id) {
        List<AddressesRest> returnValue = new ArrayList<>();
        List<AddressDto> addressesDto = addressesService.getAddresses(id);

        if(addressesDto != null && !addressesDto.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
            returnValue = modelMapper.map(addressesDto, listType);
        }
        return returnValue;
    }

    @GetMapping("/{userId}/addresses/{addressId}")
    public AddressesRest getUserAddresses(@PathVariable String addressId) {
        AddressDto addressesDto = addressesService.getAddress(addressId);
        return modelMapper.map(addressesDto, AddressesRest.class);
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsModelRequest userDetails) throws Exception {
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);
        UserDto createdUser = service.createUser(userDto);
        UserRest returnValue = modelMapper.map(createdUser,UserRest.class);

        return returnValue;
    }

    @PutMapping("/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsModelRequest userDetails) {
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);
        UserDto updatedUser = service.updateUser(id,userDto);
        UserRest returnValue = modelMapper.map(updatedUser,UserRest.class);

        return returnValue;
    }

    @DeleteMapping("/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        service.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }
}
