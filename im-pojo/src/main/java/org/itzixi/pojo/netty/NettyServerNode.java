package org.itzixi.pojo.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NettyServerNode {
    private String ip;
    private Integer port;
    private Integer onlineCounts = 0;
}
