package com.panda520.mall.web.controller.monitor;

import com.panda520.mall.common.annotation.Log;
import com.panda520.mall.common.core.controller.BaseController;
import com.panda520.mall.common.core.domain.ResponseResult;
import com.panda520.mall.common.core.page.TableDataInfo;
import com.panda520.mall.common.enums.BusinessType;
import com.panda520.mall.common.utils.poi.ExcelUtil;
import com.panda520.mall.system.domain.SysOperLog;
import com.panda520.mall.system.service.ISysOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 玛丽莲梦明
 * @描述.操作日志记录
 */
@Controller
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {
    private String prefix = "monitor/operlog";

    @Autowired
    private ISysOperLogService operLogService;

    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog() {
        return prefix + "/operlog";
    }

    @RequiresPermissions("monitor:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:operlog:export")
    @PostMapping("/export")
    @ResponseBody
    public ResponseResult export(SysOperLog operLog) {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public ResponseResult remove(String ids) {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long operId, ModelMap modelMap) {
        modelMap.put("operLog", operLogService.selectOperLogById(operId));
        return prefix + "/detail";
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public ResponseResult clean() {
        operLogService.cleanOperLog();
        return success();
    }
}
