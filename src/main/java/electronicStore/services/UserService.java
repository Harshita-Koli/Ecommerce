package electronicStore.services;

import electronicStore.payloads.UserDto;
import electronicStore.payloads.pagebleResponse;


import java.util.List;

public interface UserService {

   UserDto createUser(UserDto userDto);
   UserDto updateUser(UserDto userDto, String userId);
   UserDto getUserById(String userId);
   pagebleResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String direction);
   void deleteUser(String userId);
   UserDto getUserByEmail(String email);
   List<UserDto> searchUser(String keyword);
   UserDto getUserByEmailAndPassword(String email, String password);


}
