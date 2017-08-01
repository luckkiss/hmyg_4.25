package com.white.utils;

import java.io.IOException;

/**
 * 压缩方式接口，在写入压缩文件时被调用
 * 
 * @author tian
 * 
 */
public interface ICompress {
	
	byte[] compress(byte[] bytes) throws IOException;
}
