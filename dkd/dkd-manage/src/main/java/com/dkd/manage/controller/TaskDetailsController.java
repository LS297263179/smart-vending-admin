package com.dkd.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.dkd.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dkd.common.annotation.Log;
import com.dkd.common.core.controller.BaseController;
import com.dkd.common.core.domain.AjaxResult;
import com.dkd.common.enums.BusinessType;
import com.dkd.manage.domain.TaskDetails;
import com.dkd.manage.service.ITaskDetailsService;
import com.dkd.common.utils.poi.ExcelUtil;
import com.dkd.common.core.page.TableDataInfo;

/**
 * 工单详情Controller
 *
 * @author lusheng
 * @date 2025-10-20
 */
@Api(tags = "工单详情管理")
@RestController
@RequestMapping("/manage/task_details")
public class TaskDetailsController extends BaseController
{
    @Autowired
    private ITaskDetailsService taskDetailsService;

    /**
     * 查询工单详情列表
     */
    @ApiOperation("查询工单详情列表")
    @PreAuthorize("@ss.hasPermi('manage:task_details:list')")
    @GetMapping("/list")
    public TableDataInfo list(@ApiParam("工单详情查询参数") TaskDetails taskDetails)
    {
        startPage();
        List<TaskDetails> list = taskDetailsService.selectTaskDetailsList(taskDetails);
        return getDataTable(list);
    }

    /**
     * 导出工单详情列表
     */
    @ApiOperation("导出工单详情列表")
    @PreAuthorize("@ss.hasPermi('manage:task_details:export')")
    @Log(title = "工单详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,
                       @ApiParam("工单详情查询参数") TaskDetails taskDetails)
    {
        List<TaskDetails> list = taskDetailsService.selectTaskDetailsList(taskDetails);
        ExcelUtil<TaskDetails> util = new ExcelUtil<TaskDetails>(TaskDetails.class);
        util.exportExcel(response, list, "工单详情数据");
    }

    /**
     * 获取工单详情详细信息
     */
    @ApiOperation("获取工单详情详细信息")
    @PreAuthorize("@ss.hasPermi('manage:task_details:query')")
    @GetMapping(value = "/{detailsId}")
    public AjaxResult getInfo(@ApiParam("工单详情ID") @PathVariable("detailsId") Long detailsId)
    {
        return success(taskDetailsService.selectTaskDetailsByDetailsId(detailsId));
    }

    /**
     * 新增工单详情
     */
    @ApiOperation("新增工单详情")
    @PreAuthorize("@ss.hasPermi('manage:task_details:add')")
    @Log(title = "工单详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@ApiParam("工单详情对象") @RequestBody TaskDetails taskDetails)
    {
        return toAjax(taskDetailsService.insertTaskDetails(taskDetails));
    }

    /**
     * 修改工单详情
     */
    @ApiOperation("修改工单详情")
    @PreAuthorize("@ss.hasPermi('manage:task_details:edit')")
    @Log(title = "工单详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@ApiParam("工单详情对象") @RequestBody TaskDetails taskDetails)
    {
        return toAjax(taskDetailsService.updateTaskDetails(taskDetails));
    }

    /**
     * 删除工单详情
     */
    @ApiOperation("删除工单详情")
    @PreAuthorize("@ss.hasPermi('manage:task_details:remove')")
    @Log(title = "工单详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailsIds}")
    public AjaxResult remove(@ApiParam("工单详情ID数组") @PathVariable Long[] detailsIds)
    {
        return toAjax(taskDetailsService.deleteTaskDetailsByDetailsIds(detailsIds));
    }

    /**
     * 查看工单补货详情
     */
//    @ApiOperation("查看工单补货详情")
//    @PreAuthorize("@ss.hasPermi('manage:taskDetails:list')")
//    @GetMapping("/byTaskId/{taskId}")
//    public R<List<TaskDetails>> byTaskId(@ApiParam("工单ID") @PathVariable("taskId") Long taskId) {
//        TaskDetails taskDetailsParam = new TaskDetails();
//        taskDetailsParam.setTaskId(taskId);
//        return R.ok(taskDetailsService.selectTaskDetailsList(taskDetailsParam));
//    }
//    }


    @PreAuthorize("@ss.hasPermi('manage:taskDetails:list')")
    @GetMapping("/byTaskId/{taskId}")
    public AjaxResult byTaskId(@PathVariable Long taskId){
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setTaskId(taskId);
        return success(taskDetailsService.selectTaskDetailsList(taskDetails));
    }

}

