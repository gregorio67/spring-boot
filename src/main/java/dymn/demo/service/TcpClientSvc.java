/**
 * @Project :  스마트톨링정보시스템 구축
 * @Class : TcpInterfaceServiceImpl.java
 * @Description : 
 *
 * @Author : LGCNS
 * @Since : 2017. 4. 20.
 *
 * @Copyright (c) 2018 EX All rights reserved.
 *-------------------------------------------------------------
 *              Modification Information
 *-------------------------------------------------------------
 * 날짜            수정자             변경사유 
 *-------------------------------------------------------------
 * 2018. 4. 27.        LGCNS             최초작성
 *-------------------------------------------------------------
 */

package dymn.demo.service;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dymn.demo.exception.BizException;
import dymn.demo.tcp.BaseBootstrap;
import dymn.demo.tcp.ByteClientHandler;
import dymn.demo.util.JsonUtil;
import dymn.demo.util.PropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

@Service("tcpClientSvi")
public class TcpClientSvc  {

	private static final Logger LOGGER = LoggerFactory.getLogger(TcpClientSvc.class);

	public byte[] sendMessage(String host, int port, byte[] requestPacket, boolean rcvWait) throws Exception {
		/** Check input parameter **/
		if (host == null || requestPacket == null) {
			throw new BizException(" Server Information or send message is null");
		}
		
		long startTime = System.currentTimeMillis();
		
		/** Socket Channel **/
		Channel channel = null;
		final CountDownLatch latch = new CountDownLatch(1);

		/** String Handler **/
		ByteClientHandler clientHandler = null;

		/** Netty Event Loop Group **/
		final EventLoopGroup group = new NioEventLoopGroup();
		try {

			/** Create Client Handler **/
			clientHandler = new ByteClientHandler(requestPacket, rcvWait, latch);

			/** Create Client Bootstrap **/
			Bootstrap bootstrap = BaseBootstrap.byteBootstrap(group, clientHandler);

			int maxRetryCnt = PropertiesUtil.getInt("tcp.max.retry.count");
			
			int rcvTimeout = PropertiesUtil.getInt("tcp.receive.timeout");
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Retry Count :: {}, Receive Timeout :: {}", maxRetryCnt, rcvTimeout);
			}

			/** If connect is failed, retry **/
			for (int retryCnt = 1; retryCnt <= maxRetryCnt; retryCnt++) {
				try {
					channel = bootstrap.connect(host, port).sync().channel();
					break;
				} catch (Exception connectionError) {
					
					if (retryCnt == maxRetryCnt) {
						// if.tcp.client.maxretry
						throw new BizException("Can't connect agent, Please check ageent is running");
					} else {
						LOGGER.error("[Client][ERROR] {}:{} Connection error occured. retry again {}/{}", host, port, retryCnt, maxRetryCnt);
						Thread.sleep(500);
					}
				}
			}
			
			/** If needed to get response, wait for response **/
			if (rcvWait) {
				if (!latch.await(rcvTimeout, TimeUnit.MILLISECONDS)) {
					channel.disconnect().sync();
					throw new TimeoutException("[Client][MSG RCV TIMEOUT] Response Time is over");
				}				
			}
		} 
		catch (Exception ex) {
			throw new BizException(ex.getMessage());
		} 
		finally {
			if (channel != null) {
				try {
					channel.closeFuture().sync();
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("Socket Channel is closed");
					}
				} 
				catch (InterruptedException interruptedEx) {
					LOGGER.error("Interrupted: {}", interruptedEx.getMessage());
				}
			}
			group.shutdownGracefully();
		}
		LOGGER.info("Service is successfully done :: {}, {}", host, port, System.currentTimeMillis() - startTime);
		return clientHandler != null ? clientHandler.getResponse() : null;			
	}


	public Map<String, Object> sendMessage(String host, int port, Map<String, Object> requestPacket, boolean rcvWait) throws Exception {
		
		byte[] respData = sendMessage(host, port, JsonUtil.map2Json(requestPacket).getBytes(), rcvWait);
		return JsonUtil.json2Map(new String(respData));
	}
	
	public String sendMessage(String host, int port, String requestPacket, boolean rcvWait) throws Exception {
		byte[] respData = sendMessage(host, port, requestPacket.getBytes(), rcvWait);
		return new String(respData);
	}

}
