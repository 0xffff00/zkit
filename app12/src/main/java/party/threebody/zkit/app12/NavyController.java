package party.threebody.zkit.app12;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("navy")
public class NavyController {

    @GetMapping("ships/{code}")
    public Object getShip(@PathVariable("code") String code) {
        return "ship:" + code;
    }

    @GetMapping("fff")
    public Object fff() {
        return "fuck";
    }

    @GetMapping("r")
    public Object r() {
        return Math.random();
    }

    @PatchMapping("ppp/{id}")
    public Object ppp(@PathVariable int id) {
        return id;
    }
}
