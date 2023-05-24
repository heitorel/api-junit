package br.com.heitor.apijunit.services;

import br.com.heitor.apijunit.domain.User;
import br.com.heitor.apijunit.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);
    User update(UserDTO obj);
    void delete(Integer id);
}
