DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `user_id` varchar(64) NOT NULL DEFAULT '' COMMENT '用户编码',
    `username` varchar(64) NOT NULL COMMENT '用户名',
    `create_time` datetime NOT NULL DEFAULT '2000-01-31 23:59:59' COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ;
