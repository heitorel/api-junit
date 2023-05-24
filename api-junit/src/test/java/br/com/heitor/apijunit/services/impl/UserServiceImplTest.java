package br.com.heitor.apijunit.services.impl;

import br.com.heitor.apijunit.domain.User;
import br.com.heitor.apijunit.domain.dto.UserDTO;
import br.com.heitor.apijunit.repositories.UserRepository;
import br.com.heitor.apijunit.services.exceptions.DataIntegrityViolationException;
import br.com.heitor.apijunit.services.exceptions.ObjectNotFoundException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    public static final int ID = 1;
    public static final String NAME = "mock";
    public static final String EMAIL = "mock@email.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userdto;
    private Optional<User> optionaluser;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userdto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionaluser =Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance(){
        when(repository.findById(anyInt())).thenReturn(optionaluser);
        User response = service.findById(ID);

        assertEquals(User.class,response.getClass(), "findById() deve retornar um User");
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado."));

        try{
            service.findById(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass(), "A excessão deve ser uma ObjectNotFound.");
            assertEquals("Objeto não encontrado.", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers(){
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());
    }

    @Test
    void whenCreateThenReturnSuccess(){
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userdto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass(), "create() deve retornar um User");
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException(){
        when(repository.findByEmail(anyString())).thenReturn(optionaluser);

        try{
            optionaluser.get().setId(2);
            service.create(userdto);
        } catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass(),
                    "Deve lançar uma exception de integridade de dados.");
        }
    }

    @Test
    void whenUpdateThenReturnSuccess(){
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userdto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass(), "create() deve retornar um User");
    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException(){
        when(repository.findByEmail(anyString())).thenReturn(optionaluser);

        try{
            optionaluser.get().setId(2);
            service.update(userdto);
        } catch (Exception ex){
            assertEquals(DataIntegrityViolationException.class, ex.getClass(),
                    "Deve lançar uma exception de integridade de dados.");
        }
    }

    @Test
    void whenDeleteWithSuccess(){
        when(repository.findById(anyInt())).thenReturn(optionaluser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteWithObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try{
            service.delete(ID);
        } catch(Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass(),
                    "Deve lançar uma exception de objeto não encontrado.");
        }
    }

}