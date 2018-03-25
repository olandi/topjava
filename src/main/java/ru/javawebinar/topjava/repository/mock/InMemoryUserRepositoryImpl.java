package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);


    private AtomicInteger counter = new AtomicInteger(0);

    private static final List<User> USER_LIST = Arrays.asList(

        new User(null, "admin", "admin@mail", "password", Role.ROLE_ADMIN),

        new User(null, "user1", "user1@mail", "password", Role.ROLE_USER),
        new User(null, "user2", "user1@mail", "password", Role.ROLE_USER),
        new User(null, "user3", "aaaaa@mail", "password", Role.ROLE_USER),
        new User(null, "user3", "hrllo@mail", "password", Role.ROLE_USER),
        new User(null, "user3", "abbba@mail", "password", Role.ROLE_USER),
        new User(null, "user4", "user4@mail", "password", Role.ROLE_USER),
        new User(null, "user5", "user5@mail", "password", Role.ROLE_USER),
        new User(null, "user6", "user6@mail", "password", Role.ROLE_USER)
    );

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    {
       USER_LIST.forEach(this::save);
    }


    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id)!=null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

         if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");

       /* List<User> getAll = */return repository.values().stream()
                .sorted(
                        Comparator.comparing(
                                (Function<User, String>) AbstractNamedEntity::getName).thenComparing(User::getEmail)
                ).collect(Collectors.toList());

       // return getAll==null? Collections.EMPTY_LIST : getAll;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(u->u.getEmail().equals(email)).findFirst().orElse(null);
    }



    public static void main(String[] args) {

        //--------------------TEST----------------------------
        InMemoryUserRepositoryImpl i = new InMemoryUserRepositoryImpl();
        System.out.println(i.getAll()+"\n");

        System.out.println(i.delete(2));
        System.out.println(i.getAll()+"\n");

        System.out.println(i.delete(123));
        System.out.println(i.getAll()+"\n");

        System.out.println(i.save(
                new User(8, "aaaaa", "user6@mail", "password", Role.ROLE_USER)
        ));
        System.out.println(i.getAll()+"\n");

        System.out.println(i.save(
                new User(null, "aaa124aa", "user6@mail", "password", Role.ROLE_USER)
        ));
        System.out.println(i.getAll()+"\n");

        System.out.println(i.getByEmail("helloDolly"));

        System.out.println(i.getByEmail("user6@mail"));
    }




}


/*
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
 */
