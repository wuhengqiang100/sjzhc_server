package com.kexin.admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 *
 */
@Data
public class QaInspectSelect {
    private Integer page;
    private Integer limit;
    private String sort;
    private String cartNumber;
    private String productName;
    private String operationName;
    private String machineName;
    private String workUnitName;
    private Date startDate;
    private Date endDate;



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartDate() {
        return startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }


}
