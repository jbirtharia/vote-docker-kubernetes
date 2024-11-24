package com.worker.node.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Votes {

    private Long id;

    @NonNull
    private String vote;

    private Long count;

    private boolean allProcessed;
}
