package com.fan.apicommon.api;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 6868985716750318508L;

    /**
     * id
     */
    private Long id;
}
