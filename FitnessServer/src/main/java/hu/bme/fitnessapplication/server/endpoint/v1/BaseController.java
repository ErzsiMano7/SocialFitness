package hu.bme.fitnessapplication.server.endpoint.v1;

import hu.bme.fitnessapplication.server.repository.user.model.User;
import hu.bme.fitnessapplication.server.repository.user.model.dto.UserRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.Authentication;

public abstract class BaseController {
    protected static ModelMapper modelMapper;

    public BaseController() {
        if(modelMapper == null){
            configureModelMapper();
        }
    }

    private void configureModelMapper(){

    }
}
