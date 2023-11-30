package electronicStore.servicesImpl;


import electronicStore.entities.User;

import electronicStore.exceptions.ResourceNotFoundException;
import electronicStore.helpers.helper;
import electronicStore.payloads.UserDto;
import electronicStore.payloads.pagebleResponse;
import electronicStore.repositories.UserRepo;
import electronicStore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    //create user
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Intiating Dao Call for create User data");
        String str = UUID.randomUUID().toString();
        userDto.setUserId(str);
        User user = this.modelMapper.map(userDto, User.class);
        this.userRepo.save(user);
        UserDto userDto1 = this.modelMapper.map(user, UserDto.class);
        log.info("Complete Dao Call for create User data");
        return userDto1;
    }

    //update user
    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Intiating Dao Call for update the User data with userId{}:", userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given Id !!"));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        log.info("Complate Dao Call for update the User data with userId{}:", userId);
        return userDto1;
    }

    //    get single user
    @Override
    public UserDto getUserById(String userId) {
        log.info("Intiating Dao Call for get the User data with userId{}:", userId);
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given Id "));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("compleate Dao Call for get the User data with userId{}:", userId);
        return userDto;
    }

    //    get all users
    @Override
    public pagebleResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction) {

        log.info("Entering the Dao call for getAll the users ");
        Sort desc = (direction.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pr = PageRequest.of(pageNumber, pageSize, desc);
        Page<User> pages = this.userRepo.findAll(pr);
        List<User> users = pages.getContent();
        List<UserDto> dtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        pagebleResponse<UserDto> pageableResponse = helper.getPageableResponse(pages, UserDto.class);
        log.info("Completed the Dao call for getAll the users ");
        return (pagebleResponse) dtos;
    }

    //    delete user
    @Override
    public void deleteUser(String userId) {
        log.info("Intiating Dao Call for delete the User data with userId{}:", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with given Id"));
        log.info("Complate Dao Call for delete the User data with userId{}:", userId);
        this.userRepo.delete(user);
    }

    //get user by email
    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Intiating Dao Call for  get the User data with email{}:", email);
        User user = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found given email id !!"));
        log.info("Complate Dao Call for  get the User data with email{}:", email);
        return userToDto(user);
    }

    //serch user by name
    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("Intiating Dao Call for  get the User data by keyword{}:", keyword);
        List<User> users = userRepo.findByNameContaining(keyword);
        log.info("Complate Dao Call for  get the User data with keyword{}:", keyword);
        List<UserDto> dtoList = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return dtoList;

    }

    @Override
    public UserDto getUserByEmailAndPassword(String email, String password) {
        log.info("Entering the Dao call for get Single user with Email And Password  :{} :{} ", email, password);
        User user = this.userRepo.findByEmailAndPassword(email, password).get();
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        log.info("Completed the Dao call for get Single user with Email And Password  :{} :{} ", email, password);
        return userDto;
    }


    //    model-mapper
    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}

