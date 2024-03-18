package com.jz.multiThread.async;

import lombok.*;
import java.util.List;

@Data @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SpuBo {
    private List<String> brands;
    private List<String> categories;
    private String spuDetails;
    private Long spuId;
}
