package com.jsjf.interceptor;

public interface MessageConverterHandler<T, K> {

	/**
	 * 用于在httpMessageConverter read(..)方法完成之后调用
	 * <p>
	 * 1.可以对converter映射出的Object进行处理
	 * </p>
	 */
	public Object readAfter( T obj, K type );
}