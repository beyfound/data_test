package com.alvaria.datareuse.controller;

import com.alvaria.datareuse.entity.ConditionModel;
import com.alvaria.datareuse.entity.ResponseResult;
import com.alvaria.datareuse.entity.WorkType;
import com.alvaria.datareuse.service.UploadCSVService;
import com.alvaria.datareuse.service.UserStatusService;
import com.alvaria.datareuse.service.WorkTypeService;
import com.alvaria.datareuse.service.WorkTypeStatusService;
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

    @DeleteMapping("/{testCase}")
    public ResponseResult releaseDataByTestCase(@PathVariable String testCase) {
        int workTypeRow = workTypeStatusService.releaseWorkTypeByTestCase(testCase);
        int userRow =userStatusService.releaseUserByTestCase(testCase);
        return new ResponseResult(0, "effect user " + userRow + " row(s), effect work type " + workTypeRow + " row(s).", "Release successfully");
    }

}
