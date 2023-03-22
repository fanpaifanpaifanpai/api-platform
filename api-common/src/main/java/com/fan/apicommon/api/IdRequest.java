package com.fan.apicommon.api;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 19677
 */
@Data
public class IdRequest implements Serializable {

    private static final long serialVersionUID = 3269792842140340774L;
    /**
     * id
     */
    private Long id;
}
