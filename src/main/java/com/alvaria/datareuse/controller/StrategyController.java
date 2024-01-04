package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.Strategy;
import com.alvaria.datareuse.entity.StrategyStatus;
import com.alvaria.datareuse.service.StrategyService;
import com.alvaria.datareuse.service.StrategyStatusService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/strategy")
@CrossOrigin
@Api(tags = "strategy controller")
public class StrategyController {
    @Autowired
    StrategyService strategyService;
    @Autowired
    StrategyStatusService strategyStatusService;
    @PostMapping("/apply")
    public ResponseResult applyStrategy(@RequestBody ConditionModel conditionModel) throws Exception {
        StrategyStatus strategy = strategyService.applyStrategy(conditionModel);
        String message = strategy != null?"Apply strategy successfully!":"Apply strategy failed!";
        return new ResponseResult(0,strategy , message);
    }
    @DeleteMapping("/release")
    public ResponseResult releaseStrategy(@RequestParam String name, @RequestParam String org) throws Exception {
        strategyStatusService.releaseStrategyByName(name, org);
        return new ResponseResult(0, name, "Release strategy successfully!");
    }

    @DeleteMapping("/release/uuids")
    public ResponseResult releaseUserList(@RequestBody List<String> uuids) {
        if (uuids == null || uuids.isEmpty()) {
            return new ResponseResult(-1, uuids, "parameter error");
        }

        return new ResponseResult(0, "effect " + strategyStatusService.releaseStrategyList(uuids) + " rows", "Release successfully");
    }
}
