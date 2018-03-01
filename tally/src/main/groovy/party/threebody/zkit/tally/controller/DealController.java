package party.threebody.zkit.tally.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.data.result.Count;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;
import party.threebody.zkit.tally.dao.DealDao;
import party.threebody.zkit.tally.domain.Deal;
import party.threebody.zkit.tally.service.TallyService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("deals")
public class DealController extends SinglePKUriVarCrudRestController<Deal, Integer> {
    @Autowired DealDao dealDao;
    @Autowired TallyService tallyService;

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Deal, Integer> builder) {
        builder.fromSinglePKCrudDAO(dealDao);
    }

    @RequestMapping("----")
    @Override
    public ResponseEntity<Object> httpCreateOrUpdate(Integer integer, Deal entity) {
        return super.httpCreateOrUpdate(integer, entity);
    }

    @PutMapping("batch")
    public Count batchPut(@RequestBody List<Deal> deals,
                          @RequestParam String buyer,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMin,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMax) {
        return tallyService.batchPut(deals, buyer, dateMin, dateMax);
    }

    @GetMapping("buyers")
    public List<Map> listAllBuyers() {
        return tallyService.listAllBuyersWithPinyin();
    }

    @GetMapping("buyers/3cnts")
    public Object list3CntsGroupByBuyer() {
        return tallyService.list3CntsGroupByBuyer();
    }
}
