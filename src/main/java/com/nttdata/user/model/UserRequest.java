package com.nttdata.user.model;

import java.util.List;
import com.nttdata.user.model.entity.Phone;
import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private List<Phone> phones;

}
