package com.xzit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 31507
 */
@Data
@Accessors
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo {
    private String token;
    private Long expireTime;
}
