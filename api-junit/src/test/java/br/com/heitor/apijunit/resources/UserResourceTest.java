package br.com.heitor.apijunit.resources;

import br.com.heitor.apijunit.domain.User;
import br.com.heitor.apijunit.domain.dto.UserDTO;
import br.com.heitor.apijunit.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserResourceTest {

    @InjectMocks
    UserResource resource;

    @Mock
    private UserService service;

    @Mock
    private ModelMapper mapper;

    private User user = new User();
    private UserDTO userdto = new UserDTO();

    public static final int ID = 1;
    public static final String NAME = "mock";
    public static final String EMAIL = "mock@email.com";
    public static final String PASSWORD = "123";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userdto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    void whenFindByIdThenReturnSuccess(){
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userdto);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response.getBody(),"response não pode ter corpo vazio.");
        assertEquals(ResponseEntity.class, response.getClass(), "response deve ser uma ResponseEntity.");
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO(){
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userdto);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response.getBody(),"response não pode ter corpo vazio.");
        assertEquals(ResponseEntity.class, response.getClass(), "response deve ser uma ResponseEntity.");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenCreateThenReturnCreated(){
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(userdto);

        assertNull(response.getBody(),"response deve ter o corpo vazio.");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess(){
        when(service.update(userdto)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userdto);

        ResponseEntity<UserDTO> response = resource.update(ID, userdto);

        assertNotNull(response.getBody(),"response não pode ter corpo vazio.");
        assertEquals(ResponseEntity.class, response.getClass(), "response deve ser uma ResponseEntity.");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserDTO.class, response.getBody().getClass());
    }

    @Test
    void whenDeleteWithSuccess(){
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response,"response não pode ser vazio.");
        assertEquals(ResponseEntity.class, response.getClass(), "response deve ser uma ResponseEntity.");
        verify(service, times(1)).delete(anyInt());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}