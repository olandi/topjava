package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    /*@GetMapping(value = "/enable/{id}")
    public String setEnable(@PathVariable("id") int id) {
        super.setEnable(id);
        return "redirect: /ajax/admin/users";
    }*/
//http://localhost:8080/topjava/ajax/admin/users/enable/100000
    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        }
    }

    /**
     * @CacheEvict(value = "users", allEntries = true)
     *
     * из-за этого была проблема с обновлением данных, доставаемых из бд
     * */
    @PostMapping(value = "enable")
    public void test(@RequestParam("id") Integer id) {
        super.setEnable(id);
    }




}
