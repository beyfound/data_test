package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.WorkType;
import com.alvaria.datareuse.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/release")
@CrossOrigin
@Api(tags = "Work Type controller")
public class ReleaseDataController {

    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private WorkTypeStatusService workTypeStatusService;

    @Autowired
    private StrategyStatusService strategyStatusService;
    @DeleteMapping("/{testCase}")
    public ResponseResult releaseDataByTestCase(@PathVariable String testCase) {
        int workTypeRow = workTypeStatusService.releaseWorkTypeByTestCase(testCase);
        int userRow = userStatusService.releaseUserByTestCase(testCase);
        int strategyRow = strategyStatusService.releaseStrategyByTestCase(testCase);
        return new ResponseResult(0, "effect user " + userRow + " row(s), effect work type " + workTypeRow + " row(s), effect strategy " + strategyRow + " row(s)", "Release successfully");
    }

}
