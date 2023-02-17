CREATE TABLE `interface_info` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `interface_name` varchar(256) NOT NULL COMMENT '接口名称',
                                  `interface_description` varchar(512) DEFAULT NULL COMMENT '接口描述',
                                  `interface_url` varchar(512) NOT NULL COMMENT '接口地址',
                                  `request_header` text COMMENT '请求头',
                                  `response_header` text COMMENT '响应头',
                                  `interface_status` int(11) NOT NULL DEFAULT '0' COMMENT '接口状态（正常-0）',
                                  `method` varchar(256) NOT NULL COMMENT '请求方法',
                                  `user_id` bigint(20) NOT NULL COMMENT '创建者id',
                                  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（未删-0 已删除-1）',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `user` (
                        `id` bigint(100) NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(512) NOT NULL,
                        `user_password` varchar(512) NOT NULL,
                        `user_avatar` varchar(1024) DEFAULT NULL,
                        `user_role` int(11) NOT NULL DEFAULT '0',
                        `user_status` int(11) NOT NULL DEFAULT '0',
                        `user_gender` tinyint(4) NOT NULL DEFAULT '0',
                        `access_key` varchar(100) NOT NULL,
                        `secret_key` varchar(100) NOT NULL,
                        `delete_id` tinyint(4) NOT NULL DEFAULT '0',
                        `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT
CHARSET=utf8