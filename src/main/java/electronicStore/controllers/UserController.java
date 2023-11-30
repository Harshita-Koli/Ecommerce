package electronicStore.controllers;

import electronicStore.payloads.ApiResponse;
import electronicStore.payloads.ImageResponse;
import electronicStore.payloads.UserDto;
import electronicStore.payloads.pagebleResponse;
import electronicStore.services.FileService;
import electronicStore.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadePath;

    /**
     * @param user
1     * @return UserDto
     * @author Priyanka
     * @apiNote saved user data in database
     * @since 1.8v
     */

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
        UserDto user1 = this.userService.createUser(user);
        logger.info("Entering the request for save user data");
        this.userService.createUser(user1);
        logger.info("Complate the request for save user data");
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        logger.info("Entering the request for update the user data with userId {} :", userId);
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        logger.info("Complate the request for update the user data with userId {} :", userId);
        return ResponseEntity.ok(updatedUser);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        logger.info("Entering the request for delete the user data with userId {} :", userId);
        ApiResponse message = ApiResponse.builder().message("User Deleted Successfully").success(true).build();
        logger.info("Complate the request for delete the user data with userId {} :", userId);
        return new ResponseEntity<ApiResponse>(message, HttpStatus.OK);
    }
    //get All
    @GetMapping("/getUsers")
    public ResponseEntity<pagebleResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("Entering the request for get all user data");
        pagebleResponse allUsers = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortDirection);
        logger.info("Complated the request for get all user data");
        return new ResponseEntity<pagebleResponse>(allUsers, HttpStatus.OK);
    }

    // get single user
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    //get by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        String imageName = fileService.uplodFile(image, imageUploadePath);
        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }
    //  serve user image
}










