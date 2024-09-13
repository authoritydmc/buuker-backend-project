package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
@GetMapping
    public String welcome()
    {
        return "this is working";
    }
}
