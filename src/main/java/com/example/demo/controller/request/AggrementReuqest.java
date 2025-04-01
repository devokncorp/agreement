package com.example.demo.controller.request;

import com.example.demo.core.api.IOvmRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AggrementReuqest implements IOvmRequest {
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")

    private LocalDate begin;
    private LocalDate end;
    private List<String> subjectCodes;
    private List<String> autoProcessedSubjectCodes;
}
