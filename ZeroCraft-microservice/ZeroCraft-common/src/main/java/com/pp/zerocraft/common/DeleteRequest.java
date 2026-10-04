package com.pp.zerocraft.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求包装类，接收要删除的id
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
