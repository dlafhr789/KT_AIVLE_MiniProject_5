package ktaivlethminiproject.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktaivlethminiproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(
        value = "/users/signup",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public User signUp(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SignUpCommand signUpCommand
    ) throws Exception {
        System.out.println("##### /user/signUp  called #####");
        User user = new User();
        user.signUp(signUpCommand);
        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/plancancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User planCancel(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/planCancel  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.planCancel();

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/planpurchase",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User planPurchase(
        @PathVariable(value = "id") Long id,
        @RequestBody PlanPurchaseCommand planPurchaseCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/planPurchase  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.planPurchase(planPurchaseCommand);

        userRepository.save(user);
        return user;
    }
}
//>>> Clean Arch / Inbound Adaptor
